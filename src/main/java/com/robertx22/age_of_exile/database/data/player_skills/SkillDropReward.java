package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.database.data.MinMax;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class SkillDropReward {

    public int weight = 1000;
    public String item_id = "";
    public MinMax count = new MinMax(1, 1);

    public SkillDropReward(int weight, Item item, MinMax count) {
        this.weight = weight;
        this.item_id = Registry.ITEM.getId(item)
            .toString();
        this.count = count;
    }

    public SkillDropReward() {
    }
}
