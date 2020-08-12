package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;

import java.util.function.Predicate;

public class SpellPredicate {
    public Predicate<LivingEntity> predicate;
    public MutableText text;

    public SpellPredicate(Predicate<LivingEntity> predicate, MutableText text) {
        this.predicate = predicate;
        this.text = text;

    }

}


