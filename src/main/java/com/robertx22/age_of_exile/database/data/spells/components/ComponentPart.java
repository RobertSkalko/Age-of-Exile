package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.CasterHasModifierCondition;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

import java.util.*;

public class ComponentPart {

    public List<MapHolder> targets = new ArrayList<>();
    public List<MapHolder> acts = new ArrayList<>();
    public List<MapHolder> ifs = new ArrayList<>();

    List<ComponentPart> per_entity_hit = null;

    public ComponentPart addPerEntityHit(ComponentPart add) {
        if (per_entity_hit == null) {
            per_entity_hit = new ArrayList<>();
        }
        this.per_entity_hit
            .add(add);
        return this;
    }

    public ComponentPart onTick(Double ticks) {
        ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return this;
    }

    public ComponentPart setTarget(MapHolder target) {
        targets.add(target);
        return this;
    }

    public void addActivationRequirement(EntityActivation act) {
        if (act == EntityActivation.ON_EXPIRE) {
            this.addCondition(EffectCondition.ON_ENTITY_EXPIRE.create());
        }
        if (act == EntityActivation.ON_HIT) {
            this.addCondition(EffectCondition.ON_HIT.create());
        }
        if (act == EntityActivation.ON_CAST) {
            this.addCondition(EffectCondition.ON_CAST.create());
        }
    }

    public void validate() {
        for (MapHolder part : ifs) {
            EffectCondition condition = EffectCondition.MAP.get(part.type);
            condition.validate(part);
        }
        for (MapHolder part : targets) {
            BaseTargetSelector selector = BaseTargetSelector.MAP.get(part.type);
            selector.validate(part);
        }
        for (MapHolder part : acts) {
            SpellAction action = SpellAction.MAP.get(part.type);
            action.validate(part);
        }

        if (per_entity_hit != null) {
            per_entity_hit.forEach(x -> x.validate());
        }
    }

    public void tryActivate(SpellCtx ctx) {

        for (MapHolder part : ifs) {
            EffectCondition condition = EffectCondition.MAP.get(part.type);
            if (!condition.canActivate(ctx, part)) {
                return;
            }
        }

        Set<LivingEntity> list = new HashSet<>();

        for (MapHolder part : targets) {
            BaseTargetSelector selector = BaseTargetSelector.MAP.get(part.type);
            list.addAll(selector.get(ctx.caster, ctx.target, ctx.pos, part));
        }

        for (MapHolder part : acts) {
            SpellAction action = SpellAction.MAP.get(part.type);
            action.tryActivate(list, ctx, part);
        }

        if (per_entity_hit != null) {

            for (LivingEntity en : list) {
                SpellCtx chainedCtx = SpellCtx.onEntityHit(ctx, en);

                for (ComponentPart onEn : per_entity_hit) {

                    List<LivingEntity> single = Arrays.asList(en);

                    for (MapHolder part : onEn.ifs) {
                        EffectCondition condition = EffectCondition.MAP.get(part.type);
                        if (!condition.canActivate(chainedCtx, part)) {
                            return;
                        }
                    }
                    for (MapHolder part : onEn.acts) {
                        SpellAction action = SpellAction.MAP.get(part.type);
                        action.tryActivate(single, chainedCtx, part);
                    }

                }

            }

        }
    }

    public ComponentPart addTarget(MapHolder map) {
        this.targets.add(map);
        return this;
    }

    public ComponentPart addActions(MapHolder map) {
        this.acts.add(map);
        return this;
    }

    public ComponentPart requiresSpellMod(SpellModifier mod) {
        return addCondition(EffectCondition.CASTER_HAS_SPELL_MOD.create(mod));
    }

    public ComponentPart addCondition(MapHolder map) {
        this.ifs.add(map);
        return this;
    }

    public List<MutableText> GetTooltipString(TooltipInfo info, AttachedSpell spell) {
        List<MutableText> list = new ArrayList<>();

        MutableText text = new LiteralText("");

        String firstLetter = "*";
        boolean isSpellModifier = false;

        for (MapHolder part : acts) {
            SpellAction handler = SpellAction.MAP.get(part.type);
            if (handler instanceof ICMainTooltip) {
                ICMainTooltip line = (ICMainTooltip) handler;
                list.addAll(line.getLines(spell, part));
            }
        }

        for (MapHolder part : ifs) {
            EffectCondition handler = EffectCondition.MAP.get(part.type);
            if (handler instanceof CasterHasModifierCondition) {
                isSpellModifier = true;
            }
            if (handler instanceof ICMainTooltip) {
                ICMainTooltip line = (ICMainTooltip) handler;
                list.addAll(line.getLines(spell, part));

            }
        }

        boolean hasAction = false;

        for (MapHolder part : ifs) {
            EffectCondition handler = EffectCondition.MAP.get(part.type);

            if (handler instanceof ICTextTooltip) {
                ICTextTooltip ictext = (ICTextTooltip) handler;
                text.append(ictext.getText(info, part));
            }
        }

        for (MapHolder part : acts) {
            SpellAction handler = SpellAction.MAP.get(part.type);

            if (handler instanceof ICTextTooltip) {
                ICTextTooltip ictext = (ICTextTooltip) handler;
                text.append(ictext.getText(info, part));
                hasAction = true;
            }
        }

        for (MapHolder part : targets) {
            BaseTargetSelector handler = BaseTargetSelector.MAP.get(part.type);

            if (handler instanceof ICTextTooltip) {
                ICTextTooltip ictext = (ICTextTooltip) handler;
                text.append(ictext.getText(info, part));
            }
        }

        if (isSpellModifier) {
            text.formatted(Formatting.DARK_PURPLE);
            text = new LiteralText(" - ").formatted(Formatting.DARK_PURPLE)
                .append(text);
        } else {
            text.formatted(Formatting.GREEN);
        }

        if (hasAction) {
            list.add(text);
        }

        return list;
    }

}
