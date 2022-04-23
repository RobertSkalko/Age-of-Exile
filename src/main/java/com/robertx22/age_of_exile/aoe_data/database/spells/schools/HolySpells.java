package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.ExileEffectActionBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.VanillaEffectActionBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class HolySpells implements ExileRegistryInit {

    public static String HEALING_AURA_ID = "healing_aura";
    public static String WISH = "wish";
    public static String UNDYING_WILL = "undying_will";
    public static String INSPIRATION = "awaken_mana";
    public static String SHOOTING_STAR = "shooting_star";

    public static String CHARGE_ID = "charge";
    public static String WHIRLWIND = "whirlwind";
    public static String TAUNT = "taunt";
    public static String SHOUT_WARN = "shout_warn";
    public static String PULL = "pull";

    public static String HYMN_OF_VALOR = "song_of_valor";
    public static String HYMN_OF_PERSERVANCE = "song_of_perseverance";
    public static String HYMN_OF_VIGOR = "song_of_vigor";

    @Override
    public void registerAll() {

        song(HYMN_OF_VALOR, "Hymn of Valor", BeneficialEffects.VALOR);
        song(HYMN_OF_PERSERVANCE, "Hymn of Perseverance", BeneficialEffects.PERSEVERANCE);
        song(HYMN_OF_VIGOR, "Hymn of Vigor", BeneficialEffects.VIGOR);

        SpellBuilder.of(WHIRLWIND, SpellConfiguration.Builder.multiCast(10, 0, 100, 10)
                    .setSwingArm(), "Whirlwind",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies around you for " +
                SpellCalcs.WHIRLWIND.getLocDmgTooltip(Elements.Fire) + ". Scales with attack speed.")

            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)

            .onCast(new VanillaEffectActionBuilder(SlashPotions.KNOCKBACK_RESISTANCE.get()).giveToSelfOnly()
                .seconds(5)
                .build())

            .onCast(PartBuilder.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1D, 1D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 2D, 0.5D))
            .onCast(PartBuilder.damageInAoe(SpellCalcs.WHIRLWIND, 1.5D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 50D, 0.5D, 0.1D))
            )
            .build();

        SpellBuilder.of(CHARGE_ID, SpellConfiguration.Builder.multiCast(10, 20 * 10, 60, 60)
                , "Charge",
                Arrays.asList(SpellTag.area, SpellTag.damage, SpellTag.movement))
            .manualDesc(
                "Charge in a direction, stopping upon first enemy hit to deal "
                    + SpellCalcs.CHARGE.getLocDmgTooltip() + " " + Elements.Physical.getIconNameDmg() + " in radius."

            )
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ANCIENT_DEBRIS_STEP, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 0.2D, ParticleMotion.CasterLook)
                    .put(MapField.IGNORE_Y, true))
                .addTarget(TargetSelector.CASTER.create()))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 20D, 1D, 0.5D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 1D, 0.5D))
            .onCast(PartBuilder.damageInAoe(SpellCalcs.CHARGE, 1.75D)
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.ANVIL_LAND, 1D, 1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 100D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.cancelSpell())
            )
            .build();

        SpellBuilder.of(TAUNT, SpellConfiguration.Builder.instant(0, 20 * 30)
                    .setSwingArm(), "Taunt",
                Arrays.asList(SpellTag.area))
            .manualDesc(
                "Shout, making enemies nearby want to attack you. " +
                    "Generates " + SpellCalcs.TAUNT.getLocDmgTooltip() + " threat."
            )
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.SHIELD_BLOCK, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.TAUNT, AggroAction.Type.AGGRO))
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 20D, 3D))

            .build();

        SpellBuilder.of(SHOUT_WARN, SpellConfiguration.Builder.instant(10, 60 * 20), "Warning Shout",
                Arrays.asList(SpellTag.area, SpellTag.shout, SpellTag.shield))

            .manualDesc(
                "Let out a warning shout, giving a "
                    + SpellCalcs.SHOUT_WARN.getLocDmgTooltip() + " Shield to all nearby allies.")

            .attackStyle(PlayStyle.melee)
            .onCast(PartBuilder.playSound(SoundEvents.WOLF_HOWL, 1D, 1D))
            .onCast(PartBuilder.giveShieldInRadius(10D, SpellCalcs.SHOUT_WARN, 10D))
            .build();

        SpellBuilder.of(PULL, SpellConfiguration.Builder.instant(5, 60 * 20), "Pull",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Pull enemies in area to you, dealing " +
                    SpellCalcs.PULL.getLocDmgTooltip() + " " +
                    Elements.Physical.getIconNameDmg() + " and slowing them."
            )
            .attackStyle(PlayStyle.melee)
            .onCast(PartBuilder.playSound(SoundEvents.ANVIL_HIT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addActions(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 20D * 5))
                .addActions(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PULL))
                .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.STUN.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 2))
                .addTarget(TargetSelector.AOE.create(8D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 100D, 6D, 0.1D))
            .build();

        SpellBuilder.of(INSPIRATION, SpellConfiguration.Builder.instant(0, 300 * 20), "Inspiration",
                Arrays.asList(SpellTag.heal)
            )
            .manualDesc("Restores " + SpellCalcs.AWAKEN_MANA.getLocDmgTooltip() + " mana.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.restoreManaToCaster(SpellCalcs.AWAKEN_MANA))
            .build();

        SpellBuilder.of(SHOOTING_STAR, SpellConfiguration.Builder.instant(10, 20)
                    .setSwingArm(), "Shooting Star",
                Arrays.asList(SpellTag.projectile, SpellTag.heal))
            .manualDesc("Shoots a star that heals allies for " + SpellCalcs.SHOOTING_STAR.getLocDmgTooltip() + " health on hit.")

            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BEACON_ACTIVATE, 1D, 1.7D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.NETHER_STAR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)
                .put(MapField.HITS_ALLIES, true)))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 1D, 0.5D)
                .onTick(1D))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.ENCHANT, 1D, 0.7D)
                .onTick(1D))
            .onExpire(PartBuilder.healInAoe(SpellCalcs.SHOOTING_STAR, 2D))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.SOUL_FIRE_FLAME, 10D, 1D))
            .build();

        SpellBuilder.of(HEALING_AURA_ID, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Healing Aura",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Heal allies around you for " + SpellCalcs.HEALING_AURA.getLocDmgTooltip() +
                    " health")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.healInAoe(SpellCalcs.HEALING_AURA, 2D))
            .build();

        SpellBuilder.of(WISH, SpellConfiguration.Builder.instant(20, 20 * 60), "Wish",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Heal allies around you for " + SpellCalcs.HEALING_AURA.getLocDmgTooltip() +
                    " health")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 5D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
            .onCast(PartBuilder.healInAoe(SpellCalcs.WISH, 5D))
            .build();

    }

    static void song(String id, String name, EffectCtx effect) {

        SpellBuilder.of(id, SpellConfiguration.Builder.nonInstant(10, 20 * 10, 30)
                , name,
                Arrays.asList(SpellTag.area, SpellTag.song))
            .manualDesc(
                "Give a stack of " + effect.locname + " to all allies around you."
            )
            .onCast(PartBuilder.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.NOTE, 50D, 3D))

            .onCast(new ExileEffectActionBuilder(effect).radius(8)
                .seconds(30)
                .build())

            .build();
    }
}
