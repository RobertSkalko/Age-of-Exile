package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station.ScribeBuffBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.BlockGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.BlockGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.BlockGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station.SocketStationBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BeetrootsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class ModBlocks {

    public BlockGearModify GEAR_MODIFY = of("modify_station", new BlockGearModify());
    public SocketStationBlock SOCKET_STATION = of("socket_station", new SocketStationBlock());
    public BlockGearSalvage GEAR_SALVAGE = of("salvage_station", new BlockGearSalvage());
    public BlockGearRepair GEAR_REPAIR = of("repair_station", new BlockGearRepair());
    public ScribeBuffBlock SCRIBE_BUFF = of("scribe_buff", new ScribeBuffBlock());
    public CookingBlock COOKING_STATION = of("cooking_station", new CookingBlock());

    public HashMap<SkillItemTier, Block> FARMING_PLANTS = new HashMap<>();

    public ModBlocks() {
        for (SkillItemTier tier : SkillItemTier.values()) {
            FARMING_PLANTS.put(tier, plant("plant" + (tier.tier + 1)));
        }
    }

    Block plant(String id) {
        return of(id, new BeetrootsBlock(AbstractBlock.Settings.of(Material.PLANT)
            .noCollision()
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.CROP)));
    }

    <T extends Block> T of(String id, T c) {
        Registry.register(Registry.BLOCK, new Identifier(Ref.MODID, id), c);
        return c;
    }

}
