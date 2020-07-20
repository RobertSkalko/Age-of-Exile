package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.capability.bases.EntityGears;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.event_hooks.TODO.OnHurtEvent;
import com.robertx22.mine_and_slash.event_hooks.entity.damage.LivingHurtUtils;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.LivingHurtEvent;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
  Nothing is done to environmental damage
  By default, entity damage ignores vanilla armor (configurable)
  */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)B", at = @At("HEAD"), argsOnly = true)
    public float on$damage(float amount, DamageSource source) {

        float modified = amount;

        if ((Object) this instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) (Object) this;

            if (entity.world.isClient) {
                return modified;
            }
            MMORPG.mixinLog("before dmg hook: " + modified);
            modified = OnHurtEvent.onHurtEvent(new LivingHurtEvent(entity, source, modified));
            MMORPG.mixinLog("after dmg hook: " + modified);
        }

        return modified;
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void on$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.world.isClient) {
            return;
        }

        EntityCap.UnitData data = Load.Unit(entity);

        EntityGears gears = data.getCurrentGears();

        boolean calc = false;

        ItemStack chestnow = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (gears.my$chest == null || !ItemStack.areEqual(chestnow, gears.my$chest)) {
            gears.my$chest = chestnow;
            calc = true;
        }
        ItemStack legsnow = entity.getEquippedStack(EquipmentSlot.LEGS);
        if (gears.my$legs == null || !ItemStack.areEqual(legsnow, gears.my$legs)) {
            gears.my$legs = legsnow;
            calc = true;
        }
        ItemStack feetnow = entity.getEquippedStack(EquipmentSlot.FEET);
        if (gears.my$feet == null || !ItemStack.areEqual(feetnow, gears.my$feet)) {
            gears.my$feet = feetnow;
            calc = true;
        }
        ItemStack headnow = entity.getEquippedStack(EquipmentSlot.HEAD);
        if (gears.my$head == null || !ItemStack.areEqual(headnow, gears.my$head)) {
            gears.my$head = headnow;
            calc = true;
        }
        ItemStack weaponnow = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (gears.my$weapon == null || !ItemStack.areEqual(weaponnow, gears.my$weapon)) {
            gears.my$weapon = weaponnow;
            calc = true;
        }
        ItemStack offhandnow = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (gears.my$offhand == null || !ItemStack.areEqual(offhandnow, gears.my$offhand)) {
            gears.my$offhand = offhandnow;
            calc = true;

        }

        if (calc) {
            on$change(entity);
        }

    }

    private void on$change(LivingEntity entity) {
        if (entity != null) {

            EntityCap.UnitData data = Load.Unit(entity);
            data.setEquipsChanged(true);
            data.tryRecalculateStats(entity);

            if (entity instanceof PlayerEntity) {
                data.syncToClient((PlayerEntity) entity);
            }
        }

    }

    @Inject(
        method = "applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F",
        at = @At("HEAD"),
        cancellable = true
    )
    public void onArmorReduction(DamageSource source, float damage, CallbackInfoReturnable<Float> ci) {
        MMORPG.mixinLog(source.name + " class:" + source.getClass()
            .toString());

        if (LivingHurtUtils.isEnviromentalDmg(source)) {
            MMORPG.mixinLog("Not changing enviromental dmg");
            return;
        } else {
            if (!source.bypassesArmor()) {
                MMORPG.mixinLog("Returning dmg value before it's affected by armor calculation.");

                LivingEntity en = (LivingEntity) (Object) this;

                LivingHurtUtils.damageArmorItems(en);

                float afterArmor = DamageUtil.getDamageLeft(damage, (float) en.getArmor(), (float) en.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS)
                    .getValue());

                float diff = damage - afterArmor;

                if (diff > 0) {
                    float effectiveness = (float) ModConfig.INSTANCE.Server.VANILLA_ARMOR_EFFECTIVENESS;

                    damage -= diff * effectiveness;
                }

                ci.setReturnValue(damage);
            } else {
                MMORPG.mixinLog("Is unblockable");
            }
        }

    }
}
