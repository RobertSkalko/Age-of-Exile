package com.robertx22.age_of_exile.database.data.spells.modifiers;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SpellModifier implements ISerializedRegistryEntry<SpellModifier>, IAutoGson<SpellModifier>, ITooltipList {
    public static SpellModifier SERIALIZER = new SpellModifier();
    public String identifier;

    public List<SpellModStatData> mods = new ArrayList<>();

    public String for_spell;

    public static SpellModifier addToSeriazables(String spell, float val, SpellModEnum spellStat) {
        SpellModifier mod = new SpellModifier();
        mod.identifier = getIdFor(spellStat, spell);
        mod.for_spell = spell;
        mod.mods.add(SpellModStatData.create(val, spellStat));
        mod.addToSerializables();
        return mod;
    }

    public static String getIdFor(SpellModEnum spellStat, String spell) {
        return spell + "_" + spellStat.id;
    }

    public Perk createPerkForSerialization() {
        Perk perk = new Perk();
        perk.identifier = identifier;
        perk.type = Perk.PerkType.SPELL_MOD;
        perk.spell_mods.add(this.GUID());
        perk.icon = this.mods.get(0).spell_stat.getIconLoc()
            .toString();
        perk.addToSerializables();
        return perk;
    }

    public boolean affectsSpell(Spell spell) {
        return for_spell.equals(spell.GUID());
    }

    public transient String locName;
    public transient String iconForPerk;

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SPELL_MODIFIER;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public Class<SpellModifier> getClassForSerialization() {
        return SpellModifier.class;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        List<Text> list = new ArrayList<>();
        this.mods.forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }
}


