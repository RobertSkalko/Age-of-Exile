package com.robertx22.mine_and_slash.event_hooks.item;

import com.robertx22.exiled_lib.registry.FilterListWrap;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.auto_comp.PowerLevel;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnContainerCompatibleItem {

    @SubscribeEvent
    public static void onContainerCompatibleItem(PlayerContainerEvent event) {

        try {

            if (ModConfig.INSTANCE.Server.USE_COMPATIBILITY_ITEMS.get() == false) {
                return;
            }
            if (event.getPlayer().world.isClient) {
                return;
            }

            EntityCap.UnitData data = null;

            for (ItemStack stack : event.getPlayer().inventory.main) {

                if (stack.isEmpty()) {
                    continue;
                }

                // fast check for every item
                if (Gear.has(stack) == false) {

                    String reg = stack.getItem()
                        .getRegistryName()
                        .toString();

                    FilterListWrap<CompatibleItem> matchingItems = SlashRegistry.CompatibleItems()
                        .getFilterWrapped(x -> x.item_id.equals(reg.toString()) && !x.only_add_stats_if_loot_drop);

                    CompatibleItem config = null;

                    if (!matchingItems.list.isEmpty()) {
                        config = matchingItems.random();
                    } else {

                        if (ModConfig.INSTANCE.autoCompatibleItems.ENABLE_AUTOMATIC_COMPATIBLE_ITEMS.get()) {

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
                        if (data == null) {
                            data = Load.Unit(event.getPlayer());
                        }

                        // slow check to make absolutely sure it doesnt have stats
                        GearItemData gear = Gear.Load(stack);
                        if (gear == null) {
                            stack = config.createStack(data.getLevel(), stack);
                            event.getPlayer().inventory.markDirty();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
