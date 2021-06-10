package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class WeaponSuffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("of_steadiness")
            .Named("Of Steadiness")
            .tier(1, new StatModifier(10, 20, Stats.ACCURACY.get(), ModType.FLAT))
            .tier(2, new StatModifier(7, 10, Stats.ACCURACY.get(), ModType.FLAT))
            .tier(3, new StatModifier(5, 7, Stats.ACCURACY.get(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_precision")
            .Named("Of Precision")
            .tier(1, new StatModifier(25F, 50F, Stats.CRIT_CHANCE.get(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(20F, 25F, Stats.CRIT_CHANCE.get(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(10, 20F, Stats.CRIT_CHANCE.get(), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_vampirism")
            .Named("Of Vampirism")
            .tier(1, new StatModifier(3, 5, Stats.LIFESTEAL.get(), ModType.FLAT))
            .tier(2, new StatModifier(2, 3, Stats.LIFESTEAL.get(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2, Stats.LIFESTEAL.get(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_good_aim")
            .Named("Of Good Aim")
            .tier(1, new StatModifier(4, 8, Stats.CRIT_CHANCE.get(), ModType.FLAT))
            .tier(2, new StatModifier(2, 4, Stats.CRIT_CHANCE.get(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2F, Stats.CRIT_CHANCE.get(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();
        AffixBuilder.Normal("of_brutality")
            .Named("Of Brutality")
            .tier(1, new StatModifier(15, 20, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
            .tier(2, new StatModifier(10, 15, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
            .tier(3, new StatModifier(5, 10, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
            .tier(4, new StatModifier(3, 5, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_speed")
            .Named("Of Speed")
            .tier(1, new StatModifier(20, 25, Stats.ATTACK_SPEED.get(), ModType.FLAT))
            .tier(2, new StatModifier(15, 20, Stats.ATTACK_SPEED.get(), ModType.FLAT))
            .tier(3, new StatModifier(10, 15, Stats.ATTACK_SPEED.get(), ModType.FLAT))
            .tier(4, new StatModifier(7, 10, Stats.ATTACK_SPEED.get(), ModType.FLAT))
            .tier(5, new StatModifier(5, 7, Stats.ATTACK_SPEED.get(), ModType.FLAT))
            .includesTags(SlotTag.melee_weapon)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_gluttony")
            .Named("Of Gluttony")
            .tier(1, new StatModifier(4, 6, Stats.RESOURCE_ON_KILL.get(ResourceType.health), ModType.FLAT))
            .tier(2, new StatModifier(3, 4, Stats.RESOURCE_ON_KILL.get(ResourceType.health), ModType.FLAT))
            .tier(3, new StatModifier(1, 3, Stats.RESOURCE_ON_KILL.get(ResourceType.health), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_consumption")
            .Named("Of Consumption")
            .tier(1, new StatModifier(4, 6, Stats.RESOURCE_ON_KILL.get(ResourceType.mana), ModType.FLAT))
            .tier(2, new StatModifier(3, 4, Stats.RESOURCE_ON_KILL.get(ResourceType.mana), ModType.FLAT))
            .tier(3, new StatModifier(1, 3, Stats.RESOURCE_ON_KILL.get(ResourceType.mana), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_fast_cast")
            .Named("Of Faster Casting")
            .tier(1, new StatModifier(15, 20, Stats.CAST_SPEED.get(), ModType.FLAT))
            .tier(2, new StatModifier(12, 15, Stats.CAST_SPEED.get(), ModType.FLAT))
            .tier(3, new StatModifier(7, 12, Stats.CAST_SPEED.get(), ModType.FLAT))
            .includesTags(SlotTag.mage_weapon, SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_less_cd")
            .Named("Of Repetition")
            .tier(1, new StatModifier(12, 15, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT))
            .tier(2, new StatModifier(10, 12, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT))
            .tier(3, new StatModifier(6, 10, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT))
            .includesTags(SlotTag.mage_weapon, SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_spell_dmg")
            .Named("Of Spell Damage")
            .tier(1, new StatModifier(12, 15, SpellDamage.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(10, 12, SpellDamage.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(6, 10, SpellDamage.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.mage_weapon, SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_focus")
            .Named("Of Focus")
            .tier(1, new StatModifier(4, 8, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT))
            .tier(2, new StatModifier(2, 4, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2F, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT))
            .includesTags(SlotTag.mage_weapon)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_devastation")
            .Named("Of Devastation")
            .tier(1, new StatModifier(15, 20, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT))
            .tier(2, new StatModifier(10, 15, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT))
            .tier(3, new StatModifier(5, 10, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT))
            .tier(4, new StatModifier(3, 5, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT))
            .includesTags(SlotTag.mage_weapon)
            .Suffix()
            .Build();

        AffixBuilder.Normal("heal_suff")
            .Named("Of Restoration")
            .tier(1, new StatModifier(15, 20, Stats.HEAL_STRENGTH.get(), ModType.FLAT))
            .tier(2, new StatModifier(10, 15, Stats.HEAL_STRENGTH.get(), ModType.FLAT))
            .tier(3, new StatModifier(5, 10, Stats.HEAL_STRENGTH.get(), ModType.FLAT))
            .includesTags(SlotTag.scepter)
            .Suffix()
            .Build();
    }
}
