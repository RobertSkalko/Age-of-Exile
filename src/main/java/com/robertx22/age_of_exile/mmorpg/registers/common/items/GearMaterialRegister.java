package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.CraftEssenceItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.GearMaterialItem;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Locale;

public class GearMaterialRegister extends BaseItemRegistrator {

    public GearMaterialRegister() {

        for (SkillItemTier tier : SkillItemTier.values()) {
            for (TYPE type : TYPE.values()) {
                String lowercase = type.name()
                    .toLowerCase(Locale.ROOT);
                String id = lowercase + "/" + lowercase + tier.tier;

                if (!MAP.containsKey(type)) {
                    MAP.put(type, new HashMap<>());
                }

                MAP.get(type)
                    .put(tier, item(new GearMaterialItem(tier, type, id)));
            }
        }

    }

    public enum TYPE {
        CLOTH("Cloth"),
        LEATHER("Leather"),
        ORE("Ingot");

        public String name;

        TYPE(String name) {
            this.name = name;
        }
    }

    public HashMap<TYPE, HashMap<SkillItemTier, GearMaterialItem>> MAP = new HashMap<>();

    public CraftEssenceItem ARCANA = item(new CraftEssenceItem("arcana", () -> Items.PURPLE_DYE, "Essence of Arcana"));
    public CraftEssenceItem MANA = item(new CraftEssenceItem("mana", () -> ModRegistry.MISC_ITEMS.MANA_PLANT, "Essence of Mana"));
    public CraftEssenceItem LIFE = item(new CraftEssenceItem("life", () -> ModRegistry.MISC_ITEMS.LIFE_PLANT, "Essence of Life"));
    public CraftEssenceItem ELEMENTAL = item(new CraftEssenceItem("elemental", () -> Items.WHITE_DYE, "Essence of Elements"));
    public CraftEssenceItem WATER = item(new CraftEssenceItem("water", () -> Items.CYAN_DYE, "Essence of Water"));
    public CraftEssenceItem NATURE = item(new CraftEssenceItem("nature", () -> Items.GREEN_DYE, "Essence of Nature"));
    public CraftEssenceItem FIRE = item(new CraftEssenceItem("fire", () -> Items.ORANGE_DYE, "Essence of Fire"));
    public CraftEssenceItem THUNDER = item(new CraftEssenceItem("thunder", () -> Items.YELLOW_DYE, "Essence of Thunder"));
    public CraftEssenceItem PHYSICAL = item(new CraftEssenceItem("physical", () -> Items.BLACK_DYE, "Essence of Physique"));

}
