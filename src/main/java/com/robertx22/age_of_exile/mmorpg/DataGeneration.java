package com.robertx22.age_of_exile.mmorpg;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.robertx22.age_of_exile.aoe_data.datapacks.curio_tags.GenerateCurioDataJsons;
import com.robertx22.age_of_exile.aoe_data.datapacks.generators.RecipeGenerator;
import com.robertx22.age_of_exile.aoe_data.datapacks.lang_file.CreateLangFile;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.modpack_helper_lists.ModpackerHelperLists;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class DataGeneration {

    public static void generateAll() {

        if (!MMORPG.RUN_DEV_TOOLS) {
            return;
        }

        new RecipeGenerator().run();

        ModpackerHelperLists.generate();

        CreateLangFile.create();
        GenerateCurioDataJsons.generate();
        ItemModelManager.INSTANCE.generateModels();

    }

    public static void registerLoaders(ReloadableResourceManager manager) {
        SlashRegistryType.getAllInRegisterOrder()
            .forEach(x -> {
                if (x.getLoader() != null) {
                    manager.registerListener(x.getLoader());
                }
            });

        manager.registerListener(new JsonDataLoader(new Gson(), "nothing") {
            @Override
            protected void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {

                Watch watch = new Watch();
                SlashRegistry.getAllRegistries()
                    .forEach(x -> x.onAllDatapacksLoaded());

                SlashRegistry.backup();

                watch.print("After datapacks loaded ");

            }
        });

    }

}
