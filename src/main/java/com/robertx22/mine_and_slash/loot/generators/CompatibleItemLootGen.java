package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.database.registry.FilterListWrap;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CompatibleItemLootGen extends BaseLootGen<GearBlueprint> {

    public CompatibleItemLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        return (float) ModConfig.get().DropRates.COMPATIBLE_ITEMS_DROPRATE;
    }

    @Override
    public LootType lootType() {
        return LootType.CompatibleItem;
    }

    @Override
    public boolean condition() {
        return ModConfig.get().Server.USE_COMPATIBILITY_ITEMS && info.mobData != null;
    }

    @Override
    public ItemStack generateOne() {
        return gen();
    }

    public ItemStack gen() {

        try {
            FilterListWrap<CompatibleItem> possible = SlashRegistry.CompatibleItems()
                .getFilterWrapped(x -> x.add_to_loot_drops);

            if (!possible.list.isEmpty()) {

                CompatibleItem config = possible.random();

                if (config != null) {
                    Identifier res = new Identifier(config.item_id);

                    if (Registry.ITEM.containsId(res)) {
                        ItemStack stack = new ItemStack(Registry.ITEM.get(res));
                        return config.createStack(info.level, stack);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ItemStack.EMPTY;
    }

}