package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackContainer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModContainers {

    public ResourceLocation BACKPACK = id("backpack");
    public ContainerType<BackpackContainer> BACKPACK_TYPE = ScreenHandlerRegistry.registerExtended(BACKPACK, BackpackContainer::new);

    public ResourceLocation GEAR_SALVAGE = id("salvage");
    public ResourceLocation GEAR_SOCKET = id("socket");
    public ResourceLocation SCRIBE_BUFF = id("scribe_buff");
    public ResourceLocation COOKING_STATION = id("cooking");
    public ResourceLocation TABLET_STATION = id("tablet");
    public ResourceLocation ALCHEMY_STATION = id("alchemy");
    public ResourceLocation SMITHING_STATION = id("smithing");

    ResourceLocation id(String id) {
        return new ResourceLocation(Ref.MODID, id);
    }

    @SuppressWarnings("deprecation")
    public ModContainers() {
        register(GEAR_SALVAGE);
        register(GEAR_SOCKET);
        register(SCRIBE_BUFF);
        register(COOKING_STATION);
        register(TABLET_STATION);
        register(ALCHEMY_STATION);
        register(SMITHING_STATION);
    }

    @SuppressWarnings("deprecation")
    void register(ResourceLocation ide) {

        ContainerProviderRegistry.INSTANCE.
            registerFactory(ide, (syncId, identifier, player, buf) -> {

                final World world = player.level;
                final BlockPos pos = buf.readBlockPos();
                return world.getBlockState(pos)
                    .getMenuProvider(player.level, pos)
                    .createMenu(syncId, player.inventory, player);
            });

    }
}
