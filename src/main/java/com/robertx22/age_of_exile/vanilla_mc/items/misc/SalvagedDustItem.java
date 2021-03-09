package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class SalvagedDustItem extends Item implements IAutoLocName, IWeighted, IAutoModel, IShapelessRecipe {

    String name;
    int tier;
    public LevelRange range;

    public SalvagedDustItem(String name, int tier, LevelRange range) {
        super(new Settings().maxCount(64)
            .group(CreativeTabs.MyModTab));
        this.name = name;
        this.tier = tier;
        this.range = range;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Words.CreatedInSalvageStation.locName()
            .formatted(Formatting.BLUE));
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
        return "mat/salvage/salvage" + tier;
    }

    @Override
    public int Weight() {
        return 100;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        // de-craft recipe into lower tiers
        if (tier < 1) {
            return null;
        }

        Item output = ModRegistry.MISC_ITEMS.getDusts()
            .stream()
            .filter(x -> x.tier == tier - 1)
            .findAny()
            .get();

        return ShapelessRecipeJsonFactory.create(output, 3)
            .input(this, 1)
            .criterion("player_level", trigger());

    }
}
