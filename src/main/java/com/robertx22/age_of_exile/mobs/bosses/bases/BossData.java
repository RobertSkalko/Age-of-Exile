package com.robertx22.age_of_exile.mobs.bosses.bases;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NeutralEffects;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
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

        if (channelAction.getChannelType() == ChannelAction.ChannelType.BAD_FOR_PLAYER) {
            ExileEffectsManager.apply(SlashRegistry.ExileEffects()
                .get(NeutralEffects.BAD_FOR_PLAYER), en, en, 1);

        } else {
            ExileEffectsManager.apply(SlashRegistry.ExileEffects()
                .get(NeutralEffects.GOOD_FOR_BOSS), en, en, 1);
        }

    }

    private void stop() {
        if (channelAction.getChannelType() == ChannelAction.ChannelType.BAD_FOR_PLAYER) {
            ExileEffectsManager.reduceStacks(SlashRegistry.ExileEffects()
                .get(NeutralEffects.BAD_FOR_PLAYER), en, 1);

        } else {
            ExileEffectsManager.reduceStacks(SlashRegistry.ExileEffects()
                .get(NeutralEffects.GOOD_FOR_BOSS), en, 1);
        }

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
