package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.plate;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BasePlateArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Wisdom;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ArmorSet;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.EntityType;

import java.util.Arrays;

public class OakArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.OAK_SET,
            ArmorSet.SlotEnum.HELMET,
            "Great Oak",
            BasePlateArmors.CHESTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(10, 10, Health.getInstance(), ModType.FLAT),
                    new StatModifier(10, 20, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(15, 25, new ElementalResist(Elements.Nature)),
                    new StatModifier(15, 25, new ElementalResist(Elements.Light))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.OAK_SET)
            .mobFilter(EntityType.SPIDER)
            .req(new StatRequirement()
                .setStr(0.5F)
                .setWis(0.25F)
                .setVit(0.8F))
            .build();

    }
}
