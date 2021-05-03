package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.ModifyResourceEffect;
import net.minecraft.entity.LivingEntity;

public class ModifyResourceContext {

    public String spell = null;

    public EntityCap.UnitData sourceData;
    public LivingEntity source;

    public EntityCap.UnitData targetData;
    public LivingEntity target;

    public ResourceType type;
    public float amount;
    public ResourcesData.Use use;

    public boolean statsCalculated = false;

    public ModifyResourceContext setSpell(String id) {
        this.spell = id;
        return this;
    }

    public ModifyResourceContext(EntityCap.UnitData data, LivingEntity entity, ResourceType type, float amount, ResourcesData.Use use) {
        this.targetData = data;
        this.target = entity;
        this.sourceData = data;
        this.source = entity;
        this.type = type;
        this.amount = amount;
        this.use = use;
        calculateStats();
    }

    public ModifyResourceContext(LivingEntity caster, LivingEntity target, ResourceType type, float amount, ResourcesData.Use use) {
        this.targetData = Load.Unit(target);
        this.target = target;
        this.sourceData = Load.Unit(caster);
        this.source = caster;
        this.type = type;
        this.amount = amount;
        this.use = use;

        calculateStats();
    }

    public ModifyResourceContext(LivingEntity caster, LivingEntity target, EntityCap.UnitData casterData, EntityCap.UnitData targetData, ResourceType type,
                                 float amount, ResourcesData.Use use, Spell spell) {
        this.targetData = targetData;
        this.target = target;
        this.sourceData = casterData;
        this.source = caster;
        this.type = type;
        this.amount = amount;
        this.use = use;
        calculateStats();
    }

    private void calculateStats() {
        if (!statsCalculated) {
            new ModifyResourceEffect(this).Activate();
        }
    }

}
