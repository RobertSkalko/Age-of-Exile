package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.enchants.BonusSkillLootEnchant;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class ModEnchants {

    public HashMap<PlayerSkillEnum, BonusSkillLootEnchant> BONUS_SKILL_LOOT = new HashMap<>();

    public ModEnchants() {

        EquipmentSlot[] slots = new EquipmentSlot[]{EquipmentSlot.MAINHAND};

        for (PlayerSkillEnum skill : PlayerSkillEnum.getAll()) {
            if (skill.enchantmentTarget != null) {
                Identifier id = Ref.id("bonus_" + skill.id + "_loot_chance");
                BONUS_SKILL_LOOT.put(skill, ench(new BonusSkillLootEnchant(skill, Enchantment.Rarity.RARE, skill.enchantmentTarget, slots), id));
            }
        }

    }

    <T extends Enchantment> T ench(T ench, Identifier id) {
        return Registry.register(Registry.ENCHANTMENT, id, ench);
    }
}
