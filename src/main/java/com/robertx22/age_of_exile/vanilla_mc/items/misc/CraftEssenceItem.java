package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class CraftEssenceItem extends Item implements IAutoLocName, IAutoModel, IShapedRecipe {

    String id;

    Supplier<Item> craftItem;
    String locname;

    public CraftEssenceItem(String id, Supplier<Item> craftItem, String locname) {
        super(new Settings().group(CreativeTabs.MyModTab));
        this.id = id;
        this.craftItem = craftItem;
        this.locname = locname;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Used for crafting Special Gears."));
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
        return locname;
    }

    @Override
    public String GUID() {
        return "gear_mat/essences/" + id;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {

        Item resultItem = Registry.ITEM.get(new Identifier(Ref.MODID, GUID()));

        ShapedRecipeJsonFactory fac = ShapedRecipeJsonFactory.create(resultItem, 16);

        return fac.input('e', Items.COAL)
            .input('s', craftItem.get())
            .pattern("ses")
            .criterion("player_level", trigger());

    }

}
