package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

@Storable
public class ScrollBuffData {

    @Store
    public int lvl = 1;
    @Store
    public String id = "";
    @Store
    public String rar = IRarity.COMMON_ID;

    public ScrollBuff getBuff() {
        return Database.ScrollBuffs()
            .get(id);
    }

    public void saveToStack(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        LoadSave.Save(this, stack.getTag(), "sb");

    }
}
