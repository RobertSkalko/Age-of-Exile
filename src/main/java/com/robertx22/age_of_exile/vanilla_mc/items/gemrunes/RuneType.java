package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

public enum RuneType {
    YUN(100, "yun", "Yun", 5),
    VEN(100, "ven", "Ven", 5),
    NOS(1000, "nos", "Nos", 1),
    MOS(1000, "mos", "Mos", 1),
    ITA(1000, "ita", "Ita", 1),
    CEN(1000, "cen", "Cen", 2),
    FEY(1000, "fey", "Fey", 2),
    DOS(1000, "dos", "Dos", 2),
    ANO(1000, "ano", "Ano", 2),
    TOQ(1000, "toq", "Toq", 2),
    ORU(500, "oru", "Oru", 4),
    WIR(200, "wir", "Wir", 4),
    ENO(1000, "eno", "Eno", 3),
    HAR(1000, "har", "Har", 3),
    XER(1000, "xer", "Xer", 3);

    public String id;
    public String locName;
    public int tier;
    public int weight;

    RuneType(int weight, String id, String locName, int tier) {
        this.id = id;
        this.locName = locName;
        this.tier = tier;
        this.weight = weight;
    }

}
