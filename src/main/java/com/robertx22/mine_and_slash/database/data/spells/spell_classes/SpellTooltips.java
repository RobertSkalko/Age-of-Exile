package com.robertx22.mine_and_slash.database.data.spells.spell_classes;

import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class SpellTooltips {

    public static MutableText singleTargetProjectile() {
        return new LiteralText("Throw a projectile, damaging first enemy hit");
    }

    public static MutableText buff() {
        return new LiteralText("Applies buff to caster");
    }
}
