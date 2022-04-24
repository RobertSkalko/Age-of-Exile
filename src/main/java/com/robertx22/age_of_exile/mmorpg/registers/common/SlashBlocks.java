package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlock;
import com.robertx22.age_of_exile.dimension.teleporter.TeleporterBlock;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlock;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BlackHoleBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.MNSCraftingTableBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.TotemBlock;

public class SlashBlocks {

    public static void init() {

    }

    public static RegObj<TeleporterBlock> TELEPORTER = Def.block("teleporter", () -> new TeleporterBlock());
    public static RegObj<MNSCraftingTableBlock> MNS_CRAFTING_TABLE = Def.block("mns_crafting_table", () -> new MNSCraftingTableBlock());

    public static RegObj<PortalBlock> PORTAL = Def.block("portal", () -> new PortalBlock());

    public static RegObj<BlackHoleBlock> BLACK_HOLE = Def.block("black_hole", () -> new BlackHoleBlock());
    public static RegObj<ModSpawnerBlock> SPAWNER = Def.block("spawner", () -> new ModSpawnerBlock());
    public static RegObj<TotemBlock> BLUE_TOTEM = Def.block("blue_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> GREEN_TOTEM = Def.block("green_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> GUARD_TOTEM = Def.block("guard_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> TRAP = Def.block("trap", () -> new TotemBlock());
    public static RegObj<TotemBlock> GLYPH = Def.block("glyph", () -> new TotemBlock());

}
