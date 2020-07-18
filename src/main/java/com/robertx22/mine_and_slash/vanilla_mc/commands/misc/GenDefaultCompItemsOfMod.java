package com.robertx22.mine_and_slash.vanilla_mc.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions.ModsWithItemsSuggestions;
import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.SlashDataProvider;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SerializationUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GenDefaultCompItemsOfMod {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("generate").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("compatible_items").
                        then(argument("mod", StringArgumentType.string())
                            .suggests(new ModsWithItemsSuggestions())
                            .executes(ctx -> run(ctx.getSource(), StringArgumentType.getString(ctx, "mod")))))));
    }

    private static int run(ServerCommandSource source, String mod) {

        try {

            List<CompatibleItem> list = new ArrayList<>();

            ForgeRegistries.ITEMS.forEach(x -> {
                if (x.getRegistryName() != null && mod.equals(x.getRegistryName()
                    .getNamespace()))
                    SlashRegistry.GearTypes()
                        .getList()
                        .forEach(t -> {
                            if (BaseGearType.isGearOfThisType(t, x)) {

                                String id = x.getRegistryName()
                                    .toString();

                                CompatibleItem comp = new CompatibleItem();
                                comp.item_type = t.GUID();
                                comp.item_id = id;
                                comp.guid = t.GUID() + ":" + id;

                                list.add(comp);

                            }
                        });
            });

            list.forEach(x -> output(x, mod));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            source.getPlayer()
                .sendMessage(new SText("Outputted All auto generated default compatible item files to: " + Formatting.GREEN + folder(mod)));
            source.getPlayer()
                .sendMessage(new SText(Formatting.RED + "Do note this is prone to error. Some gear types can't be found automatically, and others might be wrong. But it should work for most things."));

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return 1;
    }

    static String folder(String mod) {
        return SerializationUtils.CONFIG_PATH + "generated/" + mod + "/";
    }

    static void output(CompatibleItem item, String mod) {
        try {
            Path path = Paths.get(folder(mod))
                .resolve(item.getFileName() + ".json");

            String json = SlashDataProvider.GSON.toJson(item.toJson());
            Files.createDirectories(path.getParent());

            String filename = item.item_type + "_" + path.getFileName(); // suffix with itemtype so there can be multiple type variations of same item.

            SerializationUtils.makeFileAndDirAndWrite(path.getParent()
                .toString(), filename
                .toString(), json, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
