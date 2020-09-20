package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ExileEffect implements ISerializedRegistryEntry<ExileEffect>, IAutoGson<ExileEffect>, IAutoLocName {

    public static ExileEffect SERIALIZER = new ExileEffect();

    public String id;
    public String one_of_a_kind_id = "";
    public EffectType type = EffectType.NEUTRAL;
    public int max_stacks = 1;

    public transient String locName = "";

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
        return Registry.STATUS_EFFECT.getId(getStatusEffect())
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return this.locName;
    }
}
