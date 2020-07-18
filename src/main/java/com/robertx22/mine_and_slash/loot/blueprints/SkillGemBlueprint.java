package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import net.minecraft.item.ItemStack;

public class SkillGemBlueprint extends ItemBlueprint {

    public SkillGemBlueprint(int lvl) {
        super(lvl);
    }

    @Override
    public BaseRaritiesContainer<? extends Rarity> getRarityContainer() {
        return Rarities.SkillGems;
    }

    @Override
    ItemStack generate() {

        SkillGemData data = new SkillGemData();

        data.spell_id = SlashRegistry.Spells()
            .random()
            .GUID();
        data.level = this.level.get();
        data.rarity = this.rarity.get()
            .Rank();
        data.stat_percents = data.getRarity()
            .statPercents()
            .random();

        return data.toItemStack();

    }

}
