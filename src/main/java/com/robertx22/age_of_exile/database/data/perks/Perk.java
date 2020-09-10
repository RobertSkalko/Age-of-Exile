package com.robertx22.age_of_exile.database.data.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AdvancementUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Perk implements ISerializedRegistryEntry<Perk>, IAutoGson<Perk>, ITooltipList {
    public static Perk SERIALIZER = new Perk();

    public PerkType type;
    public String identifier;
    public String spell = "";
    public String lock_under_adv = "";
    public List<OptScaleExactStat> stats = new ArrayList<>();
    public List<String> spell_mods = new ArrayList<>();
    public String icon = "";
    public boolean is_entry = false;
    public int lvl_req = 1;

    public List<SpellModifier> getSpellMods() {
        return spell_mods.stream()
            .map(x -> SlashRegistry.SpellModifiers()
                .get(x))
            .collect(Collectors.toList());
    }

    public Identifier getIcon() {
        return new Identifier(icon);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        List<Text> list = new ArrayList<>();
        Spell spell = getSpell();

        try {

            if (spell != null && !spell.GUID()
                .isEmpty()) {
                list.addAll(new SpellCastContext(info.player, 0, getSpell()).calcData.GetTooltipString(info));
            }

            getSpellMods().forEach(x -> list.addAll(x.GetTooltipString(info)));

            stats.forEach(x -> list.addAll(x.GetTooltipString(info)));

            Advancement adv = AdvancementUtils.getAdvancement(info.player.world, new Identifier(lock_under_adv));

            if (adv != null) {
                list.add(new LiteralText("Needs advancement: ").append(adv.toHoverableText()));
            }

            if (lvl_req > 1) {
                list.add(TooltipUtils.level(lvl_req));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public enum Connection {
        LINKED, BLOCKED, POSSIBLE
    }

    public enum PerkType {
        STAT(1, 24, 24), SPECIAL(2, 26, 26), SPELL(3, 24, 26), START(4, 23, 23), SPELL_MOD(1, 24, 24);
        int order;

        public int width;
        public int height;

        PerkType(int order, int width, int height) {
            this.order = order;
            this.width = width;
            this.height = height;
        }

        public int getXOffset() {
            return order + PerkButton.SPACING * (order - 1);
        }

    }

    public boolean isLockedUnderAdvancement() {
        return !lock_under_adv.isEmpty();
    }

    public PerkType getType() {
        return type;
    }

    public Spell getSpell() {
        return SlashRegistry.Spells()
            .get(spell);
    }

    public boolean didPlayerUnlockAdvancement(PlayerEntity player) {
        Identifier id = new Identifier(this.lock_under_adv);
        return AdvancementUtils.didPlayerFinish(player, id);
    }

    @Override
    public Class<Perk> getClassForSerialization() {
        return Perk.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.PERK;
    }

    @Override
    public String GUID() {
        return identifier;
    }
}
