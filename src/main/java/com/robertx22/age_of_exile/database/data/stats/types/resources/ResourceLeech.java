package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResource;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.ArrayList;
import java.util.List;

public class ResourceLeech extends Stat implements IGenerated<Stat> {

    Info info;

    public ResourceLeech(Info info) {
        this.info = info;
        this.statEffect = new Effect(info);
        this.is_percent = true;

        this.isLongStat = true;
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        for (Elements ele : Elements.values()) {
            for (ResourceType res : ResourceType.values()) {
                if (res != ResourceType.BLOOD) {
                    for (AttackType atk : AttackType.values()) {
                        list.add(new ResourceLeech(new Info(ele, res, atk)));
                    }
                }
            }
        }
        return list;
    }

    public static class Info {

        Elements element;
        ResourceType resource;
        AttackType attackType;

        public Info(Elements element, ResourceType resource, AttackType attackType) {
            this.element = element;
            this.resource = resource;
            this.attackType = attackType;

        }
    }

    @Override
    public Elements getElement() {
        return info.element;
    }

    @Override
    public String locDescForLangFile() {
        return "Leeches resource based on % of damage dealt.";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat.resource_leech";
    }

    @Override
    public String locNameForLangFile() {
        String ele = this.getElement() == Elements.All ? "" : getElement().getIconNameFormat() + " ";
        String type = info.attackType == AttackType.ALL ? "" : " " + info.attackType.locname + " ";

        return SpecialStats.format(
            "Leech " + SpecialStats.VAL1 + "% of your " + ele + type + "Damage as " + info.resource.locname
        );
    }

    @Override
    public String GUID() {
        return info.attackType.id + "_" + info.element.guidName + "_dmg_leech_as_" + info.resource.id;
    }

    private static class Effect extends BaseDamageEffect {

        Info info;

        public Effect(Info info) {
            this.info = info;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            float amount = data.getAverageValue() * effect.number / 100F;
            effect.addToRestore(new RestoreResource(RestoreResource.RestoreType.LEECH, info.resource, amount));
            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.attackType.matches(effect.attackType) && info.element.elementsMatch(effect.GetElement());
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public int GetPriority() {
            return Priority.AlmostLast.priority;
        }
    }

}
