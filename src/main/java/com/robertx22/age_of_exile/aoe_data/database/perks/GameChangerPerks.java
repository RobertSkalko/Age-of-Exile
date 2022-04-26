package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GameChangerPerks implements ExileRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.gameChanger("mantra", "Mantra",
            new OptScaleExactStat(3, DatapackStats.PHYS_DMG_PER_MANA),
            new OptScaleExactStat(-10, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(-25, Stats.CRIT_CHANCE.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("sniper", "Sniper",
            new OptScaleExactStat(-50, Stats.STYLE_DAMAGE.get(PlayStyle.melee)),
            new OptScaleExactStat(-25, Stats.COOLDOWN_REDUCTION.get()),
            new OptScaleExactStat(25, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.arrow))
        );

        PerkBuilder.gameChanger("nightcrawler", "Night Crawler",
            new OptScaleExactStat(-100, Stats.STYLE_DAMAGE.get(PlayStyle.magic)),
            new OptScaleExactStat(50, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.movement))
        );

        PerkBuilder.gameChanger("spell_slinger", "Spellslinger",
            new OptScaleExactStat(-50, Stats.TOTAL_DAMAGE.get()),
            new OptScaleExactStat(-50, ManaRegen.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(30, Stats.COOLDOWN_REDUCTION.get()),
            new OptScaleExactStat(30, Stats.PROJECTILE_SPEED.get()),
            new OptScaleExactStat(30, Stats.PROJECTILE_DAMAGE.get())
        );
        PerkBuilder.gameChanger("songbird", "Songbird",
            new OptScaleExactStat(-50, Stats.TOTAL_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(25, Stats.HEAL_CRIT_CHANCE.get()),
            new OptScaleExactStat(20, Stats.COOLDOWN_REDUCTION.get())
        );

        PerkBuilder.gameChanger("blood_mage", "Blood Mage",
            new OptScaleExactStat(1, BloodUser.getInstance(), ModType.FLAT),
            new OptScaleExactStat(50, HealthRestorationToBlood.getInstance(), ModType.FLAT),
            new OptScaleExactStat(50, DatapackStats.BLOOD_PER_10VIT, ModType.FLAT),
            new OptScaleExactStat(-100, Mana.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("refined_taste", "Refined Taste",
            new OptScaleExactStat(50, Stats.INCREASED_LEECH.get(), ModType.FLAT),
            new OptScaleExactStat(-75, HealthRegen.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(-75, ManaRegen.getInstance(), ModType.PERCENT)
        );

        PerkBuilder.gameChanger("overflowing_vitality", "Overflowing Vitality",
            new OptScaleExactStat(0.5F, DatapackStats.CONVERT_HEALTH_TO_PHYS_DMG, ModType.FLAT),
            new OptScaleExactStat(-10, Health.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("steady_hand", "Steady Hand",
            new OptScaleExactStat(-100, Stats.CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-100, Stats.CRIT_CHANCE.get(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(20, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("true_hit", "True Hit",
            new OptScaleExactStat(25, Stats.CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-25, Stats.NON_CRIT_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("harmony", "Harmony",
            new OptScaleExactStat(20, Health.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(20, Mana.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(-10, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("mana_battery", "Mana Battery",
            new OptScaleExactStat(50, DamageAbsorbedByMana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(-20, Health.getInstance(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-15, DodgeRating.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("divinity", "Divinity",
            new OptScaleExactStat(25, DatapackStats.HEAL_TO_SPELL_DMG, ModType.FLAT),
            new OptScaleExactStat(-50, Stats.CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("tormentor", "Tormentor",
            new OptScaleExactStat(35, Stats.DOT_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(-10, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
        );

    }

}
