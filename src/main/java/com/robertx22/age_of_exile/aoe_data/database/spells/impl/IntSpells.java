package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class IntSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of("black_hole", SpellConfiguration.Builder.nonInstant(30, 20 * 60, 30)
                    .setSwingArm(), "Black Hole",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.area))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .manualDesc("Summon a dark sphere that attracts nearby enemies to it, dealing damage when it expires.")

            .onCast(PartBuilder.Sound.play(SoundEvents.END_PORTAL_SPAWN, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.BLACK_HOLE.get(), 20D * 5)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.Particles.tickAoe(1D, ParticleTypes.PORTAL, 40D, 1D))
            .onTick("block", PartBuilder.Particles.tickAoe(1D, ParticleTypes.WITCH, 8D, 1D))
            .onTick("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onExpire("block", PartBuilder.Damage.aoe(SpellCalcs.BLACK_HOLE, 2D))
            .build();

        ;

    }
}
