package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.GearMaterialRegister;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
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

    public GearMaterialItem(SkillItemTier tier, GearMaterialRegister.TYPE type, String id) {
        super(new Item.Settings().maxCount(64)
            .group(CreativeTabs.MyModTab));
        this.locname = tier.word + " " + type.name;
        this.id = id;
        this.range = tier.levelRange;
        this.type = type;
        this.tier = tier;
    }

    SkillItemTier tier;
    GearMaterialRegister.TYPE type;
    String locname;
    String id;
    LevelRange range;

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {
            if (Database.areDatapacksLoaded(world)) {
                tooltip.add(new LiteralText("Crafts Level " + range.getMinLevel() + " - " + range.getMaxLevel() + " items.").formatted(Formatting.GOLD));
            }
        } catch (Exception e) {
            // it seems to do this before the game even loads..
        }

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
        Item essence = ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.values()
            .stream()
            .filter(x -> x.range.equals(this.range))
            .findAny()
            .get();

        if (type == GearMaterialRegister.TYPE.CLOTH) {
            smallResource = Items.STRING;
        } else if (type == GearMaterialRegister.TYPE.LEATHER) {
            smallResource = Items.STRING;
        }

        ShapedRecipeJsonFactory fac = shaped(resultItem);

        if (type == GearMaterialRegister.TYPE.CLOTH) {
            fac.input('v', ItemTags.WOOL);
        } else if (type == GearMaterialRegister.TYPE.LEATHER) {
            fac.input('v', Items.LEATHER);
        } else {
            fac.input('v', Items.IRON_INGOT);
        }

        fac.input('e', essence)
            .input('s', smallResource)
            .pattern(" e ");

        if (type == GearMaterialRegister.TYPE.ORE) {
            fac.pattern("svs");
        } else {
            fac.pattern(" v "); // less strings needed for cloth and leather
        }

        fac.pattern(" s ");

        return fac.criterion("player_level", trigger());

    }

}
