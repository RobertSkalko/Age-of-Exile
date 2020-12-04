package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.testing.CommandTest;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class CountTalentTreeAttributes extends CommandTest {

    @Override
    public void run(ServerPlayerEntity player) {

        SpellSchool tree = Database.SpellSchools()
            .get("talents");

        List<OptScaleExactStat> stats = new ArrayList<>();

        int totalperks = tree.calcData.perks.size();

        int notables = 0;
        int gamechangers = 0;

        Set<Perk> diffPerks = new HashSet<>();

        for (String x1 : tree.calcData.perks.values()) {
            Perk perk = Database.Perks()
                .get(x1);

            if (perk != null) {
                perk.stats.forEach(s -> {
                    if (s.first > 0) {
                        stats.add(s);
                    }
                });

                if (perk.type == Perk.PerkType.SPECIAL) {
                    notables++;
                }
                if (perk.type == Perk.PerkType.MAJOR) {
                    gamechangers++;
                }

                diffPerks.add(perk);
            }

        }
        int differentPerks = diffPerks.size();

        OptScaleExactStat.combine(stats);
        stats.sort(Comparator.comparingInt(x -> (int) -x.first));

        System.out.print("" + "\n");

        System.out.print("Talent tree statistics:" + "\n");

        System.out.print("Total perks: " + totalperks + "\n");
        System.out.print("Different perks: " + differentPerks + "\n");
        System.out.print("Total notable perks: " + notables + "\n");
        System.out.print("Total game changer perks: " + gamechangers + "\n");

        System.out.print("" + "\n");

        System.out.print("Total stats: " + "\n");

        for (OptScaleExactStat x : stats) {
            System.out.print(x.getDebugString() + "\n");

        }
    }

    @Override
    public String GUID() {
        return "talent_tree_info";
    }
}
