package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class CommandSuggestions implements SuggestionProvider<CommandSource> {

    public abstract List<String> suggestions();

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSource> context,

                                                         SuggestionsBuilder builder) {
        ISuggestionProvider.suggest(this.suggestions(), builder);
        return builder.buildFuture();
    }
}

