package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class DexSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of("execute", SpellConfiguration.Builder.instant(10, 20 * 60)
                    .setSwingArm(), "Execute",
                Arrays.asList(SpellTag.area, SpellTag.damage, SpellTag.technique))
            .attackStyle(PlayStyle.ranged)
            .manualDesc(
                "Slash enemies in front of you for " + SpellCalcs.EXECUTE.getLocDmgTooltip()
                    + " " + Elements.Physical.getIconNameDmg()
            )
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.WITHER_DEATH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(SpellCalcs.EXECUTE, Elements.Physical, 1D, 2D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SOUL, 5D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 25D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SMOKE, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of("mark_for_death", SpellConfiguration.Builder.instant(5, 60 * 20 * 2)
                    .setScaleManaToPlayer(),
                "Marked for Death",
                Arrays.asList())
            .attackStyle(PlayStyle.ranged)
            .manualDesc("Mark your target for death, making them more vulnerable to damage.")
            .onCast(PartBuilder.giveSelfEffect(SlashPotions.KNOCKBACK_RESISTANCE.get(), 20D * 10))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.MURDER_INSTINCT, 20D * 10))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 2D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)
                .put(MapField.IS_SILENT, true)
            ))
            .onHit(PartBuilder.justAction(SpellAction.POTION.createGive(Effects.GLOWING, 20 * 10D))
                .addActions(SpellAction.POTION.createGive(SlashPotions.KNOCKBACK_RESISTANCE.get(), 20 * 10D))
                .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.MARK_OF_DEATH, 10))
                .addTarget(TargetSelector.TARGET.create()))
            .build();

    }
}
