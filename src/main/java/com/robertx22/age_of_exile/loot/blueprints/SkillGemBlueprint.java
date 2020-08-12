package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.age_of_exile.loot.blueprints.bases.SpellPart;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import net.minecraft.item.ItemStack;

public class SkillGemBlueprint extends ItemBlueprint {

    public SkillGemBlueprint(int lvl) {
        super(lvl);
    }

    @Override
    public BaseRaritiesContainer<? extends Rarity> getRarityContainer() {
        return Rarities.SkillGems;
    }

    public SpellPart spellPart = new SpellPart(this);

    @Override
    ItemStack generate() {

        SkillGemData data = new SkillGemData();

        data.spell_id = spellPart.get()
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
