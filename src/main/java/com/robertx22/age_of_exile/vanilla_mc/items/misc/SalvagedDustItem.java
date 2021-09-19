package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SalvagedDustItem extends Item implements IAutoLocName, IWeighted, IAutoModel, IShapelessRecipe {

    String name;
    public SkillItemTier tier;
    public LevelRange range;

    public SalvagedDustItem(String name, SkillItemTier tier, LevelRange range) {
        super(new Properties().stacksTo(64)
            .tab(CreativeTabs.MyModTab));
        this.name = name;
        this.tier = tier;
        this.range = range;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        tooltip.add(Words.CreatedInSalvageStation.locName());

        tooltip.add(TooltipUtils.gearTier(tier.getDisplayTierNumber()));

        tooltip.add(new StringTextComponent(""));

        tooltip.add(new StringTextComponent("Repairs gear of equal tier."));
        tooltip.add(TooltipUtils.dragOntoGearToUse());

    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return "mat/salvage/salvage" + tier.tier;
    }

    @Override
    public int Weight() {
        return 100;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        // de-craft recipe into lower tiers
        if (tier.tier < 1) {
            return null;
        }

        Item output = ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.values()
            .stream()
            .filter(x -> x.tier.tier == tier.tier - 1)
            .findAny()
            .get();

        return ShapelessRecipeBuilder.shapeless(output, 3)
            .requires(this, 1)
            .unlockedBy("player_level", trigger());

    }
}
