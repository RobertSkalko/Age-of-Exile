package com.robertx22.age_of_exile.event_hooks.entity.damage;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.LivingHurtEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.lang.reflect.Field;

public class DamageEventData {

    public DamageEventData(LivingHurtEvent event) {

        try {

            this.event = event;

            this.source = (LivingEntity) event.getSource()
                .getAttacker();
            this.target = event.getEntityLiving();

            this.sourceData = Load.Unit(source);
            this.targetData = Load.Unit(target);

            setupWeaponData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isValidEntityDamage(LivingHurtEvent event) {

        if (event.getSource() != null && event.getSource()
            .getAttacker() instanceof LivingEntity) {
            return true;
        }

        return false;
    }

    public LivingHurtEvent event;

    public ItemStack weapon;
    public GearItemData weaponData;

    public LivingEntity source;
    public LivingEntity target;

    public UnitData sourceData;
    public UnitData targetData;

    public float multiplier = 1;

    public float getEventDamage() {
        return event.getAmount();
    }

    private void setupWeaponData() {

        ItemStack stack = source.getMainHandStack();
        GearItemData gear = Gear.Load(stack);

        if (gear == null) {

            try {
                Entity is = event.getSource()
                    .getSource();
                Entity ts = event.getSource()
                    .getAttacker();

                if (is != null && is instanceof PlayerEntity == false && is instanceof LivingEntity == false) {
                    if (ts instanceof LivingEntity) {

                        stack = getWeaponStackFromThrownEntity(is);
                        gear = Gear.Load(stack);

                        if (gear == null) {
                            stack = ItemStack.EMPTY;
                        } else {
                            sourceData.setEquipsChanged(true);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (gear != null) {
            weaponData = gear;
            weapon = stack;
        }

        if (weapon == null) {
            weapon = ItemStack.EMPTY;
        }

    }

    public static ItemStack getWeaponStackFromThrownEntity(Entity en) {

        for (Field field : en.getClass()
            .getFields()) {

            if (field.getType()
                .isAssignableFrom(ItemStack.class)) {
                try {
                    ItemStack stack = (ItemStack) field.get(en);
                    GearItemData gear = Gear.Load(stack);
                    if (gear != null) {
                        return stack;
                    }
                } catch (Exception e) {

                }

            }
        }

        try {
            for (DataTracker.Entry<?> entry : en.getDataTracker()
                .getAllEntries()) {
                if (entry.get() instanceof ItemStack) {
                    GearItemData gear = Gear.Load((ItemStack) entry.get());
                    if (gear != null) {
                        return (ItemStack) entry.get();
                    }
                }
            }
        } catch (Exception e) {
        }

        try {
            CompoundTag nbt = new CompoundTag();
            en.toTag(nbt);

            ItemStack stack = ItemStack.EMPTY;

            for (String key : nbt.getKeys()) {
                if (stack == null || stack.isEmpty()) {
                    try {
                        if (nbt.get(key) instanceof CompoundTag) {
                            ItemStack s = tryGetStackFromNbt(nbt.get(key));

                            if (!s.isEmpty() && Gear.has(s)) {
                                return s;
                            }

                        } else {

                            CompoundTag nbt2 = (CompoundTag) nbt.get(key);

                            for (String key2 : nbt2.getKeys()) {
                                if (nbt.get(key) instanceof CompoundTag) {
                                    ItemStack s2 = tryGetStackFromNbt(nbt2.get(key2));
                                    if (!s2.isEmpty() && Gear.has(s2)) {
                                        return s2;
                                    }

                                }
                            }

                        }
                    } catch (Exception e) {
                    }
                }

            }

            ItemStack tryWholeNbt = ItemStack.fromTag(nbt);

            if (tryWholeNbt != null) {
                GearItemData gear = Gear.Load(tryWholeNbt);
                if (gear != null) {
                    return tryWholeNbt;
                }
            }

            if (stack == null) {
                stack = ItemStack.EMPTY;
            }

            return stack;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ItemStack.EMPTY;
    }

    private static ItemStack tryGetStackFromNbt(Tag nbt) {
        if (nbt instanceof CompoundTag) {
            ItemStack s = ItemStack.fromTag((CompoundTag) nbt);
            if (s != null && !s.isEmpty()) {
                return s;

            }
        }

        return ItemStack.EMPTY;
    }
}
