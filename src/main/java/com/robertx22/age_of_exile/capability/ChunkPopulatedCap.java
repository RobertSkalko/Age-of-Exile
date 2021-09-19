package com.robertx22.age_of_exile.capability;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.components.forge.ICommonCap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class ChunkPopulatedCap implements ICommonCap {
    public static final ResourceLocation RESOURCE = new ResourceLocation(Ref.MODID, "popchunk");
    private static final String LOC = "b";

    public boolean populated = false;

    @Override
    public void loadFromNBT(CompoundNBT tag) {
        this.populated = tag.getBoolean(LOC);
    }

    @Override
    public CompoundNBT saveToNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putBoolean(LOC, populated);
        return tag;
    }

}
