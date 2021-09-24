package com.robertx22.age_of_exile.player_skills.ingredient.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class CraftToolItem extends AutoItem {

    public PlayerSkillEnum skill;
    public String locname;

    public CraftToolItem(String locname, PlayerSkillEnum skill) {

        super(new Properties().tab(CreativeTabs.Professions)
            .stacksTo(1));
        this.skill = skill;
        this.locname = locname;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        tooltip.add(new StringTextComponent("Used for: ").append(skill.word.locName()));
        tooltip.add(new StringTextComponent("Use with Ingredients in a workbench."));

        if (skill.isGearCraftingProf()) {
            tooltip.add(new StringTextComponent("Requires a matching piece of gear."));
        }

    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return "craft_tools/" + skill.id;
    }
}
