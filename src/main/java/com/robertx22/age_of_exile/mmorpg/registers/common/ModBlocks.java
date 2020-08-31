package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.spells.blocks.holy_flower.HolyFlowerBlock;
import com.robertx22.age_of_exile.database.data.spells.blocks.magma_flower.MagmaFlowerBlock;
import com.robertx22.age_of_exile.database.data.spells.blocks.thorn_bush.ThornBushBlock;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.BlockGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.BlockGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.BlockGearSalvage;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public BlockGearModify GEAR_MODIFY = of("modify_station", new BlockGearModify());
    public BlockGearSalvage GEAR_SALVAGE = of("salvage_station", new BlockGearSalvage());
    public BlockGearRepair GEAR_REPAIR = of("repair_station", new BlockGearRepair());

    public MagmaFlowerBlock MAGMA_FLOWER = of("magma_flower", new MagmaFlowerBlock());
    public HolyFlowerBlock HOLY_FLOWER = of("holy_flower", new HolyFlowerBlock());
    public ThornBushBlock THORN_BUSH = of("thorn_bush", new ThornBushBlock());

    <T extends Block> T of(String id, T c) {
        Registry.register(Registry.BLOCK, new Identifier(Ref.MODID, id), c);
        return c;
    }

}
