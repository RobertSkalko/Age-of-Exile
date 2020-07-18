package com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions;

import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModsWithItemsSuggestions extends CommandSuggestions {

    static List<String> cached;

    @Override
    public List<String> suggestions() {
        if (cached == null || cached.isEmpty()) {
            cached = new ArrayList<>(ForgeRegistries.ITEMS.getValues()
                .stream()
                .map(x -> {
                    if (x == null || x.getRegistryName() == null) {
                        return "";
                    }
                    return x.getRegistryName()
                        .getNamespace();
                })
                .collect(Collectors.toSet()));
        }
        return cached;
    }

}
