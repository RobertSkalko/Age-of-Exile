package com.robertx22.age_of_exile.repair_kits;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class RepairKitItem extends Item implements IAutoLocName, IAutoModel, IShapedRecipe {

    transient String name;

    public final Boolean autoRepair;
    public final int tier;

    Item recipeItem;

    public RepairKitItem(int tier, String name, int max, Boolean autoRepair, Item recipeItem) {
        super(new Settings().maxCount(1)
            .maxDamage(max)
            .group(CreativeTabs.MyModTab));
        this.autoRepair = autoRepair;
        this.name = name;
        this.tier = tier;
        this.recipeItem = recipeItem;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return "repair/repair_kit" + tier;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {

        tooltip.add(Words.RepairKit1.locName()
            .formatted(Formatting.BLUE));
        tooltip.add(Words.RepairKit2.locName()
            .formatted(Formatting.BLUE));

        /*
        tooltip.add(Words.Durability
            .locName()
            .append(": " + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage())
            .formatted(Formatting.GREEN));

         */
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        if (tier == 0) {
            return shaped(this)
                .input('v', recipeItem)
                .input('t', ModRegistry.MISC_ITEMS.MAGIC_ESSENCE)
                .pattern(" v ")
                .pattern("vtv")
                .pattern(" v ")
                .criterion("player_level", trigger());
        } else {
            return shaped(this)
                .input('t', ModRegistry.REPAIR_KITS.KIT_MAP.get(tier - 1))
                .input('v', recipeItem)
                .pattern(" v ")
                .pattern("vtv")
                .pattern(" v ")
                .criterion("player_level", trigger());
        }
    }
}
