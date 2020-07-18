package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import java.util.function.Predicate;

public class SpellPredicate {
    public Predicate<LivingEntity> predicate;
    public Text text;

    public SpellPredicate(Predicate<LivingEntity> predicate, Text text) {
        this.predicate = predicate;
        this.text = text;

    }

}


