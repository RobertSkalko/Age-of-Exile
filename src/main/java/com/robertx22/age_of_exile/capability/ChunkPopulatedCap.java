package com.robertx22.age_of_exile.capability;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.components.forge.BaseProvider;
import com.robertx22.library_of_exile.components.forge.BaseStorage;
import com.robertx22.library_of_exile.components.forge.ICommonCap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ChunkPopulatedCap implements ICommonCap {
    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "popchunk");

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<Chunk> event) {
            event.addCapability(RESOURCE, new Provider());
        }
    }

    public static class Storage implements BaseStorage<ChunkPopulatedCap> {

    }

    public static class Provider extends BaseProvider<ChunkPopulatedCap, Integer> {
        public Provider() {
            super(0);
        }

        @Override
        public ChunkPopulatedCap newDefaultImpl(Integer owner) {
            return new ChunkPopulatedCap();
        }

        @Override
        public Capability<ChunkPopulatedCap> dataInstance() {
            return Data;
        }
    }

    @CapabilityInject(ChunkPopulatedCap.class)
    public static final Capability<ChunkPopulatedCap> Data = null;

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
