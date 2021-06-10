package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Storable
public class SpellCastingData {

    @Store
    public int castingTicksLeft = 0;

    @Store
    public int castingTicksDone = 0;

    @Store
    public int lastSpellCastTimeInTicks = 0;

    @Store
    public String spellBeingCast = "";

    @Store
    public Boolean casting = false;

    @Store
    public HashMap<String, AuraData> auras = new HashMap<>();

    @Store
    public ChargeData charges = new ChargeData();

    @Store
    public List<PlayerAction> last_actions = new ArrayList<>();

    static String BLOCK_ID = "block_action";

    public void onAction(PlayerEntity player, PlayerAction action) {

        if (action == PlayerAction.BLOCK) {
            if (Load.Unit(player)
                .getCooldowns()
                .isOnCooldown(BLOCK_ID)) {
                return;
            }
        }

        last_actions.add(action);
        if (last_actions.size() > 10) {
            last_actions.remove(0);
        }

        if (action == PlayerAction.BLOCK) {
            Load.Unit(player)
                .getCooldowns()
                .setOnCooldown(BLOCK_ID, 20);

        }

    }

    public boolean meetActionRequirements(Spell spell) {

        if (spell.config.actions_needed.isEmpty()) {
            return true;
        }

        List<PlayerAction> needed = spell.config.actions_needed;

        if (needed.size() > last_actions.size()) {
            return false;
        }

        int x = last_actions.size() - 1;
        for (int i = needed.size() - 1; i > -1; i--) {
            PlayerAction act = last_actions.get(x);
            PlayerAction act2 = needed.get(i);
            if (act != act2) {
                return false;
            }
            x--;
        }

        return true;
    }

    @Storable
    public static class AuraData {
        @Store
        public boolean active = false;
        @Store
        public int place = 0;
        @Store
        public float mana_reserved = 0;

        public AuraData() {
        }

        public AuraData(boolean active, int place, float mana_reserved) {
            this.active = active;
            this.place = place;
            this.mana_reserved = mana_reserved;
        }
    }

    public void cancelCast(LivingEntity entity) {
        try {
            if (isCasting()) {
                SpellCastContext ctx = new SpellCastContext(entity, 0, getSpellBeingCast());

                Spell spell = getSpellBeingCast();
                if (spell != null) {
                    int cd = ctx.spell.getCooldownTicks(ctx);
                    Load.Unit(entity)
                        .getCooldowns()
                        .setOnCooldown(spell.GUID(), cd);

                }

                spellBeingCast = "";
                castingTicksLeft = 0;
                lastSpellCastTimeInTicks = 0;
                castingTicksDone = 0;
                this.casting = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isCasting() {
        return spellBeingCast != null && casting && Database.Spells()
            .isRegistered(spellBeingCast);
    }

    transient static Spell lastSpell = null;

    public void onTimePass(LivingEntity entity, EntitySpellCap.ISpellsCap spells, int ticks) {

        try {

            if (isCasting()) {
                Spell spell = Database.Spells()
                    .get(spellBeingCast);

                SpellCastContext ctx = new SpellCastContext(entity, castingTicksDone, spell);

                if (spell != null && spells != null && Database.Spells()
                    .isRegistered(spell)) {
                    spell.onCastingTick(ctx);
                }

                if (ctx.spell.config.cast_type != SpellCastType.USE_ITEM) {
                    tryCast(ctx);
                }

                if (entity.world.isClient) {
                    if (spell.config.cast_type == SpellCastType.USE_ITEM) {
                        if (Gear.has(entity.getMainHandStack())) {
                            ClientOnly.pressUseKey();
                        }
                    }
                }

                lastSpell = spell;

                castingTicksLeft--;
                castingTicksDone++;

                if (castingTicksLeft < 0) {
                    if (spell.config.cast_type != SpellCastType.USE_ITEM) {
                        this.spellBeingCast = "";
                    }
                }
            } else {

                lastSpell = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.cancelCast(entity);
            // cancel when error, cus this is called on tick, so it doesn't crash servers when 1 spell fails
        }
    }

    public List<String> getSpellsOnCooldown(LivingEntity en) {
        return Load.Unit(en)
            .getCooldowns()
            .getAllSpellsOnCooldown();
    }

    public void setToCast(Spell spell, LivingEntity entity) {
        SpellCastContext ctx = new SpellCastContext(entity, 0, spell);

        this.spellBeingCast = spell.GUID();
        this.castingTicksLeft = ctx.spell.getCastTimeTicks(ctx);
        this.lastSpellCastTimeInTicks = this.castingTicksLeft;
        this.castingTicksDone = 0;
        this.casting = true;
    }

    public void tryCast(SpellCastContext ctx) {

        if (getSpellBeingCast() != null) {
            if (castingTicksLeft <= 0) {
                Spell spell = getSpellBeingCast();

                int timesToCast = ctx.spell.getConfig().times_to_cast;

                if (timesToCast == 1) {
                    spell.cast(ctx);
                }

                onSpellCast(ctx);
                spellBeingCast = "";

            }
        }

    }

    public Spell getSpellBeingCast() {

        if (!Database.Spells()
            .isRegistered(spellBeingCast)) {
            return null;
        }

        return Database.Spells()
            .get(spellBeingCast);
    }

    public boolean canCast(Spell spell, PlayerEntity player) {

        if (isCasting()) {
            return false;
        }

        if (spell == null) {
            return false;
        }

        if (Load.Unit(player)
            .getCooldowns()
            .isOnCooldown(spell.GUID())) {
            return false;
        }

        if (player.isCreative()) {
            return true;
        }
        if (spell.config.hasActionRequirements()) {
            if (!Load.spells(player)
                .getCastingData()
                .meetActionRequirements(spell)) {
                return false;
            }
        }

        if (spell.config.charges > 0) {
            if (!charges.hasCharge(spell.config.charge_name)) {
                return false;
            }
        }

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        return spell.canCast(ctx);

    }

    public void onSpellCast(SpellCastContext ctx) {

        int cd = ctx.spell.getCooldownTicks(ctx);

        ctx.data.getCooldowns()
            .setOnCooldown(ctx.spell.GUID(), cd);

        if (ctx.spell.config.charges > 0) {
            if (ctx.caster instanceof PlayerEntity) {
                this.charges.spendCharge((PlayerEntity) ctx.caster, ctx.spell.config.charge_name);
            }
        }

        if (ctx.caster instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) ctx.caster;
            if (p.isCreative()) {
                if (cd > 20) {
                    ctx.data.getCooldowns()
                        .setOnCooldown(ctx.spell.GUID(), 20);

                }
            }
        }

        this.casting = false;

        if (ctx.caster instanceof PlayerEntity) {

            Load.Unit(ctx.caster)
                .syncToClient((PlayerEntity) ctx.caster);
        }
    }

}
