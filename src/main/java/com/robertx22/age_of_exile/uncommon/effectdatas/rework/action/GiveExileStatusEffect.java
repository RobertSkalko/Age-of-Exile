package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
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
    public void activate(EffectData event, EffectSides statSource, StatData data, Stat stat) {
        ExileEffectsManager.apply(event.sourceData.getLevel(), Database.ExileEffects()
            .get(effect), event.source, event.getSide(give_to), seconds * 20);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return GiveExileStatusEffect.class;
    }
}