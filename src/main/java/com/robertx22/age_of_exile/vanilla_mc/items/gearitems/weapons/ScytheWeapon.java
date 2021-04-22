package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ScytheWeapon extends AoeSwordWeapon {

    public ScytheWeapon() {
        super(WeaponTypes.Scythe);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ParticleUtils.spawnDefaultSlashingWeaponParticles(attacker);
        return super.postHit(stack, target, attacker);
    }
}
