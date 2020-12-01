package com.robertx22.age_of_exile.database.data.skill_gem;

import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;

@Storable
public class SkillGemData {

    @Store
    public String id = "";
    @Store
    public String rar = "";
    @Store
    public int stat_perc = 100;
    @Store
    public int lvl = 100;

    public SkillGem getSkillGem() {
        return Database.SkillGems()
            .get(id);
    }

    public SkillGemRarity getRarity() {
        return Database.SkillGemRarities()
            .get(rar);
    }

    public static SkillGemData fromStack(ItemStack stack) {
        try {
            SkillGemData gem = LoadSave.Load(SkillGemData.class, new SkillGemData(), stack.getTag(), "gem");
            return gem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToStack(ItemStack stack) {
        LoadSave.Save(this, stack.getOrCreateTag(), "gem");
    }

}
