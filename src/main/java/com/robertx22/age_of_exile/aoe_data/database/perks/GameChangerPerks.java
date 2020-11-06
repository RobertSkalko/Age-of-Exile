package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.DatapackStatAdder;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.NonCritDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.TotalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealToMagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class GameChangerPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.gameChanger("blood_mage",
            new OptScaleExactStat(1, BloodUser.getInstance(), ModType.FLAT),
            new OptScaleExactStat(50, HealthRestorationToBlood.getInstance(), ModType.FLAT),
            new OptScaleExactStat(50, DatapackStatAdder.HEALTH_TO_BLOOD, ModType.FLAT),
            new OptScaleExactStat(-100, Mana.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("arcane_devotion",
            new OptScaleExactStat(-50, Health.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-50, HealthRegen.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-50, DodgeRating.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(25, MagicShield.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("elemental_purity",
            new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(-50, new ElementalDamageBonus(Elements.Physical), ModType.FLAT)
        );

        PerkBuilder.gameChanger("reckless_blows",
            new OptScaleExactStat(-50, HealthRegen.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(15, new ElementalPenetration(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.gameChanger("overflowing_vitality",
            new OptScaleExactStat(0.5F, DatapackStatAdder.CONVERT_HEALTH_TO_PHYS_DMG, ModType.FLAT),
            new OptScaleExactStat(-10, Health.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("steady_hand",
            new OptScaleExactStat(-100, CriticalDamage.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-100, CriticalHit.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(20, TotalDamage.getInstance(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("true_hit",
            new OptScaleExactStat(25, CriticalDamage.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-25, NonCritDamage.getInstance(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("harmony",
            new OptScaleExactStat(50, HealToMagicShield.getInstance(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("mana_battery",
            new OptScaleExactStat(50, DamageAbsorbedByMana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(-20, Health.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-15, DodgeRating.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-10, MagicShield.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("magical_life",
            new OptScaleExactStat(50, DatapackStatAdder.CONVERT_MAGIC_SHIELD_TO_HEALTH, ModType.FLAT)
        );

        PerkBuilder.gameChanger("familiar_instincts",
            new OptScaleExactStat(25, DatapackStatAdder.DODGE_TO_ELE_DODGE, ModType.FLAT),
            new OptScaleExactStat(-25, Armor.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-25, MagicShield.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("divinity",
            new OptScaleExactStat(25, DatapackStatAdder.HEAL_TO_SPELL_DMG, ModType.FLAT),
            new OptScaleExactStat(-50, CriticalDamage.getInstance(), ModType.GLOBAL_INCREASE)
        );

    }

}
