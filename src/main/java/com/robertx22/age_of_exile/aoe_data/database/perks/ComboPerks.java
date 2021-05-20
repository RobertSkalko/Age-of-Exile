package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

public class ComboPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.stat("all_attributes",
            new OptScaleExactStat(1, Wisdom.INSTANCE),
            new OptScaleExactStat(1, Intelligence.INSTANCE),
            new OptScaleExactStat(1, Vitality.INSTANCE),
            new OptScaleExactStat(1, Strength.INSTANCE),
            new OptScaleExactStat(1, Agility.INSTANCE),
            new OptScaleExactStat(1, Dexterity.INSTANCE)
        );

        PerkBuilder.stat("big_all_attributes",
            new OptScaleExactStat(3, Wisdom.INSTANCE),
            new OptScaleExactStat(3, Intelligence.INSTANCE),
            new OptScaleExactStat(3, Vitality.INSTANCE),
            new OptScaleExactStat(3, Strength.INSTANCE),
            new OptScaleExactStat(3, Agility.INSTANCE),
            new OptScaleExactStat(3, Dexterity.INSTANCE)
        );

        PerkBuilder.stat("agi_dex",
            new OptScaleExactStat(1, Agility.INSTANCE),
            new OptScaleExactStat(1, Dexterity.INSTANCE)
        );
        PerkBuilder.stat("accuracy_melee_dmg",
            new OptScaleExactStat(50, Stats.ACCURACY.get()),
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.melee))
        );

        PerkBuilder.stat("armor_health",
            new OptScaleExactStat(2, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("health_armor",
            new OptScaleExactStat(2, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("mana_health",
            new OptScaleExactStat(2, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("health_dodge_int",
            new OptScaleExactStat(1, Intelligence.INSTANCE),
            new OptScaleExactStat(1, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("all_phys_less_atkspeed",
            new OptScaleExactStat(1, Stats.CRIT_DAMAGE.get()),
            new OptScaleExactStat(2, Stats.CRIT_CHANCE.get()),
            new OptScaleExactStat(-3, Stats.ATTACK_SPEED.get())
        );

        PerkBuilder.stat("dodge_mana",
            new OptScaleExactStat(2, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("big_dodge_health",
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("flat_dodge_ranged_damage",
            new OptScaleExactStat(2, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(50, DodgeRating.getInstance())
        );

        PerkBuilder.stat("dagger_and_gauntlet_damage",
            new OptScaleExactStat(2, Stats.ELEMENTAL_WEAPON_DAMAGE.get(WeaponTypes.dagger)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_WEAPON_DAMAGE.get(WeaponTypes.glove))
        );

        PerkBuilder.stat("ele_mace_damage_more_mana",
            new OptScaleExactStat(3, Stats.ELEMENTAL_WEAPON_DAMAGE.get(WeaponTypes.mace)),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("ele_hammer_damage_more_armor",
            new OptScaleExactStat(3, Stats.ELEMENTAL_WEAPON_DAMAGE.get(WeaponTypes.hammer)),
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("hp_mana_cost",
            new OptScaleExactStat(-2, Stats.MANA_COST.get(), ModType.FLAT),
            new OptScaleExactStat(4, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("atk_cast_speed",
            new OptScaleExactStat(2, Stats.ATTACK_SPEED.get(), ModType.FLAT),
            new OptScaleExactStat(2, Stats.CAST_SPEED.get(), ModType.FLAT)
        );

        PerkBuilder.stat("projectile_crit_damage",
            new OptScaleExactStat(1, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(3, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.stat("hp_res",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, new ElementalResist(Elements.Elemental), ModType.FLAT)
        );
        PerkBuilder.stat("mana_cost_mana_regen",
            new OptScaleExactStat(-1, Stats.MANA_COST.get(), ModType.FLAT),
            new OptScaleExactStat(2, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("spell_dmg_flat_mana",
            new OptScaleExactStat(3, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.stat("dodge_ele_resistance",
            new OptScaleExactStat(2, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, new ElementalResist(Elements.Elemental))
        );

        PerkBuilder.stat("health_regen_and_mana_regen",
            new OptScaleExactStat(2, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("mana_regen_increased_healing",
            new OptScaleExactStat(2, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Stats.HEAL_STRENGTH.get(), ModType.FLAT)
        );

        PerkBuilder.stat("mana_and_health",
            new OptScaleExactStat(3, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("mana_and_regen",
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("flat_ms_mana_regen",
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("flat_health_mana",
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.stat("flat_health_phys_dmg",
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(1, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("phys_acc",
            new OptScaleExactStat(3, Stats.ACCURACY.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("flat_mana_reg_melee_dmg",
            new OptScaleExactStat(1, ManaRegen.getInstance(), ModType.FLAT),
            new OptScaleExactStat(2, Stats.STYLE_DAMAGE.get(PlayStyle.melee), ModType.FLAT)
        );

        PerkBuilder.stat("armor_and_ms",
            new OptScaleExactStat(5, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("armor_and_dodge",
            new OptScaleExactStat(5, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("armor_and_health",
            new OptScaleExactStat(5, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(4, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("dodge_and_ms",
            new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("health_mana",
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("health_dodge",
            new OptScaleExactStat(4, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(4, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("dodge_health",
            new OptScaleExactStat(4, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(4, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("dodge_and_hp",
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("crit_proj_dmg",
            new OptScaleExactStat(2, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(1, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.stat("hp_dot_dmg",
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, Stats.DOT_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.stat("fire_water_spell_dmg",
            new OptScaleExactStat(3, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
            new OptScaleExactStat(3, Stats.ELEMENTAL_DAMAGE.get(Elements.Water), ModType.FLAT)
        );
        PerkBuilder.stat("inc_area_spell_dmg",
            new OptScaleExactStat(4, Stats.INCREASED_AREA.get(), ModType.FLAT),
            new OptScaleExactStat(2, SpellDamage.getInstance(), ModType.FLAT)
        );

        PerkBuilder.stat("area_dmg_mana",
            new OptScaleExactStat(4, Stats.AREA_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.stat("spell_dmg_atk_speed",
            new OptScaleExactStat(2, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(2, Stats.ATTACK_SPEED.get(), ModType.FLAT)
        );

        PerkBuilder.stat("dark_light_spell_damage",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light))
        );
        PerkBuilder.stat("water_fire_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire))
        );
        PerkBuilder.stat("nature_dark_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark))
        );

        PerkBuilder.stat("water_dark_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark))
        );

        PerkBuilder.stat("water_light_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light))
        );
        PerkBuilder.stat("nature_light_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light))
        );
        PerkBuilder.stat("nature_fire_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire))
        );
        PerkBuilder.stat("light_fire_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire))
        );
        PerkBuilder.stat("fire_dark_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire))
        );
        PerkBuilder.stat("nature_water_spell_dmg",
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water))
        );

        PerkBuilder.stat("all_light_nature_damage",
            new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Nature))
        );

        PerkBuilder.stat("all_dark_nature_damage",
            new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Nature))
        );
        PerkBuilder.stat("all_dark_all_light",
            new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Light))
        );

    }
}
