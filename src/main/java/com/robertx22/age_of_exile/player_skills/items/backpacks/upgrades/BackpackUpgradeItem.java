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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class BackpackUpgradeItem extends TieredItem implements IStationRecipe, ICurrencyItemEffect {

    String id;
    String locname;
    public BackpackUpgrade upgrade;

    public BackpackUpgradeItem(BackpackUpgrade upgrade, SkillItemTier tier, String id, String locname) {
        super(tier, new Settings().group(CreativeTabs.Professions)
            .maxCount(1));
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
            .input(ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(tier), 1)
            .input(upgrade.craftItem(), 1)
            .criterion("player_level", trigger());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {

        tooltip.add(Words.BagUpgradeDesc.locName()
            .formatted(Formatting.BLUE));

        this.upgrade.addToTooltip(this, tooltip);

    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack currency) {

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
            public MutableText getText() {
                return new LiteralText("No duplicate upgrades, Must upgrade tier by tier.");
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
