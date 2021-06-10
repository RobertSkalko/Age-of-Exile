package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.GiveOrTake;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class GiveExileStatusEffect extends StatEffect {

    public EffectSides give_to;
    public int seconds = 10;
    public String effect = "";

    public GiveExileStatusEffect(String effect, EffectSides give_to, int sec) {
        super("give_" + effect + "_to_" + give_to.id, "give_exile_effect");
        this.give_to = give_to;
        this.seconds = sec;
        this.effect = effect;
    }

    public GiveExileStatusEffect() {
        super("", "give_exile_effect");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        ExilePotionEvent potionEvent = EventBuilder.ofEffect(event.getSide(statSource), event.getSide(give_to), Load.Unit(event.getSide(statSource))
            .getLevel(), Database.ExileEffects()
            .get(effect), GiveOrTake.give, seconds * 20)
            .build();
        potionEvent.Activate();

    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return GiveExileStatusEffect.class;
    }
}