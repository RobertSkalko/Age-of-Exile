package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.gui.screens.spell_hotbar_setup.HotbarSetupContainer;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Containers {

    public Identifier GEAR_MODIFY = of("modify");
    public Identifier GEAR_REPAIR = of("repair");
    public Identifier GEAR_SALVAGE = of("salvage");
    public Identifier CURRENCY_BAG = of("currency_bag");
    public Identifier HOTBAR_SETUP = of("hotbar_setup");

    Identifier of(String id) {
        return new Identifier(Ref.MODID, id);
    }

    @SuppressWarnings("deprecation")
    public Containers() {
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
