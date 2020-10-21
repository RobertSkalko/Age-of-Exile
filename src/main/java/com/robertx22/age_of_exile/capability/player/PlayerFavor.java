package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.Comparator;

public class PlayerFavor implements ICommonPlayerCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "favor");
    private static final String LOC = "favor";

    PlayerEntity player;

    int favor = 0;

    public FavorRank getRank() {
        try {
            return SlashRegistry.FavorRanks()
                .getFiltered(x -> x.min >= this.getFavor())
                .stream()
                .max(Comparator.comparingInt(x -> x.rank))
                .get();
        } catch (Exception e) {
            e.printStackTrace();
            return FavorRank.SERIALIZER;
        }
    }

    public PlayerFavor(PlayerEntity player) {
        this.player = player;
    }

    public int getFavor() {
        return 3000; // TODO TEST return favor;
    }

    public int getMaxPossibleFavor() {
        return 5000;
    }

    public void onOpenNewLootChest() {
        this.favor += 50;
    }

    public void afterLootingItems(LootInfo info) {
        if (info.lootOrigin != LootInfo.LootOrigin.CHEST) {

            FavorRank rank = getRank();

            this.favor -= rank.favor_drain_per_item * info.amount;

        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        nbt.putInt(LOC, favor);
        return nbt;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.FAVOR;
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.favor = nbt.getInt(LOC);
    }

}