package com.robertx22.age_of_exile.database.data.stats.datapacks.test;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

import java.util.ArrayList;
import java.util.List;

public class DataPackStatEffect implements IStatEffect {

    public int order = 0;

    public EffectSides side = EffectSides.Source;

    public List<String> ifs = new ArrayList<>();

    public List<String> effects = new ArrayList<>();

    public List<String> events = new ArrayList<>();

    @Override
    public EffectSides Side() {
        return side;
    }

    @Override
    public int GetPriority() {
        return order;
    }

    @Override
    public void TryModifyEffect(EffectData effect, Unit source, StatData data, Stat stat) {

        if (events.contains(effect.GUID())) {

            if (ifs.stream()
                .allMatch(x -> Database.StatConditions()
                    .get(x)
                    .can(effect, data, stat))) {

                effects.forEach(x -> Database.StatEffects()
                    .get(x)
                    .activate(effect, data, stat));

            }

        }
    }
}
