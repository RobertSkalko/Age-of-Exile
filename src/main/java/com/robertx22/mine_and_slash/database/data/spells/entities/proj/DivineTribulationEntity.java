package com.robertx22.mine_and_slash.database.data.spells.entities.proj;

import com.robertx22.mine_and_slash.database.data.spells.SpellUtils;
import com.robertx22.mine_and_slash.database.data.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.mine_and_slash.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.mine_and_slash.mmorpg.registers.common.EntityRegister;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacketData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class DivineTribulationEntity extends EntityBaseProjectile {

    public DivineTribulationEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public DivineTribulationEntity(World worldIn) {
        super(EntityRegister.DIVINE_TRIBULATION, worldIn);

    }

    @Override
    public void initSpellEntity() {
        this.setNoGravity(true);
        this.setDeathTime(100);

    }

    @Override
    public double radius() {
        return 3.5F;
    }

    @Override
    protected void onImpact(HitResult result) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.NETHER_STAR);
    }

    @Override
    public void onTick() {

        if (this.inGround) {
            this.remove();
            return;
        }

        int tickRate = 20;

        int ticksToRaise = 30;

        if (this.age < ticksToRaise) {
            this.setVelocity(getVelocity().add(0, 0.003F, 0));
            // go up the sky
        } else {
            this.setVelocity(0, 0, 0);
        }

        if (world.isClient) {
            if (age % 2 == 0) {
                ParticleEnum.sendToClients(
                    this, new ParticlePacketData(getPosVector(), ParticleEnum.AOE).amount(5)
                        .type(ParticleTypes.CLOUD)
                        .motion(new Vec3d(0, 0, 0)));
            }
        }

        if (this.age > ticksToRaise) {
            if (this.age % tickRate == 0) {
                if (!world.isClient) {
                    LivingEntity caster = getCaster();

                    if (caster == null) {
                        return;
                    }

                    List<LivingEntity> entities = EntityFinder.start(caster, LivingEntity.class, getPosVector())
                        .radius(radius())
                        .height(4)
                        .build();

                    entities.forEach(x -> {

                        DamageEffect dmg = dealSpellDamageTo(x, new ISpellEntity.Options().knockbacks(false)
                            .activatesEffect(false));

                        dmg.Activate();

                        SpellUtils.summonLightningStrike(x);

                    });
                }
            }

        }

    }

}
