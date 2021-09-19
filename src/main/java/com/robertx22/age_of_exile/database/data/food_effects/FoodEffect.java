package com.robertx22.age_of_exile.database.data.food_effects;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.library_of_exile.registry.serialization.IGson;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FoodEffect implements IGson<FoodEffect>, ITooltipList {

    public List<StatusEffectData> effects_given = new ArrayList<>();

    @Override
    public Type getClassType() {
        return FoodEffect.class;
    }

    public void apply(LivingEntity en) {
        effects_given.forEach(x -> x.apply(en));
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {
        List<ITextComponent> list = new ArrayList<>();
        effects_given.forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }
}
