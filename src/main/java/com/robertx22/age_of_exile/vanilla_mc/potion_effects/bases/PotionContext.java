package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ExtraPotionData;
import net.minecraft.entity.LivingEntity;

public class PotionContext {
    public LivingEntity entity;
    public ExtraPotionData data;
    public LivingEntity caster;
    public EntityCap.UnitData casterData;
    public EntityCap.UnitData entityData;
    public PlayerSpellCap.ISpellsCap spellsCap;

    public PotionContext(LivingEntity entity, ExtraPotionData data, LivingEntity caster) {
        this.entity = entity;
        this.data = data;
        this.caster = caster;

        this.casterData = Load.Unit(caster);
        this.entityData = Load.Unit(entity);

        this.spellsCap = Load.spells(caster);
    }
}
