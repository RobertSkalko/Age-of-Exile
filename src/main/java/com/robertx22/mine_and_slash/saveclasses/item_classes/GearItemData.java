package com.robertx22.mine_and_slash.saveclasses.item_classes;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.*;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.*;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.datasaving.ItemType;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.DataItemType;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
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
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

@Storable
public class GearItemData implements ICommonDataItem<GearRarity> {

    public boolean meetsStatRequirements(EntityCap.UnitData data) {

        if (!getStatRequirements().passesStatRequirements(data, this)) {
            return false;
        }

        return true;
    }

    public FinalizedGearStatReq getStatRequirements() {
        FinalizedGearStatReq req = GetBaseGearType().getStatRequirements()
            .getFinalized(this);
        req.calculate(this);
        return req;
    }

    public int getMaxSockets() {
        return this.getRarity()
            .maxSockets();
    }

    public StatData getStatTotalOf(Stat stat) {

        StatData data = new StatData();

        this.GetAllStats(true, true)
            .forEach(x -> data.add(x, null));

        return data;
    }

    public void insertJewel(JewelData jewel) {

        AffixData touse = null;

        if (jewel.affix.affixType == Affix.Type.prefix) {
            for (int i = 0; i < affixes.prefix_sockets.size(); i++) {
                AffixData socket = affixes.prefix_sockets.get(i);
                if (socket.isSocketAndEmpty()) {
                    touse = socket;
                }
            }
        } else {
            for (int i = 0; i < affixes.suffix_sockets.size(); i++) {
                AffixData socket = affixes.suffix_sockets.get(i);
                if (socket.isSocketAndEmpty()) {
                    touse = socket;
                }
            }
        }

        if (touse != null) {
            touse.level = jewel.affix.level;
            touse.baseAffix = jewel.affix.baseAffix;
            touse.percent = jewel.affix.percent;
            touse.tier = jewel.affix.tier;
        }
    }

    public boolean canInsertJewel(JewelData jewel) {

        if (jewel.affix.level > this.level) {
            return false;
        }

        if (!canGetAffix(jewel.affix.getAffix())) {
            return false;
        }

        return affixes.getAllAffixesAndSockets()
            .stream()
            .anyMatch(x -> x.isSocketAndEmpty() && x.affixType == jewel.affix.affixType);
    }

    public boolean canGetAffix(Affix affix) {

        if (affixes.containsAffix(affix)) {
            return false;
        }

        if (!affix.meetsRequirements(new GearRequestedFor(this))) {
            return false;
        }

        return affixes.getAllAffixesAndSockets()
            .stream()
            .anyMatch(x -> x.isSocketAndEmpty() && x.affixType == affix.type);
    }

    @Store
    public boolean is_unique = false;

    @Store
    public String unique_id = "";

    @Store
    public int rarity;

    @Store
    public int rare_prefix = -1;
    @Store
    public int rare_suffix = -1;

    @Store
    public int level = 1;

    @Store
    public boolean is_not_my_mod = false;

    @Store
    public String gear_type = "";

    @Store
    private boolean ided = true;

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
    public int getRarityRank() {
        return MathHelper.clamp(rarity, Rarities.Gears.lowest()
            .Rank(), Rarities.Gears.highest()
            .Rank());
    }

    @Override
    public GearRarity getRarity() {
        return Rarities.Gears.get(this.rarity);
    }

    public boolean changesItemStack() {
        return this.is_not_my_mod == false;
    }

    public Text name(ItemStack stack) {

        return stack.getName();

    }

    // Stats
    @Store
    public BaseStatsData baseStats = new BaseStatsData();
    @Store
    public ImplicitStatsData implicitStats = new ImplicitStatsData();
    @Store
    public GearAffixesData affixes = new GearAffixesData();
    @Store
    public UniqueStatsData uniqueStats;

    // Stats

    @Store
    public boolean isSalvagable = true;

    // used when upgrading item rarity
    public Item getItem() {
        if (is_unique) {
            return SlashRegistry.UniqueGears()
                .get(unique_id)
                .getUniqueItem();
        } else {
            if (gear_type.isEmpty()) {
                return Items.AIR;
            } else {
                return SlashRegistry.GearTypes()
                    .get(gear_type)
                    .getItem();
            }
        }

    }

