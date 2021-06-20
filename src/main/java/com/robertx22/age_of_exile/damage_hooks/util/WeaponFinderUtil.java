package com.robertx22.age_of_exile.damage_hooks.util;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import java.lang.reflect.Field;

public class WeaponFinderUtil {

    public static ItemStack getWeapon(DamageSource source) {

        if (source.getAttacker() instanceof LivingEntity == false) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = ((LivingEntity) source.getAttacker()).getMainHandStack();
        GearItemData gear = Gear.Load(stack);

        if (gear == null) {

            try {
                Entity sourceEntity = source
                    .getSource();
                Entity attacker = source
                    .getAttacker();

                if (sourceEntity != null && !(sourceEntity instanceof LivingEntity)) {
                    if (attacker instanceof LivingEntity) {

                        stack = getWeaponStackFromThrownEntity(sourceEntity);
                        gear = Gear.Load(stack);

                        if (gear == null) {
                            stack = ItemStack.EMPTY;
                        } else {
                            Load.Unit(attacker)
                                .setEquipsChanged(true);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (gear != null) {
            return stack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    private static ItemStack getWeaponStackFromThrownEntity(Entity en) {

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
            NbtCompound nbt = new NbtCompound();
            en.writeNbt(nbt);

            ItemStack stack = ItemStack.EMPTY;

            for (String key : nbt.getKeys()) {
                if (stack == null || stack.isEmpty()) {
                    try {
                        if (nbt.get(key) instanceof NbtCompound) {
                            ItemStack s = tryGetStackFromNbt(nbt.get(key));

                            if (!s.isEmpty() && Gear.has(s)) {
                                return s;
                            }

                        } else {

                            NbtCompound nbt2 = (NbtCompound) nbt.get(key);

                            for (String key2 : nbt2.getKeys()) {
                                if (nbt.get(key) instanceof NbtCompound) {
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

            ItemStack tryWholeNbt = ItemStack.fromNbt(nbt);

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

    private static ItemStack tryGetStackFromNbt(NbtElement nbt) {
        if (nbt instanceof NbtCompound) {
            ItemStack s = ItemStack.fromNbt((NbtCompound) nbt);
            if (s != null && !s.isEmpty()) {
                return s;

            }
        }

        return ItemStack.EMPTY;
    }
}
