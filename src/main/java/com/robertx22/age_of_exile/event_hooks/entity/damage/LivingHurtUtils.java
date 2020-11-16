package com.robertx22.age_of_exile.event_hooks.entity.damage;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackInformation;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class LivingHurtUtils {

    public static int getItemDamage(float dmg) {
        return (int) MathHelper.clamp(dmg / 4F, 1, 6);
    }

    public static void damageCurioItems(LivingEntity en, float dmg) {

        if (en instanceof PlayerEntity) {

            PlayerEntity player = (PlayerEntity) en;

            List<ItemStack> curios = MyCurioUtils.getAllSlots(player);

            curios.forEach(x -> x.damage(getItemDamage(dmg), player, (entity) -> {
                entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            }));

        }
    }

    public static void tryAttack(AttackInformation event) {

        LivingEntity target = event.getTargetEntity();

        if (target.world.isClient) {
            return;
        }

        if (event.getSource() != null) {

            if (!RangedDamageUtil.isValidAttack(event)) {
                return;
            }
            if (DmgSourceUtils.isMyDmgSource(event.getSource())) {
                return;
            }
            if (event.getSource()
                .getAttacker() instanceof LivingEntity) {
                onAttack(event);
            }

        }

    }

    private static void onAttack(AttackInformation data) {

        try {

            if (data.getTargetEntity()
                .isAlive() == false) {
                return; // stops attacking dead mobs
            }

            GearItemData weapondata = data.weaponData;

            data.getTargetEntityData()
                .tryRecalculateStats();
            data.getAttackerEntityData()
                .tryRecalculateStats();

            if (data.getAttackerEntity() instanceof PlayerEntity) {

                if (weapondata == null) {
                    return;
                }

                if (weapondata != null && weapondata.isWeapon()) {
                    if (data.getAttackerEntityData()
                        .canUseWeapon(weapondata)) {
                        data.getAttackerEntityData()
                            .attackWithWeapon(data);
                    }

                } else {
                    // data.getAttackerEntityData()
                    //   .unarmedAttack(data);
                }

            } else { // if its a mob
                data.getAttackerEntityData()
                    .mobBasicAttack(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isEnviromentalDmg(DamageSource source) {
        return source.getAttacker() instanceof LivingEntity == false;
    }

}
