package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.api.MineAndSlashEvents;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.List;

public class CollectGearEvent {

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
            ItemStack weapon = event.entity
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

        ItemStack offhand = event.entity
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

                if (event.entity instanceof PlayerEntity) {
                    PlayerEntity p = (PlayerEntity) event.entity;
                    p.sendMessage(new LiteralText("You can't wear a weapon in offhand."), false);
                }
            }

        }

    }

}
