package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileStatusEffect;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.ELEMENT;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class DamageAction extends SpellAction implements ICTextTooltip {

    public DamageAction() {
        super(Arrays.asList(ELEMENT, VALUE_CALCULATION));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data, CalculatedSpellData spelldata) {
        MutableText text = new LiteralText("");

        ValueCalculation calc = data.get(VALUE_CALCULATION);
        Elements ele = data.getElement();

        text.append("Deal ")
            .append(calc.getShortTooltip(spelldata.level))
            .append(" ")
            .append(ele.dmgName)
            .append(" Damage");

        return text;

    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClient) {
            Elements ele = data.getElement();
            ValueCalculation calc = data.get(VALUE_CALCULATION);

            int value = calc.getCalculatedValue(ctx.caster, ctx.calculatedSpellData);

            value *= ctx.calculatedSpellData.config.getMulti(SpellModEnum.DAMAGE);

            for (LivingEntity t : targets) {

                int stacks = 1;
                try {
                    if (data.has(MapField.EXILE_POTION_ID)) {
                        // if damage done by effect, multiple dmg by effect stacks.
                        StatusEffect effect = ModRegistry.POTIONS.getExileEffect(data.get(MapField.EXILE_POTION_ID));
                        if (t.hasStatusEffect(effect)) {
                            stacks = Load.Unit(t)
                                .getStatusEffectsData()
                                .get((ExileStatusEffect) effect).stacks;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                SpellDamageEffect dmg = new SpellDamageEffect(ctx.caster, t, value * stacks, ctx.calculatedSpellData.getSpell());
                if (data.has(MapField.DMG_EFFECT_TYPE)) {
                    dmg.attackType = data.getDmgEffectType();
                }
                dmg.element = ele;
                dmg.Activate();
            }
        }

    }

    public MapHolder create(ValueCalculation calc, Elements ele) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(VALUE_CALCULATION, calc);
        dmg.put(ELEMENT, ele.name());
        return dmg;
    }

    @Override
    public String GUID() {
        return "damage";
    }

}
