package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.LeechInfo;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GameChangerPerks implements ExileRegistryInit {

    @Override
    public void registerAll() {

        Perk bear = PerkBuilder.gameChanger("bear", "Bear",
            new OptScaleExactStat(10, Stats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(Elements.Nature, ResourceType.health))),
            new OptScaleExactStat(-10, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.melee)),
            new OptScaleExactStat(-20, DatapackStats.MOVE_SPEED),
            new OptScaleExactStat(-100, DodgeRating.getInstance(), ModType.PERCENT)
        );
        bear.one_of_a_kind = "animal";

        Perk wolf = PerkBuilder.gameChanger("wolf", "Wolf",
            new OptScaleExactStat(25, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLEED)),
            new OptScaleExactStat(20, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(-50, Armor.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(-50, ManaRegen.getInstance(), ModType.PERCENT)
        );
        wolf.one_of_a_kind = "animal";

        Perk owl = PerkBuilder.gameChanger("owl", "Owl",
            new OptScaleExactStat(25, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(20, Stats.CAST_SPEED.get()),
            new OptScaleExactStat(25, new ElementalPenetration(Elements.Nature)),
            new OptScaleExactStat(-25, Armor.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(-25, Health.getInstance(), ModType.PERCENT)
        );
        owl.one_of_a_kind = "animal";

        /*
        PerkBuilder.gameChanger("one_with_nature", "One With Nature",
            new OptScaleExactStat(-25, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(-10, Stats.TOTAL_DAMAGE.get()),
            new OptScaleExactStat(-25, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(50, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.heal_over_time)), // todo this one doesnt work
            new OptScaleExactStat(20, Stats.COOLDOWN_REDUCTION.get()),
            new OptScaleExactStat(-25, Stats.MANA_COST.get())
        );
         */

        PerkBuilder.gameChanger("mantra", "Mantra",
            new OptScaleExactStat(3, DatapackStats.PHYS_DMG_PER_MANA),
            new OptScaleExactStat(-10, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(-25, Stats.CRIT_CHANCE.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("celestial_vaults", "Celestial Vaults",
            new OptScaleExactStat(15, new ElementalResist(Elements.Light)),
            new OptScaleExactStat(-15, new ElementalResist(Elements.Dark)),
            new OptScaleExactStat(20, Stats.ELEMENTAL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(-20, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark))
        );

        PerkBuilder.gameChanger("something_is_watching", "Something is Watching",
            new OptScaleExactStat(-15, Stats.TOTAL_DAMAGE.get()),
            new OptScaleExactStat(-15, new ElementalResist(Elements.Elemental)),
            new OptScaleExactStat(1, Stats.EFFECT_ON_SPELL_KILL.get(BeneficialEffects.BLESSING)),
            new OptScaleExactStat(1, Stats.EFFECT_ON_BASIC_ATTACK_KILL.get(BeneficialEffects.MARK))
        );

        PerkBuilder.gameChanger("zealot", "Zealot",
            new OptScaleExactStat(-25, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(25, Stats.CHANCE_TO_GIVE_EFFECT_WHEN_HEALING_ON_SELF.get(BeneficialEffects.ZEAL))
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
            new OptScaleExactStat(25, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song)),
            new OptScaleExactStat(25, Stats.HEAL_CRIT_CHANCE.get()),
            new OptScaleExactStat(20, Stats.COOLDOWN_REDUCTION.get())
        );

        PerkBuilder.gameChanger("blood_mage", "Blood Mage",
            new OptScaleExactStat(1, BloodUser.getInstance(), ModType.FLAT),
            new OptScaleExactStat(50, HealthRestorationToBlood.getInstance(), ModType.FLAT),
            new OptScaleExactStat(50, DatapackStats.BLOOD_PER_10VIT, ModType.FLAT),
            new OptScaleExactStat(-100, Mana.getInstance(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("elemental_purity", "Elemental Purity",
            new OptScaleExactStat(10, Stats.ELEMENTAL_DAMAGE.get(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(-50, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT)
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
            new OptScaleExactStat(-100, Stats.SPELL_CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE),
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
            new OptScaleExactStat(-50, Stats.CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE),
            new OptScaleExactStat(-50, Stats.SPELL_CRIT_DAMAGE.get(), ModType.GLOBAL_INCREASE)
        );

        PerkBuilder.gameChanger("tormentor", "Tormentor",
            new OptScaleExactStat(35, Stats.DOT_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(-10, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
        );

    }

}
