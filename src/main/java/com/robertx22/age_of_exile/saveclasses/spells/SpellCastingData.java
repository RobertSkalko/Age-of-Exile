package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.spells.components.ImbueType;
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
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

@Storable
public class SpellCastingData {

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

    public boolean tryCastImbuedSpell(LivingEntity en, ImbueType type) {
        if (imbued_spell_stacks > 0) {
            Spell spell = ExileDB.Spells()
                .get(imbued_spell);

            if (spell != null) {
                if (spell.config.imbue_type == type) {
                    spell.cast(new SpellCastContext(en, spell), false);
                    consumeImbuedSpell();
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getSpellsOnCooldown(LivingEntity en) {
        return Load.Unit(en)
            .getCooldowns()
            .getAllSpellsOnCooldown();
    }

    public void tryCast(SpellCastContext ctx) {
        if (ctx.spell != null) {
            Spell spell = ctx.spell;
            spell.cast(ctx, true);
            onSpellCast(ctx);
        }
    }

    public boolean canCast(Spell spell, PlayerEntity player) {

        if (player.level.isClientSide) {
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

        SpellCastContext ctx = new SpellCastContext(player, spell);

        LivingEntity caster = ctx.caster;

        if (caster instanceof PlayerEntity == false) {
            return true;
        }

        if (((PlayerEntity) caster).isCreative()) {
            return true;
        }

        if (!ServerContainer.get().BLACKLIST_SPELLS_IN_DIMENSIONS.isEmpty()) {
            ResourceLocation id = ctx.caster.level.registryAccess()
                .dimensionTypes()
                .getKey(ctx.caster.level.dimensionType());

            if (ServerContainer.get().BLACKLIST_SPELLS_IN_DIMENSIONS.stream()
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

        if (ctx.caster instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) ctx.caster;
            if (p.isCreative()) {
                if (cd > 20) {
                    ctx.data.getCooldowns()
                        .setOnCooldown(ctx.spell.GUID(), 20);

                }
            }
        }

        if (ctx.caster instanceof ServerPlayerEntity) {
            Load.Unit(ctx.caster)
                .syncToClient((PlayerEntity) ctx.caster);
        }
    }

}
