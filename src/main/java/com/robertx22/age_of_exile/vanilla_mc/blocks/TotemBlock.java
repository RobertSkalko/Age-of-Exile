package com.robertx22.age_of_exile.vanilla_mc.blocks;

import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.OpaqueBlock;
import net.minecraft.block.Material;

public class TotemBlock extends OpaqueBlock {

    public TotemBlock() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2));
    }
}
