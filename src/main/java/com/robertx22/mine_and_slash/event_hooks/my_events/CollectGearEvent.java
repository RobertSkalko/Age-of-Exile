package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.mine_and_slash.a_libraries.curios.MyCurioUtils;
import com.robertx22.mine_and_slash.api.MineAndSlashEvents;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class CollectGearEvent {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void collectStacks(MineAndSlashEvents.CollectGearStacksEvent event) {

        getEquipsExcludingWeapon(event.getEntityLiving()).forEach(x -> event.add(x));
        addHeldItems(event);

    }

    public static List<ItemStack> getEquipsExcludingWeapon(LivingEntity entity) {

        List<ItemStack> list = new ArrayList<ItemStack>();

        for (ItemStack stack : entity.getArmorItems()) {
            if (stack != null) {
                list.add(stack);
            }
        }

        if (entity instanceof PlayerEntity) {
            list.addAll(MyCurioUtils.getAllSlots((PlayerEntity) entity));
        }
        return list;

    }

    public static void addHeldItems(MineAndSlashEvents.CollectGearStacksEvent event) {

        boolean hasWep = false;

        if (event.data != null && event.data.weaponData != null) {
            event.add(event.data.weaponData);
            hasWep = true;
        }

        if (!hasWep) {
            ItemStack weapon = event.getEntityLiving()
                .getMainHandStack();
            if (event.isStackValidGear(weapon)) {
                GearItemData wep = Gear.Load(weapon);
                if (wep != null && wep.GetBaseGearType() != null && wep.GetBaseGearType()
                    .family()
                    .equals(BaseGearType.SlotFamily.Weapon)) {
                    hasWep = true;
                    event.add(wep);
                }

            }
        }

        ItemStack offhand = event.getEntityLiving()
            .getOffHandStack();
        if (event.isStackValidGear(offhand)) {

            GearItemData off = Gear.Load(offhand);
            if (off != null && off.GetBaseGearType() != null && off.GetBaseGearType()
                .family()
                .equals(BaseGearType.SlotFamily.OffHand)) {
                event.add(off);
            } else if (off != null && off.GetBaseGearType()
                .family()
                .equals(BaseGearType.SlotFamily.Weapon)) {
                event.getEntityLiving()
                    .sendMessage(new LiteralText("You can't wear a weapon in offhand."));
            }

        }

    }

}
