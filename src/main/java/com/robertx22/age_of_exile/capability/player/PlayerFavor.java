package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Comparator;

public class PlayerFavor implements ICommonPlayerCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "favor");
    private static final String LOC = "favor";

    PlayerEntity player;

    float favor = 0;

    public FavorRank getRank() {
        try {
            if (!ModConfig.get().Server.ENABLE_FAVOR_SYSTEM) {
                return SlashRegistry.FavorRanks()
                    .get("normal"); // simplest way of disabling everything around the system
            }
            return SlashRegistry.FavorRanks()
                .getFiltered(x -> this.getFavor() >= x.min)
                .stream()
                .max(Comparator.comparingInt(x -> x.rank))
                .get();
        } catch (Exception e) {
            e.printStackTrace();
            return FavorRank.SERIALIZER;
        }
    }

    public void setFavor(float favor) {
        this.favor = favor;
    }

    public PlayerFavor(PlayerEntity player) {
        this.player = player;
    }

    public float getFavor() {
        return favor; // TODO TEST return favor;
    }

    public float getMaxPossibleFavor() {
        return 1000000;
    }

    public void onOpenNewLootChest() {
        this.favor += 50;
    }

    public void afterLootingItems(LootInfo info) {
        if (info.lootOrigin != LootInfo.LootOrigin.CHEST) {

            FavorRank rank = getRank();

            if (rank.favor_drain_per_item > 0) {
                this.favor -= rank.favor_drain_per_item * info.amount;

                onFavorChanged();
            }
        }
    }

    private void onFavorChanged() {
        if (this.favor <= 5) {
            this.player.sendMessage(new LiteralText("You are very low on favor.").formatted(Formatting.RED), false);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        nbt.putFloat(LOC, favor);
        return nbt;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.FAVOR;
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.favor = nbt.getFloat(LOC);
    }

}