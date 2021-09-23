package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlock;
import com.robertx22.age_of_exile.dimension.teleporter.TeleporterBlock;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlock;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BlackHoleBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.TotemBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.BlockGearSalvage;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.HashMap;

public class SlashBlocks {

    public static void init() {
        for (SkillItemTier tier : SkillItemTier.values()) {
            FARMING_PLANTS.put(tier, plant("plant" + (tier.tier + 1)));
        }
    }

    public static RegObj<RuneWordStationBlock> RUNEWORD = Def.block("runeword_station", () -> new RuneWordStationBlock());
    public static RegObj<BlockGearSalvage> GEAR_SALVAGE = Def.block("salvage_station", () -> new BlockGearSalvage());
    public static RegObj<TeleporterBlock> TELEPORTER = Def.block("teleporter", () -> new TeleporterBlock());
    public static RegObj<PortalBlock> PORTAL = Def.block("portal", () -> new PortalBlock());

    public static RegObj<BlackHoleBlock> BLACK_HOLE = Def.block("black_hole", () -> new BlackHoleBlock());
    public static RegObj<ModSpawnerBlock> SPAWNER = Def.block("spawner", () -> new ModSpawnerBlock());
    public static RegObj<TotemBlock> BLUE_TOTEM = Def.block("blue_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> GREEN_TOTEM = Def.block("green_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> GUARD_TOTEM = Def.block("guard_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> TRAP = Def.block("trap", () -> new TotemBlock());
    public static RegObj<TotemBlock> GLYPH = Def.block("glyph", () -> new TotemBlock());

    public static HashMap<SkillItemTier, RegObj<Block>> FARMING_PLANTS = new HashMap<>();

    public static RegObj<Block> MANA_PLANT = plant("mana");
    public static RegObj<Block> LIFE_PLANT = plant("life");

    static RegObj<Block> plant(String id) {
        return Def.block(id, () -> new BeetrootBlock(AbstractBlock.Properties.of(Material.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)));
    }

}
