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
    },
    DEATH_STATS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return ModRegistry.COMPONENTS.PLAYER_DEATH_DATA.get(player);
        }
    },
    PLAYER_SKILLS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.playerSkills(player);
        }
    },
    FAVOR {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.favor(player);
        }
    },
    ENTITY_PERKS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.perks(player);
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
