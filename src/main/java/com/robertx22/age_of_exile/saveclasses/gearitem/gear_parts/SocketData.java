package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
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
    public Integer perc = -1;

    @Store
    public Integer lvl = 0;

    @Store
    public String gem = "";

    @Store
    public String rune = "";

    @Factory
    public SocketData() {
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();

        BaseGearType.SlotFamily fam = gear.GetBaseGearType()
            .family();

        if (isGem()) {
            getGem()
                .getFor(fam)
                .forEach(x -> {
                    ExactStatData exact = x.ToExactStat(this.perc, this.lvl);
                    list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, perc, info), x, this.lvl));
                });
        } else if (isRune()) {
            getRune()
                .getFor(fam)
                .forEach(x -> {
                    ExactStatData exact = x.ToExactStat(this.perc, this.lvl);
                    list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, perc, info), x, this.lvl));
                });
        }

        return list;
    }

    public boolean isEmpty() {
        return perc < 1;
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
        if (ExileDB.Gems()
            .isRegistered(gem)) {
            return ExileDB.Gems()
                .get(gem);

        }
        return null;
    }

    public Rune getRune() {
        if (ExileDB.Runes()
            .isRegistered(rune)) {
            return ExileDB.Runes()
                .get(rune);
        }
        return null;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        BaseGearType.SlotFamily fam = gear.GetBaseGearType()
            .family();

        List<ExactStatData> stats = new ArrayList<>();
        try {
            if (isGem()) {
                getGem()
                    .getFor(fam)
                    .forEach(x -> {
                        stats.add(x.ToExactStat(this.perc, this.lvl));
                    });
            } else if (isRune()) {
                getRune()
                    .getFor(fam)
                    .forEach(x -> {
                        stats.add(x.ToExactStat(this.perc, this.lvl));
                    });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stats;

    }

}
