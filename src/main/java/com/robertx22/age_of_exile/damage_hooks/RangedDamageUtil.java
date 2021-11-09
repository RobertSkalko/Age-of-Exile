package com.robertx22.age_of_exile.damage_hooks;

import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import java.util.Arrays;
import java.util.List;

public class RangedDamageUtil {

    private static List<String> VALID_PROJECTILE_NAMES = Arrays.asList("arrow", "bolt", "ammo", "bullet", "dart", "missile");

    public static boolean isValidAttack(AttackInformation event) {

        if (!(event.getSource()
            .getEntity() instanceof PlayerEntity)) {
            return true;
        }
        LivingEntity en = (LivingEntity) event.getSource()
            .getEntity();
        DamageSource source = event.getSource();
        Item item = en.getMainHandItem()
            .getItem();
        GearItemData gear = Gear.Load(en.getMainHandItem());

        if (gear != null && gear.GetBaseGearType()
            .weaponType().isProjectile) {
            if (!VALID_PROJECTILE_NAMES.stream()
                .anyMatch(x -> source.msgId.contains(x))) {
                return false; // if a ranged weapon's damage entity isn't an arrow or similar, don't do damage
            }
        } else {
            if (source instanceof IndirectEntityDamageSource) {
                if (source.getDirectEntity() instanceof TridentEntity) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }
}
