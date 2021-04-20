package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.LoadSave;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.chunk.Chunk;

public class DungeonSpawnChunkCap implements CopyableComponent<DungeonSpawnChunkCap> {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "dungeon");
    private static final String LOC = "dungeon_data";
    private static final String PRO = "prog";

    Chunk chunk;

    public DungeonData data = new DungeonData();
    public QuestProgression questProgression = new QuestProgression();

    public DungeonSpawnChunkCap(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void copyFrom(DungeonSpawnChunkCap other) {
        CompoundTag nbt = new CompoundTag();
        other.writeToNbt(nbt);
        this.readFromNbt(nbt);
    }

    @Override
    public void readFromNbt(CompoundTag nbt) {
        this.data = LoadSave.Load(DungeonData.class, new DungeonData(), nbt, LOC);
        this.questProgression = LoadSave.Load(QuestProgression.class, new QuestProgression(), nbt, PRO);
    }

    @Override
    public void writeToNbt(CompoundTag nbt) {
        LoadSave.Save(data, nbt, LOC);
        LoadSave.Save(questProgression, nbt, PRO);
    }
}