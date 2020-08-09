package com.robertx22.mine_and_slash.api;

import com.robertx22.mine_and_slash.event_hooks.entity.damage.DamageEventData;
import com.robertx22.mine_and_slash.event_hooks.my_events.CollectGearEvent;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RepairUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class MineAndSlashEvents {

    // called before stat calculation to gather all the gears. Add it here if you say use a different jewerly mod
    // that isn't compatible with one i use
    public static class CollectGearStacksEvent {

        public List<GearItemData> gears;

        public LivingEntity entity;
        public DamageEventData data;

        public CollectGearStacksEvent(LivingEntity entity, List<GearItemData> gears, DamageEventData data) {
            this.entity = entity;
            this.gears = gears;
            this.data = data;

            CollectGearEvent.getEquipsExcludingWeapon(entity)
                .forEach(x -> add(x));
            CollectGearEvent.addHeldItems(this);

        }

        public void add(GearItemData data) {
            if (data != null) {
                gears.add(data);
            }
        }

        public void add(ItemStack stack) {
            if (isStackValidGear(stack)) {
                GearItemData data = Gear.Load(stack);
                if (data != null) {
                    gears.add(data);
                }
            }
        }

        public static boolean isStackValidGear(ItemStack stack) {

            if (stack == null) {
                return false;
            }

            if (stack.isDamageable()) {
                return RepairUtils.isItemBroken(stack) == false;
            }

            GearItemData gear = Gear.Load(stack);

            return gear != null && gear.isValidItem();

        }

    }

}