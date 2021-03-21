package com.robertx22.age_of_exile.player_skills.items.tinkering;

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
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class ChestKeyItem extends TieredItem implements IShapelessRecipe {

    public ChestKeyItem(SkillItemTier tier) {
        super(tier, new Settings().group(CreativeTabs.Professions));

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
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 3);
        fac.input(ModRegistry.TIERED.STONE_TIER_MAP.get(this.tier), 4);
        return fac.criterion("player_level", trigger());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {

            tooltip.add(new LiteralText("Opens locked chest items."));
            tooltip.add(new LiteralText("Needs to be in inventory."));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

