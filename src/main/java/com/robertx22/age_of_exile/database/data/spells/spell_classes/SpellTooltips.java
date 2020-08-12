package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.List;

public class SpellTooltips {

    public static List<MutableText> singleTargetProjectile() {
        return Arrays.asList(new LiteralText("Throw a projectile."), new LiteralText("Damaging first enemy hit:"));
    }

    public static MutableText buff() {
        return new LiteralText("Applies buff to caster");
    }
}
