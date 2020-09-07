package com.robertx22.age_of_exile.database.data.spells.contexts;

import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public class DefaultSpellCtx extends BaseSpellCtx {

    public List<LivingEntity> targets;
    // the entity the effect came from, player summons fireball. fireball hits enemy, dmg comes from fireball
    public Entity sourceEntity;

    public LivingEntity caster;

    public DefaultSpellCtx(CalculatedSpellData calculatedSpellData, List<LivingEntity> targets, Entity sourceEntity, LivingEntity caster) {
        super(calculatedSpellData);
        this.sourceEntity = sourceEntity;
        this.caster = caster;
        this.targets = targets;
    }
}
