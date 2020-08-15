package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public class AddMobSpawns {
    public static SpawnSettings.SpawnEntry ARCANE_SLIME;

    public static void my$onConstruct(CallbackInfoReturnable<List<SpawnSettings.SpawnEntry>> ci) {

        if (ARCANE_SLIME == null) {
            ARCANE_SLIME = new SpawnSettings.SpawnEntry(ModRegistry.ENTITIES.ARCANE_SLIME, 500, 3, 3);
        }

        List<SpawnSettings.SpawnEntry> list = new ArrayList<>(ci.getReturnValue());
        list.add(ARCANE_SLIME);

        ci.setReturnValue(list);

    }
}
