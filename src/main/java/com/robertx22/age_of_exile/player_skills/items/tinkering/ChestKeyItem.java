package com.robertx22.age_of_exile.player_skills.items.tinkering;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashRecipeSers;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ChestKeyItem extends TieredItem implements IStationRecipe {

    public ChestKeyItem(SkillItemTier tier) {
        super(tier, new Properties().tab(CreativeTabs.Professions));

    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Chest Key";
    }

    @Override
    public String GUID() {
        return "key/" + tier.tier;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        try {

            tooltip.add(new StringTextComponent("Opens locked chest items."));
            tooltip.add(new StringTextComponent("Needs to be in inventory."));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        StationShapelessFactory fac = StationShapelessFactory.create(SlashRecipeSers.SMITHING.get(), this, 1);
        fac.input(ProfessionItems.STONE_TIER_MAP.get(this.tier)
            .get(), 2);
        fac.input(Items.IRON_INGOT, 1);
        return fac.criterion("player_level", trigger());
    }
}

