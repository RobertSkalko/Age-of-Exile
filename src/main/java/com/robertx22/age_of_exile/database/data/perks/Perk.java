package com.robertx22.age_of_exile.database.data.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AdvancementUtils;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Perk implements ISerializedRegistryEntry<Perk>, IAutoGson<Perk>, ITooltipList {
    public static Perk SERIALIZER = new Perk();

    public PerkType type;
    public String identifier;
    public String spell = "";
    public String lock_under_adv = "";
    public List<OptScaleExactStat> stats = new ArrayList<>();
    public String icon = "";
    public boolean is_entry = false;

    public Identifier getIcon() {
        return new Identifier(icon);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        List<Text> list = new ArrayList<>();
        BaseSpell spell = getSpell();

        try {
            if (spell != null) {
                // TODO REWORK SPELLS FIRST  list.addAll(getSpell().getto)
            }

            stats.forEach(x -> list.addAll(x.GetTooltipString(info)));

            Advancement adv = AdvancementUtils.getAdvancement(info.player.world, new Identifier(lock_under_adv));

            if (adv != null) {
                list.add(new LiteralText("Needs advancement: ").append(adv.toHoverableText()));
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
        STAT(1, 24, 24), SPECIAL(2, 26, 26), SPELL(3, 24, 26), START(4, 23, 23);
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

    public BaseSpell getSpell() {
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
