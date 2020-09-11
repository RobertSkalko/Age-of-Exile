package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class PotionAction extends SpellAction implements ICTextTooltip {

    public PotionAction() {
        super(Arrays.asList(POTION_ID, POTION_ACTION));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        StatusEffect potion = data.getPotion();
        ExilePotionAction.GiveOrTake action = data.getPotionAction();

        if (action == ExilePotionAction.GiveOrTake.GIVE_STACKS) {
            text.append("Gives ");
        } else {
            text.append("Removes ");
        }

        text.append(potion.getName());

        return text;

    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        StatusEffect potion = data.getPotion();
        ExilePotionAction.GiveOrTake action = data.getPotionAction();

        targets.forEach(t -> {
            if (action == ExilePotionAction.GiveOrTake.GIVE_STACKS) {
                int dura = data.get(POTION_DURATION)
                    .intValue();
                int str = data.get(POTION_STRENGTH)
                    .intValue();
                t.addStatusEffect(new StatusEffectInstance(potion, dura, str));
            } else {
                t.removeStatusEffect(potion);
            }
        });
    }

    public MapHolder create(StatusEffect effect, ExilePotionAction.GiveOrTake action) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_ACTION, action.name());
        dmg.put(POTION_ID, Registry.STATUS_EFFECT.getId(effect)
            .toString());
        return dmg;
    }

    @Override
    public String GUID() {
        return "potion";
    }
}

