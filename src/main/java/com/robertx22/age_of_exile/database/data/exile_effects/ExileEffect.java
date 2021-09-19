package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExileEffect implements JsonExileRegistry<ExileEffect>, IAutoGson<ExileEffect>, IAutoLocName {

    public static ExileEffect SERIALIZER = new ExileEffect();

    public String id;
    public String one_of_a_kind_id = "";
    public EffectType type = EffectType.neutral;
    public int max_stacks = 1;

    public transient String locName = "";

    public List<String> tags = new ArrayList<>();

    public boolean hasTag(EffectTags tag) {
        return tags.contains(tag.name());
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public List<VanillaStatData> mc_stats = new ArrayList<>();

    public List<OptScaleExactStat> stats = new ArrayList<>();

    public AttachedSpell spell;

    public ExileStatusEffect getStatusEffect() {
        return ModRegistry.POTIONS.getExileEffect(id);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.EXILE_EFFECT;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<ExileEffect> getClassForSerialization() {
        return ExileEffect.class;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.StatusEffects;
    }

    @Override
    public String locNameLangFileGUID() {
        return "effect." + Registry.MOB_EFFECT.getKey(getStatusEffect())
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return this.locName;
    }

    public List<ITextComponent> GetTooltipString(TooltipInfo info, EntitySavedSpellData data) {
        List<ITextComponent> list = new ArrayList<>();

        list.add(new StringTextComponent("Status Effect: ").append(this.locName())
            .withStyle(TextFormatting.YELLOW));
        if (!stats.isEmpty()) {
            list.add(Words.Stats.locName()
                .append(": ")
                .withStyle(TextFormatting.GREEN));
            stats.forEach(x -> list.addAll(x.GetTooltipString(info, data.lvl)));
        }
        if (spell != null) {
            // TODO add manual effect tooltip
        }

        if (max_stacks > 1) {
            list.add(new StringTextComponent("Maximum Stacks: " + max_stacks));
        }

        List<EffectTags> tags = this.tags.stream()
            .map(x -> EffectTags.valueOf(x))
            .collect(Collectors.toList());

        String string = "Tags: ";

        for (EffectTags x : tags) {
            string += x.name + " ";
        }

        list.add(new StringTextComponent(TextFormatting.YELLOW + string));

        return list;

    }

    @Override
    public int Weight() {
        return 1000;
    }
}
