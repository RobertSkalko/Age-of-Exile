package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ModelHelper;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.StatusEffectData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class FarmingFoodItem extends TieredItem implements IAutoLocName, IAutoModel, IStationRecipe {

    public FoodType type;
    public FoodExileEffect exileEffect;

    public FarmingFoodItem(FoodType type, FoodExileEffect exileEffect, SkillItemTier tier) {
        super(tier, new Settings().group(CreativeTabs.Professions)
            .food(type.foodValueItem.getFoodComponent()));

        this.type = type;
        this.exileEffect = exileEffect;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);
        if (!IsSkillItemUsableUtil.canUseItem(user, stack, true)) {
            return TypedActionResult.fail(stack);
        }

        return super.use(world, user, hand);

    }

    public FoodEffect getFoodEffect() {
        FoodEffect eff = new FoodEffect();
        try {
            if (type.effect != null) {
                eff.effects_given.add(new StatusEffectData(Registry.STATUS_EFFECT.getId(type.effect), tier.durationSeconds, 1));
            }
            eff.effects_given.add(new StatusEffectData(Registry.STATUS_EFFECT.getId(ModRegistry.POTIONS.FOOD_EFFECT_MAP.get(exileEffect)), tier.durationSeconds, tier.tier + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eff;
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        if (type.getCraftItem(tier, exileEffect.color) != null) {
            StationShapelessFactory fac = StationShapelessFactory.create(ModRegistry.RECIPE_SER.FOOD, this);

            if (this.type == FoodType.FISH) {
                fac.input(Items.COAL);
            } else {
                fac.input(ModRegistry.FOOD_ITEMS.EXTRACT_MAP.get(this.exileEffect.color));
            }
            fac.input(ModRegistry.TIERED.FARMING_PRODUCE.get(tier));
            fac.input(type.getCraftItem(tier, exileEffect.color));
            return fac.criterion("player_level", trigger());
        }
        return null;
    }

}
