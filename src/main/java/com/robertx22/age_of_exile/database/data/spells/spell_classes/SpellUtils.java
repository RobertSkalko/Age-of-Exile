package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.entities.IDatapackSpellEntity;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
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

    /*
        public static void shootProjectile(Vec3d pos, PersistentProjectileEntity projectile, LivingEntity caster, float speed) {

            ((Entity) projectile).updatePosition(pos.x, pos.getY() + caster.getStandingEyeHeight() - 0.1F, pos.z);

            projectile.setProperties(caster, caster.pitch, caster.yaw, 0.0F, speed, 1F);

        }


     */
    public static void shootProjectile(Vec3d pos, PersistentProjectileEntity projectile, LivingEntity caster, float speed,
                                       float pitch, float yaw) {

        // pos = pos.add(caster.getRotationVector()
        //   .multiply(0.25F));

        ((Entity) projectile).updatePosition(pos.x, caster.getEyeY() - 0.1F, pos.z);

        projectile.setProperties(caster, pitch, yaw, 0, speed, 1F);

    }

    public static void initSpellEntity(Entity spellEntity, LivingEntity caster, EntitySavedSpellData data, MapHolder holder) {

        IDatapackSpellEntity se = (IDatapackSpellEntity) spellEntity;
        se.init(caster, data, holder);
    }

    public static void heal(Spell spell, LivingEntity en, float amount) {
        SpellHealEffect heal = new SpellHealEffect(
            new ResourcesData.Context(Load.Unit(en), en, ResourceType.HEALTH,
                amount, ResourcesData.Use.RESTORE,
                spell
            ));
        heal.Activate();
    }

}
