package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillDropData extends EffectData {

    public static String ID = "on_skill_item_drop";

    @Override
    public String GUID() {
        return ID;
    }

    public PlayerSkillEnum skill;
    public List<ItemStack> originalDrops;
    public List<ItemStack> extraDrops = new ArrayList<>();

    public SkillDropData(LivingEntity source, PlayerSkillEnum skill, List<ItemStack> originalDrops) {
        super(source, source);
        this.skill = skill;
        this.originalDrops = originalDrops;
        this.extraDrops = originalDrops;
    }

    @Override
    protected void activate() {

    }
}
