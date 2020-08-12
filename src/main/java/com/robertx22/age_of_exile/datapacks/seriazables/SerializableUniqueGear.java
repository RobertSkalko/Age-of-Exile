package com.robertx22.age_of_exile.datapacks.seriazables;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class SerializableUniqueGear implements IUnique {

    List<StatModifier> uniqueStats;
    int tier;
    int weight;

    String guid;
    String langNameID;
    String langDescID;
    String gearType;
    Identifier itemID;

    public SerializableUniqueGear(List<StatModifier> uniqueStats, int tier, int weight, String guid, String langNameID, String langDescID, String gearType, Identifier itemID) {
        this.uniqueStats = uniqueStats;
        this.tier = tier;
        this.weight = weight;
        this.guid = guid;
        this.langNameID = langNameID.contains("item.") ? langNameID : "item." + langNameID;
        this.langDescID = langDescID.contains("item.") ? langDescID : "item." + langDescID;
        this.gearType = gearType;
        this.itemID = itemID;
    }

    @Override
    public Identifier getResourceLocForItem() {
        return itemID;
    }

    @Override
    public Item getUniqueItem() {
        return Registry.ITEM.get(itemID);
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public List<StatModifier> uniqueStats() {
        return this.uniqueStats;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return "";
    }

    @Override
    public String locDescLangFileGUID() {
        return langDescID;
    }

    @Override
    public String locNameLangFileGUID() {
        return langNameID;
    }

    @Override
    public String GUID() {
        return guid;
    }

    @Override
    public BaseGearType getBaseGearType() {
        return SlashRegistry.GearTypes()
            .get(gearType);
    }
}
