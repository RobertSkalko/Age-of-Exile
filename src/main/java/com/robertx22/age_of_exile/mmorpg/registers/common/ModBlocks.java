package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.BlockGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.BlockGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.BlockGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station.SocketStationBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public BlockGearModify GEAR_MODIFY = of("modify_station", new BlockGearModify());
    public SocketStationBlock SOCKET_STATION = of("socket_station", new SocketStationBlock());
    public BlockGearSalvage GEAR_SALVAGE = of("salvage_station", new BlockGearSalvage());
    public BlockGearRepair GEAR_REPAIR = of("repair_station", new BlockGearRepair());

    <T extends Block> T of(String id, T c) {
        Registry.register(Registry.BLOCK, new Identifier(Ref.MODID, id), c);
        return c;
    }

}
