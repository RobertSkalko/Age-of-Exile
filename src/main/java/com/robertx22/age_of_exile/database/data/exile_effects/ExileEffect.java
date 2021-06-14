package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExileEffect implements ISerializedRegistryEntry<ExileEffect>, IAutoGson<ExileEffect>, IAutoLocName {

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
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.EXILE_EFFECT;
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
        return "effect." + Registry.STATUS_EFFECT.getId(getStatusEffect())
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return this.locName;
    }

    public List<Text> GetTooltipString(TooltipInfo info, EntitySavedSpellData data) {
        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Status Effect: ").append(this.locName())
            .formatted(Formatting.YELLOW));
        if (!stats.isEmpty()) {
            list.add(Words.Stats.locName()
                .append(": ")
                .formatted(Formatting.GREEN));
            stats.forEach(x -> list.addAll(x.GetTooltipString(info, data.lvl)));
        }
        if (spell != null) {
            // list.add(new LiteralText("Effect:"));
            list.addAll(spell.getEffectTooltip(data));
        }

        if (max_stacks > 1) {
            list.add(new LiteralText("Maximum Stacks: " + max_stacks));
        }

        List<EffectTags> tags = this.tags.stream()
            .map(x -> EffectTags.valueOf(x))
            .collect(Collectors.toList());

        String string = "Tags: ";

        for (EffectTags x : tags) {
            string += x.name + " ";
        }

        list.add(new LiteralText(Formatting.YELLOW + string));

        return list;

    }
}
