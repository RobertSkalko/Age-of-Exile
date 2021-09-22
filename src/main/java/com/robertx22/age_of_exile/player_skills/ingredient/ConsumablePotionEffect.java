package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IOneOfATypePotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.registry.Registry;

public class ConsumablePotionEffect extends Effect implements IOneOfATypePotion, IAutoLocName {

    public PlayerSkillEnum profession;

    public ConsumablePotionEffect(PlayerSkillEnum profession) {
        super(EffectType.BENEFICIAL, 0);
        this.profession = profession;
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeModifierManager attributes, int amplifier) {
        Load.Unit(entity)
            .forceRecalculateStats();
        super.addAttributeModifiers(entity, attributes, amplifier);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity target, AttributeModifierManager attributes,
                                         int amplifier) {

        try {
            Load.Unit(target)
                .forceRecalculateStats();
            super.removeAttributeModifiers(target, attributes, amplifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getOneOfATypeType() {
        return profession.id;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Potions;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.MOB_EFFECT.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return profession.word.locNameForLangFile();
    }

    @Override
    public String GUID() {
        return "";
    }

}
