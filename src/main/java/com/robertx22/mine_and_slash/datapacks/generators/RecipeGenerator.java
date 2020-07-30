package com.robertx22.mine_and_slash.datapacks.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class RecipeGenerator {

    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting()
        .create();
    protected DataCache cache;

    public RecipeGenerator() {

        try {
            cache = new DataCache(FabricLoader.getInstance()
                .getGameDir(), "datagencache");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Path getBasePath() {
        return FabricLoader.getInstance()
            .getGameDir();
    }

    protected Path movePath(Path target) {
        String movedpath = target.toString();
        movedpath = movedpath.replace("run", "src/generated/resources");
        return Paths.get(movedpath);
    }

    private Path resolve(Path path, String id) {

        return path.resolve(
            "data/" + Ref.MODID + "/recipes/" + id
                + ".json");
    }

    public void run() {
        generateAll(cache);
    }

    protected void generateAll(DataCache cache) {

        Path path = getBasePath();

        generate(x -> {

            Path target = movePath(resolve(path, x.getRecipeId()
                .getPath()));

            try {
                DataProvider.writeToPath(GSON, cache, x.toJson(), target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private void generate(Consumer<RecipeJsonProvider> consumer) {
        for (Item item : Registry.ITEM) {
            if (item instanceof IShapedRecipe) {
                IShapedRecipe ir = (IShapedRecipe) item;
                ir.getRecipe()
                    .offerTo(consumer);

            }
        }
    }

}
