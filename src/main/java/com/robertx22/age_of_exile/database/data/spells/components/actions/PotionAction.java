package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction.GiveOrTake;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.mixin_ducks.StatusEffectAccesor;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class PotionAction extends SpellAction {

    public PotionAction() {
        super(Arrays.asList(POTION_ID, POTION_ACTION, POTION_DURATION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        try {
            GiveOrTake action = data.getPotionAction();

            for (LivingEntity t : targets) {
                if (action == GiveOrTake.GIVE_STACKS) {
                    Effect potion = data.getPotion();

                    int dura = data.get(POTION_DURATION)
                        .intValue();
                    int str = data.getOrDefault(POTION_STRENGTH, 1D)
                        .intValue();
                    t.addEffect(new EffectInstance(potion, dura, str));
                } else if (action == GiveOrTake.REMOVE_STACKS) {
                    Effect potion = data.getPotion();

                    t.removeEffect(potion);
                } else if (action == GiveOrTake.REMOVE_NEGATIVE) {
                    int count = data.getOrDefault(COUNT, 1D)
                        .intValue();

                    for (int i = 0; i < count; i++) {

                        List<EffectInstance> opt = t.getActiveEffects()
                            .stream()
                            .filter(x -> {
                                if (x.getEffect() instanceof StatusEffectAccesor) {
                                    StatusEffectAccesor acc = (StatusEffectAccesor) x.getEffect();
                                    return acc.my$getstatusEffectType() == net.minecraft.potion.EffectType.HARMFUL;
                                } else {
                                    if (x.getEffect() instanceof ExileStatusEffect) {
                                        ExileStatusEffect es = (ExileStatusEffect) x.getEffect();
                                        return es.type == EffectType.negative;
                                    }
                                }
                                return false;
                            })
                            .collect(Collectors.toList());

                        if (!opt.isEmpty()) {
                            t.removeEffect(opt.get(0)
                                .getEffect());
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MapHolder createGive(Effect effect, Double duration) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_DURATION, duration);
        dmg.put(POTION_ACTION, GiveOrTake.GIVE_STACKS.name());
        dmg.put(POTION_ID, Registry.MOB_EFFECT.getKey(effect)
            .toString());
        return dmg;
    }

    public MapHolder removeNegative(Double count) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, count);
        dmg.put(POTION_ACTION, GiveOrTake.REMOVE_NEGATIVE.name());
        return dmg;
    }

    public MapHolder createRemove(Effect effect) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_ACTION, GiveOrTake.REMOVE_STACKS.name());
        dmg.put(POTION_ID, Registry.MOB_EFFECT.getKey(effect)
            .toString());
        return dmg;
    }

    @Override
    public String GUID() {
        return "potion";
    }
}

