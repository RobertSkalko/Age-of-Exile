package com.robertx22.age_of_exile.uncommon.effectdatas.rework.components;

public class NumberComparison {

    public Type type;
    public float num = 0;

    public NumberComparison(Type comparison, float num) {
        this.type = comparison;
        this.num = num;
    }

    private NumberComparison() {
    }

    public enum Type {

        is_higher_than() {
            @Override
            public boolean is(float comparisonTarget, float valueFromEvent) {
                return valueFromEvent > comparisonTarget;
            }
        },
        is_lower_than() {
            @Override
            public boolean is(float comparisonTarget, float valueFromEvent) {
                return valueFromEvent < comparisonTarget;
            }
        };

        public abstract boolean is(float comparisonTarget, float valueFromEvent);

    }

    public boolean is(float valueFromEvent) {
        return type.is(num, valueFromEvent);
    }

}
