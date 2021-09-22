package com.robertx22.age_of_exile.player_skills.ingredient.items;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class AlchemyConsumableItem extends BaseCraftedConsumableItem {
    public AlchemyConsumableItem() {
        super(PlayerSkillEnum.ALCHEMY);
    }

    @Override
    public String locNameForLangFile() {

        return "Brewed Potion";
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

}
