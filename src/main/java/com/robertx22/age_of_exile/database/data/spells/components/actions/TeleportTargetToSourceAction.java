package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.utils.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TeleportTargetToSourceAction extends SpellAction implements ICMainTooltip {

    public TeleportTargetToSourceAction() {
        super(Arrays.asList(MapField.DISTANCE));
    }

    @Override
    public List<MutableText> getLines(AttachedSpell spell, MapHolder holder, EntitySavedSpellData savedData) {
        List<MutableText> list = new ArrayList<>();
        //  list.add(new LiteralText("Teleports target to self."));
        return list;
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        targets.forEach(x -> {
            EntityUtils.setLoc(x, ctx.sourceEntity.getPos(), x.yaw, x.pitch);
        });

    }

    public MapHolder create() {
        MapHolder c = new MapHolder();
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "tp_target_to_self";
    }

}

