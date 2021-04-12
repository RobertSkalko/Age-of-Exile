package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.CraftEssenceItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.GearMaterialItem;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Locale;

public class GearMaterialRegister extends BaseItemRegistrator {

    public enum TYPE {CLOTH, LEATHER, ORE}

    public HashMap<TYPE, HashMap<Integer, GearMaterialItem>> MAP = new HashMap<>();

    public GearMaterialItem LEATHER_0 = of(TYPE.LEATHER, 0, LevelRanges.STARTER);
    public GearMaterialItem LEATHER_1 = of(TYPE.LEATHER, 1, LevelRanges.LOW);
    public GearMaterialItem LEATHER_2 = of(TYPE.LEATHER, 2, LevelRanges.MIDDLE);
    public GearMaterialItem LEATHER_3 = of(TYPE.LEATHER, 3, LevelRanges.HIGH);
    public GearMaterialItem LEATHER_4 = of(TYPE.LEATHER, 4, LevelRanges.ENDGAME);

    public GearMaterialItem CLOTH_0 = of(TYPE.CLOTH, 0, LevelRanges.STARTER);
    public GearMaterialItem CLOTH_1 = of(TYPE.CLOTH, 1, LevelRanges.LOW);
    public GearMaterialItem CLOTH_2 = of(TYPE.CLOTH, 2, LevelRanges.MIDDLE);
    public GearMaterialItem CLOTH_3 = of(TYPE.CLOTH, 3, LevelRanges.HIGH);
    public GearMaterialItem CLOTH_4 = of(TYPE.CLOTH, 4, LevelRanges.ENDGAME);

    public GearMaterialItem ORE_0 = of(TYPE.ORE, 0, LevelRanges.STARTER);
    public GearMaterialItem ORE_1 = of(TYPE.ORE, 1, LevelRanges.LOW);
    public GearMaterialItem ORE_2 = of(TYPE.ORE, 2, LevelRanges.MIDDLE);
    public GearMaterialItem ORE_3 = of(TYPE.ORE, 3, LevelRanges.HIGH);
    public GearMaterialItem ORE_4 = of(TYPE.ORE, 4, LevelRanges.ENDGAME);

    public CraftEssenceItem ARCANA = item(new CraftEssenceItem("arcana", () -> Items.PURPLE_DYE, "Essence of Arcana"));
    public CraftEssenceItem MANA = item(new CraftEssenceItem("mana", () -> ModRegistry.MISC_ITEMS.MANA_PLANT, "Essence of Mana"));
    public CraftEssenceItem LIFE = item(new CraftEssenceItem("life", () -> ModRegistry.MISC_ITEMS.LIFE_PLANT, "Essence of Life"));
    public CraftEssenceItem ELEMENTAL = item(new CraftEssenceItem("elemental", () -> Items.WHITE_DYE, "Essence of Elements"));
    public CraftEssenceItem WATER = item(new CraftEssenceItem("water", () -> Items.CYAN_DYE, "Essence of Water"));
    public CraftEssenceItem NATURE = item(new CraftEssenceItem("nature", () -> Items.GREEN_DYE, "Essence of Nature"));
    public CraftEssenceItem FIRE = item(new CraftEssenceItem("fire", () -> Items.ORANGE_DYE, "Essence of Fire"));
    public CraftEssenceItem THUNDER = item(new CraftEssenceItem("thunder", () -> Items.YELLOW_DYE, "Essence of Thunder"));
    public CraftEssenceItem PHYSICAL = item(new CraftEssenceItem("physical", () -> Items.BLACK_DYE, "Essence of Thunder"));

    public GearMaterialItem of(TYPE type, int tier, LevelRange range) {

        String suffix = "";

        if (tier == 0) {
            suffix = "Of Potential";
        }
        if (tier == 1) {
            suffix = "Of Maturity";
        }
        if (tier == 2) {
            suffix = "Of Achievement";
        }
        if (tier == 3) {
            suffix = "Of Mastery";
        }
        if (tier == 4) {
            suffix = "Of Transcendence";
        }
        String typeid = type.name()
            .toLowerCase(Locale.ROOT);

        String first = typeid.substring(0, 1);
        String afterfirst = typeid.substring(1);

        if (type == TYPE.ORE) {
            afterfirst = "Ingot";
        }

        String name = first.toUpperCase() + afterfirst + " " + suffix;

        String lowercase = type.name()
            .toLowerCase(Locale.ROOT);

        GearMaterialItem item = new GearMaterialItem(tier, type, lowercase + "/" + lowercase + tier, name, range);

        if (!MAP.containsKey(type)) {
            MAP.put(type, new HashMap<>());
        }
        MAP.get(type)
            .put(tier, item);

        return item(item);

    }

}
