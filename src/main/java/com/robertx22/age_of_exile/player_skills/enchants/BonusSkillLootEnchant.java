package com.robertx22.age_of_exile.player_skills.enchants;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class BonusSkillLootEnchant extends BaseScribeEnchant {

    PlayerSkillEnum skill;

    public BonusSkillLootEnchant(PlayerSkillEnum skill, Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
        this.skill = skill;
    }

    public static float getBonusLootChanceMulti(LivingEntity en, PlayerSkillEnum skill) {
        if (ModRegistry.ENCHANTS.BONUS_SKILL_LOOT.containsKey(skill)) {
            BonusSkillLootEnchant ench = ModRegistry.ENCHANTS.BONUS_SKILL_LOOT.get(skill);
            int lvl = EnchantmentHelper.getEquipmentLevel(ench, en);
            return 1.2F + (lvl * 0.1F);
        }
        return 1;
    }

    @Override
    public String locNameForLangFile() {
        return "Bonus " + skill.word.locNameForLangFile() + " Skill Drops";
    }

    @Override
    public String GUID() {
        return "bonus_skill_loot_chance";
    }

    @Override
    public PlayerSkillEnum getSkill() {
        return skill;
    }
}
