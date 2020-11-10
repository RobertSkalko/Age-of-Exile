package com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets;

import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ProtectionTabletUtils {

    static List<StackAndTablet> getTabletsOfPlayer(PlayerEntity player) {

        List<StackAndTablet> list = new ArrayList<>();

        for (ItemStack stack : player.inventory.main) {
            if (stack.getItem() instanceof ProtectionTabletItem) {
                ProtectionTabletItem tablet = (ProtectionTabletItem) stack.getItem();
                list.add(new StackAndTablet(stack, tablet));
            }
        }

        return list;

    }

    public static void tryUseTablets(PlayerEntity player, DamageSource source) {
        List<StackAndTablet> tablets = getTabletsOfPlayer(player);

        List<TabletTypes> typesUsed = new ArrayList<>();

        for (StackAndTablet x : tablets) {
            if (typesUsed.contains(x.tablet.type)) {
                continue;
            }

            if (x.tablet.type.shouldActivate(player, source)) {
                typesUsed.add(x.tablet.type);
                x.stack.decrement(1);
                x.tablet.type.onActivate(player);
                SoundUtils.ding(player.world, player.getBlockPos()); // find better sound effect
            }
        }

    }
}
