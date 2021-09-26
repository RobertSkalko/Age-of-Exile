package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusFavor;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.LootUtils;
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
    float favor = 0;

    private FavorRank getDefault() {
        return ExileDB.FavorRanks()
            .get("normal");
    }

    public FavorRank getRank() {
        try {
            if (!ServerContainer.get().ENABLE_FAVOR_SYSTEM.get()) {
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
        return (int) favor;
    }

    public void addFavor(float favor) {
        this.favor += favor;
    }

    public void onAnyMobKilledSpendFavor() {
        if (favor > 0) {
            this.favor -= 0.25F;
        }
    }

    public void onOpenNewLootChest(LootInfo info) {

        float lvlpenalty = LootUtils.getLevelDistancePunishmentMulti(info.level, info.playerData
            .getLevel());

        float favorGained = (float) (ServerContainer.get().FAVOR_GAIN_PER_CHEST_LOOTED.get() * lvlpenalty);

        favorGained *= info.playerData.getUnit()
            .getCalculatedStat(BonusFavor.getInstance())
            .getMultiplier();

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
        if (ServerContainer.get().ENABLE_FAVOR_SYSTEM.get()) {
            player.displayClientMessage(new StringTextComponent("You are very low on favor.").withStyle(TextFormatting.RED), false);
        }
    }
}
