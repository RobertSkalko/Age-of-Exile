package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.entities.IDatapackSpellEntity;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class SpellUtils {

    public static void summonLightningStrike(Entity entity) {

        LightningBoltEntity lightningboltentity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, entity.level);  //boolean true means it's only an effect!'

        lightningboltentity.setPosRaw((double) entity.getX() + 0.5D,
            (double) entity.getY(),
            (double) entity.getZ() + 0.5D);

        lightningboltentity.setVisualOnly(true);

        SoundUtils.playSound(entity, SoundEvents.LIGHTNING_BOLT_IMPACT, 1, 1);

        addLightningBolt(((ServerWorld) entity.level), lightningboltentity);

    }

    public static void addLightningBolt(ServerWorld world, LightningBoltEntity entityIn) {
        world.getServer()
            .getPlayerList()
            .broadcast((PlayerEntity) null, entityIn.getX(), entityIn.getY(), entityIn.getZ(), 50, world.dimension()
                , new SSpawnObjectPacket(entityIn));
    }

    public static void shootProjectile(Vector3d pos, AbstractArrowEntity projectile, LivingEntity caster, float speed,
                                       float pitch, float yaw) {

        ((Entity) projectile).setPos(pos.x, pos.y, pos.z);

        projectile.shootFromRotation(caster, pitch, yaw, 0, speed, 1F);

    }

    public static void initSpellEntity(Entity spellEntity, LivingEntity caster, EntitySavedSpellData data, MapHolder holder) {

        IDatapackSpellEntity se = (IDatapackSpellEntity) spellEntity;
        se.init(caster, data, holder);
    }

}
