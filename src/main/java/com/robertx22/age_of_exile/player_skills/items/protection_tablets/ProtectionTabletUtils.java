package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ProtectionTabletUtils {

    static List<StackAndTablet> getTabletsOfPlayer(PlayerEntity player) {

        List<StackAndTablet> list = new ArrayList<>();

        List<ItemStack> items = new ArrayList<>();

        try {
            items.addAll(player.inventory.main);

            for (int i = 0; i < player.getEnderChestInventory()
                .size(); i++) {
                items.add(player.getEnderChestInventory()
                    .getStack(i));
            }

            for (ItemStack stack : items) {
                if (stack.getItem() instanceof ProtectionTabletItem) {
                    ProtectionTabletItem tablet = (ProtectionTabletItem) stack.getItem();
                    list.add(new StackAndTablet(stack, tablet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        list.removeIf(x -> !IsSkillItemUsableUtil.canUseItem(player, x.stack, false));

        return list;

    }

    public static void tryUseTablets(PlayerEntity player, DamageSource source) {
        List<StackAndTablet> tablets = getTabletsOfPlayer(player);

        List<TabletTypes> typesUsed = new ArrayList<>();

        for (StackAndTablet x : tablets) {
            if (typesUsed.contains(x.tablet.type)) {
                continue;
            }

            if (Load.Unit(player)
                .getCooldowns()
                .isOnCooldown(x.tablet.type.id)) {
                continue;
            }

            if (x.tablet.type.shouldActivate(player, source)) {

                if (x.tablet.type.cooldownTicks() > 0) {
                    Load.Unit(player)
                        .getCooldowns()
                        .setOnCooldown(x.tablet.type.id, x.tablet.type.cooldownTicks());
                }

                typesUsed.add(x.tablet.type);
                x.stack.decrement(1);
                x.tablet.type.onActivate(player);
                SoundUtils.ding(player.world, player.getBlockPos()); // find better sound effect
            }
        }

    }

}
