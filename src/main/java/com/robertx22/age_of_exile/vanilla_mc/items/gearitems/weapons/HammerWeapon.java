package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;

public class HammerWeapon extends AoeSwordWeapon {
    public HammerWeapon() {
        super(WeaponTypes.Hammer);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        
        SoundUtils.playSound(attacker, SoundEvents.BLOCK_ANVIL_LAND, 1, 1);
        return super.postHit(stack, target, attacker);
    }
}