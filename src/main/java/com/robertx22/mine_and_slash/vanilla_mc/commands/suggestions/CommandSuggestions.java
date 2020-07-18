package com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;

public abstract class CommandSuggestions implements SuggestionProvider<ServerCommandSource> {

    public abstract List<String> suggestions();

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context,
                                                         SuggestionsBuilder builder) throws CommandSyntaxException {

        List<String> list = new ArrayList();

        CommandSource.suggestMatching(this.suggestions(), builder);

        return builder.buildFuture();
    }

}

