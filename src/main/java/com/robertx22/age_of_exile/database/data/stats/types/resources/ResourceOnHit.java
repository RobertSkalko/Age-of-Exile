package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.ConditionalRestoreResource;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.ArrayList;
import java.util.List;

public class ResourceOnHit extends Stat implements IGenerated<Stat> {

    Info info;

    public ResourceOnHit(Info info) {
        this.info = info;
        this.is_percent = false;

        this.statEffect = new Effect(info);
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        for (ResourceType res : ResourceType.values()) {
            for (AttackType atk : AttackType.values()) {
                list.add(new ResourceOnHit(new Info(res, atk)));
            }
        }

        return list;
    }

    public static class Info {

        ResourceType resource;
        AttackType attackType;

        public Info(ResourceType resource, AttackType attackType) {
            this.resource = resource;
            this.attackType = attackType;
        }
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Restores resource on every hit of the correct type.";
    }

    @Override
    public String locNameForLangFile() {
        return info.resource.locname + " On " + info.attackType.locname + " Hit";
    }

    @Override
    public String GUID() {
        return info.resource.id + "_on_" + info.attackType.id + "_hit";
    }

    private static class Effect extends BaseDamageEffect {

        Info info;

        public Effect(Info info) {
            this.info = info;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            float amount = data.getAverageValue();
            effect.addToRestore(new ConditionalRestoreResource(info.resource, amount));
            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.attackType.matches(info.attackType);
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public int GetPriority() {
            return Priority.Last.priority;
        }
    }

}

