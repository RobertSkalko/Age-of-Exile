package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.items.SimpleMatItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.*;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class MiscItemsRegistrator extends BaseItemRegistrator {

    public IdentifyTomeItem IDENTIFY_TOME = item(new IdentifyTomeItem(), "identify_tome");

    public MagicEssenceItem MAGIC_ESSENCE = item(new MagicEssenceItem());
    public RareMagicEssence RARE_MAGIC_ESSENCE = item(new RareMagicEssence());

    public Item NEWBIE_GEAR_BAG = item(new ItemNewbieGearBag(), "newbie_gear_bag");
    public Item INCRESE_MOB_RARITY = item(new ItemIncreaseRarityNearestEntity(), "increase_rarity_nearest_entity");

    public Item INFUSED_IRON = item(new SimpleMatItem(), "mat/infused_iron");
    public Item CRYSTALLIZED_ESSENCE = item(new SimpleMatItem(), "mat/crystallized_essence");
    public Item GOLDEN_ORB = item(new SimpleMatItem(), "mat/golden_orb");
    public Item MYTHIC_ESSENCE = item(new SimpleMatItem(), "mat/mythic_essence");

    public Item HEART_OF_IMAGINATION = item(new HeartOfImaginationItem(), "mat/modify_part");

    public ResetStatPointsItem RESET_STATS_POTION = item(new ResetStatPointsItem());

    public Item GEAR_MODIFY = blockItem(ModRegistry.BLOCKS.GEAR_MODIFY);
    public Item GEAR_REPAIR = blockItem(ModRegistry.BLOCKS.GEAR_REPAIR);
    public Item GEAR_SALVAGE = blockItem(ModRegistry.BLOCKS.GEAR_SALVAGE);

    static Item.Settings stationProp = new Item.Settings().group(CreativeTabs.MyModTab);

    <T extends Block> Item blockItem(T block) {
        return item(new BlockItem(block, stationProp), Registry.BLOCK.getId(block)
            .getPath());
    }

}
