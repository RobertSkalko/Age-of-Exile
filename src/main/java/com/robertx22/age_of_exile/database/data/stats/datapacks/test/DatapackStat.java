package com.robertx22.age_of_exile.database.data.stats.datapacks.test;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DatapackStat extends Stat implements IAutoGson<DatapackStat> {

    public static DatapackStat SERIALIZER = new DatapackStat();
    public static String SER = "data";

    public String ser = SER;
    public String id = "";
    public Elements ele = Elements.Physical;

    public DataPackStatEffect effect = new DataPackStatEffect();

    public transient String locname;
    public transient String locdesc;

    @Override
    public Elements getElement() {
        return this.ele;
    }

    @Override
    public String locDescForLangFile() {
        return locdesc;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<DatapackStat> getClassForSerialization() {
        return DatapackStat.class;
    }

    @Override
    public boolean isFromDatapack() {
        return true;
    }

}
