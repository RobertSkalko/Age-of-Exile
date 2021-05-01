package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;

public interface IStatEffect {

    public enum Priority {
        First(0),
        Second(1),
        Third(2),
        Fourth(3),
        Fifth(4),
        AlmostLast(99),
        Last(100);

        Priority(int priority) {
            this.priority = priority;
        }

        public int priority = 0;

        public static int afterThis(int other) {
            return other + 1;

        }

        public static int beforeThis(int other) {
            return other - 1;

        }
    }

    public enum EffectSides {
        Source("source"),
        Target("target");

        public String id;

        EffectSides(String id) {
            this.id = id;
        }
    }

    public abstract EffectSides Side();

    public abstract int GetPriority();

    public abstract void TryModifyEffect(EffectData effect, Unit source, StatData data, Stat stat);

}
