package com.robertx22.age_of_exile.player_skills.items.tinkering;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.backpacks.IGatheringMat;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

public class MysteriousLeatherItem extends Item implements IAutoLocName, IAutoModel, IGatheringMat, IShapelessRecipe {

    public SkillItemTier tier;

    public MysteriousLeatherItem(SkillItemTier tier) {
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
        return tier.word + " Leather";
    }

    @Override
    public String GUID() {
        return "leather/" + tier.tier;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 1);
        fac.input(ModRegistry.INSCRIBING.INK_TIER_MAP.get(this.tier));
        fac.input(Items.LEATHER);
        return fac.criterion("player_level", trigger());
    }
}
