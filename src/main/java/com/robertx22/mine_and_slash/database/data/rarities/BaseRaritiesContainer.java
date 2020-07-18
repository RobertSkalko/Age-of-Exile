package com.robertx22.mine_and_slash.database.data.rarities;

import com.google.common.base.Preconditions;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class BaseRaritiesContainer<T extends Rarity> {

    public T minRarity;
    public T maxRarity;
    public T maxNonUniqueRarity;

    HashMap<Integer, T> map = new HashMap<>();

    public BaseRaritiesContainer() {

    }

    public boolean has(int rar) {
        return map.containsKey(rar);
    }

    public void updateFromDatapack(List<T> rarities) {

        Preconditions.checkArgument(rarities.size() == map.size(), "Rarities can't be added or removed through datapacks. This is a hard limitation.");

        map = new HashMap<>();
        rarities.forEach(x -> map.put(x.Rank(), x));
        onInit();
    }

    public final void onInit() {
        this.minRarity = getAllRarities().stream()
            .min((Comparator.comparingInt(Rarity::Rank)))
            .get();

        this.maxRarity = getAllRarities().stream()
            .max((Comparator.comparingInt(Rarity::Rank)))
            .get();

        this.maxNonUniqueRarity = getAllRarities().stream()
            .filter(x -> x.Rank() != IRarity.Unique)
            .max((Comparator.comparingInt(Rarity::Rank)))
            .get();

    }

    public T getHigherRarity(T rar) {

        for (T check : getAllRarities()) {
            if (check.Rank() > rar.Rank()) {
                return check;
            }

        }
        return rar;

    }

    public T getLowerRarity(T rar) {
        for (T check : getAllRarities()) {
            if (check.Rank() < rar.Rank()) {
                return check;
            }
        }
        return rar;
    }

    public final HashMap<Integer, T> getMap() {
        return map;
    }

    protected void add(T r) {
        this.getMap()
            .put(r.Rank(), r);
    }

    public List<T> getAllRarities() {
        return new ArrayList<>(getMap().values());
    }

    public T highest() {
        return maxRarity;
    }

    public T highestNonUnique() {
        return maxNonUniqueRarity;
    }

    public T lowest() {
        return minRarity;
    }

    public T random() {
        return RandomUtils.weightedRandom(getAllRarities());
    }

    public final T get(int i) {

        if (i < minRarity.Rank()) {
            try {
                throw new Exception("Rarity can't be less than " + minRarity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (i > maxRarity.Rank()) {
            try {
                throw new Exception("Rarity can't be more than " + maxRarity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return getMap().get(i);

    }

    public abstract RarityTypeEnum getType();
}
