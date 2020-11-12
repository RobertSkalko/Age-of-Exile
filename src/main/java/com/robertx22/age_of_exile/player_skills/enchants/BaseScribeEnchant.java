package com.robertx22.age_of_exile.player_skills.enchants;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.registry.Registry;

public abstract class BaseScribeEnchant extends Enchantment implements IAutoLocName {

    protected BaseScribeEnchant(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    // make it not available anywhere else besides the inscribing skill!
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    // make it not available anywhere else besides the inscribing skill!
    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    public int getMaxLevel() {
        return 5;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Enchants;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ENCHANTMENT.getId(this)
            .toString();
    }

    public abstract PlayerSkillEnum getSkill();

}
