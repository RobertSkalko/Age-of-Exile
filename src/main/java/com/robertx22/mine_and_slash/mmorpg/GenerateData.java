package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.datapacks.loaders.*;

import java.util.ArrayList;
import java.util.List;

public class GenerateData {

    public static void generateAll() {

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

    }
}
