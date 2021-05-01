package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.DataStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStatAdder;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DotDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.TotalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.IncreasedLeech;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class GameChangerPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.gameChanger("blood_mage", "Blood Mage",
            new OptScaleExactStat(1, BloodUser.getInstance(), ModType.FLAT),
            new OptScaleExactStat(50, HealthRestorationToBlood.getInstance(), ModType.FLAT),
            new OptScaleExactStat(50, DatapackStatAdder.BLOOD_PER_10VIT, ModType.FLAT),
            new OptScaleExactStat(-100, Mana.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("elemental_purity", "Elemental Purity",
            new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(-50, new ElementalDamageBonus(Elements.Physical), ModType.FLAT)
        );

        PerkBuilder.gameChanger("refined_taste", "Refined Taste",
            new OptScaleExactStat(50, IncreasedLeech.getInstance(), ModType.FLAT),
            new OptScaleExactStat(-75, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-75, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.gameChanger("overflowing_vitality", "Overflowing Vitality",
            new OptScaleExactStat(0.5F, DatapackStatAdder.CONVERT_HEALTH_TO_PHYS_DMG, ModType.FLAT),
            new OptScaleExactStat(-10, Health.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("steady_hand", "Steady Hand",
            new OptScaleExactStat(-100, DataStats.CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-100, DataStats.SPELL_CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(20, TotalDamage.getInstance(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("true_hit", "True Hit",
            new OptScaleExactStat(25, DataStats.CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-25, DataStats.NON_CRIT_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("harmony", "Harmony",
            new OptScaleExactStat(20, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(20, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-10, TotalDamage.getInstance(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("mana_battery", "Mana Battery",
            new OptScaleExactStat(50, DamageAbsorbedByMana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(-20, Health.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-15, DodgeRating.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("divinity", "Divinity",
            new OptScaleExactStat(25, DatapackStatAdder.HEAL_TO_SPELL_DMG, ModType.FLAT),
            new OptScaleExactStat(-50, DataStats.CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-50, DataStats.SPELL_CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("tormentor", "Tormentor",
            new OptScaleExactStat(35, DotDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(-10, TotalDamage.getInstance(), ModType.FLAT)
        );

    }

}
