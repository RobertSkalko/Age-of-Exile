package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class MyDamageSource extends EntityDamageSource {

    public Elements element;
    public float realDamage;

    DamageSource source;

    public MyDamageSource(DamageSource s, Entity source, Elements element, float dmg) {
        super(DamageEffect.dmgSourceName, source);
        this.element = element;
        this.setBypassesArmor();
        realDamage = dmg;
        this.source = s;
    }

}
