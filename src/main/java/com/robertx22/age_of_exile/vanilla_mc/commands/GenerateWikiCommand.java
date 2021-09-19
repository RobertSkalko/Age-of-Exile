package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.command.CommandSource;

import static net.minecraft.command.Commands.literal;

public class GenerateWikiCommand {

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("generate_wiki_files").requires(e -> e.hasPermission(2))
                    .executes(x -> {

                        if (!MMORPG.RUN_DEV_TOOLS) {
                            return 0;
                        }

                        String content = "";

                        for (PlayStyle style : PlayStyle.values()) {
                            content += style.attribute.name() + " Spells:\n";

                            content += ClientOnlyGenWiki.createSpellList(ExileDB.Spells()
                                .getFiltered(s -> s.config.style == style && s.weight > 0));

                            content += "\n\n";
                        }

                        ClientOnlyGenWiki.generateWikiFile(content, "spells");

                        return 0;
                    }))
        );
    }

}
