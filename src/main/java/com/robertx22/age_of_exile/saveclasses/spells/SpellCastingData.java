package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.NoManaPacket;
import com.robertx22.library_of_exile.main.Packets;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.protocol.game.STitlePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

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
    public ChargeData charges = new ChargeData();

    @Store
    public String imbued_spell = "";
    @Store
    public int imbued_spell_stacks = 0;

    public void imbueSpell(Spell spell, int amount) {
        this.imbued_spell = spell.GUID();
        this.imbued_spell_stacks = amount;
    }

    public void consumeImbuedSpell() {
        this.imbued_spell_stacks--;
        if (imbued_spell_stacks < 1) {
            imbued_spell = "";
        }
    }

    public boolean tryCastImbuedSpell(LivingEntity en) {
        if (imbued_spell_stacks > 0) {

            Spell spell = ExileDB.Spells()
                .get(imbued_spell);

            if (spell != null) {
                spell.cast(new SpellCastContext(en, 0, spell), false);
                consumeImbuedSpell();
                return true;
            }
        }
        return false;
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
        return spellBeingCast != null && casting && ExileDB.Spells()
            .isRegistered(spellBeingCast);
    }

    transient static Spell lastSpell = null;

    public void onTimePass(LivingEntity entity, EntitySpellCap.ISpellsCap spells, int ticks) {

        try {

            if (isCasting()) {
                Spell spell = ExileDB.Spells()
                    .get(spellBeingCast);

                SpellCastContext ctx = new SpellCastContext(entity, castingTicksDone, spell);

                if (spell != null && spells != null && ExileDB.Spells()
                    .isRegistered(spell)) {
                    spell.onCastingTick(ctx);

                    // slow down player when casting spells
                    Vector3d v = entity.getDeltaMovement();
                    entity.setDeltaMovement(new Vector3d(0.25D * v.x, v.y, 0.25D * v.z));
                    // slow down player when casting spells

                }

                tryCast(ctx);

                lastSpell = spell;

                castingTicksLeft--;
                castingTicksDone++;

                if (castingTicksLeft < 0) {
                    this.spellBeingCast = "";
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
                    spell.cast(ctx, true);
                }

                onSpellCast(ctx);
                spellBeingCast = "";

            }
        }

    }

    public Spell getSpellBeingCast() {

        if (!ExileDB.Spells()
            .isRegistered(spellBeingCast)) {
            return null;
        }

        return ExileDB.Spells()
            .get(spellBeingCast);
    }

    public boolean canCast(Spell spell, PlayerEntity player) {

        if (player.level.isClientSide) {
            return false;
        }

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

        if (spell.GUID()
            .contains("test")) {
            if (!MMORPG.RUN_DEV_TOOLS) {
                return false;
            }
        }

        if (spell.config.charges > 0) {
            if (!charges.hasCharge(spell.config.charge_name)) {
                return false;
            }
        }

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        LivingEntity caster = ctx.caster;

        if (caster instanceof PlayerEntity == false) {
            return true;
        }

        if (((PlayerEntity) caster).isCreative()) {
            return true;
        }

        if (!ModConfig.get().Server.BLACKLIST_SPELLS_IN_DIMENSIONS.isEmpty()) {
            ResourceLocation id = ctx.caster.level.registryAccess()
                .dimensionTypes()
                .getKey(ctx.caster.level.dimensionType());

            if (ModConfig.get().Server.BLACKLIST_SPELLS_IN_DIMENSIONS.stream()
                .anyMatch(x -> x.equals(id.toString()))) {
                return false;
            }
        }

        EntityData data = Load.Unit(caster);

        if (data != null) {

            if (!spell.isAllowedInDimension(caster.level)) {
                if (caster instanceof PlayerEntity) {
                    ((PlayerEntity) caster).displayClientMessage(new StringTextComponent("You feel an entity watching you. [Spell can not be casted in this dimension]"), false);
                }
                return false;
            }

            SpendResourceEvent rctx = spell.getManaCostCtx(ctx);

            if (data.getResources()
                .hasEnough(rctx)) {

                if (!spell.getConfig().castingWeapon.predicate.predicate.test(caster)) {
                    return false;
                }

                GearItemData wep = Gear.Load(ctx.caster.getMainHandItem());

                if (wep == null) {
                    return false;
                }

                if (!wep.canPlayerWear(ctx.data)) {
                    if (ctx.caster instanceof PlayerEntity) {
                        OnScreenMessageUtils.sendMessage((ServerPlayerEntity) ctx.caster, new StringTextComponent("Weapon requirements not met"), STitlePacket.Type.ACTIONBAR);
                    }
                    return false;
                }

                return true;
            } else {
                if (caster instanceof ServerPlayerEntity) {
                    Packets.sendToClient((PlayerEntity) caster, new NoManaPacket());
                }
            }
        }

        return false;

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
