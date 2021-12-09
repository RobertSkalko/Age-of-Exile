package com.robertx22.addon.infinite_dungeons.events;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISettableLevelTier;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.infinite_dungeons.database.db_types.DungeonDifficulty;
import com.robertx22.infinite_dungeons.exile_events.DungeonDifficultyTooltipEvent;
import com.robertx22.infinite_dungeons.exile_events.IDExileEvents;
import com.robertx22.infinite_dungeons.exile_events.OnItemGivenEvent;
import com.robertx22.infinite_dungeons.exile_events.TryStartDungeonEvent;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.utils.AllItemStackSavers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class IDAddonEventRegister {

    static int getAverageILVLReq(DungeonDifficulty diff, PlayerEntity player) {
        int tiers = Load.Unit(player)
            .getLevel() / GameBalanceConfig.get().levels_per_tier;
        int reqILVL = (tiers * GameBalanceConfig.get().levels_per_tier) + diff.mns_ilvl_req;
        return reqILVL;
    }

    public static String OVERRIDE_TIER_TAG = "override_tier";

    public static void register() {

        IDExileEvents.DIFFICULTY_TOOLTIP.register(new EventConsumer<DungeonDifficultyTooltipEvent>() {
            @Override
            public void accept(DungeonDifficultyTooltipEvent event) {
                int reqlvl = (int) (event.difficulty.mns_lvl_req * GameBalanceConfig.get().MAX_LEVEL);

                event.tooltip.add(new StringTextComponent(""));
                event.tooltip.add(new StringTextComponent("Requires Level: " + reqlvl));

                int ilvl = getAverageILVLReq(event.difficulty, event.player);

                if (ilvl > 0) {
                    if (ServerContainer.get().TURN_REQ_ILVL_INTO_RECCOMENDATION.get()) {
                        event.tooltip.add(new StringTextComponent(""));
                        event.tooltip.add(new StringTextComponent("Recommended Average Item Level: " + ilvl));

                    } else {
                        event.tooltip.add(new StringTextComponent(""));
                        event.tooltip.add(new StringTextComponent("Requires Average Item Level: " + ilvl));
                    }
                }
            }
        });

        IDExileEvents.TRY_START_DUNGEON.register(new EventConsumer<TryStartDungeonEvent>() {
            @Override
            public void accept(TryStartDungeonEvent event) {
                int reqlvl = (int) (event.difficulty.mns_lvl_req * GameBalanceConfig.get().MAX_LEVEL);

                if (reqlvl > Load.Unit(event.player)
                    .getLevel()) {
                    event.doNotAllowStartingDungeon();
                    event.player.displayClientMessage(new StringTextComponent("Your level isn't high enough for this difficulty"), false);
                }

                if (!ServerContainer.get().TURN_REQ_ILVL_INTO_RECCOMENDATION.get()) {
                    int ilvl = getAverageILVLReq(event.difficulty, event.player);
                    if (ilvl > Load.Unit(event.player)
                        .getAverageILVL()) {
                        event.doNotAllowStartingDungeon();
                        event.player.displayClientMessage(new StringTextComponent("Your Average Item Level isn't high enough for this difficulty"), false);
                    }
                }

            }
        });

        IDExileEvents.ON_ITEM_GIVE.register(new EventConsumer<OnItemGivenEvent>() {
            @Override
            public void accept(OnItemGivenEvent event) {

                int tier = LevelUtils.levelToTier(Load.Unit(event.player)
                    .getLevel());

                if (OnItemGivenEvent.isProducer(event.stack)) {
                    event.stack.getOrCreateTag()
                        .putInt(OVERRIDE_TIER_TAG, tier);
                }

                for (ItemstackDataSaver<? extends ISettableLevelTier> saver : AllItemStackSavers.getAllOfClass(ISettableLevelTier.class)) {
                    if (saver.has(event.stack)) {
                        ISettableLevelTier inte = saver.loadFrom(event.stack);
                        if (inte != null) {

                            if (event.producerStack != null && OnItemGivenEvent.isProducer(event.producerStack)) {
                                tier = event.producerStack.getOrCreateTag()
                                    .getInt(OVERRIDE_TIER_TAG);
                            }

                            inte.setTier(tier);
                            saver.saveToObject(event.stack, inte);
                        }

                    }
                }
            }
        });

    }
}
