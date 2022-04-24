package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;

import java.util.Arrays;

public class NatureSpells implements ExileRegistryInit {

    public static String THORN_ARMOR = "thorn_armor";
    public static String POISON_WEAPONS = "poisoned_weapons";
    public static String NATURE_BALM = "nature_balm";
    public static String ENTANGLE_SEED = "entangling_seed";

    @Override
    public void registerAll() {

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
