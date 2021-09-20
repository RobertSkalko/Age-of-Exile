package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.components.forge.BaseProvider;
import com.robertx22.library_of_exile.components.forge.BaseStorage;
import com.robertx22.library_of_exile.components.forge.ICommonCap;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldDungeonCap implements ICommonCap {

    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "dungeon");

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<World> event) {
            event.addCapability(RESOURCE, new Provider(event.getObject()));
        }
    }

    public static class Storage implements BaseStorage<WorldDungeonCap> {

    }

    public static class Provider extends BaseProvider<WorldDungeonCap, World> {
        public Provider(World owner) {
            super(owner);
        }

        @Override
        public WorldDungeonCap newDefaultImpl(World owner) {
            return new WorldDungeonCap(owner);
        }

        @Override
        public Capability<WorldDungeonCap> dataInstance() {
            return Data;
        }
    }

    @CapabilityInject(WorldDungeonCap.class)
    public static final Capability<WorldDungeonCap> Data = null;

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