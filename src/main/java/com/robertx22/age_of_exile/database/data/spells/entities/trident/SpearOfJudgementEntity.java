package com.robertx22.age_of_exile.database.data.spells.entities.trident;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.JudgementEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class SpearOfJudgementEntity extends BaseTridentEntity {

    public SpearOfJudgementEntity(World world) {
        super(ModRegistry.ENTITIES.HOLY_SPEAR, world);
    }

    public SpearOfJudgementEntity(EntityType type, World world) {
        super(type, world);
    }

    @Override
    public void onHit(LivingEntity en) {
        super.onHit(en);
        PotionEffectUtils.apply(JudgementEffect.INSTANCE, getCaster(), en);
    }
}
