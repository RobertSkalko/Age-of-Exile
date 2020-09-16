package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class ExileEffect implements ISerializedRegistryEntry<ExileEffect>, IAutoGson<ExileEffect> {

    public static ExileEffect SERIALIZER = new ExileEffect();

    public String id;
    public String one_of_a_kind_id = "";

    public List<OptScaleExactStat> stats = new ArrayList<>();

    public AttachedSpell spell;

    public void onTick(LivingEntity caster, LivingEntity effectCarrier, EntitySavedSpellData data) {
        try {
            SpellCtx ctx = SpellCtx.onTick(caster, effectCarrier, data);
            this.spell.onCast(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
