package com.robertx22.age_of_exile.mobs.bosses.bases;

import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bosses.ChannelEffect;
import net.minecraft.entity.LivingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BossData {
    LivingEntity en;
    List<ChannelAction> possibleChannels;

    public int ticksInBetweenCasts = 140;

    public BossData(LivingEntity en, List<ChannelAction> possibleChannels) {
        this.en = en;
        this.possibleChannels = possibleChannels;
    }

    public ChannelAction channelAction;

    public int ticksWithoutChanneling = 0;
    public int channelTicks;
    public int timesHitDuringChannel;

    private void startChanneling(ChannelAction act) {
        this.channelAction = act;
        this.channelTicks = 0;
        this.ticksWithoutChanneling = 0;
        this.timesHitDuringChannel = 0;

        PotionEffectUtils.applyToSelf(ChannelEffect.getFor(channelAction.getChannelType()), en);
    }

    private void stop() {

        PotionEffectUtils.reduceStacks(en, ChannelEffect.getFor(channelAction.getChannelType()), 555);

        this.channelAction = null;
        this.channelTicks = 0;
        this.ticksWithoutChanneling = 0;

    }

    private void tickChanneling() {

        if (this.channelTicks > this.channelAction.getTicksNeeded()) {
            stop();
            return;
        }
        this.ticksWithoutChanneling = 0;
        this.channelTicks++;

        if (this.channelAction.isCanceled) {
            this.stop();
            return;
        }

        if (this.channelTicks >= this.channelAction.getTicksNeeded()) {
            channelAction.onFinished();
            stop();
        }
    }

    public void onTick() {

        if (this.channelAction == null) {
            ticksWithoutChanneling++;

            if (ticksWithoutChanneling >= ticksInBetweenCasts) {

                List<ChannelAction> list = possibleChannels.stream()
                    .filter(x -> x.canStartChanneling())
                    .collect(Collectors.toList());

                if (!list.isEmpty()) {
                    startChanneling(RandomUtils.weightedRandom(list));
                }
            }

        } else {
            tickChanneling();
        }
    }
}
