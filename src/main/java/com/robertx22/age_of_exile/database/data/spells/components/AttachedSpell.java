package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttachedSpell {

    public List<ComponentPart> on_cast = new ArrayList<>();

    public HashMap<String, List<ComponentPart>> entity_components = new HashMap<>();

    public void onCast(SpellCtx ctx) {
        on_cast.forEach(x -> x.tryActivate(ctx));
    }

    public List<ComponentPart> getDataForEntity(String en) {
        return entity_components.get(en);
    }

    public void tryActivate(String entity_name, SpellCtx ctx) {
        try {
            if (entity_components.containsKey(entity_name)) {
                for (ComponentPart entry : entity_components.get(entity_name)) {
                    entry.tryActivate(ctx);
                }
            } else {
                //System.out.println("Spell doesn't have data for spell entity called: " + entity_name + ". Spell id: " + ctx.calculatedSpellData.getSpell()
                //   .GUID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ComponentPart> getAllComponents() {
        List<ComponentPart> list = new ArrayList<>();
        list.addAll(this.on_cast);
        this.entity_components.entrySet()
            .forEach(x -> list.addAll(x.getValue()));
        return list;
    }

}
