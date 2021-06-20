package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.registry.Database;
import info.loenwind.autosave.Registry;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NullHelper;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.NbtCompound;

@Storable(handler = StatContainer.class)
public class StatContainer implements IHandler<StatContainer> {

    @Store
    public HashMap<String, StatData> stats = new HashMap<>();

    public transient HashMap<String, InCalcStatData> statsInCalc = new HashMap<>();

    public boolean isCalculating() {
        return !this.statsInCalc.isEmpty();
    }

    public StatContainer cloneForSpellStats() {
        StatContainer clone = new StatContainer();
        statsInCalc.entrySet()
            .forEach(x -> {
                clone.statsInCalc.put(x.getKey(), x.getValue()
                    .cloneForSpellStats());
            });
        return clone;
    }

    public void calculate() {

        statsInCalc.values()
            .forEach(x -> {
                stats.put(x.id, x.getCalculated());
            });
        statsInCalc.clear();
    }

    public InCalcStatData getStatInCalculation(Stat stat) {
        return getStatInCalculation(stat.GUID());
    }

    public InCalcStatData getStatInCalculation(String guid) {

        InCalcStatData data = statsInCalc.get(guid);

        if (data == null) {
            Stat stat = Database.Stats()
                .get(guid);
            if (stat != null) {
                statsInCalc.put(stat.GUID(), new InCalcStatData(stat.GUID()));

                return statsInCalc.get(stat.GUID());
            } else {
                return new InCalcStatData(new UnknownStat().GUID());
            }
        } else {
            return data;
        }
    }

    @Override
    public boolean store(Registry registry, Set<NBTAction> phase, NbtCompound nbt, Type type, String name, StatContainer object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        NbtCompound tag = new NbtCompound();

        if (object.stats != null) {
            List<StatData> list = new ArrayList<>(object.stats.values());

            int size = list.size();
            tag.putInt("size", size);

            for (int i = 0; i < size; i++) {
                tag.putString(i + "", list.get(i)
                    .toSerializationString());
            }
        }

        nbt.put(name, tag);
        return true;
    }

    @Override
    public StatContainer read(Registry registry, Set<NBTAction> phase, NbtCompound nbt, Type type, String name, StatContainer object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        if (nbt.contains(name)) {
            if (object == null) {
                object = new StatContainer();
            }
            NbtCompound tag = NullHelper.notnullM(nbt.getCompound(name), "CompoundNBT.getCompound()");

            if (tag.contains("size")) {
                int size = tag.getInt("size");

                object.stats = new HashMap<>();

                for (int i = 0; i < size; i++) {

                    String ser = tag.getString(i + "");
                    StatData data = StatData.fromSerializationString(ser);
                    object.stats.put(data.getId(), data);
                }
            }
        }
        return object;
    }
}
