package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.ExileEffectActionBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class BardSpells implements ExileRegistryInit {
    @Override
    public void registerAll() {

        buffSongSpell(SpellKeys.SONG_OF_VALOR.id, "Hymn of Valor", BeneficialEffects.VALOR);
        buffSongSpell(SpellKeys.SONG_OF_PERSEVERANCE.id, "Hymn of Perseverance", BeneficialEffects.PERSEVERANCE);
        buffSongSpell(SpellKeys.SONG_OF_VIGOR.id, "Hymn of Vigor", BeneficialEffects.VIGOR);

        SpellBuilder.of(SpellKeys.POWER_CHORD, SpellConfiguration.Builder.staffImbue(30, 20 * 25, 10), "Power Chord",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .manualDesc("Imbue your staff with Word: Power.")
            .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.NOTE, 2D, 0.15D))
            .onHit(PartBuilder.damage(SpellCalcs.POWER_CHORD)
                .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.CHARM, 6)))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 10D, 1D))
            .build();

        SpellBuilder.of(SpellKeys.HEALING_ARIA, SpellConfiguration.Builder.instant(25, 20 * 30), "Healing Aria",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Heal allies around you for")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundRefs.DING))
            .onCast(PartBuilder.groundParticles(ParticleTypes.NOTE, 50D, 5D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
            .onCast(PartBuilder.healInAoe(SpellCalcs.HEALING_ARIA, 5D))
            .build();

    }

    static void buffSongSpell(String id, String name, EffectCtx effect) {

        SpellBuilder.of(id, SpellConfiguration.Builder.instant(10, 20 * 10)
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
