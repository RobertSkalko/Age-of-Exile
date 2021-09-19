package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.IFormattableTextComponent;

import java.util.function.Predicate;

public class SpellPredicate {
    public Predicate<LivingEntity> predicate;
    public IFormattableTextComponent text;

    public SpellPredicate(Predicate<LivingEntity> predicate, IFormattableTextComponent text) {
        this.predicate = predicate;
        this.text = text;

    }

}


