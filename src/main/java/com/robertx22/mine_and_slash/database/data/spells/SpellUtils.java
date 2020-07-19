package com.robertx22.mine_and_slash.database.data.spells;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.EntityCalcSpellConfigs;
import com.robertx22.mine_and_slash.saveclasses.spells.EntitySpellData;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourcesData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpellHealEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnGlobalS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Function;

public class SpellUtils {

    public static void summonLightningStrike(Entity entity) {

        LightningEntity lightningboltentity = new LightningEntity(EntityType.LIGHTNING_BOLT, entity.world);  //boolean true means it's only an effect!'

        lightningboltentity.setPos((double) entity.getX() + 0.5D,
            (double) entity.getY(),
            (double) entity.getZ() + 0.5D);

        lightningboltentity.method_29498(true);

        addLightningBolt(((ServerWorld) entity.world), lightningboltentity);

    }

    public static void addLightningBolt(ServerWorld world, LightningEntity entityIn) {
        world.getServer()
            .getPlayerManager()
            .sendToAround((PlayerEntity) null, entityIn.getX(), entityIn.getY(), entityIn.getZ(), 50, world.getDimension()
                .getType(), new EntitySpawnGlobalS2CPacket(entityIn));
    }

    public static void setupProjectileForCasting(PersistentProjectileEntity projectile, LivingEntity caster, float speed) {
        Vec3d pos = caster.getPos();

        ((Entity) projectile).updatePosition(pos.x, caster.getY() + caster.getStandingEyeHeight() - 0.1F, pos.z);

        projectile.setProperties(caster, caster.pitch, caster.yaw, 0.0F, speed, 1F);

    }

    public static void castTripleProjectileInCone(EntityCalcSpellConfigs config, float apart, BaseSpell spell, Function<World, PersistentProjectileEntity> projectile, LivingEntity caster, float speed) {
        World world = caster.world;

        for (int i = 0; i < 3; i++) {

            float f = 0;

            if (i == 0) {
                f = apart;
            }
            if (i == 2) {
                f = -apart;
            }
            f *= 10;

            PersistentProjectileEntity en = (PersistentProjectileEntity) SpellUtils.getSpellEntity(config, projectile.apply(world), spell, caster);
            SpellUtils.setupProjectileForCasting(en, caster, speed, caster.pitch,
                caster.yaw + f
            );
            caster.world.spawnEntity(en);

        }

    }

    public static void setupProjectileForCasting(PersistentProjectileEntity projectile, LivingEntity caster, float speed,
                                                 float pitch, float yaw) {
        Vec3d pos = caster.getPos();

        ((Entity) projectile).updatePosition(pos.x, caster.getY() + caster.getStandingEyeHeight() - 0.1F, pos.z);

        projectile.setProperties(caster, pitch, yaw, 0.0F, speed, 1F);

    }

    public static <T extends Entity> T getSpellEntity(EntityCalcSpellConfigs config, T spellEntity,

                                                      BaseSpell spell,

                                                      LivingEntity caster

    ) {

        ISpellEntity se = (ISpellEntity) spellEntity;

        int lifeInTicks = se.getDefaultLifeInTicks();

        EntitySpellData syncData = new EntitySpellData(spell, caster, config);

        se.setSpellData(syncData);

        se.initSpellEntity();

        return spellEntity;

    }

    /*
    public static <T extends TameableEntity> T spawnSummon(T spellEntity,

                                                           BaseSpell spell,

                                                           LivingEntity caster) {

        T en = SpellUtils.getSpellEntity(spellEntity, spell, caster);
        if (caster instanceof PlayerEntity) {
            en.setTamedBy((PlayerEntity) caster);
        }
        caster.world.addEntity(en);

        return en;

    }


     */
    public static void heal(BaseSpell spell, LivingEntity en, float amount) {
        SpellHealEffect heal = new SpellHealEffect(
            new ResourcesData.Context(Load.Unit(en), en, ResourcesData.Type.HEALTH,
                amount, ResourcesData.Use.RESTORE,
                spell
            ));
        heal.Activate();
    }

    public static void healCaster(SpellCastContext ctx) {
        SpellHealEffect heal = new SpellHealEffect(
            new ResourcesData.Context(ctx.data, ctx.caster, ResourcesData.Type.HEALTH,
                ctx.getConfigFor(ctx.ability)
                    .getCalc(ctx.spellsCap, ctx.ability)
                    .getCalculatedValue(ctx.data, ctx.spellsCap, ctx.ability), ResourcesData.Use.RESTORE,
                ctx.spell
            ));
        heal.Activate();
    }

}
