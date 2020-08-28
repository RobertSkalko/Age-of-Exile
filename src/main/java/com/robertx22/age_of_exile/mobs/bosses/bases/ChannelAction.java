package com.robertx22.age_of_exile.mobs.bosses.bases;

import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import net.minecraft.entity.LivingEntity;

public abstract class ChannelAction<T extends LivingEntity & IBossMob> implements IWeighted {

    public T en;

    public ChannelAction(T en) {
        this.en = en;
    }

    public enum ChannelType {
        GOOD_FOR_BOSS("good_for_boss"), BAD_FOR_PLAYER("bad_for_player");

        public String id;

        ChannelType(String id) {
            this.id = id;
        }
    }

    public final BossData getData() {
        return en.getBossData();
    }

    public boolean isCanceled = false;

    public abstract void onFinished();

    public abstract ChannelType getChannelType();

    public abstract boolean canStartChanneling();

    public abstract int getTicksNeeded();
}
