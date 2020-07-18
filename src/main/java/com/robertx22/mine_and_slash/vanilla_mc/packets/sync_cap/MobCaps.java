package com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap;

import com.robertx22.mine_and_slash.capability.bases.ICommonPlayerCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

public enum MobCaps {

    ENTITY_DATA {
        @Override
        public ICommonPlayerCap getCap(LivingEntity en) {
            return Load.Unit(en);
        }
    },
    BOSS {
        @Override
        public ICommonPlayerCap getCap(LivingEntity en) {
            return Load.Unit(en);
        }
    };

    MobCaps() {

    }

    public abstract ICommonPlayerCap getCap(LivingEntity player);

}
