package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class ExileEffectAction extends SpellAction implements ICTextTooltip {

    public enum GiveOrTake {
        GIVE_STACKS, REMOVE_STACKS
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        ExileEffect potion = data.getExileEffect();
        ExilePotionAction.GiveOrTake action = data.getPotionAction();
        int count = data.get(COUNT)
            .intValue();

        boolean isStackable = potion.max_stacks > 1;

        if (action == ExilePotionAction.GiveOrTake.GIVE_STACKS) {
            text.append("Gives ");
        } else {
            text.append("Removes ");
        }

        if (isStackable) {
            if (count == 1) {

                text.append("a stack of ");
            } else {
                text.append(count + "stacks of ");
            }
        }

        text.append(potion.GUID()); // todo

        return text;
    }

    public ExileEffectAction() {
        super(Arrays.asList(EXILE_POTION_ID, COUNT, POTION_ACTION, POTION_DURATION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        ExileEffect potion = data.getExileEffect();
        ExilePotionAction.GiveOrTake action = data.getPotionAction();
        int count = data.get(COUNT)
            .intValue();
        int duration = data.get(POTION_DURATION)
            .intValue();

        targets.forEach(t -> {
            if (action == ExilePotionAction.GiveOrTake.GIVE_STACKS) {
                for (int i = 0; i < count; i++) {
                    ExileEffectsManager.apply(potion, ctx.caster, t, duration);
                }
            } else {
                // ExileEffectsManager.reduceStacks(t, potion, count); // todo
            }
        });
    }

    public MapHolder create(int num, ExilePotionAction.GiveOrTake action, Double duration) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_DURATION, duration);
        dmg.put(POTION_ACTION, action.name());
        dmg.put(EXILE_POTION_ID, num + "");
        return dmg;
    }

    @Override
    public String GUID() {
        return "exile_effect";
    }
}
