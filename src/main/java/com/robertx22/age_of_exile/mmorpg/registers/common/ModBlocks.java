package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlock;
import com.robertx22.age_of_exile.dimension.teleporter.TeleporterBlock;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlock;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BlackHoleBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.TotemBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy.AlchemyBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station.ScribeBuffBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.BlockGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.smithing.SmithingBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.tablet.TabletStationBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.SoundType;

import java.util.HashMap;

public class ModBlocks {

    public RuneWordStationBlock RUNEWORD_STATION = of("socket_station", new RuneWordStationBlock());
    public BlockGearSalvage GEAR_SALVAGE = of("salvage_station", new BlockGearSalvage());

    public ScribeBuffBlock SCRIBE_BUFF = of("scribe_buff", new ScribeBuffBlock());
    public CookingBlock COOKING_STATION = of("cooking_station", new CookingBlock());
    public TabletStationBlock TABLET_STATION = of("tablet_station", new TabletStationBlock());
    public AlchemyBlock ALCHEMY_STATION = of("alchemy_station", new AlchemyBlock());
    public SmithingBlock SMITHING_STATION = of("smithing_station", new SmithingBlock());

    public TeleporterBlock TELEPORTER = of("teleporter", new TeleporterBlock());
    public PortalBlock PORTAL = of("portal", new PortalBlock());
    public BlackHoleBlock BLACK_HOLE = of("black_hole", new BlackHoleBlock());
    public ModSpawnerBlock SPAWNER = of("spawner", new ModSpawnerBlock());

    public TotemBlock BLUE_TOTEM = of("blue_totem", new TotemBlock());
    public TotemBlock GREEN_TOTEM = of("green_totem", new TotemBlock());
    public TotemBlock GUARD_TOTEM = of("guard_totem", new TotemBlock());
    public TotemBlock TRAP = of("trap", new TotemBlock());

    public TotemBlock GLYPH = of("glyph", new TotemBlock());

    public HashMap<SkillItemTier, Block> FARMING_PLANTS = new HashMap<>();

    public Block MANA_PLANT = plant("mana");
    public Block LIFE_PLANT = plant("life");

    public ModBlocks() {
        for (SkillItemTier tier : SkillItemTier.values()) {
            FARMING_PLANTS.put(tier, plant("plant" + (tier.tier + 1)));
        }
    }

    Block plant(String id) {
        return of(id, new BeetrootBlock(AbstractBlock.Properties.of(Material.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)));
    }

    <T extends Block> T of(String id, T c) {
        Registry.register(Registry.BLOCK, new ResourceLocation(SlashRef.MODID, id), c);
        return c;
    }

}
