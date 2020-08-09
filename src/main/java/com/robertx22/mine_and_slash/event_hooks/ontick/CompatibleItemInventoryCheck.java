package com.robertx22.mine_and_slash.event_hooks.ontick;

import com.robertx22.mine_and_slash.auto_comp.PowerLevel;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.registry.FilterListWrap;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.Cached;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class CompatibleItemInventoryCheck {

    // fast cached check cus this is called a lot
    public static boolean isComp(Item item) {

        if (!Cached.IS_COMP_ITEM_MAP.containsKey(item)) {
            String reg = Registry.ITEM.getId(item)
                .toString();

            List<CompatibleItem> list = SlashRegistry.CompatibleItems()
                .getFilterWrapped(x -> x.item_id.equals(reg)).list;

            boolean isCompatible = !list.isEmpty();

            if (!isCompatible) {
                if (ModConfig.get().autoCompatibleItems.ENABLE_AUTOMATIC_COMPATIBLE_ITEMS) {
                    FilterListWrap<BaseGearType> wrapped = SlashRegistry.GearTypes()
                        .getFilterWrapped(x -> BaseGearType.isGearOfThisType(x, item));
                    isCompatible = !wrapped.list.isEmpty();
                }
            }

            Cached.IS_COMP_ITEM_MAP.put(item, isCompatible);
        }
        return Cached.IS_COMP_ITEM_MAP.get(item);
    }

    public static void tryCreateCompatibleItemStats(ItemStack stack, int level) {
        // fast check for every item
        if (Gear.has(stack) == false) {

            if (!isComp(stack.getItem())) {
                return;
            }

            String reg = Registry.ITEM.getId(stack.getItem())
                .toString();

            FilterListWrap<CompatibleItem> matchingItems = SlashRegistry.CompatibleItems()
                .getFilterWrapped(x -> x.item_id.equals(reg));

            CompatibleItem config = null;

            if (!matchingItems.list.isEmpty()) {
                config = matchingItems.random();
            } else {

                if (ModConfig.get().autoCompatibleItems.ENABLE_AUTOMATIC_COMPATIBLE_ITEMS) {

                    final ItemStack finalStack = stack;

                    final Item item = finalStack.getItem();

                    FilterListWrap<BaseGearType> wrapped = SlashRegistry.GearTypes()
                        .getFilterWrapped(x -> BaseGearType.isGearOfThisType(x, item));

                    if (!wrapped.list.isEmpty()) {

                        BaseGearType slot = wrapped.random();

                        PowerLevel.Types type = PowerLevel.getPowerClassification(item);

                        config = type.getConfig()
                            .getAutoCompatibleItem(item, slot);

                    }

                }
            }

            if (config != null) {

                // slow check to make absolutely sure it doesnt have stats
                GearItemData gear = Gear.Load(stack);
                if (gear == null) {
                    stack = config.createStack(level, stack);
                }
            }
        }

    }

    public static void checkAndGenerate(PlayerEntity player) {

        try {

            if (ModConfig.get().Server.USE_COMPATIBILITY_ITEMS == false) {
                return;
            }
            if (player.world.isClient) {
                return;
            }

            for (ItemStack stack : player.inventory.main) {

                if (stack.isEmpty()) {
                    continue;
                }
                EntityCap.UnitData data = Load.Unit(player);

                tryCreateCompatibleItemStats(stack, data.getLevel());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
