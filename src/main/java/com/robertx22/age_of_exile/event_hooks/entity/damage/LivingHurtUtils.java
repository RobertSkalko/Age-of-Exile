package com.robertx22.age_of_exile.event_hooks.entity.damage;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.LivingHurtEvent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
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

    public static void onAttack(LivingHurtEvent event) {

        LivingEntity target = event.getEntityLiving();

        if (target.world.isClient) {
            return;
        }

        if (event.getSource() != null) {

            if (isForbiddenAttack(event)) {
                return;
            }

            if (event.getSource() instanceof MyDamageSource || event.getSource()
                .getName()
                .equals(DamageEffect.dmgSourceName)) {
                return;
            }

            if (event.getSource()
                .getAttacker() instanceof LivingEntity) {

                onAttack(new DamageEventData(event));
            }

        }

    }

    public static void onAttack(DamageEventData data) {

        try {

            if (data.target.isAlive() == false) {
                return; // stops attacking dead mobs
            }

            GearItemData weapondata = data.weaponData;

            data.targetData.tryRecalculateStats(data.target);
            data.sourceData.tryRecalculateStats(data.source);

            if (data.source instanceof PlayerEntity) {

                if (weapondata == null) {
                    return;
                }

                if (data.sourceData.isWeapon(weapondata)) {
                    if (data.sourceData.tryUseWeapon(weapondata, data.source)) {
                        data.sourceData.attackWithWeapon(data);
                    }

                } else {
                    data.sourceData.unarmedAttack(data);
                }

            } else { // if its a mob
                data.sourceData.mobBasicAttack(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isForbiddenAttack(LivingHurtEvent event) {

        if (event.getSource()
            .getAttacker() instanceof LivingEntity) {
            LivingEntity en = (LivingEntity) event.getSource()
                .getAttacker();
            DamageSource source = event.getSource();

            Item item = en.getMainHandStack()
                .getItem();

            if (item instanceof BowItem || item instanceof CrossbowItem) {
                if (source.name.contains("arrow")) {
                    return false;
                }
                if (source.name.contains("bolt")) {
                    return false;
                }
                if (source.name.contains("ammo")) {
                    return false;
                }
                if (source.name.contains("bullet")) {
                    return false;
                }
                if (source.name.contains("firework")) {
                    return false;
                }
                if (source.name.contains("dart")) {
                    return false;
                }
                if (source.name.contains("missile")) {
                    return false;
                }
                return true;

            } else {
                if (source instanceof ProjectileDamageSource) {
                    ProjectileDamageSource indi = (ProjectileDamageSource) source;

                    if (indi.getSource() instanceof TridentEntity) {
                        return false;
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isEnviromentalDmg(DamageSource source) {
        return source.getAttacker() instanceof LivingEntity == false;
    }

    public static void onHurtRecordNonPlayerDmg(LivingHurtEvent event) {

        LivingEntity defender = event.getEntityLiving();

        UnitData data = Load.Unit(defender);

        if (event.getSource() != null && event.getSource()
            .getAttacker() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource()
                .getAttacker();
            data.onDamagedBy(attacker, event.getAmount(), defender);

        } else {
            data.onDamagedBy(null, event.getAmount(), defender);
        }

    }

}
