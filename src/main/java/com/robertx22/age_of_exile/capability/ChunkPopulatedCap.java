package com.robertx22.age_of_exile.capability;

import com.robertx22.age_of_exile.mmorpg.Ref;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class ChunkPopulatedCap implements CopyableComponent<ChunkPopulatedCap> {
    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "popchunk");
    private static final String LOC = "b";

    public boolean populated = false;

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.populated = tag.getBoolean(LOC);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean(LOC, populated);
    }

    @Override
    public void copyFrom(ChunkPopulatedCap other) {
        this.populated = other.populated;
    }
}
