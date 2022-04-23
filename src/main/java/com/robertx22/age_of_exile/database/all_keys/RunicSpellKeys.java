package com.robertx22.age_of_exile.database.all_keys;

import com.robertx22.age_of_exile.database.all_keys.base.RunewordKey;
import net.minecraft.item.Items;

import java.util.Arrays;

public interface RunicSpellKeys {

    RunewordKey TEST_RUNIC_SPELL = new RunewordKey("test_runic_spell", () -> Arrays.asList(Items.DIAMOND, Items.GOLDEN_APPLE));

    public static void init() {

    }
}
