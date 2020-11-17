package com.robertx22.age_of_exile.damage_hooks;

import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;

import java.util.Arrays;
import java.util.List;

public class RangedDamageUtil {

    private static List<String> VALID_PROJECTILE_NAMES = Arrays.asList("arrow", "bolt", "ammo", "bullet", "dart", "missile");

    public static boolean isValidAttack(AttackInformation event) {

        if (!(event.getSource()
            .getAttacker() instanceof LivingEntity)) {
            return true;
        }
        LivingEntity en = (LivingEntity) event.getSource()
            .getAttacker();
        DamageSource source = event.getSource();
        Item item = en.getMainHandStack()
            .getItem();

        if (item instanceof RangedWeaponItem) {
            if (!VALID_PROJECTILE_NAMES.stream()
                .anyMatch(x -> source.name.contains(x))) {
                return false; // if a ranged weapon's damage entity isn't an arrow or similar, don't do damage
            }
        } else {
            if (source instanceof ProjectileDamageSource) {
                if (source.getSource() instanceof TridentEntity) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }
}
