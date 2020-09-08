package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class ExilePotionAction extends SpellAction {

    public enum PotionAction {
        GIVE_STACKS, REMOVE_STACKS
    }

    public ExilePotionAction() {
        super(Arrays.asList(POTION_ID, COUNT, POTION_ACTION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        BasePotionEffect potion = (BasePotionEffect) Registry.STATUS_EFFECT.get(new Identifier(data.get(POTION_ID)));

        PotionAction action = data.getPotionAction();

        int count = data.get(COUNT)
            .intValue();

        targets.forEach(t -> {
            if (action == PotionAction.GIVE_STACKS) {
                for (int i = 0; i < count; i++) {
                    PotionEffectUtils.apply(potion, ctx.caster, t);
                }
            } else {
                PotionEffectUtils.reduceStacks(t, potion, count);
            }
        });
    }

    public MapHolder create(BasePotionEffect effect, PotionAction action) {
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
        return "exile_potion";
    }
}

