package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<PlayerAction> last_actions = new ArrayList<>();

    public void onAction(PlayerAction action) {
        last_actions.add(action);
        if (last_actions.size() > 10) {
            last_actions.remove(0);
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

    @Store
    private HashMap<String, SpellData> spellDatas = new HashMap<>();

    public void cancelCast(PlayerEntity player) {
        try {
            if (isCasting()) {
                SpellCastContext ctx = new SpellCastContext(player, 0, getSpellBeingCast());

                Spell spell = getSpellBeingCast();
                if (spell != null) {
                    SpellData data = spellDatas.getOrDefault(spell.GUID(), new SpellData());

                    int cd = ctx.spell.getCooldownTicks(ctx);
                    data.setCooldown(cd);
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

    public void onTimePass(PlayerEntity player, PlayerSpellCap.ISpellsCap spells, int ticks) {

        try {

            if (isCasting()) {
                Spell spell = Database.Spells()
                    .get(spellBeingCast);

                SpellCastContext ctx = new SpellCastContext(player, castingTicksDone, spell);

                if (spell != null && spells != null && Database.Spells()
                    .isRegistered(spell)) {
                    spell.onCastingTick(ctx);
                }

                if (ctx.spell.config.cast_type != SpellCastType.USE_ITEM) {
                    tryCast(player, spells, ctx);
                }

                if (player.world.isClient) {
                    if (spell.config.cast_type == SpellCastType.USE_ITEM) {
                        ClientOnly.pressUseKey();
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

            spellDatas.values()
                .forEach(x -> x.tickCooldown(ticks));

        } catch (Exception e) {
            e.printStackTrace();
            this.cancelCast(player);
            // cancel when error, cus this is called on tick, so it doesn't crash servers when 1 spell fails
        }
    }

    public List<String> getSpellsOnCooldown() {
        return spellDatas.entrySet()
            .stream()
            .filter(x -> !x.getValue()
                .cooldownIsReady())
            .map(x -> x.getKey())
            .collect(Collectors.toList());

    }

    public void setToCast(Spell spell, PlayerEntity player) {
        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        this.spellBeingCast = spell.GUID();
        this.castingTicksLeft = ctx.spell.getCastTimeTicks(ctx);
        this.lastSpellCastTimeInTicks = this.castingTicksLeft;
        this.castingTicksDone = 0;
        this.casting = true;
    }

    public void tryCast(PlayerEntity player, PlayerSpellCap.ISpellsCap spells, SpellCastContext ctx) {

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

        if (spell.config.isTechnique()) {
            if (!Load.spells(player)
                .getCastingData()
                .meetActionRequirements(spell)) {
                return false;
            }
        }

        SpellData data = getDataBySpell(spell);

        if (data.cooldownIsReady() == false) {
            return false;
        }

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        return spell.canCast(ctx);

    }

    public void onSpellCast(SpellCastContext ctx) {
        SpellData data = spellDatas.getOrDefault(ctx.spell.GUID(), new SpellData());

        int cd = ctx.spell.getCooldownTicks(ctx);

        data.setCooldown(cd);

        if (ctx.caster instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) ctx.caster;
            if (p.isCreative()) {
                if (cd > 20) {
                    data.setCooldown(20);
                }
            }
        }

        ctx.spellsCap
            .getCastingData()
            .onAction(PlayerAction.NOPE);

        spellDatas.put(ctx.spell.GUID(), data);
        this.casting = false;
    }

    public SpellData getDataBySpell(Spell spell) {

        String id = spell.GUID();

        if (spellDatas.containsKey(id) == false) {
            spellDatas.put(id, new SpellData());
        }

        return spellDatas.get(id);

    }

}
