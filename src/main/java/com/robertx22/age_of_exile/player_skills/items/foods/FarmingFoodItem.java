package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ModelHelper;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.StatusEffectData;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashRecipeSers;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.FoodItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class FarmingFoodItem extends TieredItem implements IAutoLocName, IAutoModel, IStationRecipe {

    public FoodType type;
    public FoodExileEffect exileEffect;

    public FarmingFoodItem(FoodType type, FoodExileEffect exileEffect, SkillItemTier tier) {
        super(tier, new Properties().tab(CreativeTabs.Professions)
            .food(type.foodValueItem.getFoodProperties()));

        this.type = type;
        this.exileEffect = exileEffect;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getItemInHand(hand);
        if (!IsSkillItemUsableUtil.canUseItem(user, stack, true)) {
            return ActionResult.fail(stack);
        }

        return super.use(world, user, hand);

    }

    public FoodEffect getFoodEffect() {
        FoodEffect eff = new FoodEffect();
        try {
            if (type.effect != null) {
                eff.effects_given.add(new StatusEffectData(Registry.MOB_EFFECT.getKey(type.effect), tier.durationSeconds, 1));
            }
            eff.effects_given.add(new StatusEffectData(Registry.MOB_EFFECT.getKey(SlashPotions.FOOD_EFFECT_MAP.get(exileEffect)
                .get()), tier.durationSeconds, tier.tier + 1));
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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        if (type.getCraftItem(tier, exileEffect.color) != null) {
            StationShapelessFactory fac = StationShapelessFactory.create(SlashRecipeSers.FOOD.get(), this);

            if (this.type == FoodType.FISH) {
                fac.input(Items.COAL);
            } else {
                fac.input(FoodItems.EXTRACT_MAP.get(this.exileEffect.color)
                    .get());
            }
            fac.input(ProfessionItems.FARMING_PRODUCE.get(tier)
                .get());
            fac.input(type.getCraftItem(tier, exileEffect.color));
            return fac.criterion("player_level", trigger());
        }
        return null;
    }

}
