package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashRecipeSers;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ProtectionTabletItem extends Item implements IAutoLocName, IAutoModel, IStationRecipe {

    TabletTypes type;
    public SkillItemTier tier;

    public ProtectionTabletItem(SkillItemTier tier, TabletTypes type) {
        super(new Properties().tab(CreativeTabs.Professions)
            .stacksTo(8));
        this.tier = tier;
        this.type = type;
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
        return type.word + " Tablet";
    }

    @Override
    public String GUID() {
        return "tablet/" + type.id;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        try {
            tooltip.add(new StringTextComponent("Keep in Inventory or Ender chest."));
            tooltip.add(new StringTextComponent("Activates automatically to save you."));

            if (type.cooldownTicks() > 0) {
                tooltip.add(new StringTextComponent("Cooldown: " + type.cooldownTicks() / 20 + "s"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        StationShapelessFactory fac = StationShapelessFactory.create(SlashRecipeSers.TABLET.get(), this);

        this.type.getRecipeItems()
            .forEach(x -> fac.input(x));
        fac.input(ProfessionItems.INK_TIER_MAP.get(tier)
            .get());

        return fac.criterion("player_level", trigger());
    }
}
