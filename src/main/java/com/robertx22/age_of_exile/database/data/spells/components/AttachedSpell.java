package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttachedSpell {

    public List<ComponentPart> on_cast = new ArrayList<>();

    public HashMap<String, HashMap<EntityActivation, List<ComponentPart>>> entity_components = new HashMap<>();

    public void onCast(SpellCtx ctx) {
        on_cast.forEach(x -> x.tryActivate(ctx));
    }

    public HashMap<EntityActivation, List<ComponentPart>> getDataForEntity(String en) {
        return entity_components.get(en);
    }

    public List<MutableText> getTooltipForEntity(TooltipInfo info, AttachedSpell spell, String en) {
        List<MutableText> list = new ArrayList<>();

        for (Map.Entry<EntityActivation, List<ComponentPart>> entry : getDataForEntity(en)
            .entrySet()) {
            for (ComponentPart part : entry.getValue()) {
                List<MutableText> tip = part.GetTooltipString(info, spell);
                if (entry.getKey().word != null) {
                    tip.forEach(t -> t.append(entry.getKey().word.locName()));
                }
                list.addAll(tip);
            }
        }

        return list;
    }

    public List<Text> getTooltip() {
        TooltipInfo info = new TooltipInfo(ClientOnly.getPlayer());
        List<Text> list = new ArrayList<>();
        on_cast.forEach(x -> list.addAll(x.GetTooltipString(info, this)));
        return list;
    }

    public void onEntityTick(String entity_name, SpellCtx ctx) {
        tryActivate(entity_name, EntityActivation.ON_TICK, ctx);
    }

    public void onEntityImpact(String entity_name, SpellCtx ctx) {
        tryActivate(entity_name, EntityActivation.ON_HIT, ctx);
    }

    public void onEntityExpire(String entity_name, SpellCtx ctx) {
        tryActivate(entity_name, EntityActivation.ON_EXPIRE, ctx);
    }

    private void tryActivate(String entity_name, EntityActivation type, SpellCtx ctx) {
        for (Map.Entry<EntityActivation, List<ComponentPart>> entry : entity_components.get(entity_name)
            .entrySet()) {
            if (entry.getKey() == type) {
                entry.getValue()
                    .forEach(v -> v.tryActivate(ctx));
            }
        }
    }

    public List<ComponentPart> getAllComponents() {
        List<ComponentPart> list = new ArrayList<>();
        entity_components.entrySet()
            .forEach(x -> x.getValue()
                .entrySet()
                .forEach(y -> list.addAll(y.getValue())));
        return list;
    }

}
