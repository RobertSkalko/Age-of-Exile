package com.robertx22.age_of_exile.aoe_data.database.stats.base;

// add to serializables and create accesor maps

// .withAccessor<Elements>(
// then either. .generateGenericWith(Elemenets.values)   or .addSpecific(Elements.fire)
// T can be a wrapper class with multiple enums!

import com.ibm.icu.impl.Assert;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DatapackStat;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatapackStatBuilder<T> {

    private DataPackStatAccessor<T> accessor;

    private HashMap<T, DatapackStat> stats = new HashMap<>();

    private HashMap<T, Consumer<DatapackStat>> modify = new HashMap<>();

    private Function<T, String> idMaker;
    private Function<T, String> locNameMaker;
    private Function<T, String> locDescMaker;
    private Function<T, Elements> elementMaker;
    private Function<T, StatEffect> effectMaker;
    private Consumer<DatapackStat> modifyAfterDone;

    private List<StatCondition> conditions = new ArrayList<>();
    private List<StatEffect> effects = new ArrayList<>();

    private int priority = -1;
    private IStatEffect.EffectSides side = IStatEffect.EffectSides.Source;

    private List<String> events = new ArrayList<>();

    public static DatapackStatBuilder<EmptyAccessor> ofSingle(String id, Elements ele) {
        DatapackStatBuilder<EmptyAccessor> b = new DatapackStatBuilder<EmptyAccessor>();
        b.accessor = new DataPackStatAccessor<EmptyAccessor>();
        b.idMaker = x -> id;
        b.elementMaker = x -> ele;
        b.addSpecificType(EmptyAccessor.INSTANCE);
        return b;
    }

    public static <T> DatapackStatBuilder<T> of(Function<T, String> id, Function<T, Elements> ele) {
        DatapackStatBuilder<T> b = new DatapackStatBuilder<T>();
        b.accessor = new DataPackStatAccessor<T>();
        b.idMaker = id;
        b.elementMaker = ele;
        return b;
    }

    public DatapackStatBuilder<T> addAllOfType(List<T> list) {
        list.forEach(x -> addSpecificType(x));
        return this;
    }

    public DatapackStatBuilder<T> addSpecificType(T t) {
        add(t);
        return this;
    }

    public DatapackStatBuilder<T> modifyAfterDone(Consumer<DatapackStat> modifyAfterDone) {
        this.modifyAfterDone = modifyAfterDone;
        return this;
    }

    private void add(T t) {
        this.stats.put(t, new DatapackStat());
    }

    public DatapackStatBuilder<T> addCondition(StatCondition condition) {
        Objects.requireNonNull(condition);
        this.conditions.add(condition);
        return this;
    }

    public DatapackStatBuilder<T> addEffect(StatEffect effect) {
        Objects.requireNonNull(effect);
        this.effects.add(effect);
        return this;
    }

    public DatapackStatBuilder<T> addEffect(Function<T, StatEffect> effect) {
        Objects.requireNonNull(effect);
        this.effectMaker = effect;
        return this;
    }

    public DatapackStatBuilder<T> worksWithEvent(String event) {
        this.events.add(event);
        return this;
    }

    public DatapackStatBuilder<T> setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public DatapackStatBuilder<T> setSide(IStatEffect.EffectSides side) {
        this.side = side;
        return this;
    }

    public DatapackStatBuilder<T> setLocName(Function<T, String> id) {
        this.locNameMaker = id;
        return this;
    }

    public DatapackStatBuilder<T> setLocDesc(Function<T, String> id) {
        this.locDescMaker = id;
        return this;
    }

    public static Set<DatapackStat> STATS_TO_ADD_TO_SERIALIZATION = new HashSet<>();

    public DataPackStatAccessor<T> build() {

        Objects.requireNonNull(idMaker);
        Objects.requireNonNull(elementMaker);
        Objects.requireNonNull(locNameMaker);
        Objects.requireNonNull(locDescMaker);

        Assert.assrt(priority > -1);
        Assert.assrt(!events.isEmpty());

        stats.entrySet()
            .forEach(x -> {

                DatapackStat stat = x.getValue();
                stat.id = idMaker.apply(x.getKey());
                stat.ele = elementMaker.apply(x.getKey());
                stat.locdesc = locDescMaker.apply(x.getKey());
                stat.locname = locNameMaker.apply(x.getKey());

                if (modify.containsKey(x.getKey())) {
                    modify.get(x.getKey())
                        .accept(stat);
                }
                if (modifyAfterDone != null) {
                    modifyAfterDone.accept(stat);
                }

                stat.effect.order = priority;
                stat.effect.events = events;
                stat.effect.side = side;
                this.conditions.forEach(c -> stat.effect.ifs.add(c.GUID()));
                this.effects.forEach(c -> {
                    stat.effect.effects.add(c.GUID());
                });
                if (this.effectMaker != null) {
                    stat.effect.effects.add(this.effectMaker.apply(x.getKey())
                        .GUID());

                }

                accessor.add(x.getKey(), stat);

                STATS_TO_ADD_TO_SERIALIZATION.add(stat);
            });

        return this.accessor;
    }

}
