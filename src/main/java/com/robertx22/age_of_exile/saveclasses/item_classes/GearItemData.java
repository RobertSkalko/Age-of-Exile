package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.database.data.salvage_outputs.SalvageOutput;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.*;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.DataItemType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.library_of_exile.utils.CLOC;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Storable
public class GearItemData implements ICommonDataItem<GearRarity> {

    // Stats
    @Store
    public BaseStatsData baseStats = new BaseStatsData();
    @Store
    public ImplicitStatsData implicitStats = new ImplicitStatsData();
    @Store
    public GearAffixesData affixes = new GearAffixesData();
    @Store
    public GearSocketsData sockets = new GearSocketsData();
    @Store
    public UniqueStatsData uniqueStats;

    // Stats

    // i added rename ideas to comments. As tiny as possible while still allowing people to understand kinda what it is
    // apparently people had big issues with many storage mods, So i should try minimize the nbt.
    @Store
    public boolean is_unique = false; // isuniq

    @Store
    public String unique_id = ""; // uniq_id

    @Store
    public String rarity = IRarity.COMMON_ID; // rar

    @Store
    public int rare_prefix = -1; // pre_name
    @Store
    public int rare_suffix = -1; // suf_name

    @Store
    public int level = 1; // lvl

    @Store
    public String gear_type = "";

    @Store
    private boolean ided = true;

    @Store
    private float in = 0;

    @Store
    public boolean sealed = false;

    @Store
    public boolean can_sal = true;

    @Store
    public boolean is_cor = false;

    public boolean isCorrupted() {
        return is_cor;
    }

    public boolean canPlayerWear(EntityCap.UnitData data) {

        if (level > data.getLevel()) {
            return false;
        }

        int gearlvlreq = GetBaseGearType().getLevelRange()
            .getMinLevel();

        return getRequirement().meetsReq(gearlvlreq, data);

    }

    public boolean isValidItem() {

        return Database.GearTypes()
            .isRegistered(gear_type);
    }

    public boolean canGetAffix(Affix affix) {

        if (affixes.containsAffix(affix)) {
            return false;
        }

        return affix.meetsRequirements(new GearRequestedFor(this));

    }

    public StatRequirement getRequirement() {
        StatRequirement req = new StatRequirement(GetBaseGearType().getStatRequirements());

        // todo add unique req;

        return req;
    }

    public float getInstability() {
        return in;
    }

    public void setInstability(float insta) {
        this.in = insta;
    }

    public boolean isIdentified() {
        return ided;
    }

    public void setIdentified(boolean bool) {
        this.ided = bool;
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
        return Database.GearRarities()
            .get(this.rarity);
    }

    public Text name(ItemStack stack) {
        return stack.getName();
    }

    // used when upgrading item rarity
    public Item getItem() {
        if (is_unique) {
            return Database.UniqueGears()
                .get(unique_id)
                .getUniqueItem();
        } else {
            if (gear_type.isEmpty()) {
                return Items.AIR;
            } else {
                return Database.GearTypes()
                    .get(gear_type)
                    .getItem();
            }
        }

    }

    public void WriteOverDataThatShouldStay(GearItemData newdata) {

        newdata.can_sal = this.can_sal;

    }

    public BaseGearType GetBaseGearType() {
        return Database.GearTypes()
            .get(gear_type);
    }

