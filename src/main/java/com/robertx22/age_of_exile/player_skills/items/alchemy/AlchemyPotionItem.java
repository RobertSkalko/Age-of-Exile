package com.robertx22.age_of_exile.player_skills.items.alchemy;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.IReqSkillLevel;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class AlchemyPotionItem extends Item implements IAutoLocName, IAutoModel, IShapelessRecipe, IReqSkillLevel {

    public SkillItemTier tier;
    PotionType type;

    public AlchemyPotionItem(PotionType type, SkillItemTier tier) {
        super(new Settings().group(CreativeTabs.Alchemy));
        this.tier = tier;
        this.type = type;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {

        ItemStack stack = player.getStackInHand(handIn);

        if (!IsSkillItemUsableUtil.canUseItem(player, stack, true)) {
            return TypedActionResult.fail(stack);
        }

        stack.decrement(1);

        if (!world.isClient) {
            EntityCap.UnitData unitdata = Load.Unit(player);

            int restore = (int) (tier.percent_healed / 100F * unitdata.getResources()
                .getMax(player, this.type.resource));

            ResourcesData.Context ctx = new ResourcesData.Context(unitdata, player, type.resource,
                restore,
                ResourcesData.Use.RESTORE
            );
            unitdata.getResources()
                .modify(ctx);

            SoundUtils.playSound(player, SoundEvents.ENTITY_GENERIC_DRINK, 1, 1);
        }

        player.setCurrentHand(handIn);
        return TypedActionResult.success(stack);
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
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Restores " + (int) tier.percent_healed + "% " + type.word).formatted(Formatting.LIGHT_PURPLE));
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 3);
        fac.input(type.craftItem.get());
        fac.input(Items.GLASS_BOTTLE);
        fac.input(ModRegistry.ALCHEMY.MAT_TIER_MAP.get(this.tier));
        return fac.criterion("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " " + type.word + " Potion";
    }

    @Override
    public String GUID() {
        return "alchemy/potion/" + type.id + "/" + tier.tier;
    }

    @Override
    public PlayerSkillEnum getItemSkillType() {
        return PlayerSkillEnum.ALCHEMY;
    }

    @Override
    public float getSkillLevelMultiNeeded() {
        return tier.lvl_req;
    }
}
