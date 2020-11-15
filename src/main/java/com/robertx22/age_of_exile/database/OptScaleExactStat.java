package com.robertx22.age_of_exile.database;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class OptScaleExactStat implements IApplyableStats, ITooltipList, IByteBuf<OptScaleExactStat> {
    public static OptScaleExactStat SERIALIZER = new OptScaleExactStat();

    public float first = 0;
    public float second = 0;
    public String stat;
    public String type;
    public boolean scaleToLevel = false;

    public transient Stat transientstat;

    private OptScaleExactStat() {
    }

    @Override
    public OptScaleExactStat getFromBuf(PacketByteBuf buf) {
        OptScaleExactStat data = new OptScaleExactStat();
        data.first = buf.readFloat();
        data.second = buf.readFloat();
        data.stat = buf.readString(100);
        data.type = buf.readString(50);
        data.scaleToLevel = buf.readBoolean();
        return data;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeFloat(first);
        buf.writeFloat(second);
        buf.writeString(stat, 100);
        buf.writeString(type, 50);
        buf.writeBoolean(scaleToLevel);
    }

    public OptScaleExactStat(float first, Stat stat) {
        this(first, stat, ModType.FLAT);
    }

    public OptScaleExactStat(float first, Stat stat, ModType type) {
        this.first = first;
        this.stat = stat.GUID();
        this.type = type.name();
        this.transientstat = stat;
    }

    public OptScaleExactStat(float first, float second, Stat stat, ModType type) {
        this.first = first;
        this.second = second;
        this.stat = stat.GUID();
        this.type = type.name();
        this.transientstat = stat;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        Stat stat = getStat();
        TooltipStatInfo statInfo = new TooltipStatInfo(this.toExactStat(info.unitdata.getLevel()), info);
        return new ArrayList<>(stat.getTooltipList(new TooltipStatWithContext(statInfo, null, null)));
    }

    public OptScaleExactStat scale() {
        this.scaleToLevel = true;
        return this;
    }

    public Stat getStat() {
        return SlashRegistry.Stats()
            .get(stat);
    }

    public ModType getModType() {
        return ModType.fromString(type);
    }

    public ExactStatData toExactStat(int lvl) {
        Stat stat = SlashRegistry.Stats()
            .get(this.stat);

        return ExactStatData.of(first, second, stat, getModType(), lvl);

    }

    public void applyStats(EntityCap.UnitData data, int lvl) {
        toExactStat(scaleToLevel ? lvl : 1)
            .applyStats(data);
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        toExactStat(scaleToLevel ? data.getLevel() : 1)
            .applyStats(data);
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
                    current = new OptScaleExactStat(stat.first, stat.second, stat.getStat(), stat.getModType());
                    i++;
                    continue;
                }

                if (current.stat.equals(stat.stat)) {
                    if (current.type.equals(stat.type)) {
                        if (current.scaleToLevel == stat.scaleToLevel) {
                            current.first += stat.first;
                            current.second += stat.second;
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
