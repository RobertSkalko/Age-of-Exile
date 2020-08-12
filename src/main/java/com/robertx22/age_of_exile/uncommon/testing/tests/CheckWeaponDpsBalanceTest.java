package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckWeaponDpsBalanceTest {

    public static void run() {

        SlashRegistry.UniqueGears()
            .getSerializable()
            .forEach(x -> {

                if (x.getBaseGearType()
                    .isWeapon()) {

                    List<StatModifier> mods = new ArrayList<>();

                    mods.addAll(x.uniqueStats());
                    mods.addAll(x.getBaseGearType()
                        .baseStats());
                    mods.addAll(x.getBaseGearType()
                        .implicitStats());

                    List<ExactStatData> stats = mods.stream()
                        .map(m -> m.ToExactStat(100, 1))
                        .collect(Collectors.toList());

                    float totaldmg = 0;

                    for (Stat stat : new WeaponDamage(Elements.Physical).generateAllPossibleStatVariations()) {
                        StatData data = StatUtils.turnIntoStatData(stat, stats);
                        data.CalcVal();
                        totaldmg += data.getAverageValue();
                    }

                    StatData atkspeeddata = StatUtils.turnIntoStatData(AttackSpeed.getInstance(), stats);
                    atkspeeddata.CalcVal();
                    float atkpersec = x.getBaseGearType()
                        .getAttacksPerSecondCalculated(atkspeeddata);

                    float totaldps = atkpersec * totaldmg;

                    System.out.println(x.locNameForLangFile() + " has DPS: " + totaldps);
                }
            });

    }

}
