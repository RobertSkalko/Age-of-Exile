package com.robertx22.age_of_exile.database.data.spells.components.tooltips;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import net.minecraft.text.Text;

import java.util.List;

public interface ICMainTooltip {

    List<Text> getLines(AttachedSpell spell, MapHolder holder);
}
