package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class ProtectionTabletItem extends Item implements IAutoLocName, IAutoModel, IStationRecipe {

    TabletTypes type;
    public SkillItemTier tier;

    public ProtectionTabletItem(SkillItemTier tier, TabletTypes type) {
        super(new Settings().group(CreativeTabs.Professions));
        this.tier = tier;
        this.type = type;
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
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {
            tooltip.add(new LiteralText("Keep in Inventory or Ender chest."));
            tooltip.add(new LiteralText("Activates automatically to save you."));

            if (type.cooldownTicks() > 0) {
                tooltip.add(new LiteralText("Cooldown: " + type.cooldownTicks() / 20 + "s"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        StationShapelessFactory fac = StationShapelessFactory.create(ModRegistry.RECIPE_SER.TABLET, this);

        this.type.getRecipeItems()
            .forEach(x -> fac.input(x));
        fac.input(ModRegistry.TIERED.INK_TIER_MAP.get(tier));

        return fac.criterion("player_level", trigger());
    }
}
