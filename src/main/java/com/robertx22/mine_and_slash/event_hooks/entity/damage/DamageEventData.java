package com.robertx22.mine_and_slash.event_hooks.entity.damage;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.LivingHurtEvent;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

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
        GearItemData gear = Gear.loadOnlyValidWeaponData(stack);

        if (gear == null) {

            try {
                Entity is = event.getSource()
                    .getSource();
                Entity ts = event.getSource()
                    .getAttacker();

                if (is != null && is instanceof PlayerEntity == false && is instanceof LivingEntity == false) {
                    if (ts instanceof LivingEntity) {

                        stack = EntityUtils.getWeaponStackFromThrownEntity(is);
                        gear = Gear.loadOnlyValidWeaponData(stack);

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

}
