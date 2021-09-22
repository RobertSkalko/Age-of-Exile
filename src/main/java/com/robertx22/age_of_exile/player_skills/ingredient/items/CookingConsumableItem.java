package com.robertx22.age_of_exile.player_skills.ingredient.items;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class CookingConsumableItem extends BaseCraftedConsumableItem {

    public CookingConsumableItem() {
        super(PlayerSkillEnum.COOKING);
    }

    @Override
    public String locNameForLangFile() {
        return "Cooked Meal";
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.EAT;
    }

}
