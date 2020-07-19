package com.robertx22.mine_and_slash.saveclasses.spells;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface IAbility extends IGUID, ITooltipList {
    public enum Type {
        SPELL, SYNERGY, EFFECT
    }

    public static List<IAbility> getAll() {

        List<IAbility> list = new ArrayList<>();

        list.addAll(SlashRegistry.Spells()
            .getList());
        list.addAll(SlashRegistry.PotionEffects()
            .getList());

        return list;

    }

    public Elements getElement();

    public static IAbility fromId(String id) {
        IAbility ability = null;

        if (SlashRegistry.Spells()
            .isRegistered(id)) {
            ability = SlashRegistry.Spells()
                .get(id);
        } else if (SlashRegistry.PotionEffects()
            .isRegistered(id)) {
            ability = SlashRegistry.PotionEffects()
                .get(id);
        }

        return ability;
    }

    MutableText getLocName();

    PreCalcSpellConfigs getPreCalcConfig();

    Identifier getIconLoc();

    public Type getAbilityType();

    public BaseSpell getSpell();

    public default void finishTooltip(List<Text> list, SpellCastContext ctx, TooltipInfo info) {
        try {
            TooltipUtils.addEmpty(list);

            list.add(new SText(getElement().format + "Element: " + getElement().name()));

            list.add(new SText(""));

            if (!Screen.hasShiftDown()) {
                list.add(new SText(Formatting.BLUE + "").append(Words.Press_Shift_For_More_Info.locName()));
            } else {
                list.add(new SText(Formatting.LIGHT_PURPLE + "" + Formatting.BOLD).append("Ability Stats:"));
                list.addAll(ctx.getConfigFor(this)
                    .GetTooltipString(info, ctx));
            }

            TooltipUtils.removeDoubleBlankLines(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


