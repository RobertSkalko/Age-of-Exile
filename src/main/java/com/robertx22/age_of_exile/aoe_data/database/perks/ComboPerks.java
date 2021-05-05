package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

public class ComboPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.stat("hp_mana_cost",
            new OptScaleExactStat(-2, Stats.MANA_COST.get(), ModType.FLAT),
            new OptScaleExactStat(4, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.stat("atk_cast_speed",
            new OptScaleExactStat(2, Stats.ATTACK_SPEED.get(), ModType.FLAT),
            new OptScaleExactStat(2, Stats.CAST_SPEED.get(), ModType.FLAT)
        );

        PerkBuilder.stat("hp_res",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, new ElementalResist(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.stat("spell_dmg_flat_mana",
            new OptScaleExactStat(3, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.FLAT)
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

    }
}
