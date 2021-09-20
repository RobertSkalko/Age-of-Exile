package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.components.forge.ICommonCap;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WorldDungeonCap implements ICommonCap {

    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "dungeon");
    private static final String LOC = "dungeon_data";

    World world;
    public WorldDungeonsData data = new WorldDungeonsData();

    public WorldDungeonCap(World world) {
        this.world = world;
    }

    @Override
    public void loadFromNBT(CompoundNBT nbt) {
        this.data = LoadSave.Load(WorldDungeonsData.class, new WorldDungeonsData(), nbt, LOC);
        if (data == null) {
            data = new WorldDungeonsData();
        }
    }

    @Override
    public CompoundNBT saveToNBT() {
        CompoundNBT nbt = new CompoundNBT();
        LoadSave.Save(data, nbt, LOC);
        return nbt;
    }

}