package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class NatureSpells implements ExileRegistryInit {

    public static String THORN_ARMOR = "thorn_armor";
    public static String REFRESH = "refresh";
    public static String POISON_WEAPONS = "poisoned_weapons";
    public static String NATURE_BALM = "nature_balm";
    public static String ENTANGLE_SEED = "entangling_seed";
    public static String POISON_CLOUD = "poison_cloud";

    @Override
    public void registerAll() {

        SpellBuilder.of(POISON_CLOUD, SpellConfiguration.Builder.instant(30, 25 * 20), "Poison Cloud",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Erupt with poisonous gas, dealing " + SpellCalcs.FROST_NOVA.getLocDmgTooltip()
                    + " " + Elements.Earth.getIconNameDmg() + " to nearby enemies and applying Poison.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.PLAYER_SPLASH_HIGH_SPEED, 0.5D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.SNEEZE, 300D, 3.5D, 0.5D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 200D, 3.5D, 0.5D))
            .onCast(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.POISON.resourcePath, 3.5D, 8D * 20))
            .onCast(PartBuilder.damageInAoe(SpellCalcs.POISON_CLOUD, Elements.Earth, 3.5D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D)))
            .build();

        SpellBuilder.of(THORN_ARMOR, SpellConfiguration.Builder.instant(15, 200 * 20), "Thorn Armor",
                Arrays.asList(SpellTag.damage))
            .manualDesc("Gives buff to self:")

            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.THORN_ARMOR.resourcePath, 20 * 45D))
            .build();

        SpellBuilder.of(REFRESH, SpellConfiguration.Builder.nonInstant(40, 20 * 60 * 3, 20)
                , "Refresh",
                Arrays.asList())

            .manualDesc(
                "Refreshes all your spell cooldowns by 1 minute.")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SlashSounds.FREEZE.get(), 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.REFRESH_COOLDOWNS_BY_X_TICKS.create(20 * 60D))
                .addTarget(TargetSelector.CASTER.create()))

            .onCast(PartBuilder.aoeParticles(ParticleTypes.FALLING_WATER, 100D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.DRIPPING_WATER, 50D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 50D, 1.5D))
            .build();

        SpellBuilder.of(POISON_WEAPONS, SpellConfiguration.Builder.instant(15, 160 * 20), "Poison Weapons",
                Arrays.asList(SpellTag.damage))
            .manualDesc("Gives effect to nearby allies.")
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.POISON_WEAPONS.resourcePath, 20 * 30D))
            .build();

        SpellBuilder.of(NATURE_BALM, SpellConfiguration.Builder.nonInstant(15, 60 * 20, 30)
                , "Nature's Balm",
                Arrays.asList(SpellTag.heal))
            .manualDesc("Gives buff to allies nearby:")
            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(3D, BeneficialEffects.REGENERATE.resourcePath, 20 * 15D))
            .build();

        SpellBuilder.of(ENTANGLE_SEED, SpellConfiguration.Builder.instant(15, 60 * 20)
                    .setSwingArm(), "Entangling Seed",
                Arrays.asList(SpellTag.area))
            .manualDesc("Throw out a seed that explodes and petrifies enemies.")

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.BEETROOT_SEEDS, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 40D)))

            .onExpire(PartBuilder.justAction(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.PETRIFY, 5))
                .enemiesInRadius(3D))
            .onExpire(PartBuilder.groundParticles(ParticleTypes.LARGE_SMOKE, 50D, 3D, 0.25D))
            .onExpire(PartBuilder.groundParticles(ParticleTypes.ITEM_SLIME, 100D, 3D, 0.25D))
            .onExpire(PartBuilder.playSound(SlashSounds.STONE_CRACK.get(), 1D, 1D))
            .build();
    }
}
