package com.robertx22.age_of_exile.aoe_data.database.craft_req;

import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class CraftReqAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        int max = GameBalanceConfig.get().MAX_LEVEL;

        ModRegistry.FOOD_ITEMS.MAP.values()
            .forEach(x -> {
                CraftingReq.of(x, PlayerSkillEnum.COOKING, (int) (x.tier.lvl_req * max));
            });

        ModRegistry.INSCRIBING.ALL_TABLETS.forEach(x -> {
            CraftingReq.of(x, PlayerSkillEnum.INSCRIBING, (int) (x.tier.lvl_req * max));
        });

        ModRegistry.ALCHEMY.POTIONS_MAP.values()
            .forEach(x -> {
                CraftingReq.of(x, PlayerSkillEnum.ALCHEMY, (int) (x.tier.lvl_req * max));
            });

        ModRegistry.TIERED.KEY_TIER_MAP.values()
            .forEach(x -> {
                CraftingReq.of(x, PlayerSkillEnum.BLACKSMITHING, (int) (x.tier.lvl_req * max));
            });

        ModRegistry.BACKPACK_UPGRADES.ALL
            .forEach(x -> {
                CraftingReq.of(x, PlayerSkillEnum.BLACKSMITHING, (int) (x.tier.lvl_req * max));
            });

    }
}
