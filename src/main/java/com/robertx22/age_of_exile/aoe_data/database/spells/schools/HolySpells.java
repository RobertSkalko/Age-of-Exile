package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.*;

public class HolySpells implements ExileRegistryInit {
    public static String HEALING_AURA_ID = "healing_aura";

    @Override
    public void registerAll() {

        SpellBuilder.of("undying_will", SpellConfiguration.Builder.instant(7, 20 * 60)
                    .setScaleManaToPlayer(), "Undying Will",
                Arrays.asList())
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.UNDYING_WILL, 20D * 10))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 50D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.ENCHANT, 50D, 1D))
            .build();

        SpellBuilder.of("banish", SpellConfiguration.Builder.instant(10, 20 * 45)
                .setScaleManaToPlayer(), "Banish", Arrays.asList())
            .manualDesc(
                "Summon a Magic circle that banishes enemies in the area, levitating them for a certain duration.")

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.GLYPH, 20D * 5)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.SET, 0.1D, ParticleMotion.Upwards))
                .onTick(1D)
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))

            .onTick("block", PartBuilder.playSound(SoundEvents.BLOCK_SOUL_SOIL_HIT, 0.5D, 1D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(40D)))

            .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.SOUL_FIRE_FLAME, 15D, 3D, 0.5D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(3D)))
            .build();

        SpellBuilder.of("awaken_mana", SpellConfiguration.Builder.instant(0, 300 * 20), "Awaken Mana",
                Arrays.asList(SpellTag.heal)
            )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.restoreManaToCaster(SpellCalcs.AWAKEN_MANA))

            .build();
        SpellBuilder.of("shooting_star", SpellConfiguration.Builder.instant(10, 20)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Shooting Star",
                Arrays.asList(SpellTag.projectile, SpellTag.heal))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, 1D, 1.7D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.NETHER_STAR, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)
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
                "Heal allies around you for " + SpellCalcs.HEALING_AURA.getLocSpellTooltip() +
                    " health")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.BUFF, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.healInAoe(SpellCalcs.HEALING_AURA, 2D))
            .build();

        SpellBuilder.of("wish", SpellConfiguration.Builder.instant(20, 20 * 60), "Wish",
                Arrays.asList(SpellTag.heal))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.BUFF, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 5D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
            .onCast(PartBuilder.healInAoe(SpellCalcs.WISH, 5D))
            .build();

    }
}
