package com.robertx22.age_of_exile.uncommon.testing;

import com.robertx22.age_of_exile.uncommon.testing.tests.CountTalentTreeAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class CommandTests {

    public static void run(String id, ServerPlayerEntity p) {
        try {
            tests.get(id)
                .run(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, CommandTest> tests = new HashMap<>();

    static {
        reg(new CountTalentTreeAttributes());
    }

    static void reg(CommandTest t) {
        tests.put(t.GUID(), t);
    }
}
