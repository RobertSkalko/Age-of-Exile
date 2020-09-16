package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CasterHasModifierCondition extends EffectCondition implements ICMainTooltip {

    public CasterHasModifierCondition() {
        super(Arrays.asList(MapField.SPELL_MODIFIER));
    }

    @Override
    public List<MutableText> getLines(AttachedSpell spell, MapHolder data) {
        List<MutableText> list = new ArrayList<>();
        MutableText text = new LiteralText("");

        SpellModifier mod = SlashRegistry.SpellModifiers()
            .get(data.get(MapField.SPELL_MODIFIER));

        text.append("Spell Modifier: ")
            .append(mod.locName())
            .append(" ");

        text.formatted(Formatting.DARK_PURPLE);

        list.add(text);
        return list;
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        SpellModifier mod = SlashRegistry.SpellModifiers()
            .get(data.get(MapField.SPELL_MODIFIER));

        return Load.perks(ctx.caster)
            .hasSpellModifier(mod);
    }

    public MapHolder create(SpellModifier mod) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.SPELL_MODIFIER, mod.GUID());
        return d;
    }

    @Override
    public String GUID() {
        return "caster_has_spell_mod";
    }

}