package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ModelHelper;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.StatusEffectData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.IReqSkillLevel;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class FarmingFoodItem extends Item implements IAutoLocName, IAutoModel, IShapelessRecipe, IReqSkillLevel {

    public FoodType type;
    public FoodExileEffect exileEffect;
    public SkillItemTier tier;

    public FarmingFoodItem(FoodType type, FoodExileEffect exileEffect, SkillItemTier tier) {
        super(new Settings().group(CreativeTabs.Professions)
            .food(type.foodValueItem.getFoodComponent()));

        this.type = type;
        this.exileEffect = exileEffect;
        this.tier = tier;
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

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this);
        fac.input(ModRegistry.FOOD_ITEMS.EXTRACT_MAP.get(this.exileEffect.color));
        fac.input(ModRegistry.MISC_ITEMS.getDusts()
            .get(this.tier.tier));
        fac.input(type.vanillaCraftingItem);
        return fac.criterion("player_level", trigger());
    }

    @Override
    public PlayerSkillEnum getItemSkillType() {
        return PlayerSkillEnum.COOKING;
    }

    @Override
    public float getSkillLevelMultiNeeded() {
        return tier.lvl_req;
    }
}
