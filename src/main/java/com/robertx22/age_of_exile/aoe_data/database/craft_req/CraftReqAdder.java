package com.robertx22.age_of_exile.aoe_data.database.craft_req;

import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.AlchemyPotions;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class CraftReqAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        int max = GameBalanceConfig.get().MAX_LEVEL;

        AlchemyPotions.POTIONS_MAP.values()
            .forEach(x -> {
                CraftingReq.of(x.get(), PlayerSkillEnum.ALCHEMY, (int) (x.get().tier.lvl_req * max));
            });

    }
}
