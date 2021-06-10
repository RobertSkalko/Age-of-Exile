package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class MaceWeapon extends ModWeapon {

    public MaceWeapon() {
        super(WeaponTypes.mace);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        SoundEvent sound = SoundEvents.BLOCK_ANVIL_LAND;

        SoundUtils.playSound(attacker, sound, 0.2F, RandomUtils.RandomRange(0F, 1F));

        return super.
            postHit(stack, target, attacker);
    }

}
