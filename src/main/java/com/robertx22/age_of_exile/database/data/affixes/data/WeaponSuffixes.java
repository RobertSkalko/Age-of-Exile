package com.robertx22.age_of_exile.database.data.affixes.data;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class WeaponSuffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

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

        AffixBuilder.Normal("of_absorption")
            .Named("Of Absorption")
            .tier(1, new StatModifier(4, 6, PlusResourceOnKill.MAGIC_SHIELD, ModType.FLAT))
            .tier(2, new StatModifier(3, 4, PlusResourceOnKill.MAGIC_SHIELD, ModType.FLAT))
            .tier(3, new StatModifier(1, 3, PlusResourceOnKill.MAGIC_SHIELD, ModType.FLAT))
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

    }
}
