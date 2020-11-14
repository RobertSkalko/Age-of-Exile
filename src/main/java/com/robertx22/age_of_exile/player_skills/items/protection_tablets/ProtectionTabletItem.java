package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.IReqSkillLevel;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class ProtectionTabletItem extends Item implements IAutoLocName, IAutoModel, IShapelessRecipe, IReqSkillLevel {

    TabletTypes type;
    SkillItemTier tier;

    public ProtectionTabletItem(SkillItemTier tier, TabletTypes type) {
        super(new Settings().group(CreativeTabs.Inscribing));
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
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this);

        this.type.getRecipeItems()
            .forEach(x -> fac.input(x));
        fac.input(ModRegistry.INSCRIBING.INK_TIER_MAP.get(tier));

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

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {
            tooltip.add(new LiteralText("Keep in Inventory or Ender chest."));
            tooltip.add(new LiteralText("Activates automatically to save you."));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
