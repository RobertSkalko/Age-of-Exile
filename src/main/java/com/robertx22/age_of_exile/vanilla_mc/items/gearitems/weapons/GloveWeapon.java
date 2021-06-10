package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class GloveWeapon extends ModWeapon {

    public GloveWeapon() {
        super(WeaponTypes.glove);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return super.postHit(stack, target, attacker);
    }
}
