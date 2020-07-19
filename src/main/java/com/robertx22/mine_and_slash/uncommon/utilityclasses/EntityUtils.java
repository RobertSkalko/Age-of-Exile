package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Field;

public class EntityUtils {

    public static boolean isTryingButCantGetToPlayer(MobEntity mob) {
        if (mob.getTarget() != null) {
            Path path = mob.getNavigation()
                .findPathTo(mob.getTarget(), 0);
            if (path == null || !path.isFinished()) {
                return true;
            }
        }

        return false;
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

    public static void setLoc(LivingEntity entity, Vec3d p, float yaw, float pitch) {
        entity.requestTeleport(p.x, p.y, p.z);
    }

}
