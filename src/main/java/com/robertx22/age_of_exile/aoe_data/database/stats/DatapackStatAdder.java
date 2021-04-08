package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.ConvertFromOneToOtherStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MoreXPerYOf;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.OneAppliesToOtherStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DatapackStatAdder implements ISlashRegistryInit {

    public static OneAppliesToOtherStat HEAL_TO_SPELL_DMG = new OneAppliesToOtherStat(
        "heal_to_dmg",
        HealPower.getInstance(),
        SpellDamage.getInstance());

    public static MoreXPerYOf BLOOD_PER_10VIT = new MoreXPerYOf(
        "blood_per_10vit",
        Vitality.INSTANCE,
        Blood.getInstance()
    );

    public static Stat HEALTH_PER_10_INT = new MoreXPerYOf("hp_per_10int", Intelligence.INSTANCE, Health.getInstance());

    public static ConvertFromOneToOtherStat CONVERT_HEALTH_TO_PHYS_DMG = new ConvertFromOneToOtherStat(
        "convert_hp_to_phys_dmg",
        Health.getInstance()
            .GUID(),
        new AttackDamage(Elements.Physical)
            .GUID(),
        "of Health converted to Physical Damage",
        "Converts % of health to your physical damage.");

    @Override
    public void registerAll() {

        HEAL_TO_SPELL_DMG.addToSerializables();
        BLOOD_PER_10VIT.addToSerializables();
        CONVERT_HEALTH_TO_PHYS_DMG.addToSerializables();
        HEALTH_PER_10_INT.addToSerializables();

    }
}
