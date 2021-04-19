package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ExileEffectsManager {
    public static void reduceStacks(ExileEffect reg, LivingEntity target, int amount) {

        try {
            if (target.world.isClient) {
                return;
            }
            ExileStatusEffect effect = reg.getStatusEffect();

            ExilePotionEvent event = new ExilePotionEvent(reg, ExilePotionEvent.Action.TAKE, target, target);
            event.Activate();

            ExileEffectInstanceData extraData = Load.Unit(target)
                .getStatusEffectsData()
                .get(effect);

            extraData.stacks -= amount;
            extraData.stacks = MathHelper.clamp(extraData.stacks, 0, 1000);

            if (extraData.stacks < 1) {
                target.removeStatusEffect(effect);
                Load.Unit(target)
                    .getStatusEffectsData()
                    .set(effect, null);
            }

            Load.Unit(target)
                .setEquipsChanged(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void apply(int lvl, ExileEffect reg, LivingEntity caster, LivingEntity target, int duration) {

        if (caster.world.isClient) {
            return;
        }

        ExilePotionEvent event = new ExilePotionEvent(reg, ExilePotionEvent.Action.GIVE, caster, target);
        event.Activate();

        ExileStatusEffect effect = reg.getStatusEffect();

        StatusEffectInstance instance = target.getStatusEffect(effect);
        ExileEffectInstanceData extraData;

        if (instance != null) {
            extraData = Load.Unit(target)
                .getStatusEffectsData()
                .get(effect);
            if (extraData == null) {
                extraData = new ExileEffectInstanceData();
            } else {
                extraData.stacks++;
                extraData.stacks = MathHelper.clamp(extraData.stacks, 1, reg.max_stacks);
            }
        } else {
            extraData = new ExileEffectInstanceData();
        }

        extraData.spellData = EntitySavedSpellData.create(lvl, caster, reg);

        StatusEffectInstance newInstance = new StatusEffectInstance(effect, duration, 1, false, false, true);

        Load.Unit(target)
            .getStatusEffectsData()
            .set(effect, extraData);

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
