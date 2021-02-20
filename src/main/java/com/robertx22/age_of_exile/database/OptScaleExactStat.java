package com.robertx22.age_of_exile.database;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class OptScaleExactStat implements ITooltipList, IByteBuf<OptScaleExactStat> {
    public static OptScaleExactStat SERIALIZER = new OptScaleExactStat();

    public float v1 = 0;
    public float v2 = 0;
    public String stat;
    public String type;
    public boolean scale_to_lvl = false;

    private OptScaleExactStat() {
    }

    @Override
    public OptScaleExactStat getFromBuf(PacketByteBuf buf) {
        OptScaleExactStat data = new OptScaleExactStat();
        data.v1 = buf.readFloat();
        data.v2 = buf.readFloat();
        data.stat = buf.readString(100);
        data.type = buf.readString(50);
        data.scale_to_lvl = buf.readBoolean();
        return data;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeFloat(v1);
        buf.writeFloat(v2);
        buf.writeString(stat, 100);
        buf.writeString(type, 50);
        buf.writeBoolean(scale_to_lvl);
    }

    public OptScaleExactStat(float first, Stat stat) {
        this(first, stat, ModType.FLAT);
    }

    public OptScaleExactStat(float first, Stat stat, ModType type) {
        this.v1 = first;
        this.stat = stat.GUID();
        this.type = type.name();

    }

    public OptScaleExactStat(float first, float second, Stat stat, ModType type) {
        this.v1 = first;
        this.v2 = second;
        this.stat = stat.GUID();
        this.type = type.name();
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        Stat stat = getStat();
        TooltipStatInfo statInfo = new TooltipStatInfo(this.toExactStat(this.scale_to_lvl ? info.unitdata.getLevel() : 1), -99, info);
        return new ArrayList<>(stat.getTooltipList(new TooltipStatWithContext(statInfo, null, null)));
    }

    public OptScaleExactStat scale() {
        this.scale_to_lvl = true;
        return this;
    }

    public Stat getStat() {
        return Database.Stats()
            .get(stat);
    }

    public ModType getModType() {
        return ModType.fromString(type);
    }

    public ExactStatData toExactStat(int lvl) {
        Stat stat = Database.Stats()
            .get(this.stat);

        return ExactStatData.of(v1, v2, stat, getModType(), scale_to_lvl ? lvl : 1);

    }

    public String getDebugString() {

        return "" + (int) v1 + " " + getModType().name() + " " + CLOC.translate(getStat().locName());
    }

    public static void combine(List<OptScaleExactStat> list) {

        List<OptScaleExactStat> combined = new ArrayList<>();

        OptScaleExactStat current = null;

        int i = 0;

        while (!list.isEmpty()) {

            List<OptScaleExactStat> toRemove = new ArrayList<>();

            for (OptScaleExactStat stat : list) {

                if (i == 0) {
                    toRemove.add(stat);
                    current = new OptScaleExactStat(stat.v1, stat.v2, stat.getStat(), stat.getModType());
                    i++;
                    continue;
                }

                if (current.stat.equals(stat.stat)) {
                    if (current.type.equals(stat.type)) {
                        if (current.scale_to_lvl == stat.scale_to_lvl) {
                            current.v1 += stat.v1;
                            current.v2 += stat.v2;
                            toRemove.add(stat);
                        }
                    }
                }

                i++;

            }

            i = 0;
            combined.add(current);
            toRemove.forEach(n -> list.removeAll(toRemove));

        }

        list.addAll(combined);

    }

}
