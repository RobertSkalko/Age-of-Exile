package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class MyDamageSource extends EntityDamageSource {

    public Elements element = Elements.Physical;
    public int realDamage = 0;

    DamageSource source;

    public MyDamageSource(DamageSource s, String damageTypeIn, Entity source, Elements element, int dmg) {
        super(damageTypeIn, source);
        // this.setDamageBypassesArmor();
        this.element = element;
        realDamage = dmg;

        this.source = s;

    }

}
