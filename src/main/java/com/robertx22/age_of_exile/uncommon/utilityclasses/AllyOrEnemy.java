package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.stream.Collectors;

public enum AllyOrEnemy {
    allies() {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, LivingEntity caster) {
            return list.stream()
                .filter(x -> !is(caster, x))
                .collect(Collectors.toList());
        }

        @Override
        public boolean is(LivingEntity caster, LivingEntity target) {
            return !enemies.is(caster, target);
        }

        @Override
        public boolean includesCaster() {
            return true;
        }
    },
    enemies {
        @Override
        public boolean is(LivingEntity caster, LivingEntity target) {

            if (caster instanceof PlayerEntity) {
                if (EntityFinder.isTamed(target)) {
                    return false;
                }
                if (target instanceof PlayerEntity) {
                    return !TeamUtils.areOnSameTeam((PlayerEntity) caster, (PlayerEntity) target);
                }
            } else {
                if (target instanceof PlayerEntity) {
                    return true;
                } else {
                    return false;
                }
            }

            return true;
        }

        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, LivingEntity caster) {
            return list.stream()
                .filter(x -> !is(caster, x))
                .collect(Collectors.toList());
        }

        @Override
        public boolean includesCaster() {
            return false;
        }
    },
    all {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, LivingEntity caster) {
            return list;
        }

        @Override
        public boolean is(LivingEntity caster, LivingEntity target) {
            return true;
        }

        @Override
        public boolean includesCaster() {
            return true;
        }
    };

    public abstract <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, LivingEntity caster);

    public abstract boolean is(LivingEntity caster, LivingEntity target);

    public abstract boolean includesCaster();
}
