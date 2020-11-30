package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusFavor;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.events.OnChestFavorGainedExploration;
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
            if (!ModConfig.get().Favor.ENABLE_FAVOR_SYSTEM) {
                return Database.FavorRanks()
                    .get("normal"); // simplest way of disabling everything around the system
            }
            return Database.FavorRanks()
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
        return favor;
    }

    public void onOpenNewLootChest(LootInfo info) {

        float lvlpenalty = LootUtils.getLevelDistancePunishmentMulti(info.level, info.playerData
            .getLevel());

        float favorGained = ModConfig.get().Favor.FAVOR_GAIN_PER_CHEST_LOOTED * lvlpenalty;

        favorGained *= info.playerData.getUnit()
            .getCalculatedStat(BonusFavor.getInstance())
            .getMultiplier();

        OnChestFavorGainedExploration.run(info.player, (int) favorGained);

        this.favor += favorGained;

    }

    public void afterLootingItems(float favorCost, LootInfo info, int amount) {

        boolean lowfavor = false;

        if (info.lootOrigin != LootInfo.LootOrigin.CHEST) {

            if (favorCost > 0) {
                this.favor -= favorCost * amount;

                if (this.favor < 5) {
                    lowfavor = true;
                }

            }
        }

        if (lowfavor) {
            onLowFavor();
        }
    }

    private void onLowFavor() {
        if (ModConfig.get().Favor.ENABLE_FAVOR_SYSTEM) {
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