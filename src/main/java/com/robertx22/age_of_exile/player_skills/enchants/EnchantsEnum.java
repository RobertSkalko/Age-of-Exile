package com.robertx22.age_of_exile.player_skills.enchants;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum EnchantsEnum {

    BONUS_SKILL_DROPS("Bonus Skill Drops", "bonus_skill_drops", () -> Items.GOLD_INGOT) {
        @Override
        public List<PlayerSkillEnum> getSkillsUsedFor() {
            return PlayerSkillEnum.getAll()
                .stream()
                .filter(x -> x.enchantmentTarget != null)
                .collect(Collectors.toList());
        }
    };

    public String word;
    public String id;
    public Supplier<Item> craftItem;

    EnchantsEnum(String word, String id, Supplier<Item> craftItem) {
        this.word = word;
        this.craftItem = craftItem;
        this.id = id;
    }

    public abstract List<PlayerSkillEnum> getSkillsUsedFor();

}
