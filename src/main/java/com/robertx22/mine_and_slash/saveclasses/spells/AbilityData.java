package com.robertx22.mine_and_slash.saveclasses.spells;

import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.SkillGem;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Storable
public class AbilityData {

    @Store
    public SkillGemData skill_gem = new SkillGemData();

    public void addSkillGem(ItemStack gem) {
        skill_gem = SkillGem.Load(gem);
        gem.decrement(1);
    }

    public void removeSkillGem(PlayerEntity player) {
        ItemStack stack = new ItemStack(ModItems.SKILL_GEM.get());

        if (player.inventory.insertStack(stack)) {
            skill_gem = new SkillGemData();
        }
    }

    public AbilityData() {

    }

    public boolean isValid() {
        return SlashRegistry.Spells()
            .isRegistered(this.skill_gem.spell_id);

    }

    public IAbility getAbility() {
        return SlashRegistry.Spells()
            .get(skill_gem.spell_id);

    }

}
