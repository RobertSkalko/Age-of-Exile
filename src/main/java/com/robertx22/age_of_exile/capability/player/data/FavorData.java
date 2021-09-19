package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusFavor;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.player_skills.events.OnChestFavorGainedExploration;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Comparator;
import java.util.Optional;

@Storable
public class FavorData {
    @Store
    int favor = 0;

    private FavorRank getDefault() {
        return ExileDB.FavorRanks()
            .get("normal");
    }

    public FavorRank getRank() {
        try {
            if (!ModConfig.get().Server.ENABLE_FAVOR_SYSTEM) {
                return getDefault(); // simplest way of disabling everything around the system
            }
            Optional<FavorRank> opt = ExileDB.FavorRanks()
                .getFiltered(x -> this.getFavor() >= x.min)
                .stream()
                .max(Comparator.comparingInt(x -> x.rank));

            return opt.orElseGet(this::getDefault);

        } catch (Exception e) {
            e.printStackTrace();
            return ExileDB.FavorRanks()
                .get("normal");
        }
    }

    public void setFavor(int favor) {
        this.favor = favor;
    }

    public int getFavor() {
        return favor;
    }

    public void addFavor(int favor) {
        this.favor += favor;
    }

    public void onOpenNewLootChest(LootInfo info) {

        float lvlpenalty = LootUtils.getLevelDistancePunishmentMulti(info.level, info.playerData
            .getLevel());

        float favorGained = ModConfig.get().Server.FAVOR_GAIN_PER_CHEST_LOOTED * lvlpenalty;

        favorGained *= info.playerData.getUnit()
            .getCalculatedStat(BonusFavor.getInstance())
            .getMultiplier();

        OnChestFavorGainedExploration.run(info, info.player, (int) favorGained);

        this.favor += favorGained;

    }

    public void afterLootingItems(PlayerEntity player, float favorCost, LootInfo info, int amount) {

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
            onLowFavor(player);
        }
    }

    private void onLowFavor(PlayerEntity player) {
        if (ModConfig.get().Server.ENABLE_FAVOR_SYSTEM) {
            player.displayClientMessage(new StringTextComponent("You are very low on favor.").withStyle(TextFormatting.RED), false);
        }
    }
}
