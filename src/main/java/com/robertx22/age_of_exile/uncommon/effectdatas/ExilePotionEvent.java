package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileStatusEffect;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ExilePotionEvent extends EffectEvent {

    public static String ID = "on_exile_effect";

    @Override
    public String GUID() {
        return ID;
    }

    int lvl;

    public ExilePotionEvent(int lvl, ExileEffect effect, GiveOrTake giveOrTake, LivingEntity caster, LivingEntity target, int tickDuration) {
        super(1, caster, target);
        this.lvl = lvl;

        this.data.setupNumber(EventData.STACKS, 1);
        this.data.setString(EventData.GIVE_OR_TAKE, giveOrTake.name());
        this.data.setString(EventData.EXILE_EFFECT, effect.GUID());
        this.data.setupNumber(EventData.EFFECT_DURATION_TICKS, tickDuration);

    }

    @Override
    protected void activate() {

        if (source.world.isClient) {
            return;
        }
        if (this.data.isCanceled()) {
            return;
        }

        int stacks = (int) data.getNumber(EventData.STACKS).number;

        GiveOrTake action = data.getGiveOrTake();
        ExileEffect effect = data.getExileEffect();
        int duration = (int) data.getNumber(EventData.EFFECT_DURATION_TICKS).number;

        ExileStatusEffect status = effect.getStatusEffect();

        if (action == GiveOrTake.take) {
            ExileEffectInstanceData extraData = Load.Unit(target)
                .getStatusEffectsData()
                .get(status);

            extraData.stacks -= stacks;
            extraData.stacks = MathHelper.clamp(extraData.stacks, 0, 1000);
            extraData.str_multi = data.getNumber();

            if (extraData.stacks < 1) {
                target.removeStatusEffect(status);
                Load.Unit(target)
                    .getStatusEffectsData()
                    .set(status, null);
            }
            Load.Unit(target)
                .setEquipsChanged(true);
            Load.Unit(target)
                .trySync();
        } else {

            StatusEffectInstance instance = target.getStatusEffect(status);
            ExileEffectInstanceData extraData;

            if (instance != null) {
                extraData = Load.Unit(target)
                    .getStatusEffectsData()
                    .get(status);
                if (extraData == null) {
                    extraData = new ExileEffectInstanceData();
                } else {
                    extraData.stacks++;
                    extraData.stacks = MathHelper.clamp(extraData.stacks, 1, effect.max_stacks);
                }
            } else {
                extraData = new ExileEffectInstanceData();
            }

            extraData.spellData = EntitySavedSpellData.create(lvl, source, effect);

            extraData.str_multi = data.getNumber();

            StatusEffectInstance newInstance = new StatusEffectInstance(status, duration, 1, false, false, true);

            Load.Unit(target)
                .getStatusEffectsData()
                .set(status, extraData);

            if (target.hasStatusEffect(newInstance.getEffectType())) {
                target.getActiveStatusEffects()
                    .put(newInstance.getEffectType(), newInstance);
            } else {
                target.addStatusEffect(newInstance);
            }

            target.addStatusEffect(newInstance);

            // sync packets to client
            EntityStatusEffectS2CPacket packet = new EntityStatusEffectS2CPacket(target.getEntityId(), newInstance);
            PlayerStream.watching(target.world, target.getBlockPos())
                .forEach((x) -> {
                    ServerPlayerEntity server = (ServerPlayerEntity) x;
                    server.networkHandler.sendPacket(packet);
                });

            Load.Unit(target)
                .setEquipsChanged(true);
            Load.Unit(target)
                .trySync();
        }
    }

}
