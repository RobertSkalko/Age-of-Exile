package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.ImmuneToEffectStat;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;

import java.util.List;
import java.util.stream.Collectors;

public class OnPotionChange {

    public static void onAdded(LivingEntity entity, StatusEffect effect) {

        if (entity.world.isClient) {
            return;
        }

        try {

            Load.Unit(entity)
                .getUnit()
                .getStats()
                .values()
                .forEach(x -> {
                    if (x.GetStat() instanceof ImmuneToEffectStat) {
                        ImmuneToEffectStat imm = (ImmuneToEffectStat) x.GetStat();
                        if (x.getAverageValue() > 0) {
                            imm.onPotionAdded(effect, entity);
                        }
                    }
                });

            if (effect instanceof IOneOfATypePotion) {
                IOneOfATypePotion one = (IOneOfATypePotion) effect;

                List<StatusEffect> sames = entity.getStatusEffects()
                    .stream()
                    .filter(x -> {
                        if (x.getEffectType() instanceof IOneOfATypePotion) {
                            IOneOfATypePotion ot = (IOneOfATypePotion) x.getEffectType();

                            if (x.getEffectType()
                                .equals(effect)) {
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

}