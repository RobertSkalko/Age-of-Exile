package com.robertx22.mine_and_slash.saveclasses.anti_mob_farm;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.MathHelper;

@Storable
public class AntiMobFarmChunkData {

    @Store
    private float p = 1;

    public enum Type {
        NORMAL, MOB_FARM;
    }

    public void tickDown(Type type) {
        if (type.equals(Type.MOB_FARM)) {
            this.p += 0.01F;
        } else {
            this.p += 0.05F;
        }

        clamp();
    }

    public void onMobDeath() {
        this.p = p - 0.02F;
        clamp();
    }

    public float getDropsMulti() {
        return MathHelper.clamp(p, 0, 1);
    }

    public void clamp() {
        this.p = MathHelper.clamp(p, 0, 1);
    }
}
