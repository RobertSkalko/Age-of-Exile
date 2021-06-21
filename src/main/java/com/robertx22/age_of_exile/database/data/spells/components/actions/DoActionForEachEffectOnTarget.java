package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DoActionForEachEffectOnTarget extends SpellAction implements ICMainTooltip {

    public DoActionForEachEffectOnTarget() {
        super(Arrays.asList());
    }

    @Override
    public List<MutableText> getLines(AttachedSpell spell, MapHolder holder, EntitySavedSpellData savedData) {
        List<MutableText> list = new ArrayList<>();
        return list;
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        try {
            if (ctx.world.isClient) {
                return;
            }

            String action = data.get(MapField.SPECIFIC_ACTION);

            String tag = data.get(MapField.EFFECT_TAG);

            int amount = 0;

            for (LivingEntity x : targets) {

                for (String k : Load.Unit(x)
                    .getStatusEffectsData().exileMap.keySet()) {
                    ExileEffect eff = ExileDB.ExileEffects()
                        .get(k);
                    if (eff.hasTag(tag)) {
                        amount++;
                    }
                }
            }

            for (int i = 0; i < amount; i++) {
                for (ComponentPart componentPart : ctx.calculatedSpellData.getSpell().attached.entity_components.get(action)) {
                    componentPart.tryActivate(ctx);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public MapHolder create(String action, EffectTags tag) {
        MapHolder c = new MapHolder();
        c.put(MapField.SPECIFIC_ACTION, action);
        c.put(MapField.EFFECT_TAG, tag.name());
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "action_per_effect_with_tag_on_target";
    }

}
