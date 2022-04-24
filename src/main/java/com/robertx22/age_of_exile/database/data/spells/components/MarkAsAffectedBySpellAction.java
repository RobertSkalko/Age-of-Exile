package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

public class MarkAsAffectedBySpellAction extends SpellAction {
    public MarkAsAffectedBySpellAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        try {
            for (LivingEntity target : targets) {
                Load.spells(ctx.caster).mobsAffectedBySpell.onSpellHitTarget(ctx.sourceEntity, target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MapHolder create() {
        MapHolder c = new MapHolder();
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "mark_as_affected_by_spell";
    }
}

