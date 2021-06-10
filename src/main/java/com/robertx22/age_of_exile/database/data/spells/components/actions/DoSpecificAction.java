package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DoSpecificAction extends SpellAction implements ICMainTooltip {

    public DoSpecificAction() {
        super(Arrays.asList());
    }

    @Override
    public List<MutableText> getLines(AttachedSpell spell, MapHolder holder, EntitySavedSpellData savedData) {
        List<MutableText> list = new ArrayList<>();
        return list;
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        String action = data.get(MapField.SPECIFIC_ACTION);

        for (ComponentPart componentPart : ctx.calculatedSpellData.getSpell().attached.entity_components.get(action)) {
            componentPart.tryActivate(ctx);
        }

    }

    public MapHolder create(String action) {
        MapHolder c = new MapHolder();
        c.put(MapField.SPECIFIC_ACTION, action);
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "specific_action";
    }

}
