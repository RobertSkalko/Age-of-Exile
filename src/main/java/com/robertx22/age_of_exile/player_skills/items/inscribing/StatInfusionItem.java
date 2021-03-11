package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.backpacks.IGatheringMat;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.List;

public class StatInfusionItem extends Item implements IAutoLocName, IAutoModel, IGatheringMat, IShapelessRecipe, ICurrencyItemEffect {

    SkillItemTier tier;

    public StatInfusionItem(SkillItemTier tier) {
        super(new Settings().group(CreativeTabs.Professions));
        this.tier = tier;
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
    public String locNameForLangFile() {
        return tier.word + " Stat Infusion";
    }

    @Override
    public String GUID() {
        return "stat_infusion/" + tier.tier;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 1);
        fac.input(ModRegistry.ALCHEMY.CONDENSED_ESSENCE_MAP.get(tier), 1);
        fac.input(ModRegistry.MISC_ITEMS.SALVAGED_ESSENCE_MAP.get(tier), 4);
        return fac.criterion("player_level", trigger());
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack currency) {

        StatInfusionItem inf = (StatInfusionItem) currency.getItem();

        int lvl = inf.tier.levelRange.randomFromRange();

        GearBlueprint b = new GearBlueprint(lvl);
        b.level.set(lvl);

        String slot = "";

        if (stack.getItem() instanceof PickaxeItem) {
            slot = GearSlots.PICKAXE_ID;
        } else if (stack.getItem() instanceof HoeItem) {
            slot = GearSlots.HOE_ID;
        } else if (stack.getItem() instanceof FishingRodItem) {
            slot = GearSlots.FISHING_ROD_ID;
        }

        if (!slot.isEmpty()) {
            final String slotid = slot;
            b.gearItemSlot.set(Database.GearTypes()
                .getFilterWrapped(x -> x.gear_slot.equals(slotid))
                .random());

            GearItemData gear = b.createData();

            gear.saveToStack(stack);
        }

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }

    @Override
    public StationType forStation() {
        return StationType.MODIFY;
    }

    @Override
    public boolean canItemBeModified(LocReqContext context) {

        if (context.isGear()) {
            return false; // only allow empty items to be upgraded
        }

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

}

