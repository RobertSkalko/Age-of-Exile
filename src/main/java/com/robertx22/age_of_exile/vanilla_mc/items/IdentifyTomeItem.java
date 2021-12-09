package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class IdentifyTomeItem extends AutoItem {

    public IdentifyTomeItem() {
        super(new Properties().tab(CreativeTabs.MyModTab));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {
        ItemStack tomeStack = player.getItemInHand(handIn);
        List<ItemStack> list = new ArrayList<>();

        for (ItemStack stack : player.inventory.items) {

            if (tomeStack.getCount() > 0) {
                StatSoulData data = StackSaving.STAT_SOULS.loadFrom(stack);

                if (data != null && data.gear == null) {

                    GearItemData gear = data.createGearData(null);
                    data.gear = gear;

                    if (stack.getCount() == 1) {
                        StackSaving.STAT_SOULS.saveTo(stack, data);
                        tomeStack.shrink(1);

                    } else {

                        int count = 0;
                        for (int i = 0; i < stack.getCount(); i++) {

                            if (tomeStack.getCount() > 0) {
                                count++;

                                GearItemData newgear = data.createGearData(null);
                                data.gear = newgear;

                                ItemStack newsoul = new ItemStack(stack.getItem());
                                StackSaving.STAT_SOULS.saveTo(newsoul, data);
                                tomeStack.shrink(1);

                                list.add(newsoul);
                            }

                        }
                        stack.shrink(count);

                    }

                }
            }

        }
        list.forEach(x -> {
            PlayerUtils.giveItem(x, player);
        });

        return ActionResult.success(tomeStack);
    }

    @Override
    public String locNameForLangFile() {
        return "Identify Tome";
    }

    @Override
    public String GUID() {
        return "";
    }
}
