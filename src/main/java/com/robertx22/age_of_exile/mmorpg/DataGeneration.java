package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.datapacks.curio_tags.GenerateCurioDataJsons;
import com.robertx22.age_of_exile.datapacks.generators.RecipeGenerator;
import com.robertx22.age_of_exile.datapacks.lang_file.CreateLangFile;
import com.robertx22.age_of_exile.datapacks.loaders.*;
import com.robertx22.age_of_exile.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.datapacks.modpack_helper_lists.ModpackerHelperLists;
import net.minecraft.resource.ReloadableResourceManager;

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
        manager.registerListener(new GearSlotDatapackLoader());
        manager.registerListener(new BaseGearTypeDatapackLoader());
        manager.registerListener(new TierDatapackLoader());
        manager.registerListener(new AffixDataPackLoader());
        manager.registerListener(new SpellDatapackLoader());
        manager.registerListener(new GemDatapackLoader());
        manager.registerListener(new MobAffixDataPackLoader());
        manager.registerListener(new UniqueGearDatapackLoader());
        manager.registerListener(new CompatibleItemDataPackLoader());
        manager.registerListener(new GearRarityLoader());
        manager.registerListener(new SkillGemRarityLoader());
        manager.registerListener(new DimConfigsDatapackLoader());
        manager.registerListener(new SpellModifierDatapackLoader());
        manager.registerListener(new EntityConfigsDatapackLoader());
        manager.registerListener(new RuneDatapackLoader());
        manager.registerListener(new RunewordDatapackLoader());
        manager.registerListener(new PerkDatapackLoader());
        manager.registerListener(new SpellSchoolDatapackLoader());

    }

}