    public boolean canHaveMoreSockets() {
        int currentGearSockets = (int) affixes.getAllAffixesAndSockets()
            .stream()
            .filter(x -> x.is_socket)
            .count();
        int maxGearSockets = getMaxSockets();

        return currentGearSockets < maxGearSockets;
    }

    public void WriteOverDataThatShouldStay(GearItemData newdata) {

        newdata.isSalvagable = this.isSalvagable;
        newdata.is_not_my_mod = this.is_not_my_mod;

    }

    public BaseGearType GetBaseGearType() {
        return SlashRegistry.GearTypes()
            .get(gear_type);
    }

    public MutableText getOnGroundDisplayName() {
        return new SText(getRarity().textFormatting() + "").append(GetBaseGearType().locName());
    }

    public MutableText GetDisplayName(ItemStack stack) {

        Formatting format = this.getRarity()
            .textFormatting();

        if (!isIdentified()) {
            MutableText text = new SText(format + "")
                .append(Words.Unidentified.locName())
                .append(" ")
                .append(getRarity().locName())
                .append(" ")
                .append(GetBaseGearType().locName());

            return text;
        }

        MutableText text = new LiteralText(format + "[");

        if (useAffixedName()) {

            if (affixes.hasPrefix()) {

                AffixData prefix = affixes.prefixes.get(0);

                text.append(format + "")
                    .append(prefix.BaseAffix()
                        .locName()
                        .append(" "));
            }
            text.append(GetBaseGearType().locName()
                .formatted(format));

            if (affixes.hasSuffix()) {
                AffixData suffix = affixes.suffixes.get(0);

                text.append(format + " ")
                    .append(suffix.BaseAffix()
                        .locName())
                    .append("");
            }
        } else {
            if (!isUnique()) {
                Words prefix = RareItemAffixNames.getPrefix(this);
                Words suffix = RareItemAffixNames.getSuffix(this);

                if (prefix != null && suffix != null) {
                    text.append(prefix.locName())
                        .append(" ")
                        .append(suffix.locName());
                }

            } else {
                text.append(this.uniqueStats.getUnique()
                    .locName());

            }

            text.append(" ")
                .append(GetBaseGearType().locName());
        }

        return text.append("]")
            .formatted(format);

    }

    private boolean useAffixedName() {

        if (isUnique()) {
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
    public ItemStack getSalvageResult(float salvageBonus) {

        if (this.isSalvagable) { // problems with issalvagable?
            ItemStack stack = ItemStack.EMPTY;
            int tier = 0;

            int min = 1;
            int max = 2;

            if (rarity == IRarity.Common) {
                max = 1;
            }

            min = tryIncreaseAmount(salvageBonus, min);
            max = tryIncreaseAmount(salvageBonus, max);

            if (is_unique) {
                try {
                    tier = this.uniqueStats.getUnique()
                        .getTier();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (RandomUtils.roll(this.getRarity()
                .salvageLotteryWinChance())) {

                Item item = SlashRegistry.CurrencyItems()
                    .getWrapped()
                    .ofCurrencyUsableOnItemType(ItemType.GEAR)
                    .ofTierRange(tier - 5, tier + 2)
                    .random();

                int tierAmountBonus = (tier / 4);

                int amount = RandomUtils.RandomRange(min + tierAmountBonus, max + tierAmountBonus);
                stack = new ItemStack(item);
                stack.setCount(amount);

                return stack;

            } else {

                int amount = RandomUtils.RandomRange(min, max);

                Item item = ItemUtils.randomMagicEssence();
                stack = new ItemStack(item);
                stack.setCount(amount);

            }

            return stack;
        } else
            return ItemStack.EMPTY;

    }

    @Override
    public DataItemType getDataType() {
        return DataItemType.GEAR;
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {

        return this.isSalvagable;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        Gear.Save(stack, this);
    }

    @Override
    public int getTier() {

        if (this.isUnique()) {
            try {
                return this.uniqueStats.getUnique()
                    .getTier();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

}
