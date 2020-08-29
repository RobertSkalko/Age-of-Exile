package com.robertx22.teleport;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public class TeleportComponent implements ICommonPlayerCap {

    public PlayerEntity player;
    public TeleportData data = new TeleportData();

    public TeleportComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.TELEPORT;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        try {
            this.data = LoadSave.Load(TeleportData.class, new TeleportData(), tag, "data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        try {
            LoadSave.Save(data, tag, "data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }
}
