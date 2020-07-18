package com.robertx22.mine_and_slash.event_hooks.entity;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.ImmuneToEffectStat;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IOneOfATypePotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.stream.Collectors;

public class OnPotionChange {

    //PotionAddedEvent is called BEFORE adding the potion
    @SubscribeEvent
    public static void onAdded(PotionEvent.PotionAddedEvent event) {

        if (event.getEntityLiving().world.isClient) {
            return;
        }

        try {
            LivingEntity entity = event.getEntityLiving();

            Load.Unit(entity)
                .getUnit()
                .getStats()
                .values()
                .forEach(x -> {
                    if (x.GetStat() instanceof ImmuneToEffectStat) {
                        ImmuneToEffectStat imm = (ImmuneToEffectStat) x.GetStat();
                        if (x.getAverageValue() > 0) {
                            imm.onPotionAdded(event.getPotionEffect()
                                .getEffectType(), entity);
                        }
                    }
                });

            if (event.getPotionEffect()
                .getEffectType() instanceof IOneOfATypePotion) {
                IOneOfATypePotion one = (IOneOfATypePotion) event.getPotionEffect()
                    .getEffectType();

                List<StatusEffect> sames = entity.getStatusEffects()
                    .stream()
                    .filter(x -> {
                        if (x.getEffectType() instanceof IOneOfATypePotion) {
                            IOneOfATypePotion ot = (IOneOfATypePotion) x.getEffectType();

                            if (x.equals(event.getPotionEffect())) {
                                return false;
                            }

                            if (ot.getOneOfATypeType()
                                .equals(one.getOneOfATypeType())) {
                                return true;
                            }
                        }

                        return false;
                    })
                    .map(x -> x.getEffectType())
                    .collect(Collectors.toList());

                sames.forEach(x -> entity.removeStatusEffect(x));
            }

            if (entity != null) {
                EntityCap.UnitData data = Load.Unit(entity);
                data.setEquipsChanged(true);
                //data.tryRecalculateStats(entity); dont calc stats, PotionAddedEvent is called BEFORE adding the potion O_O
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //PotionExpiryEvent is called BEFORE removing the potion
    @SubscribeEvent
    public static void onExpired(PotionEvent.PotionExpiryEvent event) {

        LivingEntity entity = event.getEntityLiving();

        if (entity != null && !entity.world.isClient) {
            EntityCap.UnitData data = Load.Unit(entity);
            data.setEquipsChanged(true);
            //data.tryRecalculateStats(entity);
        }

    }

}