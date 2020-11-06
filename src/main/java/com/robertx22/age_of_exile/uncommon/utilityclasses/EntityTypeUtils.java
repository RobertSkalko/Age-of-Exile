package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Npc;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityTypeUtils {

    public enum EntityClassification {
        MOB("mob"),
        PLAYER("player"),
        ANIMAL("animal"),
        NPC("npc"),
        OTHER("other");

        EntityClassification(String id) {
            this.id = id;
        }

        public String id;

    }

    public static EntityClassification getType(LivingEntity entity) {

        if (isMob(entity)) {
            return EntityClassification.MOB;
        } else if (isAnimal(entity)) {
            return EntityClassification.ANIMAL;
        } else if (isNPC(entity)) {
            return EntityClassification.NPC;
        } else if (entity instanceof PlayerEntity) {
            return EntityClassification.PLAYER;
        } else {
            return EntityClassification.OTHER;
        }

    }

    public static boolean isMob(Entity en) {
        if (en instanceof Monster) {
            return true;
        }
        if (en instanceof Angerable) {
            return true;
        }
        if (!en.getType()
            .getSpawnGroup()
            .isPeaceful()) {
            return true;
        }
        if (EntityType.getId(en.getType())
            .getNamespace()
            .equals(Ref.WORLD_OF_EXILE_ID)) {
            return true; // all my mobs are supposed to be rewarding
        }

        return false;
    }

    public static boolean isAnimal(Entity en) {
        return en instanceof AnimalEntity;
    }

    /**
     * has to be checked first because inpc extends ianimals
     *
     * @param en
     * @return
     */
    public static boolean isNPC(Entity en) {

        return en instanceof Npc;

    }
}
