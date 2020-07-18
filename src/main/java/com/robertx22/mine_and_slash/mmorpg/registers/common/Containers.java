package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.item_modify_station.ContainerGearModify;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.repair_station.ContainerGearRepair;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station.ContainerGearSalvage;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag.ContainerCurrencyBag;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.Container;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Containers {

    public Identifier GEAR_MODIFY = of("modify", new ContainerGearModify());
    public Identifier GEAR_REPAIR = of("repair", new ContainerGearRepair());
    public Identifier GEAR_SALVAGE = of("salvage", new ContainerGearSalvage());
    public Identifier CURRENCY_BAG = of("currency_bag", new ContainerCurrencyBag());

    <T extends Container> Identifier of(String id, T c) {

        Identifier ide = new Identifier(Ref.MODID, id);

        ContainerProviderRegistry.INSTANCE.
            registerFactory(ide, (syncId, identifier, player, buf) -> {
                final World world = player.world;
                final BlockPos pos = buf.readBlockPos();
                return world.getBlockState(pos)
                    .createContainerFactory(player.world, pos)
                    .createMenu(syncId, player.inventory, player);
            });

        return ide;
    }

}
