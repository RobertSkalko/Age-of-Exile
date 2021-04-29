package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.LoadSave;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WorldDungeonCap implements CopyableComponent<WorldDungeonCap> {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "dungeon");
    private static final String LOC = "dungeon_data";

    World world;
    public WorldDungeonsData data = new WorldDungeonsData();

    public WorldDungeonCap(World world) {
        this.world = world;
    }

    @Override
    public void copyFrom(WorldDungeonCap other) {
        CompoundTag nbt = new CompoundTag();
        other.writeToNbt(nbt);
        this.readFromNbt(nbt);
    }

    @Override
    public void readFromNbt(CompoundTag nbt) {
        this.data = LoadSave.Load(WorldDungeonsData.class, new WorldDungeonsData(), nbt, LOC);

        if (data == null) {
            data = new WorldDungeonsData();
        }
    }

    @Override
    public void writeToNbt(CompoundTag nbt) {
        LoadSave.Save(data, nbt, LOC);
    }
}