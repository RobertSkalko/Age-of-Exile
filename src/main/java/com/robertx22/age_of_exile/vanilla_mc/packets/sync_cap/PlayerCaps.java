package com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

public enum PlayerCaps {

    ENTITY_DATA {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.Unit(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return true;
        }
    },
    STAT_POINTS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.statPoints(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return true;
        }
    },
    DEATH_STATS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return ModRegistry.COMPONENTS.PLAYER_DEATH_DATA.get(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return false;
        }
    },
    PLAYER_SKILLS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.playerSkills(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return false;
        }
    },
    FAVOR {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.favor(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return false;
        }
    },
    MAPS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.playerMaps(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return false;
        }
    },
    TEAM {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.team(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return false;
        }
    },
    CHARACTERS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.characters(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return false;
        }
    },
    ENTITY_PERKS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.perks(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return true;
        }
    },
    SPELLS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.spells(player);
        }

        @Override
        public boolean shouldSaveToPlayerCharacter() {
            return true;
        }
    };

    PlayerCaps() {

    }

    public abstract ICommonPlayerCap getCap(PlayerEntity player);

    public abstract boolean shouldSaveToPlayerCharacter();

}
