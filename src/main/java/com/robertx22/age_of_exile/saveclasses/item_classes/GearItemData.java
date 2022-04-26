package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.*;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Storable
public class GearItemData implements ICommonDataItem<GearRarity> {

    // Stats
    @Store
    public BaseStatsData baseStats = new BaseStatsData();
    @Store
    public GearAffixesData affixes = new GearAffixesData();
    @Store
    public ReforgeData reforge = new ReforgeData();
    @Store
    public UniqueStatsData uniqueStats;

    // Stats

    // i added rename ideas to comments. As tiny as possible while still allowing people to understand kinda what it is
    // apparently people had big issues with many storage mods, So i should try minimize the nbt.
    @Store
    public String uniq_id = ""; // uniq_id
    @Store
    public String rarity = IRarity.COMMON_ID; // rar
    @Store
    public String item_id = ""; // item registry name
    @Store
    public int rp = -1; // pre_name rare prefix
    @Store
    public int rs = -1; // suf_name rare suffix
    @Store
    public int lvl = 1; // lvl
    @Store
    public String gear_type = "";
    @Store
    private float in = 0;
    @Store
    public boolean can_sal = true;

    public void upgradeToHigherRarity() {

        GearRarity old = this.getRarity();
        GearRarity rar = this.getRarity()
            .getHigherRarity();

        this.rarity = rar.GUID();

        int affixes = rar.affixes - old.affixes;

        for (int i = 0; i < affixes; i++) {
            this.affixes.addOneRandomAffix(this);
        }
    }

    public int getTier() {
        return LevelUtils.levelToTier(lvl);
    }

    public float getILVL() {

        return lvl;

    }

    public boolean canPlayerWear(EntityData data) {

        if (LevelUtils.tierToLevel(getTier()) > data.getLevel()) {
            return false;
        }

        return true;

    }

    public boolean isValidItem() {

        return ExileDB.GearTypes()
            .isRegistered(gear_type);
    }

    public int getTotalSockets() {
        int sockets = 0;

        // todo items will be able to have as many sockets as it can have random stats

        return sockets;

    }

    public boolean canGetAffix(Affix affix) {

        if (affix.only_one_per_item && affixes.containsAffix(affix)) {
            return false;
        }

        return affix.meetsRequirements(new GearRequestedFor(this));

    }

    public float getInstability() {
        return in;
    }

    public void setInstability(float insta) {
        this.in = insta;

        if (in < 0) {
            in = 0;
        }
    }

    public GearItemEnum getGearEnum() {

        if (this.isUnique()) {
            return GearItemEnum.UNIQUE;
        }

        return GearItemEnum.NORMAL;
    }

    @Override
    public String getRarityRank() {
        return rarity;
    }

    @Override
    public GearRarity getRarity() {
        return ExileDB.GearRarities()
            .get(this.rarity);
    }

    public ITextComponent name(ItemStack stack) {
        return stack.getHoverName();
    }

    public void WriteOverDataThatShouldStay(GearItemData newdata) {

        newdata.can_sal = this.can_sal;

    }

    public BaseGearType GetBaseGearType() {
        return ExileDB.GearTypes()
            .get(gear_type);
    }

