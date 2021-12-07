package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.MathHelper;

public class ExilePotionEvent extends EffectEvent {

    public static String ID = "on_exile_effect";

    public String spellid = "";

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

        if (source.level.isClientSide) {
            return;
        }
        if (this.data.isCanceled()) {
            return;
        }

        int stacks = (int) data.getNumber(EventData.STACKS).number;

        GiveOrTake action = data.getGiveOrTake();
        ExileEffect effect = data.getExileEffect();
        int duration = (int) data.getNumber(EventData.EFFECT_DURATION_TICKS).number;

        if (effect.id.contains("35")) {
            byte effectId = (byte) (Effect.getId(effect.getStatusEffect()) & 255);

            System.out.print(effectId);
        }

        ExileStatusEffect status = effect.getStatusEffect();

        if (action == GiveOrTake.take) {
            ExileEffectInstanceData extraData = Load.Unit(target)
                .getStatusEffectsData()
                .get(status);

            extraData.stacks -= stacks;
            extraData.stacks = MathHelper.clamp(extraData.stacks, 0, 1000);
            extraData.str_multi = data.getNumber();

            if (extraData.stacks < 1) {
                target.removeEffect(status);
                Load.Unit(target)
                    .getStatusEffectsData()
                    .set(status, null);
            }
            Load.Unit(target)
                .setEquipsChanged(true);
            Load.Unit(target)
                .trySync();
        } else {

            EffectInstance instance = target.getEffect(status);
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

            extraData.spellData.spell_id = this.spellid;

            extraData.str_multi = data.getNumber();

            EffectInstance newInstance = new EffectInstance(status, duration, extraData.stacks, false, false, true);

            Load.Unit(target)
                .getStatusEffectsData()
                .set(status, extraData);

            if (target.hasEffect(newInstance.getEffect())) {
                target.getActiveEffectsMap()
                    .put(newInstance.getEffect(), newInstance);
            } else {
                target.addEffect(newInstance);
            }

            // sync packets to client
            SPlayEntityEffectPacket packet = new SPlayEntityEffectPacket(target.getId(), newInstance);

            PlayerUtils.getNearbyPlayers(target, 50D)
                .forEach((x) -> {
                    ServerPlayerEntity server = (ServerPlayerEntity) x;
                    server.connection.send(packet);
                });

            Load.Unit(target)
                .setEquipsChanged(true);
            Load.Unit(target)
                .tryRecalculateStats();
            Load.Unit(target)
                .trySync();
        }
    }

}
