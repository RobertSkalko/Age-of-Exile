package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.misc.MoreSocketsStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class GearSocketsData implements IStatsContainer, IGearPartTooltip {

    @Store
    public List<SocketData> sockets = new ArrayList<>();

    @Store
    public int slots = 0;

    @Store
    public String word = "";

    @Store
    public int word_perc = 0;

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();

        sockets.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear, info)));
        RuneWord word = getRuneWord();

        if (word != null) {
            word.stats.forEach(s -> {
                ExactStatData exact = s.ToExactStat(word_perc, gear.lvl);
                list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, word_perc, info), s, gear.lvl));
            });
        }
        return list;
    }

    public void setSocketsCount(GearItemData gear) {

        List<ExactStatData> stats = gear.GetAllStats();

        this.slots = 0;

        for (ExactStatData x : stats) {
            if (x.getStat() == MoreSocketsStat.getInstance()) {
                this.slots += x.getAverageValue();
            }
        }
    }

    public int getEmptySockets() {
        return slots - sockets.size();
    }

    public RuneWord getRuneWord() {

        if (ExileDB.Runewords()
            .isRegistered(word)) {
            return ExileDB.Runewords()
                .get(word);
        }
        return null;

    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        List<ExactStatData> list = new ArrayList<>();
        sockets.forEach(x -> list.addAll(x.GetAllStats(gear)));
        RuneWord word = getRuneWord();

        if (word != null) {
            word.stats.forEach(s -> list.add(s.ToExactStat(word_perc, gear.lvl)));
        }

        return list;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Text> list = new ArrayList<Text>();
        getAllStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    @Override
    public Part getPart() {
        return null;
    }
}