    public List<IFormattableTextComponent> GetDisplayName(ItemStack stack) {

        try {
            TextFormatting format = this.getRarity()
                .textFormatting();

            if (useFullAffixedName()) {
                return getFullAffixedName();
            } else {
                if (uniqueStats != null) {
                    return getUniqueName();
                } else {
                    return getTooManyAffixesName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }

    public boolean uniqueBaseStatsReplaceBaseStats() {
        return uniqueStats != null && this.uniqueStats.getUnique(this) != null && !uniqueStats.getUnique(this).base_stats.isEmpty();
    }

    private List<IFormattableTextComponent> getFullAffixedName() {
        List<IFormattableTextComponent> list = new ArrayList<>();
        TextFormatting format = this.getRarity()
            .textFormatting();

        IFormattableTextComponent text = new StringTextComponent("");

        if (affixes.hasPrefix()) {

            AffixData prefix = affixes.pre.get(0);

            text
                .append(prefix.BaseAffix()
                    .locName()
                    .append(" "));
        }
        if (this.uniqueStats == null) {
            text.append(GetBaseGearType().locName());
        } else {
            text.append(uniqueStats.getUnique(this)
                .locName()
            );
        }

        if (affixes.hasSuffix()) {
            AffixData suffix = affixes.suf.get(0);
            text.append(" ")
                .append(suffix.BaseAffix()
                    .locName());
        }

        text.withStyle(format);

        list.addAll(TooltipUtils.cutIfTooLong(text, format));

        return list;

    }

    private List<IFormattableTextComponent> getUniqueName() {
        List<IFormattableTextComponent> list = new ArrayList<>();
        TextFormatting format = this.getRarity()
            .textFormatting();

        UniqueGear uniq = this.uniqueStats.getUnique(this);

        IFormattableTextComponent txt = new StringTextComponent("").append(uniq.locName()
            .withStyle(format));

        if (!uniq.replaces_name) {
            txt.append(new StringTextComponent(format + " ").append(GetBaseGearType().locName()
                .withStyle(format)));
        }

        list.addAll(TooltipUtils.cutIfTooLong(txt, format));

        return list;
    }

    private List<IFormattableTextComponent> getTooManyAffixesName() {
        List<IFormattableTextComponent> list = new ArrayList<>();
        TextFormatting format = this.getRarity()
            .textFormatting();

        Words prefix = RareItemAffixNames.getPrefix(this);
        Words suffix = RareItemAffixNames.getSuffix(this);

        if (prefix != null && suffix != null) {

            IFormattableTextComponent txt = new StringTextComponent("");

            txt.append(new StringTextComponent("").append(prefix.locName())
                .append(" "));

            txt.append(new StringTextComponent("").append(suffix.locName())
                .withStyle(format));

            txt.append(new StringTextComponent(" ").append(GetBaseGearType().locName()));

            txt.withStyle(format);

            list.addAll(TooltipUtils.cutIfTooLong(txt, format));

            return list;
        }

        return list;

    }

    private boolean useFullAffixedName() {

        if (getRarity().is_unique_item) {
            return false;
        }
        if (affixes.getNumberOfPrefixes() > 1) {
            return false;
        }
        if (affixes.getNumberOfSuffixes() > 1) {
            return false;
        }
        return true;
    }

    public List<IStatsContainer> GetAllStatContainers() {

        List<IStatsContainer> list = new ArrayList<IStatsContainer>();

        IfNotNullAdd(baseStats, list);

        affixes.getAllAffixesAndSockets()
            .forEach(x -> IfNotNullAdd(x, list));

        IfNotNullAdd(uniqueStats, list);

        return list;

    }

    public List<ExactStatData> GetAllStats() {

        List<ExactStatData> list = new ArrayList<>();
        for (IStatsContainer x : GetAllStatContainers()) {

            List<ExactStatData> stats = x.GetAllStats(this);

            stats.forEach(s -> {
                list.add(s);
            });

        }
        return list;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void BuildTooltip(TooltipContext ctx) {
        GearTooltipUtils.BuildTooltip(this, ctx.stack, ctx.tooltip, ctx.data);
    }

    public List<IRerollable> GetAllRerollable() {
        List<IRerollable> list = new ArrayList<IRerollable>();
        IfNotNullAdd(baseStats, list);

        affixes.getAllAffixesAndSockets()
            .forEach(x -> IfNotNullAdd(x, list));

        IfNotNullAdd(uniqueStats, list);
        return list;
    }

    private <T> void IfNotNullAdd(T obj, List<T> list) {
        if (obj != null) {
            list.add(obj);
        }
    }

    public static class SalvagedItemInfo {

        public int tier;
        public GearRarity rarity;

        public SalvagedItemInfo(int tier, GearRarity rarity) {
            this.tier = tier;
            this.rarity = rarity;
        }
    }

    public static List<ItemStack> getSalvagedResults(SalvagedItemInfo info) {
        try {
            List<ItemStack> list = new ArrayList<>();

            ItemStack dust = new ItemStack(ProfessionItems.SALVAGED_ESSENCE_MAP.get(SkillItemTier.of(info.tier))
                .get());

            dust.setCount(info.rarity.dust_per_sal.random());

            list.add(dust);

            if (info.rarity.rar_ess_per_sal > 0) {
                ItemStack essence = info.rarity.getRarityEssenceStack();
                essence.setCount(info.rarity.rar_ess_per_sal);
                list.add(essence);
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(ItemStack.EMPTY);
    }

    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        if (this.can_sal) {
            return getSalvagedResults(new SalvagedItemInfo(getTier(), getRarity()));
        }

        return Arrays.asList(ItemStack.EMPTY);
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {
        return this.can_sal;
    }

    @Override
    public ItemstackDataSaver<GearItemData> getStackSaver() {
        return StackSaving.GEARS;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        getStackSaver().saveTo(stack, this);
    }

    public boolean isWeapon() {
        try {
            if (GetBaseGearType()
                .family()
                .equals(SlotFamily.Weapon)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBetterThan(GearItemData other) {

        if (other.lvl > lvl + 10) {
            return false;
        }
        return getRarity()
            .isHigherThan(other.getRarity());
    }

}
