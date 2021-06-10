package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.GiveOrTake;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class RemoveExileEffectAction extends StatEffect {

    public EffectSides remove_from;
    public String effect = "";
    public int stacks = 1;

    public RemoveExileEffectAction(String effect, EffectSides remove_from) {
        super("give_" + effect + "_to_" + remove_from.id, "remove_exile_effect");
        this.remove_from = remove_from;
        this.effect = effect;
    }

    public RemoveExileEffectAction() {
        super("", "remove_exile_effect");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        ExilePotionEvent potionEvent = EventBuilder.ofEffect(event.getSide(statSource), event.getSide(remove_from), Load.Unit(event.getSide(statSource))
            .getLevel(), Database.ExileEffects()
            .get(effect), GiveOrTake.take, 1)
            .set(x -> x.data.getNumber(EventData.STACKS).number = stacks)
            .build();
        potionEvent.Activate();
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return RemoveExileEffectAction.class;
    }
}
