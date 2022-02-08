package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class ExileEffectAction extends SpellAction {

    public enum GiveOrTake {
        GIVE_STACKS, REMOVE_STACKS, REMOVE_NEGATIVE
    }

    public ExileEffectAction() {
        super(Arrays.asList(EXILE_POTION_ID, COUNT, POTION_ACTION, POTION_DURATION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        try {
            ExileEffect potion = data.getExileEffect();
            GiveOrTake action = data.getPotionAction();
            int count = data.get(COUNT)
                .intValue();
            int duration = data.get(POTION_DURATION)
                .intValue();

            targets.forEach(t -> {

                ExilePotionEvent potionEvent = EventBuilder.ofEffect(ctx.caster, t, Load.Unit(ctx.caster)
                        .getLevel(), potion, com.robertx22.age_of_exile.uncommon.effectdatas.GiveOrTake.give, duration)
                    .setSpell(ctx.calculatedSpellData.getSpell())
                    .set(x -> x.data.getNumber(EventData.STACKS).number = count)
                    .build();

                potionEvent.spellid = ctx.calculatedSpellData.getSpell()
                    .GUID();

                potionEvent.Activate();

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MapHolder giveSeconds(EffectCtx ctx, int seconds) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_DURATION, seconds * 20D);
        dmg.put(POTION_ACTION, GiveOrTake.GIVE_STACKS.name());
        dmg.put(EXILE_POTION_ID, ctx.resourcePath);
        return dmg;
    }

    public MapHolder create(String id, GiveOrTake action, Double duration) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(COUNT, 1D);
        dmg.put(POTION_DURATION, duration);
        dmg.put(POTION_ACTION, action.name());
        dmg.put(EXILE_POTION_ID, id);
        return dmg;
    }

    @Override
    public String GUID() {
        return "exile_effect";
    }
}
