package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.GiveSpellStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;

public class PerkBuilder {
    private final Perk perk = new Perk();

    public static PerkBuilder spellModifier(MarkerStat modStat, Spell spell) {
        PerkBuilder b = new PerkBuilder();
        b.perk.identifier = modStat.id;
        b.perk.type = Perk.PerkType.SPELL_MOD;
        b.perk.stats.add(new OptScaleExactStat(1, modStat, ModType.FLAT));
        b.perk.icon = spell.getIconLoc()
            .toString();
        b.perk.one_of_a_kind = spell.GUID() + "_mod";
        return b;
    }

    public PerkBuilder addStat(OptScaleExactStat stat) {
        perk.stats.add(stat);
        return this;
    }

    public Perk build() {
        this.perk.addToSerializables();
        return perk;
    }

    public static Perk spell(Spell spell) {
        Perk perk = new Perk();

        perk.spell = spell.GUID();
        perk.stats.add(new OptScaleExactStat(1, new GiveSpellStat(spell), ModType.FLAT));
        perk.type = Perk.PerkType.MAJOR;
        perk.identifier = spell.GUID();
        perk.icon = spell.getIconLoc()
            .toString();

        if (spell.getConfig().passive_config.is_passive) {
            perk.one_of_a_kind = "passive_spell";
            perk.lvl_req = 20;
        }

        perk.addToSerializables();
        return perk;
    }

    public static Perk stat(String id, OptScaleExactStat stat) {
        Perk perk = new Perk();
        perk.stats = Arrays.asList(stat);
        perk.type = Perk.PerkType.STAT;
        perk.identifier = id;
        perk.icon = stat.getStat()
            .getIconLocation()
            .toString();
        perk.addToSerializables();
        return perk;
    }

    public static Perk gameChanger(String id, OptScaleExactStat... stats) {
        Perk perk = new Perk();

        perk.stats = new ArrayList<>();

        for (OptScaleExactStat stat : stats) {
            perk.stats.add(stat);
        }
        perk.type = Perk.PerkType.MAJOR;
        perk.identifier = id;
        perk.icon = new Identifier(Ref.MODID, "textures/gui/stat_icons/game_changers/" + id + ".png")
            .toString();
        perk.addToSerializables();
        return perk;
    }

    public static Perk bigStat(String id, OptScaleExactStat stat) {
        Perk perk = stat(id, stat);
        perk.type = Perk.PerkType.SPECIAL;
        return perk;
    }

    public static Perk stat(OptScaleExactStat stat) {
        return stat(stat.stat, stat);
    }

}
