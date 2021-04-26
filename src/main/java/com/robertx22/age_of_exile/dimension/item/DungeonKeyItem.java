package com.robertx22.age_of_exile.dimension.item;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
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

    public static void setTier(ItemStack stack, int tier) {

        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        stack.getTag()
            .putInt("tier", tier);
    }

    public static int getTier(ItemStack stack) {
        return stack.hasTag() ? stack.getTag()
            .getInt("tier") : 0;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 1);
        fac.input(ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(tier), 8);
        fac.input(Items.ENDER_PEARL);
        return fac.criterion("player_level", trigger());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TooltipUtils.tier(getTier(stack)));
        tooltip.add(new LiteralText("Level Cap: " + tier.levelRange.getMaxLevel()));
    }

}
