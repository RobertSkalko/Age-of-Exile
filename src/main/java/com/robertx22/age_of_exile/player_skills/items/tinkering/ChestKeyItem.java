package com.robertx22.age_of_exile.player_skills.items.tinkering;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class ChestKeyItem extends Item implements IAutoLocName, IAutoModel, IShapelessRecipe {

    public SkillItemTier tier;

    public ChestKeyItem(SkillItemTier tier) {
        super(new Settings().group(CreativeTabs.Professions));
        this.tier = tier;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(tier.format);
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
        return tier.word + " Chest Key";
    }

    @Override
    public String GUID() {
        return "key/" + tier.tier;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 3);
        fac.input(ModRegistry.TINKERING.STONE_TIER_MAP.get(this.tier), 4);
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

