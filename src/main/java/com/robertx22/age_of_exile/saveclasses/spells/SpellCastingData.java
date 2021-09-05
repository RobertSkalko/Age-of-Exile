package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.NoManaPacket;
import com.robertx22.library_of_exile.main.Packets;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

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

        if (!ExileDB.Spells()
            .isRegistered(spellBeingCast)) {
            return null;
        }

        return ExileDB.Spells()
            .get(spellBeingCast);
    }

    public boolean canCast(Spell spell, PlayerEntity player) {

        if (player.world.isClient) {
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
            Identifier id = ctx.caster.world.getRegistryManager()
                .getDimensionTypes()
                .getId(ctx.caster.world.getDimension());

            if (ModConfig.get().Server.BLACKLIST_SPELLS_IN_DIMENSIONS.stream()
                .anyMatch(x -> x.equals(id.toString()))) {
                return false;
            }
        }

        if (spell.isAura()) {
            if (!ctx.spellsCap.getCastingData().auras.getOrDefault(spell.GUID(), new SpellCastingData.AuraData()).active) { // if not active
                if (ctx.spellsCap.getManaReservedByAuras() + spell.aura_data.mana_reserved > 1) {
                    return false; // todo make affected by mana reserve reduction
                }
            }
        }

        EntityCap.UnitData data = Load.Unit(caster);

        if (data != null) {

            if (!spell.isAllowedInDimension(caster.world)) {
                if (caster instanceof PlayerEntity) {
                    ((PlayerEntity) caster).sendMessage(new LiteralText("You feel an entity watching you. [Spell can not be casted in this dimension]"), false);
                }
                return false;
            }

            SpendResourceEvent rctx = spell.getManaCostCtx(ctx);

            if (data.getResources()
                .hasEnough(rctx)) {

                if (!spell.getConfig().castingWeapon.predicate.predicate.test(caster)) {
                    return false;
                }

                GearItemData wep = Gear.Load(ctx.caster.getMainHandStack());

                if (wep == null) {
                    return false;
                }

                if (!wep.canPlayerWear(ctx.data)) {
                    if (ctx.caster instanceof PlayerEntity) {
                        OnScreenMessageUtils.sendMessage((ServerPlayerEntity) ctx.caster, new LiteralText("Weapon requirements not met"), TitleS2CPacket.Action.ACTIONBAR);
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