    public List<MutableText> GetDisplayName(ItemStack stack) {

        try {
            Formatting format = this.getRarity()
                .textFormatting();

            if (!isIdentified()) {
                MutableText text = new SText(format + "")
                    .append(Words.Unidentified.locName())
                    .append(" ")
                    .append(getRarity().locName())
                    .append(" ")
                    .append(GetBaseGearType().locName());

                return Arrays.asList(text);
            }

            if (useFullAffixedName()) {
                return getFullAffixedName();
            } else {
                if (isUnique()) {
                    return getUniqueName();
                } else if (hasRuneWord()) {
                    return getRuneWordName();
                } else {
                    return getTooManyAffixesName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }

    private List<MutableText> getFullAffixedName() {
        List<MutableText> list = new ArrayList<>();
        Formatting format = this.getRarity()
            .textFormatting();

        MutableText text = new LiteralText(format + "");

        if (affixes.hasPrefix()) {

            AffixData prefix = affixes.prefixes.get(0);

            text.append(format + "")
                .append(prefix.BaseAffix()
                    .locName()
                    .formatted(format)
                    .append(" "));
        }
        if (this.uniqueStats == null) {
            text.append(GetBaseGearType().locName()
                .formatted(format));
        } else {
            text.append(uniqueStats.getUnique(this)
                .locName()
                .formatted(format));
        }

        list.add(text);

        if (affixes.hasSuffix()) {
            AffixData suffix = affixes.suffixes.get(0);
            list.add(new LiteralText(format + "").append(suffix.BaseAffix()
                .locName())
                .formatted(format));
        }
        return list;

    }

    private List<MutableText> getUniqueName() {
        List<MutableText> list = new ArrayList<>();
        Formatting format = this.getRarity()
            .textFormatting();

        UniqueGear uniq = this.uniqueStats.getUnique(this);
        list.add(new LiteralText(format + "").append(uniq.locName()
            .formatted(format)));
        list.add(new LiteralText(format + "").append(GetBaseGearType().locName()
            .formatted(format)));
        return list;
    }

    private List<MutableText> getRuneWordName() {
        List<MutableText> list = new ArrayList<>();
        Formatting format = this.getRarity()
            .textFormatting();
        list.add(new LiteralText(format + "").append(this.sockets.getRuneWord()
            .locName()));
        list.add(new LiteralText(format + "").append(GetBaseGearType().locName()
            .formatted(format)));
        return list;
    }

    private List<MutableText> getTooManyAffixesName() {
        List<MutableText> list = new ArrayList<>();
        Formatting format = this.getRarity()
            .textFormatting();

        Words prefix = RareItemAffixNames.getPrefix(this);
        Words suffix = RareItemAffixNames.getSuffix(this);

        if (prefix != null && suffix != null) {

            MutableText txt = new LiteralText(format + "");

            txt.append(new LiteralText(format + "").append(prefix.locName())
                .append(" "));

            txt.append(new LiteralText(format + "").append(suffix.locName())
                .formatted(format));

            String translated = CLOC.translate(txt) + GetBaseGearType().translate();

            if (translated.length() > 30) {
                list.add(txt);
                list.add(new LiteralText(format + " ").append(GetBaseGearType().locName()));
            } else {
                txt.append(new LiteralText(" ").append(GetBaseGearType().locName()));
                list.add(txt);
            }

            return list;
        }

        return list;

    }

    public boolean hasRuneWord() {
        return sockets.getRuneWord() != null;
    }

    private boolean useFullAffixedName() {

        if (isUnique() && affixes.getNumberOfAffixes() == 0) {
            return false;
        }
        if (hasRuneWord()) {
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

    public List<IStatsContainer> GetAllStatContainers(boolean includeBase) {

        List<IStatsContainer> list = new ArrayList<IStatsContainer>();

        if (includeBase) {
            IfNotNullAdd(baseStats, list);
        }

        IfNotNullAdd(implicitStats, list);

        affixes.getAllAffixesAndSockets()
            .forEach(x -> IfNotNullAdd(x, list));

        IfNotNullAdd(sockets, list);

        IfNotNullAdd(uniqueStats, list);

        return list;

    }

    public List<ExactStatData> GetAllStats(boolean includebase, boolean includelocaladditions) {

        List<ExactStatData> list = new ArrayList<>();
        GetAllStatContainers(includebase).stream()
            .forEach(x -> {

                List<ExactStatData> stats = x.GetAllStats(this);

                stats.forEach(s -> {

                    if (!x.isBaseStats() && s.shouldBeAddedToLocalStats(this)) {
                        if (includelocaladditions) {
                            list.add(s);
                        }
                    } else {
                        list.add(s);
                    }
                });

            });
        return list;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void BuildTooltip(TooltipContext ctx) {
        GearTooltipUtils.BuildTooltip(this, ctx.stack, ctx.tooltip, ctx.data);
    }

    public List<IRerollable> GetAllRerollable() {
        List<IRerollable> list = new ArrayList<IRerollable>();
        IfNotNullAdd(baseStats, list);

        affixes.getAllAffixesAndSockets()
            .forEach(x -> IfNotNullAdd(x, list));

        list.add(implicitStats);

        IfNotNullAdd(uniqueStats, list);
        return list;
    }

    private <T> void IfNotNullAdd(T obj, List<T> list) {
        if (obj != null) {
            list.add(obj);
        }
    }

    @Override
    public List<ItemStack> getSalvageResult(float salvageBonus) {
        if (this.can_sal) {

            SalvageOutput sal = RandomUtils.weightedRandom(Database.SalvageOutputs()
                .getFiltered(x -> x.isForItem(this))
            );

            return sal.getResult(this);

        } else
            return Arrays.asList(ItemStack.EMPTY);

    }

    @Override
    public DataItemType getDataType() {
        return DataItemType.GEAR;
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {

        return this.can_sal;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        Gear.Save(stack, this);
    }

    @Override
    public int getTier() {

        if (this.isUnique()) {
            try {
                return this.uniqueStats.getUnique(this)
                    .getTier();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public boolean isWeapon() {
        try {
            if (GetBaseGearType()
                .family()
                .equals(BaseGearType.SlotFamily.Weapon)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBetterThan(GearItemData other) {

        if (other.level > level + 10) {
            return false;
        }
        return getRarity()
            .isHigherThan(other.getRarity());
    }

}
