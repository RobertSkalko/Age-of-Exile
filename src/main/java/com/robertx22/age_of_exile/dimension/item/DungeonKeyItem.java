package com.robertx22.age_of_exile.dimension.item;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class DungeonKeyItem extends TieredItem implements IShapelessRecipe {

    public DungeonKeyItem(SkillItemTier tier) {
        super(tier, new Settings().maxCount(1)
            .group(CreativeTabs.MyModTab));
    }

    @Override
    public String locNameForLangFile() {
        return "Dungeon Key";
    }

    @Override
    public String GUID() {
        return "dungeon_key/" + tier.tier;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 1);
        fac.input(ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(tier), 6);
        fac.input(Items.ENDER_PEARL);
        fac.input(Items.DIAMOND);
        fac.input(Items.GOLD_BLOCK);
        return fac.criterion("player_level", trigger());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Level Cap: " + tier.levelRange.getMaxLevel()));
    }

}
