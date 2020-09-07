package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bosses;

import com.robertx22.age_of_exile.mobs.bosses.bases.ChannelAction.ChannelType;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.OnTickAction;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class ChannelEffect extends BasePotionEffect {
    public static final ChannelEffect BAD_FOR_PLAYER = new ChannelEffect(ChannelType.BAD_FOR_PLAYER);
    public static final ChannelEffect GOOD_FOR_BOSS = new ChannelEffect(ChannelType.GOOD_FOR_BOSS);

    public static ChannelEffect getFor(ChannelType type) {
        if (type == ChannelType.BAD_FOR_PLAYER) {
            return BAD_FOR_PLAYER;
        }
        if (type == ChannelType.GOOD_FOR_BOSS) {
            return GOOD_FOR_BOSS;
        }
        return null;

    }

    ChannelType type;

    protected ChannelEffect(ChannelType type1) {
        super(StatusEffectType.HARMFUL, 50000);
        this.type = type1;
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "2107DE5E-5CE8-4030-940E-514C1F160890",
            -100F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );

        this.tickActions.add(new OnTickAction(ctx -> {
            LivingEntity en = ctx.entity;
            if (type == ChannelType.BAD_FOR_PLAYER) {
                ParticleEnum.sendToClients(
                    en.getBlockPos(), en.world,
                    new ParticlePacketData(en.getPos(), ParticleEnum.AOE).radius(en.getHeight() * 0.5F)
                        .motion(new Vec3d(0, 0, 0))
                        .type(ParticleTypes.WITCH)
                        .amount((int) (en.getHeight() * 5)));

            }
            if (type == ChannelType.GOOD_FOR_BOSS) {
                ParticleEnum.sendToClients(
                    en.getBlockPos(), en.world,
                    new ParticlePacketData(en.getPos(), ParticleEnum.AOE).radius(en.getHeight() * 0.5F)
                        .motion(new Vec3d(0, 0, 0))
                        .type(ParticleTypes.HAPPY_VILLAGER)
                        .amount((int) (en.getHeight() * 5)));
            }

            return ctx;
        }, null));

    }

    @Override
    public ValueCalculationData getCalc(LivingEntity caster) {
        return ValueCalculationData.base(5);
    }

    @Override
    public String GUID() {
        return type.id + "_" + "channel";
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 10;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 1;
    }

    @Override
    public String locNameForLangFile() {
        return "Channel";
    }

}
