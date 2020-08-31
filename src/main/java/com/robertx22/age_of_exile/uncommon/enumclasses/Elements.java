package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.uncommon.interfaces.IColor;
import com.robertx22.library_of_exile.utils.RGB;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public enum Elements implements IColor {
    Physical(0, new RGB(240, 157, 55), false, "Physical", Formatting.GOLD, Items.COAL, "physical", "\u2726"),
    Fire(1, new RGB(255, 0, 0), true, "Fire", Formatting.RED, Items.FIRE_CHARGE, "fire",
        "\u2600"
    ),
    Water(2, new RGB(0, 128, 255), true, "Ice", Formatting.AQUA, Items.SNOWBALL, "water", "\u2749"
    ),
    Thunder(3, new RGB(204, 0, 255), true, "Lightning", Formatting.YELLOW, Items.GLOWSTONE_DUST,
        "thunder", "\u272A"),
    Nature(4, new RGB(0, 204, 0), true, "Poison", Formatting.DARK_GREEN, Items.SLIME_BALL, "nature", "\u273F"),
    Elemental(5, new RGB(0, 0, 0), false, "Elemental", Formatting.GOLD, Items.EMERALD, "elemental", "\u269C"
    );

    public boolean isSingleElement = true;
    public Item projectileItem;
    private RGB color;

    Elements(int i, RGB color, boolean isSingleElement, String dmgname, Formatting format, Item item,
             String guidname, String icon) {
        this.i = i;
        this.color = color;
        this.isSingleElement = isSingleElement;
        this.dmgName = dmgname;
        this.format = format;
        this.projectileItem = item;
        this.guidName = guidname;
        this.icon = icon;
    }

    public String dmgName;
    public String guidName;
    public int i = 0;
    public String icon;

    public Formatting format;

    public boolean isPhysical() {
        return this == Physical;
    }

    private static List<Elements> allIncludingPhys = Arrays.asList(Physical, Fire, Water, Nature, Thunder);
    private static List<Elements> allExcludingPhys = Arrays.asList(Fire, Water, Nature, Thunder, Elemental);
    private static List<Elements> allSingleElementals = Arrays.asList(Fire, Water, Nature, Thunder);

    public static List<Elements> getAllSingleElementals() {
        return allSingleElementals;
    }

    public static List<Elements> getAllSingleIncludingPhysical() {
        return allIncludingPhys;
    }

    public static List<Elements> getEverythingBesidesPhysical() {
        return allExcludingPhys;
    }

    @Override
    public RGB getRGBColor() {
        return color;
    }

}
