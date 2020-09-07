package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EntityFinder {

    static boolean isTamed(LivingEntity x) {
        if (x instanceof TameableEntity) {
            TameableEntity tame = (TameableEntity) x;
            return tame.isTamed();
        }
        return false;
    }

    private static boolean isPlayer(Entity en) {
        return en instanceof PlayerEntity;
    }

    public enum EntityPredicate {
        ALLIES() {
            @Override
            public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Setup setup) {
                return list.stream()
                    .filter(x -> {
                        if (setup.isCasterPlayer()) {
                            if (isPlayer(x)) {
                                if (x.world.isClient) {
                                    return true;
                                } else {

                                    if (setup.caster.isPartOf(x)) {
                                        return true;
                                    }

                                    return TeamUtils.areOnSameTeam((ServerPlayerEntity) setup.caster, (ServerPlayerEntity) x);
                                }
                            } else {
                                return isTamed(x);
                            }

                        } else {
                            return x instanceof PlayerEntity == false && !isTamed(x);
                        }
                    })
                    .collect(Collectors.toList());
            }

            @Override
            public boolean includesCaster() {
                return true;
            }
        },
        ENEMIES {
            @Override
            public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Setup setup) {
                return list.stream()
                    .filter(x -> {
                            if (setup.isCasterPlayer()) {
                                if (isPlayer(x)) {
                                    if (x.world.isClient) {
                                        return false;
                                    } else {
                                        return !TeamUtils.areOnSameTeam((ServerPlayerEntity) setup.caster, (ServerPlayerEntity) x);
                                    }
                                } else {
                                    return !isTamed(x);
                                }
                            } else
                                return isPlayer(x);
                        }
                    )
                    .collect(Collectors.toList());
            }

            @Override
            public boolean includesCaster() {
                return false;
            }
        },
        ALL {
            @Override
            public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Setup setup) {
                return list;
            }

            @Override
            public boolean includesCaster() {
                return true;
            }
        };

        public abstract <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Setup setup);

        public abstract boolean includesCaster();
    }

    public enum SelectionType {
        RADIUS() {
            @Override
            public <T extends Entity> List<T> getEntities(Setup setup) {

                double x = setup.pos.getX();
                double y = setup.pos.getY();
                double z = setup.pos.getZ();

                double hori = setup.horizontal;
                double verti = setup.vertical;

                Box aabb = new Box(x - hori, y - verti, z - hori, x + hori, y + verti, z + hori);

                if (setup.addTestParticles) {
                    Utilities.spawnParticlesForTesting(aabb, setup.world);
                }

                List<T> entityList = setup.world.getNonSpectatingEntities(setup.entityType, aabb);

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

                Vec3d l = Utilities.getEndOfLook(entity, distance);

                double minX = Math.min(x, l.x);
                double minY = Math.min(y, l.y);
                double minZ = Math.min(z, l.z);

                double maxX = Math.max(x, l.x);
                double maxY = Math.max(y, l.y);
                double maxZ = Math.max(z, l.z);

                Box aabb = new Box(minX - horizontal, minY - vertical, minZ - horizontal,
                    maxX + horizontal, maxY + vertical, maxZ + horizontal
                );

                if (setup.addTestParticles) {
                    Utilities.spawnParticlesForTesting(aabb, setup.world);
                }

                List<T> entityList = entity.world.getNonSpectatingEntities(setup.entityType, aabb);
                entityList.removeIf(e -> e == entity);

                return entityList;
            }
        };

        public abstract <T extends Entity> List<T> getEntities(Setup setup);

    }

    public static <T extends LivingEntity> Setup<T> start(LivingEntity caster, Class<T> entityType, Vec3d pos) {
        Setup<T> setup = new Setup<T>(caster, entityType, pos);
        return setup;
    }

    public static <T extends LivingEntity> Setup<T> start(LivingEntity caster, Class<T> entityType, BlockPos p) {
        return start(caster, entityType, new Vec3d(p.getX(), p.getY(), p.getZ()));
    }

    public static class Setup<T extends LivingEntity> {

        Class<T> entityType;
        SelectionType selectionType = SelectionType.RADIUS;
        EntityPredicate entityPredicate = EntityPredicate.ENEMIES;
        LivingEntity caster;
        boolean forceExcludeCaster = false;
        World world;
        Vec3d pos;
        double radius = 1;
        double horizontal = 1;
        double vertical = 1;
        boolean addTestParticles = false;

        List<Predicate<T>> predicates = new ArrayList();

        boolean setRadius = false;

        double distanceToSearch = 10;

        public Setup(LivingEntity caster, Class<T> entityType, Vec3d pos) {
            this.entityType = entityType;
            this.caster = caster;
            this.world = caster.world;
            this.pos = pos;
        }

        public boolean isCasterPlayer() {
            return caster instanceof PlayerEntity;
        }

        public List<T> build() {

            Objects.requireNonNull(caster, "Caster can't be null");
            Objects.requireNonNull(caster, "Blockpos can't be null");
            Objects.requireNonNull(caster, "World can't be null");

            List<T> list = this.selectionType.getEntities(this);

            list.removeIf(x -> x == null);

            for (Predicate<T> predicate : predicates) {
                list.removeIf(y -> !predicate.test(y));
            }

            list = this.entityPredicate.getMatchingEntities(list, this);

            if (forceExcludeCaster || !entityPredicate.includesCaster()) {
                list.removeIf(x -> x == caster);
            }

            list.removeIf(x -> !x.isAlive());

            return list;

        }

        public Setup<T> addPredicate(Predicate<T> p) {
            this.predicates.add(p);
            return this;
        }

        public Setup<T> finder(SelectionType f) {
            this.selectionType = f;
            return this;
        }

        public Setup<T> searchFor(EntityPredicate f) {
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
