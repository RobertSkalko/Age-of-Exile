package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileStatusEffect;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction.GiveOrTake;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.mixin_ducks.StatusEffectAccesor;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class PotionAction extends SpellAction implements ICTextTooltip {

    public PotionAction() {
        super(Arrays.asList(POTION_ID, POTION_ACTION, POTION_DURATION));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data, EntitySavedSpellData savedData) {
        MutableText text = new LiteralText("");

        GiveOrTake action = data.getPotionAction();

        if (action == GiveOrTake.GIVE_STACKS) {
            StatusEffect potion = data.getPotion();
            text.append("Give ");
            text.append(potion.getName());

        } else if (action == GiveOrTake.REMOVE_STACKS) {
            StatusEffect potion = data.getPotion();
            text.append("Remove ");
            text.append(potion.getName());
        } else {
            int count = data.getOrDefault(COUNT, 1D)
                .intValue();
            text.append("Remove " + count + " negative effects");
        }

        return text;

    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        try {
            GiveOrTake action = data.getPotionAction();

            for (LivingEntity t : targets) {
                if (action == GiveOrTake.GIVE_STACKS) {
                    StatusEffect potion = data.getPotion();

                    int dura = data.get(POTION_DURATION)
                        .intValue();
                    int str = data.getOrDefault(POTION_STRENGTH, 1D)
                        .intValue();
                    t.addStatusEffect(new StatusEffectInstance(potion, dura, str));
                } else if (action == GiveOrTake.REMOVE_STACKS) {
                    StatusEffect potion = data.getPotion();

                    t.removeStatusEffect(potion);
                } else if (action == GiveOrTake.REMOVE_NEGATIVE) {
                    int count = data.getOrDefault(COUNT, 1D)
                        .intValue();

                    for (int i = 0; i < count; i++) {

                        List<StatusEffectInstance> opt = t.getStatusEffects()
                            .stream()
                            .filter(x -> {
                                if (x.getEffectType() instanceof StatusEffectAccesor) {
                                    StatusEffectAccesor acc = (StatusEffectAccesor) x.getEffectType();
                                    return acc.my$getstatusEffectType() == StatusEffectType.HARMFUL;
                                } else {
                                    if (x.getEffectType() instanceof ExileStatusEffect) {
                                        ExileStatusEffect es = (ExileStatusEffect) x.getEffectType();
                                        return es.type == EffectType.negative;
                                    }
                                }
                                return false;
                            })
                            .collect(Collectors.toList());

                        if (!opt.isEmpty()) {
                            t.removeStatusEffect(opt.get(0)
                                .getEffectType());
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MapHolder createGive(StatusEffect effect, Double duration) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_DURATION, duration);
        dmg.put(POTION_ACTION, GiveOrTake.GIVE_STACKS.name());
        dmg.put(POTION_ID, Registry.STATUS_EFFECT.getId(effect)
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

    public MapHolder createRemove(StatusEffect effect) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_ACTION, GiveOrTake.REMOVE_STACKS.name());
        dmg.put(POTION_ID, Registry.STATUS_EFFECT.getId(effect)
            .toString());
        return dmg;
    }

    @Override
    public String GUID() {
        return "potion";
    }
}

