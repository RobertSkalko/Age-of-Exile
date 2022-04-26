package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.CommonSpellBuilders;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.NewSpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.SpellEntityBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.VanillaEffectActionBuilder;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class BardSpells implements ExileRegistryInit {
    @Override
    public void registerAll() {

        CommonSpellBuilders.buffSongSpell(SpellKeys.SONG_OF_VALOR.id, "Hymn of Valor", BeneficialEffects.VALOR);
        CommonSpellBuilders.buffSongSpell(SpellKeys.SONG_OF_PERSEVERANCE.id, "Hymn of Perseverance", BeneficialEffects.PERSEVERANCE);
        CommonSpellBuilders.buffSongSpell(SpellKeys.SONG_OF_VIGOR.id, "Hymn of Vigor", BeneficialEffects.VIGOR);

        SpellBuilder.of(SpellKeys.POWER_CHORD, SpellConfiguration.Builder.staffImbue(30, 20 * 25, 10), "Power Chord",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .manualDesc("Imbue your staff with Word: Power.")
            .onCast(PartBuilder.Sound.play(SoundEvents.SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)))
            .onTick(PartBuilder.Particles.tickAoe(1D, ParticleTypes.NOTE, 2D, 0.15D))
            .onHit(PartBuilder.Damage.of(SpellCalcs.POWER_CHORD)
                .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.CHARM, 6)))
            .onHit(PartBuilder.Particles.aoe(ParticleTypes.ENCHANTED_HIT, 10D, 1D))
            .build();

        SpellBuilder.of(SpellKeys.HEALING_ARIA, SpellConfiguration.Builder.instant(25, 20 * 30), "Healing Aria",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Heal allies around you for")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundRefs.DING))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.NOTE, 50D, 5D, 0.2D))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.HEART, 50D, 5D, 0.2D))
            .onCast(PartBuilder.Restore.Health.aoe(SpellCalcs.HEALING_ARIA, 5D))
            .build();

        SpellBuilder.of(SpellKeys.SHOOTING_STAR, SpellConfiguration.Builder.instant(10, 20)
                    .setSwingArm(), "Shooting Star",
                Arrays.asList(SpellTag.projectile, SpellTag.heal))
            .manualDesc("Shoots a star that heals allies")

            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.BEACON_ACTIVATE, 1D, 1.7D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.NETHER_STAR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)
                .put(MapField.HITS_ALLIES, true)))
            .onTick(PartBuilder.Particles.aoe(ParticleTypes.CRIT, 3D, 0.5D)
                .onTick(1D))
            .onTick(PartBuilder.Particles.aoe(ParticleTypes.ENCHANT, 3D, 0.7D)
                .onTick(1D))
            .onExpire(PartBuilder.Restore.Health.aoe(SpellCalcs.SHOOTING_STAR, 3D))
            .onExpire(PartBuilder.Particles.aoe(ParticleTypes.SOUL_FIRE_FLAME, 30D, 1D))
            .build();

        SpellBuilder.of(SpellKeys.PURIFYING_TOUCH, SpellConfiguration.Builder.staffImbue(15, 20 * 30, 3), "Purifying Touch",
                Arrays.asList(SpellTag.heal))
            .manualDesc("Heal allies around you and remove a negative effect")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.Sound.play(SlashSounds.BUFF.get(), 1D, 1D))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.Restore.Health.aoe(SpellCalcs.PURIFYING_TOUCH, 3D))
            .onCast(PartBuilder.removeNegativeEffectInAoe(3D))
            .build();

        NewSpellBuilder.of(SpellKeys.EXPLOSIVE_NOTE, SpellConfiguration.Builder.instant(20, 20 * 15), "Explosive Note")
            .tags(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell)
            .desc(
                "Summon an explosive note, dealing massive damage in the resulting explosion.")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.BLAZE_SHOOT, 1D, 1D))
            .onCast(PartBuilder.Sound.play(SoundEvents.ENDERMAN_SCREAM, 1D, 0.5D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.PLAYER_HEAD, 1D, 2.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 60D, false)
            ))

            .addEntity(SpellEntityBuilder.defaultId()
                .onTick(PartBuilder.Particles.aoe(ParticleTypes.INSTANT_EFFECT, 100D, 0.5D))

                .onExpire(PartBuilder.Particles.aoe(ParticleTypes.INSTANT_EFFECT, 600D, 4D))
                .onExpire(PartBuilder.Particles.aoe(ParticleTypes.EXPLOSION, 40D, 4D))

                .onExpire(PartBuilder.Sound.play(SoundEvents.GENERIC_EXPLODE, 2D, 1D))

                .onExpire(PartBuilder.Damage.aoe(SpellCalcs.EXPLOSIVE_NOTE, 4D)
                    .addPerEntityHit(
                        PartBuilder.Sound.play(SoundEvents.GENERIC_HURT, 1D, 2D)
                    ))
            )
            .build();

        NewSpellBuilder.of(SpellKeys.NOCTURNE, SpellConfiguration.Builder.instant(10, 45 * 20), "Nocturne")
            .desc("Make enemies around you sleepy, decreasing their speed and attack damage.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .tags(SpellTag.area)
            .onCast(
                PartBuilder.Sound.play(SoundRefs.DING_LOW_PITCH),
                PartBuilder.Sound.play(SoundEvents.GHAST_SCREAM, 1D, 0D),

                PartBuilder.Particles.groundEdge(ParticleTypes.NOTE, 100D, 5D, 0.1D),
                PartBuilder.Particles.ground(ParticleTypes.WITCH, 400D, 4D, 0.2D),
                PartBuilder.Particles.groundEdge(ParticleTypes.POOF, 25D, 1.5D, 0.3D),

                new VanillaEffectActionBuilder(Effects.MOVEMENT_SLOWDOWN).radius(5)
                    .amplifier(3)
                    .seconds(10)
                    .targetEnemies()
                    .build(),
                new VanillaEffectActionBuilder(Effects.WEAKNESS).radius(5)
                    .amplifier(1)
                    .seconds(10)
                    .targetEnemies()
                    .build()
            )

            .build();

    }

}
