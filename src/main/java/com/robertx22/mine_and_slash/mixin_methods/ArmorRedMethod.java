package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.event_hooks.entity.damage.LivingHurtUtils;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ArmorRedMethod {

    public static void onArmorReduction(DamageSource source, float damage, CallbackInfoReturnable<Float> ci, LivingEntity en) {
        MMORPG.mixinLog(source.name + " class:" + source.getClass()
            .toString());

        if (LivingHurtUtils.isEnviromentalDmg(source)) {
            MMORPG.mixinLog("Not changing enviromental dmg");
            return;
        } else {
            if (!source.bypassesArmor()) {
                MMORPG.mixinLog("Returning dmg value before it's affected by armor calculation.");

                float afterArmor = DamageUtil.getDamageLeft(damage, (float) en.getArmor(), (float) en.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS)
                    .getValue());

                float diff = damage - afterArmor;

                if (diff > 0) {
                    float effectiveness = (float) ModConfig.get().Server.VANILLA_ARMOR_EFFECTIVENESS;

                    damage -= diff * effectiveness;
                }

                if (damage > 0) {
                    LivingHurtUtils.damageArmorItems(en);
                    LivingHurtUtils.damageCurioItems(en);
                }

                ci.setReturnValue(damage);
            } else {
                MMORPG.mixinLog("Is unblockable");
            }
        }
    }

}
