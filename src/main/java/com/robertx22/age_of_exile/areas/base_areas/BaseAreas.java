package com.robertx22.age_of_exile.areas.base_areas;

import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.world.biome.Biome.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BaseAreas {

    public static BaseAreas INSTANCE;

    public List<BaseArea> AREAS = new ArrayList<>();
    public HashMap<String, BaseArea> MAP = new HashMap<>();

    public BaseArea SEA = of("sea", "Sea", Category.OCEAN);
    public BaseArea DESERT = of("desert", "Desert", Category.DESERT);
    public BaseArea FOREST = of("forest", "Forest", Category.FOREST);
    public BaseArea RIVER = of("river", "River", Category.RIVER);
    public BaseArea ICELANDS = of("icelands", "Icelands", Category.ICY);
    public BaseArea BEACH = of("beach", "Beach", Category.BEACH);
    public BaseArea SAVANNAH = of("savannah", "Savannah", Category.SAVANNA);
    public BaseArea JUNGLE = of("jungle", "Jungle", Category.JUNGLE);
    public BaseArea FLATLANDS = of("flatlands", "Flatlands", Category.PLAINS);
    public BaseArea SWAMP = of("swamp", "Swamp", Category.SWAMP);
    public BaseArea MOUNTAINS = of("mountain", "Mountains", Category.EXTREME_HILLS);
    public BaseArea TAIGA = of("taiga", "Taiga", Category.TAIGA);

    public BaseArea HELLSCAPE = of("hellscape", "Hellscape", Category.NETHER);
    public BaseArea CHARRED_DOMAIN = of("charred_domain", "Charred Domain", Category.NETHER);

    public BaseArea SHROOMLANDS = of("shroomland", "Shroomlands", Category.MUSHROOM);

    public BaseArea DEFAULT = of("land", "Land", null);

    public BaseArea getRandomAreaFor(Category category) {

        if (AREAS.stream()
            .anyMatch(x -> x.category == category)) {
            return RandomUtils.randomFromList(AREAS.stream()
                .filter(x -> x.category == category)
                .collect(Collectors.toList()));
        }

        return DEFAULT;

    }

    BaseArea of(String id, String locname, Category category) {
        BaseArea area = new BaseArea(id, locname, category);
        AREAS.add(area);
        MAP.put(id, area);
        return area;

    }
}
