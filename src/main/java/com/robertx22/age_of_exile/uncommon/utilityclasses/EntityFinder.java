package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;

public class EntityFinder {

    public static boolean isTamed(LivingEntity x) {
        if (x instanceof TamableAnimal) {
            TamableAnimal tame = (TamableAnimal) x;
            return tame.isTame();
        }
        return false;
    }

    public enum SelectionType {
        RADIUS() {
            @Override
            public <T extends Entity> List<T> getEntities(Setup setup) {

                double x = setup.pos.x();
                double y = setup.pos.y();
                double z = setup.pos.z();

                double hori = setup.horizontal;
                double verti = setup.vertical;

                AABB aabb = new AABB(x - hori, y - verti, z - hori, x + hori, y + verti, z + hori);

                if (setup.addTestParticles) {
                    Utilities.spawnParticlesForTesting(aabb, setup.world);
                }

                List<T> entityList = setup.world.getEntitiesOfClass(setup.entityType, aabb);

                /* // TODO raycast remove wall cheese, experimental
                entityList.removeIf(e -> {

                    BlockHitResult result = e.world.raycast(new RaycastContext(e.getPos(), setup.caster.getPos(), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, e));

                    if (result.getPos()
                        .distanceTo(setup.caster.getPos()) < 2) {
                        return false;
                    }

                    return true;

                });

                 */

                return entityList;
            }
        },

        IN_FRONT {
            @Override
            public <T extends Entity> List<T> getEntities(Setup setup) {

                LivingEntity entity = setup.caster;

                double distance = setup.distanceToSearch;

                double horizontal = setup.horizontal;
                double vertical = setup.vertical;

                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();

                Vector3d l = Utilities.getEndOfLook(entity, distance);

                double minX = Math.min(x, l.x);
                double minY = Math.min(y, l.y);
                double minZ = Math.min(z, l.z);

                double maxX = Math.max(x, l.x);
                double maxY = Math.max(y, l.y);
                double maxZ = Math.max(z, l.z);

                AABB aabb = new AABB(minX - horizontal, minY - vertical, minZ - horizontal,
                    maxX + horizontal, maxY + vertical, maxZ + horizontal
                );

                if (setup.addTestParticles) {
                    Utilities.spawnParticlesForTesting(aabb, setup.world);
                }

                List<T> entityList = entity.level.getEntitiesOfClass(setup.entityType, aabb);
                entityList.removeIf(e -> e == entity);

                return entityList;
            }
        };

        public abstract <T extends Entity> List<T> getEntities(Setup setup);

    }

    public static <T extends LivingEntity> Setup<T> start(LivingEntity caster, Class<T> entityType, Vector3d pos) {
        Setup<T> setup = new Setup<T>(caster, entityType, pos);
        return setup;
    }

    public static <T extends LivingEntity> Setup<T> start(LivingEntity caster, Class<T> entityType, BlockPos p) {
        return start(caster, entityType, new Vector3d(p.getX(), p.getY(), p.getZ()));
    }

    public static class Setup<T extends LivingEntity> {

        Class<T> entityType;
        SelectionType selectionType = SelectionType.RADIUS;
        AllyOrEnemy entityPredicate = AllyOrEnemy.enemies;
        LivingEntity caster;
        boolean forceExcludeCaster = false;
        World world;
        Vector3d pos;
        double radius = 1;
        double horizontal = 1;
        double vertical = 1;
        boolean addTestParticles = false;

        double distanceToSearch = 10;

        public Setup(LivingEntity caster, Class<T> entityType, Vector3d pos) {
            Objects.requireNonNull(caster);
            this.entityType = entityType;
            this.caster = caster;
            this.world = caster.level;
            this.pos = pos;
        }

        public List<T> build() {

            Objects.requireNonNull(caster, "Caster can't be null");
            Objects.requireNonNull(caster, "Blockpos can't be null");
            Objects.requireNonNull(caster, "World can't be null");

            List<T> list = this.selectionType.getEntities(this);

            list.removeIf(x -> x == null);

            list = this.entityPredicate.getMatchingEntities(list, this.caster);

            if (forceExcludeCaster || !entityPredicate.includesCaster()) {
                list.removeIf(x -> x == caster);
            }

            list.removeIf(x -> !x.isAlive());

            return list;

        }

        public Setup<T> finder(SelectionType f) {
            this.selectionType = f;
            return this;
        }

        public Setup<T> searchFor(AllyOrEnemy f) {
            this.entityPredicate = f;
            return this;
        }

        public Setup<T> distance(double distance) {
            this.distanceToSearch = distance;
            return this;
        }

        public Setup<T> height(double rad) {
            this.vertical = rad;

            return this;
        }

        public Setup<T> radius(double rad) {
            this.radius = rad;
            this.horizontal = rad;
            this.vertical = rad;
            return this;
        }

        public Setup<T> excludeCaster(boolean bool) {
            this.forceExcludeCaster = bool;
            return this;
        }

    }
}
