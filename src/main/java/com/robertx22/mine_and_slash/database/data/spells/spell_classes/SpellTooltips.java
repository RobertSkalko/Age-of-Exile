package com.robertx22.mine_and_slash.database.data.spells.spell_classes;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class SpellTooltips {

    public static Text singleTargetProjectile() {
        return new LiteralText("Throw a projectile, damaging first enemy hit");
    }

    public static Text buff() {
        return new LiteralText("Applies buff to caster");
    }
}
