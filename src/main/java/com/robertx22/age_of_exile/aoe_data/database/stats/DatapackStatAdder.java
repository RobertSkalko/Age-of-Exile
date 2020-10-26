package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.OneAppliesToOtherStat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class DatapackStatAdder implements ISlashRegistryInit {

    public static OneAppliesToOtherStat HEAL_TO_SPELL_DMG = new OneAppliesToOtherStat(
        "heal_to_dmg",
        HealPower.getInstance()
            .GUID(),
        SpellDamage.getInstance()
            .GUID(),
        "of Heal Power to Spell Damage",
        "Adds % of your increased healing to your total spell damage.");

    public static OneAppliesToOtherStat HEALTH_TO_BLOOD = new OneAppliesToOtherStat(
        "hp_to_blood",
        Health.getInstance()
            .GUID(),
        Blood.getInstance()
            .GUID(),
        "of Health to Blood",
        "Adds % of health to total blood pool.");

    @Override
    public void registerAll() {
        HEAL_TO_SPELL_DMG.addToSerializables();
        HEALTH_TO_BLOOD.addToSerializables();
    }
}
