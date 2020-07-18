package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.items.SimpleMatItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag.ItemCurrencyBag;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class MiscItemsRegistrator extends BaseItemRegistrator {

    public List<JewelItem> ALL_JEWELS = new ArrayList<>();

    public Item CURRENCY_BAG = item(new ItemCurrencyBag(), ItemCurrencyBag.ID);

    // todo register all gears like this.

    public IdentifyTomeItem IDENTIFY_TOME = item(new IdentifyTomeItem(), "identify_tome");
    public Item SKILL_GEM = item(new Item(new Item.Settings().maxCount(1)
        .maxDamage(0)), "skill_gem");

    public MagicEssenceItem MAGIC_ESSENCE = item(new MagicEssenceItem());
    public RareMagicEssence RARE_MAGIC_ESSENCE = item(new RareMagicEssence());

    public JewelItem BLUE_JEWEL = jewel(new JewelItem(), "jewels/blue");
    public JewelItem GREEN_JEWEL = jewel(new JewelItem(), "jewels/green");

    public Item INT_SKILL_GEM = item(new SkillGemItem(), "skill_gems/int");
    public Item DEX_SKILL_GEM = item(new SkillGemItem(), "skill_gems/dex");
    public Item STR_SKILL_GEM = item(new SkillGemItem(), "skill_gems/str");
    public Item NEWBIE_GEAR_BAG = item(new ItemNewbieGearBag(), "newbie_gear_bag");
    public Item INCRESE_MOB_RARITY = item(new ItemIncreaseRarityNearestEntity(), "increase_rarity_nearest_entity");

    public Item INFUSED_IRON = item(new SimpleMatItem(), "mat/infused_iron");
    public Item CRYSTALLIZED_ESSENCE = item(new SimpleMatItem(), "mat/crystallized_essence");
    public Item GOLDEN_ORB = item(new SimpleMatItem(), "mat/golden_orb");
    public Item MYTHIC_ESSENCE = item(new SimpleMatItem(), "mat/mythic_essence");

    public ResetStatPointsItem RESET_STATS_POTION = item(new ResetStatPointsItem());

    JewelItem jewel(JewelItem c, String id) {

        Registry.register(Registry.ITEM, new Identifier(Ref.MODID, id), c);

        if (ALL_JEWELS == null) {
            ALL_JEWELS = new ArrayList<>();
        }

        ALL_JEWELS.add(c);
        return c;
    }

}
