package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.advancements.criterias.PlayerLevelCriteria;
import com.robertx22.age_of_exile.mixins.CriteriaAccessor;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ModCriteria {
    private static final Map<Identifier, Criterion<?>> VALUES = CriteriaAccessor.getValues();

    private static <T extends Criterion<?>> T register(T criterion) {
        if (VALUES.containsKey(criterion.getId())) {
            throw new IllegalArgumentException("Duplicate criterion id " + criterion.getId());
        } else {
            VALUES.put(criterion.getId(), criterion);
            return criterion;
        }
    }

    public static final PlayerLevelCriteria PLAYER_LEVEL =
        register(new PlayerLevelCriteria());

    public static void init() {

    }

}
