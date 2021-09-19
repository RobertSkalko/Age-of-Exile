package com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

public enum PlayerCaps {

    ENTITY_DATA {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.Unit(player);
        }

    },

    PLAYER_SKILLS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.playerSkills(player);
        }

    },

    PLAYER_RPG_DATA {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.playerRPGData(player);
        }

    },

    SPELLS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.spells(player);
        }

    };

    PlayerCaps() {

    }

    public abstract ICommonPlayerCap getCap(PlayerEntity player);

}
