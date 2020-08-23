package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.MobSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin implements MobSpawnerLogicDuck {

    @Accessor(value = "spawnPotentials")
    @Override
    public abstract List<MobSpawnerEntry> getspawnPotentials();

}
