package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.speed.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.speed.CastSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.CooldownReduction;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class WeaponSuffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("of_steadiness")
            .Named("Of Steadiness")
            .tier(1, new StatModifier(10, 20, Accuracy.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(7, 10, Accuracy.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(5, 7, Accuracy.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_precision")
            .Named("Of Precision")
            .tier(1, new StatModifier(25F, 50F, CriticalHit.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(20F, 25F, CriticalHit.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(10, 20F, CriticalHit.getInstance(), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_good_aim")
            .Named("Of Good Aim")
            .tier(1, new StatModifier(4, 8, CriticalHit.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(2, 4, CriticalHit.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2F, CriticalHit.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_vampirism")
            .Named("Of Vampirism")
            .tier(1, new StatModifier(3, 5, Lifesteal.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(2, 3, Lifesteal.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2, Lifesteal.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_brutality")
            .Named("Of Brutality")
            .tier(1, new StatModifier(15, 20, CriticalDamage.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(10, 15, CriticalDamage.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(5, 10, CriticalDamage.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(3, 5, CriticalDamage.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_speed")
            .Named("Of Speed")
            .tier(1, new StatModifier(20, 25, AttackSpeed.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(15, 20, AttackSpeed.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(10, 15, AttackSpeed.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(7, 10, AttackSpeed.getInstance(), ModType.FLAT))
            .tier(5, new StatModifier(5, 7, AttackSpeed.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.melee_weapon)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_gluttony")
            .Named("Of Gluttony")
            .tier(1, new StatModifier(4, 6, PlusResourceOnKill.HEALTH, ModType.FLAT))
            .tier(2, new StatModifier(3, 4, PlusResourceOnKill.HEALTH, ModType.FLAT))
            .tier(3, new StatModifier(1, 3, PlusResourceOnKill.HEALTH, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_consumption")
            .Named("Of Consumption")
            .tier(1, new StatModifier(4, 6, PlusResourceOnKill.MANA, ModType.FLAT))
            .tier(2, new StatModifier(3, 4, PlusResourceOnKill.MANA, ModType.FLAT))
            .tier(3, new StatModifier(1, 3, PlusResourceOnKill.MANA, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_fast_cast")
            .Named("Of Faster Casting")
            .tier(1, new StatModifier(15, 20, CastSpeed.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(12, 15, CastSpeed.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(7, 12, CastSpeed.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.wand, SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_less_cd")
            .Named("Of Repetition")
            .tier(1, new StatModifier(12, 15, CooldownReduction.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(10, 12, CooldownReduction.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(6, 10, CooldownReduction.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.wand, SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_spell_dmg")
            .Named("Of Spell Damage")
            .tier(1, new StatModifier(12, 15, SpellDamage.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(10, 12, SpellDamage.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(6, 10, SpellDamage.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.wand, SlotTag.jewelry_family)
            .Suffix()
            .Build();

    }
}
