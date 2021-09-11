package com.robertx22.age_of_exile.database.data.value_calc;

public class ValueCalcBuilder {

    public static ValueCalculation of(String id) {
        ValueCalculation data = new ValueCalculation();
        data.id = id;
        data.addToSerializables();
        return data;
    }

    public static ValueCalculation baseValue(int min, int max) {
        
    }
}
