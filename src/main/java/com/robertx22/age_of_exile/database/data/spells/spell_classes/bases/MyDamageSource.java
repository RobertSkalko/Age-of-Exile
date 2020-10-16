package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class MyDamageSource extends EntityDamageSource {

    public Elements element;
    public float realDamage;

    DamageSource source;

    public MyDamageSource(DamageSource s, Entity source, Elements element, float dmg) {
        super(source(s), source);
        this.element = element;
        this.setBypassesArmor();
        realDamage = dmg;
        this.source = s;

    }

    static String source(DamageSource s) {
        if (s == null) {
            return DamageEffect.dmgSourceName;

        }
        return s.name;
    }
}