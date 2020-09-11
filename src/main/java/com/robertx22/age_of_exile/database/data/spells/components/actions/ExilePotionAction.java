package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class ExilePotionAction extends SpellAction implements ICTextTooltip {

    public enum GiveOrTake {
        GIVE_STACKS, REMOVE_STACKS
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        BasePotionEffect potion = (BasePotionEffect) data.getExilePotion();
        GiveOrTake action = data.getPotionAction();
        int count = data.get(COUNT)
            .intValue();

        boolean isStackable = potion.getMaxStacks() > 1;

        if (action == GiveOrTake.GIVE_STACKS) {
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

        text.append(potion.locName());

        return text;

    }

    public ExilePotionAction() {
        super(Arrays.asList(EXILE_POTION_ID, COUNT, POTION_ACTION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        BasePotionEffect potion = (BasePotionEffect) data.getExilePotion();
        GiveOrTake action = data.getPotionAction();
        int count = data.get(COUNT)
            .intValue();

        targets.forEach(t -> {
            if (action == GiveOrTake.GIVE_STACKS) {
                for (int i = 0; i < count; i++) {
                    PotionEffectUtils.apply(potion, ctx.caster, t);
                }
            } else {
                PotionEffectUtils.reduceStacks(t, potion, count);
            }
        });
    }

    public MapHolder create(BasePotionEffect effect, GiveOrTake action) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_ACTION, action.name());
        dmg.put(EXILE_POTION_ID, Registry.STATUS_EFFECT.getId(effect)
            .toString());
        return dmg;
    }

    @Override
    public String GUID() {
        return "exile_potion";
    }
}

