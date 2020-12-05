package com.robertx22.age_of_exile.uncommon.testing;

import com.robertx22.age_of_exile.uncommon.testing.tests.CountTalentTreeAttributes;
import com.robertx22.age_of_exile.uncommon.testing.tests.FindUnusedPerksTest;
import com.robertx22.age_of_exile.uncommon.testing.tests.PlayerLevelTest;
import com.robertx22.age_of_exile.uncommon.testing.tests.SkillLevelTest;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class CommandTests {

    public static void run(String id, ServerPlayerEntity p) {
        try {

            System.out.print("\n");

            System.out.print("Starting test: " + "id" + "\n");

            tests.get(id)
                .run(p);

            System.out.print("Test ended: " + id + "\n");

            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, CommandTest> tests = new HashMap<>();

    static {
        reg(new CountTalentTreeAttributes());
        reg(new SkillLevelTest());
        reg(new PlayerLevelTest());
        reg(new FindUnusedPerksTest());
    }

    static void reg(CommandTest t) {
        tests.put(t.GUID(), t);
    }
}
