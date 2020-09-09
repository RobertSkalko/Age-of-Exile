package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart.Builder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.TestSpells;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryEntry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.saveclasses.spells.IAbility;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.age_of_exile.vanilla_mc.packets.NoManaPacket;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PoisonEffect;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSpell implements ISlashRegistryEntry<BaseSpell>, IAbility {

    public List<EffectChance> onDamageEffects = new ArrayList<>();

    private final ImmutableSpellConfigs immutableConfigs;

    public BaseSpell(ImmutableSpellConfigs immutable) {
        this.immutableConfigs = immutable;
    }

    public final ImmutableSpellConfigs getImmutableConfigs() {
        return immutableConfigs;
    }

    public void modifyPerkForSerialization(Perk perk) {

    }

    public final void onCastingTick(SpellCastContext ctx) {

        int timesToCast = (int) ctx.getConfigFor(this)
            .get(SC.TIMES_TO_CAST)
            .get(ctx.calcData);

        if (timesToCast > 1) {

            int castTimeTicks = (int) ctx.getConfigFor(this)
                .get(SC.CAST_TIME_TICKS)
                .get(ctx.calcData);

            // if i didnt do this then cast time reduction would reduce amount of spell hits.
            int castEveryXTicks = castTimeTicks / timesToCast;

            if (ctx.isLastCastTick) {
                this.cast(ctx);
            } else {
                if (ctx.ticksInUse > 0 && ctx.ticksInUse % castEveryXTicks == 0) {
                    this.cast(ctx);
                }
            }

        } else if (timesToCast < 1) {
            System.out.println("Times to cast spell is: " + timesToCast + " . this seems like a bug.");
        }

    }

    public abstract BaseGearType.PlayStyle getPlayStyle();

    @Override
    public MutableText getLocName() {
        return this.getName()
            .locName();
    }

    public void spawnParticles(SpellCastContext ctx) {

    }

    @Override
    public BaseSpell getSpell() {
        return this;
    }

    public boolean shouldActivateCooldown(PlayerEntity player, PlayerSpellCap.ISpellsCap spells) {
        return true;
    }

    public enum CastingWeapon {
        MAGE_WEAPON(SpellPredicates.REQUIRE_MAGE_WEAPON),
        MELEE_WEAPON(SpellPredicates.REQUIRE_MELEE),
        ANY_WEAPON(SpellPredicates.ANY_WEAPON),
        RANGED(SpellPredicates.REQUIRE_SHOOTABLE);

        public SpellPredicate predicate;

        CastingWeapon(SpellPredicate predicate) {
            this.predicate = predicate;

        }
    }

    @Override
    public Type getAbilityType() {
        return Type.SPELL;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Gears.get(getRarityRank());
    }

    public final boolean goesOnCooldownIfCastCanceled() {
        // override for spells that do oncastingtick
        return immutableConfigs.goesOnCooldownIfCanceled();
    }

    @Override
    public final Identifier getIconLoc() {
        return new Identifier(Ref.MODID, "textures/gui/spells/icons/" + GUID() + ".png");
    }

    public int getCooldownInTicks(SpellCastContext ctx) {
        int ticks = (int) ctx.getConfigFor(this)
            .get(SC.COOLDOWN_TICKS)
            .get(ctx.calcData);

        int seconds = (int) ctx.getConfigFor(this)
            .get(SC.COOLDOWN_SECONDS)
            .get(ctx.calcData);

        return (int) ((seconds * 20F) + ticks);
    }

    public final int getCooldownInSeconds(SpellCastContext ctx) {
        return getCooldownInTicks(ctx) / 20;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SPELL;
    }

    public abstract String GUID();

    public final int getCalculatedManaCost(SpellCastContext ctx) {
        return (int) Mana.getInstance()
            .scale(ctx.getConfigFor(this)
                .get(SC.MANA_COST)
                .get(ctx.calcData), ctx.calcData.level);
    }

    public final int useTimeTicks(SpellCastContext ctx) {
        return (int) ctx.getConfigFor(this)
            .get(SC.CAST_TIME_TICKS)
            .get(ctx.calcData);
    }

    public final float getUseDurationInSeconds(SpellCastContext ctx) {
        return (float) useTimeTicks(ctx) / 20;
    }

    public final ValueCalculationData getCalculation(SpellCastContext ctx) {
        return ctx.getConfigFor(this)
            .getCalc(ctx.calcData);
    }

    public final Elements getElement() {
        return this.immutableConfigs.element();
    }

    public abstract List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx);

    public abstract Words getName();

    public final boolean cast(SpellCastContext ctx) {

        if (true) {

            Spell.Builder.of("thorn_bush", TestSpells.SINGLE_TARGET_PROJ_CONFIG)
                .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
                .onTick("projectile", Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
                .onExpire("projectile", Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.BUBBLE_CORAL, 150D)))
                .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                    .put(MapField.ENTITY_NAME, "projectile")))
                .onTick(Builder.particleOnTick(30D, ParticleTypes.ITEM_SLIME, 60D, 2D))
                .onTick(Builder.particleOnTick(30D, ParticleTypes.WITCH, 15D, 2D))
                .onTick(Builder.playSoundEveryTicks(30D, SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1D, 2D))
                .onTick(Builder.onTickDamageInAoe(30D, ValueCalculationData.base(1), Elements.Nature, 2D))
                .onTick(Builder.addExileEffectToEnemiesInAoe(PoisonEffect.INSTANCE, 2D))
                .build()
                .cast(ctx.caster);

            // TestSpells.HEALING_FLOWER.cast(ctx.caster);

            return true; // REMOVE AFTER TESTING
        }

        boolean bool = immutableConfigs.castType()
            .cast(ctx);

        ctx.castedThisTick = true;

        if (getImmutableConfigs().getSwingsArmOnCast()) {
            ctx.caster.swingHand(Hand.MAIN_HAND, true);
        }

        castExtra(ctx);
        return bool;
    }

    public void castExtra(SpellCastContext ctx) {

    }

    public void spendResources(SpellCastContext ctx) {
        ctx.data.getResources()
            .modify(getManaCostCtx(ctx));
    }

    public ResourcesData.Context getManaCostCtx(SpellCastContext ctx) {

        float cost = 0;

        cost += this.getCalculatedManaCost(ctx);

        return new ResourcesData.Context(
            ctx.data, ctx.caster, ResourcesData.Type.MANA, cost, ResourcesData.Use.SPEND);
    }

    public boolean canCast(SpellCastContext ctx) {

        LivingEntity caster = ctx.caster;

        if (caster instanceof PlayerEntity == false) {
            return true;
        }

        if (!caster.world.isClient) {

            UnitData data = Load.Unit(caster);

            if (data != null) {

                ResourcesData.Context rctx = getManaCostCtx(ctx);

                if (data.getResources()
                    .hasEnough(rctx)) {

                    if (!immutableConfigs.castingWeapon.predicate.predicate.test(caster)) {
                        return false;
                    }

                    return true;
                } else {
                    if (caster instanceof ServerPlayerEntity) {
                        Packets.sendToClient((PlayerEntity) caster, new NoManaPacket());
                    }

                }
            }
        }
        return false;

    }

    public final List<Text> GetTooltipString(TooltipInfo info, CalculatedSpellData skillgem) {

        SpellCastContext ctx = new SpellCastContext(info.player, 0, skillgem);

        List<Text> list = new ArrayList<>();

        TooltipUtils.addEmpty(list);

        if (Screen.hasShiftDown()) {
            list.addAll(GetDescription(info, ctx));
        }

        TooltipUtils.addEmpty(list);

        MutableText mana = new LiteralText(Formatting.BLUE + "Mana Cost: " + getCalculatedManaCost(ctx));
        MutableText cd = new LiteralText(Formatting.YELLOW + "Cooldown: " + getCooldownInSeconds(ctx) + "s");
        MutableText casttime = new LiteralText(Formatting.GREEN + "Cast time: " + getUseDurationInSeconds(ctx) + "s");

        list.add(mana);
        list.add(cd);
        list.add(casttime);

        TooltipUtils.addEmpty(list);

        list.add(immutableConfigs.castingWeapon.predicate.text);

        TooltipUtils.addEmpty(list);
        this.onDamageEffects.forEach(x -> {
            list.add(new SText(x.chance + "% Chance to apply " + x.effect.locNameForLangFile()));
        });

        TooltipUtils.addEmpty(list);

        return list;

    }

}
