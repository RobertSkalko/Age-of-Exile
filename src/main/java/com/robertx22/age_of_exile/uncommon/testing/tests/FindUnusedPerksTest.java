package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.testing.CommandTest;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class FindUnusedPerksTest extends CommandTest {

    @Override
    public void run(ServerPlayerEntity player) {

        TalentTree tree = ExileDB.TalentTrees()
            .get("talents");

        List<Perk> perks = ExileDB.Perks()
            .getList();

        perks.forEach(x -> {
            if (x.type == Perk.PerkType.SPECIAL) {
                if (tree.calcData.perks.values()
                    .stream()
                    .noneMatch(e -> e.equals(x.GUID()))) {
                    System.out.print(x.GUID() + " perk isn't used anywhere in the tree." + "\n");
                }
            }
        });
    }

    @Override
    public String GUID() {
        return "find_unused_talents";
    }
}
