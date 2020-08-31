package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.gui.screens.spell_hotbar_setup.HotbarSetupContainer;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModContainers {

    public Identifier GEAR_MODIFY = id("modify");
    public Identifier GEAR_REPAIR = id("repair");
    public Identifier GEAR_SALVAGE = id("salvage");
    public Identifier CURRENCY_BAG = id("currency_bag");
    public Identifier HOTBAR_SETUP = id("hotbar_setup");

    Identifier id(String id) {
        return new Identifier(Ref.MODID, id);
    }

    @SuppressWarnings("deprecation")
    public ModContainers() {
        register(GEAR_MODIFY);
        register(GEAR_REPAIR);
        register(GEAR_SALVAGE);
        register(CURRENCY_BAG);

        ContainerProviderRegistry.INSTANCE.registerFactory(HOTBAR_SETUP, (syncId, identifier, player, buf) -> {
            return new HotbarSetupContainer(syncId, player);
        });
    }

    @SuppressWarnings("deprecation")
    void register(Identifier ide) {
        ContainerProviderRegistry.INSTANCE.
            registerFactory(ide, (syncId, identifier, player, buf) -> {
                final World world = player.world;
                final BlockPos pos = buf.readBlockPos();
                return world.getBlockState(pos)
                    .createScreenHandlerFactory(player.world, pos)
                    .createMenu(syncId, player.inventory, player);
            });

    }
}
