package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.event_hooks.entity.damage.WeaponFinderUtil;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

public class AttackInformation {

    Mitigation mitigation;
    boolean canceled = false;
    LivingEntity targetEntity;
    LivingEntity attackerEntity;
    DamageSource damageSource;
    float amount;

    public ItemStack weapon;
    public GearItemData weaponData;

    public AttackInformation(Mitigation miti, LivingEntity target, DamageSource source, float amount) {
        this.targetEntity = target;
        this.damageSource = source;
        this.amount = amount;
        this.mitigation = miti;
        this.weapon = WeaponFinderUtil.getWeapon(source);
        this.weaponData = Gear.Load(weapon);

        Preconditions.checkArgument(source.getAttacker() instanceof LivingEntity);
        this.attackerEntity = (LivingEntity) source.getAttacker();

    }

    public DamageSource getSource() {
        return damageSource;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float f) {
        amount = f;
    }

    public LivingEntity getTargetEntity() {
        return targetEntity;
    }

    public LivingEntity getAttackerEntity() {
        return attackerEntity;
    }

    public EntityCap.UnitData getAttackerEntityData() {
        return Load.Unit(attackerEntity);
    }

    public EntityCap.UnitData getTargetEntityData() {
        return Load.Unit(targetEntity);
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public enum Mitigation {
        PRE, POST;
    }
}
