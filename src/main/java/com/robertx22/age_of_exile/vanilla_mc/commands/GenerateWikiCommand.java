package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellDesc;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DirUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static net.minecraft.server.command.CommandManager.literal;

public class GenerateWikiCommand {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("generate_wiki_files").executes(x -> {

                    String content = "";

                    for (PlayStyle style : PlayStyle.values()) {
                        content += style.attribute.name() + " Spells:\n";

                        content += createSpellList(Database.Spells()
                            .getFiltered(s -> s.config.style == style && s.weight > 0));

                        content += "\n\n";
                    }

                    generateWikiFile(content, "spells");

                    return 0;
                }))
        );
    }

    public static String createSpellList(List<Spell> spells) {
        String TOREPLACE = "SPELL_ID";

        String spelliconpath = "![](" + "https://raw.githubusercontent.com/RobertSkalko/Age-of-Exile/master/src/main/resources/assets/mmorpg/textures/gui/spells/icons/" + TOREPLACE + ".png" + ")";

        String content = "| Icon | Name | Description |";

        content += "\n| ------------- | ------------- | ------------- |";

        for (Spell spell : spells) {

            String icon = spelliconpath.replace(TOREPLACE, spell.GUID());

            String desc = "";

            if (spell.manual_tip) {
                desc = StringUtils.join(SpellDesc.getTooltip(spell, 1), " ");
                desc = Formatting.strip(desc);
            }

            content += '\n';
            content += "| " + icon + " | " + CLOC.translate(spell.locName()) + " | " + desc + " | ";

        }

        return content;

    }

    public static void generateWikiFile(String content, String filename) {

        String path = DirUtils.genWikiFolderDir() + filename + ".txt";

        try {
            Files.createDirectories(Paths.get(DirUtils.genWikiFolderDir()));

            Path file;
            if (!Files.exists(Paths.get(path))) {
                file = Files.createFile(Paths.get(path));
            } else {
                file = Paths.get(path);
            }

            Files.write(file, content.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
