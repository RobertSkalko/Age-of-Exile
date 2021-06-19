package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class SpellEntry extends WikiEntry {

    Spell spell;

    public SpellEntry(Spell spell) {
        this.spell = spell;
    }

    @Override
    public ItemStack createMainStack() {
        SkillGemBlueprint b = new SkillGemBlueprint(LootInfo.ofDummyForClient(1));
        if (Database.SkillGems()
            .isRegistered(spell.GUID())) {
            b.type.set(Database.SkillGems()
                .get(spell.GUID()));

            b.level.set(1);
            return b.createStack();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public List<Text> getExtraInfo() {
        List<Text> list = new ArrayList<>();
        return list;
    }

    @Override
    public String getName() {
        return spell.translate();
    }

    @Override
    public Formatting getFormat() {
        return Formatting.GOLD;
    }
}
