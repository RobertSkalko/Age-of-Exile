package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.ConvertFromOneToOtherStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.OneAppliesToOtherStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

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

    public static OneAppliesToOtherStat MANA_TO_MAGIC_SHIELD = new OneAppliesToOtherStat(
        "mana_to_ms",
        Mana.getInstance()
            .GUID(),
        MagicShield.getInstance()
            .GUID(),
        "of Mana to Magic Shield",
        "Magic shield is increased by a % of your maximum mana.");

    public static ConvertFromOneToOtherStat CONVERT_HEALTH_TO_PHYS_DMG = new ConvertFromOneToOtherStat(
        "convert_hp_to_phys_dmg",
        Health.getInstance()
            .GUID(),
        new AttackDamage(Elements.Physical)
            .GUID(),
        "of Health converted to Physical Damage",
        "Converts % of health to your physical damage.");

    public static ConvertFromOneToOtherStat CONVERT_MAGIC_SHIELD_TO_HEALTH = new ConvertFromOneToOtherStat(
        "convert_ms_to_hp",
        MagicShield.getInstance()
            .GUID(),
        Health.getInstance()
            .GUID(),
        "of Magic Shield converted to Health",
        "Converts % of magic shield to health.");

    @Override
    public void registerAll() {

        HEAL_TO_SPELL_DMG.addToSerializables();
        HEALTH_TO_BLOOD.addToSerializables();
        CONVERT_HEALTH_TO_PHYS_DMG.addToSerializables();
        CONVERT_MAGIC_SHIELD_TO_HEALTH.addToSerializables();
        MANA_TO_MAGIC_SHIELD.addToSerializables();
    }
}
