package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.datapacks.models.IAutoModel;
import com.robertx22.mine_and_slash.datapacks.models.ItemModelManager;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.GearMaterialRegister;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class GearMaterialItem extends Item implements IAutoLocName, IWeighted, IAutoModel, IShapedRecipe {

    public GearMaterialItem(int tier, GearMaterialRegister.TYPE type, String id, String locname, LevelRange levelRange) {
        super(new Item.Settings().maxCount(64)
            .group(CreativeTabs.MyModTab));
        this.locname = locname;
        this.id = id;
        this.range = levelRange;
        this.type = type;
        this.tier = tier;
    }

    int tier;
    GearMaterialRegister.TYPE type;
    String locname;
    String id;
    LevelRange range;

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Crafts Level " + range.getMinLevel() + " - " + range.getMaxLevel() + " items.").formatted(Formatting.GOLD));
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
        return "gear_mat/" + id;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {

        Item resultItem = ModRegistry.GEAR_MATERIALS.MAP.get(type)
            .get(tier);

        Item smallResource = Items.GOLD_NUGGET;

        if (type == GearMaterialRegister.TYPE.CLOTH) {
            smallResource = Items.STRING;
        } else if (type == GearMaterialRegister.TYPE.LEATHER) {
            smallResource = Items.STRING;
        }

        if (tier == 0) {
            ShapedRecipeJsonFactory fac = shaped(resultItem);

            if (type == GearMaterialRegister.TYPE.CLOTH) {
                fac.input('v', ItemTags.WOOL);
            } else if (type == GearMaterialRegister.TYPE.LEATHER) {
                fac.input('v', Items.LEATHER);
            } else {
                fac.input('v', Items.IRON_INGOT);
            }

            return fac.input('e', ModRegistry.MISC_ITEMS.MAGIC_ESSENCE)
                .input('s', smallResource)
                .pattern(" e ")
                .pattern("svs")
                .pattern(" s ")
                .criterion("player_level", trigger());
        }

        Item essence = ModRegistry.MISC_ITEMS.MAGIC_ESSENCE;

        if (tier > 3) {
            essence = ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE;
        }

        if (tier >= 1) {
            return shaped(resultItem)
                .input('v', ModRegistry.GEAR_MATERIALS.MAP.get(type)
                    .get(tier - 1))
                .input('s', smallResource)
                .input('e', essence)
                .pattern(" e ")
                .pattern("svs")
                .pattern(" s ")
                .criterion("player_level", trigger());
        }

        return null;
    }

}
