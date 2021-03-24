package com.robertx22.age_of_exile.player_skills.items.fishing;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ModelHelper;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;

public class RawFishItem extends TieredItem {

    public FoodType type;
    public FoodExileEffect exileEffect;

    public RawFishItem(SkillItemTier tier, FoodType type, FoodExileEffect exileEffect) {
        super(tier);
        this.type = type;
        this.exileEffect = exileEffect;
    }

    @Override
    public String locNameForLangFile() {
        return "Raw " + tier.word + " " + exileEffect.word + " Fish";
    }

    public void generateModel(ItemModelManager manager) {
        // so i dont have to copy icons for each of the 5 tiers
        ModelHelper helper = new ModelHelper(this, ModelHelper.Type.GENERATED);
        helper.modelPath = "raw_fish/" + exileEffect.color.id;
        helper.generate();
    }

    @Override
    public String GUID() {
        return "raw_fish/" + exileEffect.color.id + "/" + tier.tier;
    }

}
