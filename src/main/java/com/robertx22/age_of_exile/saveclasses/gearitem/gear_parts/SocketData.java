package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPart;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class SocketData implements IGearPartTooltip, IStatsContainer {

    @Store
    public BaseGearType.SlotFamily slot_family;

    @Store
    public Integer percent = -1;

    @Store
    public Integer level = 0;

    @Store
    public String gem_id = "";

    @Store
    public String rune_id = "";

    @Factory
    public SocketData() {
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();

        if (isGem()) {
            getGem()
                .getFor(this.slot_family)
                .forEach(x -> {
                    ExactStatData exact = x.ToExactStat(this.percent, this.level);
                    list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, info), x, gear));
                });
        } else if (isRune()) {
            getRune()
                .getFor(this.slot_family)
                .forEach(x -> {
                    ExactStatData exact = x.ToExactStat(this.percent, this.level);
                    list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, info), x, gear));
                });
        }

        return list;
    }

    public boolean isEmpty() {
        return percent < 1;
    }

    public boolean isRune() {
        return getRune() != null;
    }

    public boolean isGem() {
        return getGem() != null;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Text> list = new ArrayList<Text>();
        GetAllStats(gear).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    @Override
    public IGearPart.Part getPart() {
        return Part.SOCKETS;
    }

    public Gem getGem() {
        if (SlashRegistry.Gems()
            .isRegistered(gem_id)) {
            return SlashRegistry.Gems()
                .get(gem_id);

        }
        return null;
    }

    public Rune getRune() {
        if (SlashRegistry.Runes()
            .isRegistered(rune_id)) {
            return SlashRegistry.Runes()
                .get(rune_id);
        }
        return null;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        List<ExactStatData> stats = new ArrayList<>();
        try {
            if (isGem()) {
                getGem()
                    .getFor(this.slot_family)
                    .forEach(x -> {
                        stats.add(x.ToExactStat(this.percent, this.level));
                    });
            } else if (isRune()) {
                getRune()
                    .getFor(this.slot_family)
                    .forEach(x -> {
                        stats.add(x.ToExactStat(this.percent, this.level));
                    });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stats;

    }

}
