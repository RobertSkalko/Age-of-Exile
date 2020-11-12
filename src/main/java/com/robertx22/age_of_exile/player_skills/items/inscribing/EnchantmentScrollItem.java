package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ModelHelper;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.IReqSkillLevel;
import com.robertx22.age_of_exile.player_skills.enchants.EnchantsEnum;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public class EnchantmentScrollItem extends Item implements IAutoLocName, IAutoModel, IShapelessRecipe, IReqSkillLevel {

    public SkillItemTier tier;
    public EnchantsEnum enchant;
    PlayerSkillEnum skill;

    public EnchantmentScrollItem(PlayerSkillEnum skill, EnchantsEnum enchant, SkillItemTier tier) {
        super(new Settings().group(CreativeTabs.Inscribing));
        this.tier = tier;
        this.enchant = enchant;
        this.skill = skill;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        try {
            if (!IsSkillItemUsableUtil.canUseItem(user, stack, true)) {
                return TypedActionResult.fail(stack);
            }
            Enchantment ench = ModRegistry.ENCHANTS.MAP.get(ImmutablePair.of(skill, enchant));

            stack.decrement(1);
            ItemStack book = EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ench, tier.tier + 1));
            PlayerUtils.giveItem(book, user);

            return TypedActionResult.success(stack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(tier.format);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Enchants;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return skill.word + " " + enchant.word + " Scroll";
    }

    @Override
    public String GUID() {
        return "enchant_scroll/" + enchant.id + "/" + skill.id + "/" + tier.tier;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        // so i dont have to copy icons for each of the 5 tiers
        ModelHelper helper = new ModelHelper(this, ModelHelper.Type.GENERATED);
        helper.modelPath = "enchanting_scroll";
        helper.generate();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {

            tooltip.add(new LiteralText("Use to create enchanting book."));
            tooltip.add(new LiteralText("Tier " + tier.tier + " Inscribing Item."));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this);
        fac.input(Items.PAPER);
        fac.input(skill.craftItem.get());
        fac.input(enchant.craftItem.get());
        fac.input(ModRegistry.INSCRIBING.INK_TIER_MAP.get(this.tier));
        return fac.criterion("player_level", trigger());
    }

    @Override
    public PlayerSkillEnum getSkillTypeToCraft() {
        return PlayerSkillEnum.INSCRIBING;
    }

    @Override
    public float getSkillLevelMultiNeeded() {
        return tier.lvl_req;
    }
}
