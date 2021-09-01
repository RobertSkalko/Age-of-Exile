package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.ids.GearSlotIds;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class StatInfusionItem extends Item implements IAutoLocName, IAutoModel, IStationRecipe, ICurrencyItemEffect {

    public SkillItemTier tier;

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
    public float getBreakChance() {
        return 10;
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Tool Upgrade";
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(Words.ChanceToUpgrade.locName()
            .append(": " + upgradeChance() + "%")
            .formatted(Formatting.LIGHT_PURPLE));

    }

    @Override
    public String GUID() {
        return "stat_infusion/" + tier.tier;
    }

    int upgradeChance() {
        return this.tier.getDisplayTierNumber() * 10;
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack currency) {

        try {

            StatInfusionItem inf = (StatInfusionItem) currency.getItem();

            int lvl = 1;

            GearBlueprint b = new GearBlueprint(stack.getItem(), lvl);
            b.level.set(lvl);

            String slot = "";

            if (stack.getItem() instanceof PickaxeItem) {
                slot = GearSlotIds.PICKAXE_ID;
            } else if (stack.getItem() instanceof HoeItem) {
                slot = GearSlotIds.HOE_ID;
            } else if (stack.getItem() instanceof FishingRodItem) {
                slot = GearSlotIds.FISHING_ROD_ID;
            }

            if (!slot.isEmpty()) {
                final String slotid = slot;
                b.gearItemSlot.set(ExileDB.GearTypes()
                    .getFilterWrapped(x -> x.gear_slot.equals(slotid))
                    .random());

                GearItemData gear = null;

                if (Gear.has(stack)) {
                    gear = Gear.Load(stack);

                } else {
                    gear = b.createData();
                }

                if (RandomUtils.roll(upgradeChance())) {

                    List<Affix.Type> types = Arrays.asList(Affix.Type.prefix, Affix.Type.suffix);
                    Affix.Type type = RandomUtils.randomFromList(types);

                    if (gear.affixes.pre.size() > gear.affixes.suf.size()) {
                        type = Affix.Type.suffix;
                    } else if (gear.affixes.suf.size() > gear.affixes.pre.size()) {
                        type = Affix.Type.prefix;
                    }

                    AffixData affix = new AffixData(type);
                    affix.RerollFully(gear);
                    gear.affixes.add(affix);

                }

                gear.saveToStack(stack);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(
            new SimpleGearLocReq(x -> x.GetBaseGearType()
                .isTool(), Words.IsAtool.locName()).setTrueIfNotGear(),
            new SimpleGearLocReq(x -> x.affixes.getNumberOfAffixes() < 3, Words.LessThan3Upgrade.locName()).setTrueIfNotGear()
        );
    }

    @Override
    public StationType forStation() {
        return StationType.MODIFY;
    }

    @Override
    public boolean canItemBeModified(LocReqContext context) {

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        StationShapelessFactory fac = StationShapelessFactory.create(ModRegistry.RECIPE_SER.SMITHING, this, 1);
        fac.input(ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(this.tier), 1);
        fac.input(ModRegistry.TIERED.STONE_TIER_MAP.get(this.tier), 1);
        fac.input(Items.DIAMOND, 1);
        return fac.criterion("player_level", trigger());
    }
}

