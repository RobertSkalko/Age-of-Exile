package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class RarityRegistryContainer<T extends Rarity> extends SlashRegistryContainer<T> {

    public RarityRegistryContainer(SlashRegistryType type, T emptyDefault) {
        super(type, emptyDefault);
    }

    public boolean has(int rar) {
        return this.getMap()
            .containsKey(rar);
    }

    public final HashMap<Integer, T> getMap() {
        HashMap<Integer, T> map = new HashMap<>();
        getList().forEach(x -> map.put(x.Rank(), x));
        return map;
    }

    protected void add(T r) {
        this.getMap()
            .put(r.Rank(), r);
    }

    public List<T> getAllRarities() {
        return new ArrayList<T>(getMap().values());
    }

    public T highest() {
        return getAllRarities().stream()
            .max((Comparator.comparingInt(Rarity::Rank)))
            .get();
    }

    public T maxNonUnique() {
        return getAllRarities().stream()
            .filter(x -> x.Rank() != IRarity.Unique)
            .max((Comparator.comparingInt(Rarity::Rank)))
            .get();
    }

    public T lowest() {
        return getAllRarities().stream()
            .min((Comparator.comparingInt(Rarity::Rank)))
            .get();
    }

    public T random() {
        return RandomUtils.weightedRandom(getAllRarities());
    }

    public final T get(int i) {

        HashMap<Integer, T> map = getMap();

        if (map.containsKey(i)) {

            return map.get(i);
        } else {
            System.out.print("Rarity : " + i + " not found in: " + this.getType().id + " registry ");
            return lowest();
        }

    }
}
