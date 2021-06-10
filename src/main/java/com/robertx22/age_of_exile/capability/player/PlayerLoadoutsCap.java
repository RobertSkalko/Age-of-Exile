package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.player.data.PlayerLoadoutsData;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class PlayerLoadoutsCap implements ICommonPlayerCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "chars");
    private static final String LOC = "favor";

    PlayerEntity player;

    public PlayerLoadoutsCap(PlayerEntity player) {
        this.player = player;
    }

    public PlayerLoadoutsData data = new PlayerLoadoutsData();

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        LoadSave.Save(data, nbt, LOC);
        return nbt;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.LOADOUTS;
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.data = LoadSave.Load(PlayerLoadoutsData.class, new PlayerLoadoutsData(), nbt, LOC);
    }

}