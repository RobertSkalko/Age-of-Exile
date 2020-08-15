package com.robertx22.age_of_exile.areas.base_areas;

import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.world.biome.Biome.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BaseArea {

    public String id;
    public String locname;
    public Category category;

    public BaseArea(String id, String locname, Category category) {
        this.id = id;
        this.locname = locname;
        this.category = category;
    }

    public static List<BaseArea> AREAS = new ArrayList<>();
    public static HashMap<String, BaseArea> MAP = new HashMap<>();

    public static BaseArea SEA = of("sea", "Sea", Category.OCEAN);
    public static BaseArea DESERT = of("desert", "Desert", Category.DESERT);
    public static BaseArea FOREST = of("forest", "Forest", Category.FOREST);

    public static BaseArea DEFAULT = of("land", "Land", null);

    public static BaseArea getRandomAreaFor(Category category) {

        if (AREAS.stream()
            .anyMatch(x -> x.category == category)) {
            return RandomUtils.randomFromList(AREAS.stream()
                .filter(x -> x.category == category)
                .collect(Collectors.toList()));
        }

        return DEFAULT;

    }

    static BaseArea of(String id, String locname, Category category) {
        BaseArea area = new BaseArea(id, locname, category);
        AREAS.add(area);
        MAP.put(id, area);
        return area;

    }

}
