package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.stream.Collectors;

public enum AllyOrEnemy {
    allies() {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, EntityFinder.Setup setup) {
            return list.stream()
                .filter(x -> {
                    if (setup.isCasterPlayer()) {
                        if (EntityFinder.isPlayer(x)) {
                            if (x.world.isClient) {
                                return true;
                            } else {
                                if (setup.caster.isPartOf(x)) {
                                    return true;
                                }

                                return TeamUtils.areOnSameTeam((ServerPlayerEntity) setup.caster, (ServerPlayerEntity) x);
                            }
                        } else {
                            return EntityFinder.isTamed(x);
                        }

                    } else {
                        return x instanceof PlayerEntity == false && !EntityFinder.isTamed(x);
                    }
                })
                .collect(Collectors.toList());
        }

        @Override
        public boolean includesCaster() {
            return true;
        }
    },
    enemies {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, EntityFinder.Setup setup) {
            return list.stream()
                .filter(x -> {
                        if (setup.isCasterPlayer()) {
                            if (EntityFinder.isPlayer(x)) {
                                if (x.world.isClient) {
                                    return false;
                                } else {
                                    return !TeamUtils.areOnSameTeam((ServerPlayerEntity) setup.caster, (ServerPlayerEntity) x);
                                }
                            } else {
                                return !EntityFinder.isTamed(x);
                            }
                        } else
                            return EntityFinder.isPlayer(x);
                    }
                )
                .collect(Collectors.toList());
        }

        @Override
        public boolean includesCaster() {
            return false;
        }
    },
    all {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, EntityFinder.Setup setup) {
            return list;
        }

        @Override
        public boolean includesCaster() {
            return true;
        }
    };

    public abstract <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, EntityFinder.Setup setup);

    public abstract boolean includesCaster();
}
