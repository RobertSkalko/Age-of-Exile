package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.EntityCalcSpellConfigs;
import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.spells.EntitySpellData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellHealEffect;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class SpellUtils {

    public static void summonLightningStrike(Entity entity) {

        LightningEntity lightningboltentity = new LightningEntity(EntityType.LIGHTNING_BOLT, entity.world);  //boolean true means it's only an effect!'

        lightningboltentity.setPos((double) entity.getX() + 0.5D,
            (double) entity.getY(),
            (double) entity.getZ() + 0.5D);

        lightningboltentity.setCosmetic(true);

        SoundUtils.playSound(entity, SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, 1, 1);

        addLightningBolt(((ServerWorld) entity.world), lightningboltentity);

    }

    public static void addLightningBolt(ServerWorld world, LightningEntity entityIn) {
        world.getServer()
            .getPlayerManager()
            .sendToAround((PlayerEntity) null, entityIn.getX(), entityIn.getY(), entityIn.getZ(), 50, world.getRegistryKey()
                , new EntitySpawnS2CPacket(entityIn));
    }

    public static void setupProjectileForCasting(PersistentProjectileEntity projectile, LivingEntity caster, float speed) {
        Vec3d pos = caster.getPos();

        ((Entity) projectile).updatePosition(pos.x, caster.getY() + caster.getStandingEyeHeight() - 0.1F, pos.z);

        projectile.setProperties(caster, caster.pitch, caster.yaw, 0.0F, speed, 1F);

    }

    /*
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

     */

    public static void setupProjectileForCasting(PersistentProjectileEntity projectile, LivingEntity caster, float speed,
                                                 float pitch, float yaw) {
        Vec3d pos = caster.getPos();

        ((Entity) projectile).updatePosition(pos.x, caster.getY() + caster.getStandingEyeHeight() - 0.1F, pos.z);

        projectile.setProperties(caster, pitch, yaw, 0.0F, speed, 1F);

    }

    public static <T extends Entity> T getSpellEntity(EntityCalcSpellConfigs config, T spellEntity,

                                                      SkillGemData skillgem,

                                                      LivingEntity caster

    ) {

        ISpellEntity se = (ISpellEntity) spellEntity;

        int lifeInTicks = se.getDefaultLifeInTicks();

        EntitySpellData syncData = new EntitySpellData(skillgem, caster, config);

        se.setSpellData(syncData);

        se.initSpellEntity();

        return spellEntity;

    }

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
                    .getCalc(ctx.skillGem)
                    .getCalculatedValue(ctx.data, ctx.skillGem), ResourcesData.Use.RESTORE,
                ctx.spell
            ));
        heal.Activate();
    }

    public static void healCasterMagicShield(SpellCastContext ctx) {
        ctx.data
            .modifyResource(new ResourcesData.Context(ctx.data, ctx.caster, ResourcesData.Type.MAGIC_SHIELD,
                ctx.getConfigFor(ctx.ability)
                    .getCalc(ctx.skillGem)
                    .getCalculatedValue(ctx.data, ctx.skillGem), ResourcesData.Use.RESTORE,
                ctx.spell
            ));
    }

}
