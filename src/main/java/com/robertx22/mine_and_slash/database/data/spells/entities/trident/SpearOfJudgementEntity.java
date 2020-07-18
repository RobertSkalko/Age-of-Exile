package com.robertx22.mine_and_slash.database.data.spells.entities.trident;

import com.robertx22.mine_and_slash.mmorpg.registers.common.EntityRegister;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine.JudgementEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class SpearOfJudgementEntity extends BaseTridentEntity {

    public SpearOfJudgementEntity(World world) {
        super(EntityRegister.HOLY_SPEAR, world);
    }

    public SpearOfJudgementEntity(EntityType type, World world) {
        super(type, world);
    }

    public SpearOfJudgementEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(EntityRegister.HOLY_SPEAR, world);
    }

    @Override
    public void onHit(LivingEntity en) {
        super.onHit(en);
        PotionEffectUtils.apply(JudgementEffect.INSTANCE, getCaster(), en);
    }
}
