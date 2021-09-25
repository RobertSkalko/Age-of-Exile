package com.robertx22.age_of_exile.player_skills.ingredient.items;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class InscribingConsumableItem extends BaseCraftedConsumableItem {

    public InscribingConsumableItem() {
        super(PlayerSkillEnum.INSCRIBING);
        this.isAOE = true;
    }

    @Override
    public String locNameForLangFile() {
        return "Enchanted Scroll";
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

}
