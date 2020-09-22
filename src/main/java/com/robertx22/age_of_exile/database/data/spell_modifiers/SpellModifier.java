package com.robertx22.age_of_exile.database.data.spell_modifiers;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class SpellModifier implements ISerializedRegistryEntry<SpellModifier>, IByteBuf<SpellModifier>, IAutoGson<SpellModifier>, ITooltipList, IAutoLocName {
    public static SpellModifier SERIALIZER = new SpellModifier();

    public String identifier;
    public List<SpellModStatData> mods = new ArrayList<>();
    public String for_spell;

    @Override
    public SpellModifier getFromBuf(PacketByteBuf buf) {
        SpellModifier mod = new SpellModifier();
        mod.identifier = buf.readString(100);
        int c = buf.readInt();
        for (int i = 0; i < c; i++) {
            mod.mods.add(SpellModStatData.SERIALIZER.getFromBuf(buf));
        }
        mod.for_spell = buf.readString(100);
        return mod;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeString(identifier, 100);
        buf.writeInt(mods.size());
        mods.forEach(x -> x.toBuf(buf));
        buf.writeString(for_spell, 100);
    }

    public static SpellModifier createSpecial(String id, String spell, String locname) {

        SpellModifier mod = new SpellModifier();
        mod.identifier = id;
        mod.locName = locname;
        mod.for_spell = spell;
        mod.iconForPerk = Spell.getIconLoc(spell)
            .toString();

        return mod;

    }

    public static SpellModifier addToSeriazables(String spell, SpellModEnum spellStat) {
        SpellModifier mod = new SpellModifier();
        mod.identifier = getIdFor(spellStat, spell);
        mod.for_spell = spell;
        mod.mods.add(SpellModStatData.create(spellStat));
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

        if (iconForPerk.isEmpty()) {
            perk.icon = this.mods.get(0).spell_stat.getIconLoc()
                .toString();
        } else {
            perk.icon = iconForPerk;
        }
        perk.addToSerializables();
        return perk;
    }

    public boolean affectsSpell(Spell spell) {
        return for_spell.equals(spell.GUID());
    }

    public transient String locName;
    public transient String iconForPerk = "";

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

        if (I18n.hasTranslation(locNameLangFileGUID())) {
            list.add(locName().formatted(Formatting.DARK_PURPLE));
        }

        list.add(SlashRegistry.Spells()
            .get(for_spell)
            .locName()
            .append(" ")
            .append(Words.Modifier.locName())
            .formatted(Formatting.RED));

        this.mods.forEach(x -> list.addAll(x.GetTooltipString(info)));

        return list;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".spell_mods." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locName;
    }

}


