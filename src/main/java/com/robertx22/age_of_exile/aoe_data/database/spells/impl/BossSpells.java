package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.MISC_ITEMS;

public class BossSpells implements ExileRegistryInit {

    public static String FIRE_BOMBS = "boss_fire_bombs";

    @Override
    public void registerAll() {

        SpellBuilder.of(FIRE_BOMBS, SpellConfiguration.Builder.instant(15, 400)
                .setSwingArm(), "Fire Bombs",
            Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weight(0)

            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1D, 1D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WITCH_CELEBRATE, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_FEET.create(MISC_ITEMS.FIREBALL, ENTITIES.SIMPLE_PROJECTILE, 20D * 6D))
                .addTarget(TargetSelector.AOE.create(12D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.FLAME, 200D, 2D))

            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.FLAME, 15D, 0.3D))

            .onExpire(PartBuilder.damageInAoe(ValueCalculation.base("fire_bomb", 15), Elements.Fire, 2D))
            .onExpire(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.FLAME, 200D, 2D))

            .build();

    }
}
