package com.robertx22.age_of_exile.database;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.RpgLevel;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.serialization.IByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class OptScaleExactStat implements IByteBuf<OptScaleExactStat> {
    public static OptScaleExactStat SERIALIZER = new OptScaleExactStat();

    public float v1 = 0;
    public String stat;
    public String type;
    public boolean scale_to_lvl = false;

    private OptScaleExactStat() {
    }

    @Override
    public OptScaleExactStat getFromBuf(PacketBuffer buf) {
        OptScaleExactStat data = new OptScaleExactStat();
        data.v1 = buf.readFloat();
        data.stat = buf.readUtf(100);
        data.type = buf.readUtf(50);
        data.scale_to_lvl = buf.readBoolean();
        return data;
    }

    @Override
    public void toBuf(PacketBuffer buf) {
        buf.writeFloat(v1);
        buf.writeUtf(stat, 100);
        buf.writeUtf(type, 50);
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

    public List<ITextComponent> GetTooltipString(TooltipInfo info, int lvl) {
        return toExactStat(lvl).GetTooltipString(new RpgLevel(lvl));
    }

    public List<ITextComponent> GetTooltipString(TooltipInfo info) {
        int lvl = scale_to_lvl ? info.unitdata.getLevel() : 1;
        return toExactStat(lvl).GetTooltipString(new RpgLevel(lvl));
    }

    public OptScaleExactStat scale() {
        this.scale_to_lvl = true;
        return this;
    }

    public Stat getStat() {
        return ExileDB.Stats()
            .get(stat);
    }

    public ModType getModType() {
        return ModType.fromString(type);
    }

    public ExactStatData toExactStat(int lvl) {
        Stat stat = ExileDB.Stats()
            .get(this.stat);

        return ExactStatData.of(v1, stat, getModType(), scale_to_lvl ? lvl : 1);

    }

    public ExactStatData ToExactScaleToLevel(int lvl) {
        Stat stat = ExileDB.Stats()
            .get(this.stat);

        return ExactStatData.of(v1, stat, getModType(), lvl);

    }

    public String getDebugString() {
        return "" + (int) v1 + " " + getModType().name() + " " + getStat().translate();
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
                    current = new OptScaleExactStat(stat.v1, stat.getStat(), stat.getModType());
                    i++;
                    continue;
                }

                if (current.stat.equals(stat.stat)) {
                    if (current.type.equals(stat.type)) {
                        if (current.scale_to_lvl == stat.scale_to_lvl) {
                            current.v1 += stat.v1;
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
