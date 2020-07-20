package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.datapacks.curio_tags.GenerateCurioDataJsons;
import com.robertx22.mine_and_slash.datapacks.lang_file.CreateLangFile;
import com.robertx22.mine_and_slash.datapacks.loaders.*;
import com.robertx22.mine_and_slash.datapacks.models.ItemModelManager;
import net.minecraft.resource.ReloadableResourceManager;

import java.util.ArrayList;
import java.util.List;

public class DataGeneration {

    public static void generateAll() {

        if (!MMORPG.RUN_DEV_TOOLS) {
            return;
        }

        List<BaseDataPackLoader> list = new ArrayList<>();

        list.add(new BaseGearTypeDatapackLoader());
        list.add(new MobAffixDataPackLoader());
        list.add(new TierDatapackLoader());
        list.add(new AffixDataPackLoader());
        list.add(new UniqueGearDatapackLoader());
        list.add(new CompatibleItemDataPackLoader());
        list.add(new DimConfigsDatapackLoader());
        list.add(new EntityConfigsDatapackLoader());

        List<BaseRarityDatapackLoader> rars = new ArrayList<>();
        rars.add(new GearRarityLoader());
        rars.add(new SkillGemRarityLoader());

        list.forEach(x -> x.getDataPackGenerator()
            .run());
        rars.forEach(x -> x.getDatapackGenerator()
            .run());

        CreateLangFile.create();
        GenerateCurioDataJsons.generate();
        ItemModelManager.INSTANCE.generateModels();

    }

    public static void registerLoaders(ReloadableResourceManager manager) {
        manager.registerListener(new BaseGearTypeDatapackLoader());
        manager.registerListener(new TierDatapackLoader());
        manager.registerListener(new AffixDataPackLoader());
        manager.registerListener(new MobAffixDataPackLoader());
        manager.registerListener(new UniqueGearDatapackLoader());
        manager.registerListener(new CompatibleItemDataPackLoader());
        manager.registerListener(new GearRarityLoader());
        manager.registerListener(new SkillGemRarityLoader());
        manager.registerListener(new DimConfigsDatapackLoader());
        manager.registerListener(new EntityConfigsDatapackLoader());
    }

}
