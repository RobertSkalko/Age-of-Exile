package com.robertx22.age_of_exile.mobs.bosses.channels;

import com.robertx22.age_of_exile.mobs.bosses.bases.ChannelAction;
import com.robertx22.age_of_exile.mobs.bosses.bases.IBossMob;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bosses.AngerEffect;
import net.minecraft.entity.LivingEntity;

public class AngerChannel<T extends LivingEntity & IBossMob> extends ChannelAction<T> {

    public AngerChannel(T en) {
        super(en);
    }

    @Override
    public void onFinished() {
        PotionEffectUtils.applyToSelf(AngerEffect.INSTANCE, en);
    }

    @Override
    public void onTick() {

    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.BAD_FOR_PLAYER;
    }

    @Override
    public boolean canStartChanneling() {
        return this.en.getHealth() < en.getMaxHealth() * 0.9F;
    }

    @Override
    public int getTicksNeeded() {
        return 80;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
