package com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;

public class BackpackUpgradeItem extends TieredItem implements IStationRecipe, ICurrencyItemEffect {

    String id;
    String locname;
    public BackpackUpgrade upgrade;

    public BackpackUpgradeItem(BackpackUpgrade upgrade, SkillItemTier tier, String id, String locname) {
        super(tier, new Properties().tab(CreativeTabs.Professions)
            .stacksTo(1));
        this.id = id;
        this.upgrade = upgrade;
        this.locname = locname;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return "backpack/upgrade/" + id + tier.tier;
    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        return StationShapelessFactory.create(ModRegistry.RECIPE_SER.SMITHING, this, 1)
            .input(ModRegistry.TIERED.STONE_TIER_MAP.get(tier), 1)
            .input(Items.GOLD_INGOT)
            .input(upgrade.craftItem(), 1)
            .criterion("player_level", trigger());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        tooltip.add(Words.BagUpgradeDesc.locName()
            .withStyle(TextFormatting.BLUE));

        this.upgrade.addToTooltip(this, tooltip);

    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack currency) {

        BagUpgradesData data = BagUpgradesData.load(stack);

        data.upgrade((BackpackUpgradeItem) currency.getItem());

        data.saveToStack(stack);

        return stack;
    }

    @Override
    public boolean canItemBeModified(LocReqContext context) {

        if (context.stack.getItem() instanceof BackpackItem == false) {
            return false;
        }
        if (context.Currency.getItem() instanceof BackpackUpgradeItem == false) {
            return false;
        }

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(new BaseLocRequirement() {
            @Override
            public IFormattableTextComponent getText() {
                return new StringTextComponent("No duplicate upgrades, Must upgrade tier by tier.");
            }

            @Override
            public boolean isAllowed(LocReqContext context) {
                if (context.stack.getItem() instanceof BackpackItem) {
                    if (context.Currency.getItem() instanceof BackpackUpgradeItem) {
                        return BagUpgradesData.load(context.stack)
                            .canUpgrade((BackpackUpgradeItem) context.Currency.getItem());
                    }
                }
                return false;
            }
        });
    }

    @Override
    public StationType forStation() {
        return StationType.MODIFY;
    }
}
