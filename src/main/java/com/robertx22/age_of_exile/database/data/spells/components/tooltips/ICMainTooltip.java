package com.robertx22.age_of_exile.database.data.spells.components.tooltips;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import net.minecraft.text.MutableText;

import java.util.List;

public interface ICMainTooltip {

    List<MutableText> getLines(AttachedSpell spell, MapHolder holder, EntitySavedSpellData savedData);
}
