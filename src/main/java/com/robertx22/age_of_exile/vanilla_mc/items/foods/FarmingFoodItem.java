package com.robertx22.age_of_exile.vanilla_mc.items.foods;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ModelHelper;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.StatusEffectData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class FarmingFoodItem extends Item implements IAutoLocName, IAutoModel {

    public FoodType type;
    public FoodExileEffect exileEffect;
    public FoodTier tier;

    public FarmingFoodItem(FoodType type, FoodExileEffect exileEffect, FoodTier tier) {
        super(new Settings().group(CreativeTabs.Foods)
            .food(type.foodValueItem.getFoodComponent()));

        this.type = type;
        this.exileEffect = exileEffect;
        this.tier = tier;
    }

    public FoodEffect getFoodEffect() {
        FoodEffect eff = new FoodEffect();
        try {
            eff.effects_given.add(new StatusEffectData(Registry.STATUS_EFFECT.getId(type.effect), tier.durationSeconds, 1));
            eff.effects_given.add(new StatusEffectData(Registry.STATUS_EFFECT.getId(ModRegistry.POTIONS.FOOD_EFFECT_MAP.get(exileEffect)), tier.durationSeconds, tier.tier + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eff;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(tier.format);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Foods;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " " + exileEffect.word + " " + type.word;
    }

    @Override
    public String GUID() {
        return "food/" + type.id + "/" + exileEffect.color.id + "/" + tier.tier;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        // so i dont have to copy icons for each of the 5 tiers
        ModelHelper helper = new ModelHelper(this, ModelHelper.Type.GENERATED);
        helper.modelPath = "food/" + type.id + "/" + exileEffect.color.id;
        helper.generate();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {

            tooltip.add(new LiteralText("Tier " + tier.tier + " Food Item."));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
