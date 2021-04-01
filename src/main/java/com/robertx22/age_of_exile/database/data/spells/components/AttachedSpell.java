package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AttachedSpell {

    public List<ComponentPart> on_cast = new ArrayList<>();

    public HashMap<String, List<ComponentPart>> entity_components = new HashMap<>();

    public void onCast(SpellCtx ctx) {
        on_cast.forEach(x -> x.tryActivate(ctx));
    }

    public List<ComponentPart> getDataForEntity(String en) {
        return entity_components.get(en);
    }

    public List<MutableText> getTooltipForEntity(TooltipInfo info, AttachedSpell spell, String en, CalculatedSpellData spelldata) {
        List<MutableText> list = new ArrayList<>();

        for (ComponentPart part : spell.getDataForEntity(en)) {
            List<MutableText> tip = part.GetTooltipString(info, spell, spelldata)
                .stream()
                .map(x -> new LiteralText(Formatting.RED + " * ").append(x))
                .collect(Collectors.toList());

            list.addAll(tip);
        }

        return list;
    }

    public List<Text> getTooltip(CalculatedSpellData spelldata) {
        TooltipInfo info = new TooltipInfo(ClientOnly.getPlayer());
        List<Text> list = new ArrayList<>();
        on_cast.forEach(x -> list.addAll(x.GetTooltipString(info, this, spelldata)));
        return list;
    }

    public List<Text> getEffectTooltip(CalculatedSpellData spelldata) {
        TooltipInfo info = new TooltipInfo(ClientOnly.getPlayer());
        List<Text> list = new ArrayList<>();
        entity_components.values()
            .forEach(x -> x.forEach(e -> list.addAll(e.GetTooltipString(info, this, spelldata))));
        return list;
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
