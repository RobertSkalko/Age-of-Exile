package com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap;

import com.robertx22.mine_and_slash.capability.bases.ICommonPlayerCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

public enum PlayerCaps {

    ENTITY_DATA {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.Unit(player);
        }
    }, STAT_POINTS {
        @Override
        public ICommonPlayerCap getCap(PlayerEntity player) {
            return Load.statPoints(player);
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
