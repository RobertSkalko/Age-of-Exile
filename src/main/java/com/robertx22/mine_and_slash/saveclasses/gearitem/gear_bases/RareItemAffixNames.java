package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.localization.Words;

import java.util.HashMap;

public class RareItemAffixNames {

    public static Words getPrefix(GearItemData gear) {

        if (prefixAny.containsKey(gear.rare_prefix)) {
            return prefixAny.get(gear.rare_prefix);
        }

        return null;
    }

    public static Words getSuffix(GearItemData gear) {

        if (getSuffixMap(gear.GetBaseGearType())
            .containsKey(gear.rare_suffix)) {
            return getSuffixMap(gear.GetBaseGearType()).get(gear.rare_suffix);
        }

        return null;
    }

    public static HashMap<Integer, Words> prefixAny = new HashMap<Integer, Words>() {{
        put(0, Words.Miracle);
        put(1, Words.Oblivion);
        put(2, Words.Golem);
        put(3, Words.Beast);
        put(4, Words.Spirit);
        put(5, Words.Rage);
    }};

    private static HashMap<Integer, Words> ring = new HashMap<Integer, Words>() {{
        put(0, Words.Band);
        put(1, Words.Eye);
        put(2, Words.Loop);
    }};
    private static HashMap<Integer, Words> necklace = new HashMap<Integer, Words>() {{
        put(0, Words.Beads);
        put(1, Words.Charm);
        put(2, Words.Locket);
    }};
    private static HashMap<Integer, Words> helmet = new HashMap<Integer, Words>() {{
        put(0, Words.Crown);
        put(1, Words.Circlet);
        put(2, Words.Horn);
    }};
    private static HashMap<Integer, Words> chest = new HashMap<Integer, Words>() {{
        put(0, Words.Cloak);
        put(1, Words.Coat);
        put(2, Words.Mantle);
        put(3, Words.Shell);
    }};

    private static HashMap<Integer, Words> shield = new HashMap<Integer, Words>() {{
        put(0, Words.Aegis);
        put(1, Words.Barrier);
        put(2, Words.Guard);
        put(3, Words.Tower);
    }};
    private static HashMap<Integer, Words> boots = new HashMap<Integer, Words>() {{
        put(0, Words.Road);
        put(1, Words.Dash);
        put(2, Words.Hoof);
    }};
    private static HashMap<Integer, Words> weapons = new HashMap<Integer, Words>() {{
        put(0, Words.Bane);
        put(1, Words.Bite);
        put(2, Words.Wind);
        put(3, Words.Star);
        put(4, Words.Splitter);
    }};
    private static HashMap<Integer, Words> pants = new HashMap<Integer, Words>() {{
        put(0, Words.Leggings);
        put(1, Words.Legguards);
        put(2, Words.Legwraps);
        put(3, Words.Robes);
        put(4, Words.Britches);
    }};

    private static HashMap<Integer, Words> defaults = new HashMap<Integer, Words>() {
        {
            put(0, Words.Creation);

        }
    };

    public static HashMap<Integer, Words> getSuffixMap(BaseGearType slot) {

        if (slot.isWeapon()) {
            return weapons;
        }
        if (slot.getTags()
            .contains(BaseGearType.SlotTag.Boots)) {
            return boots;
        }
        if (slot.getTags()
            .contains(BaseGearType.SlotTag.Chest)) {
            return chest;
        }
        if (slot.getTags()
            .contains(BaseGearType.SlotTag.Helmet)) {
            return helmet;
        }
        if (slot.getTags()
            .contains(BaseGearType.SlotTag.Ring)) {
            return ring;
        }
        if (slot.getTags()
            .contains(BaseGearType.SlotTag.Necklace)) {
            return necklace;
        }
        if (slot.getTags()
            .contains(BaseGearType.SlotTag.Shield)) {
            return shield;
        }
        if (slot.getTags()
            .contains(BaseGearType.SlotTag.Pants)) {
            return pants;
        }

        return defaults;

    }

}
