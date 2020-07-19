package com.robertx22.mine_and_slash.event_hooks.data_gen;

import com.robertx22.mine_and_slash.data_generation.DimConfigsDatapackManager;
import com.robertx22.mine_and_slash.data_generation.EntityConfigsDatapackManager;
import com.robertx22.mine_and_slash.data_generation.affixes.AffixDataPackManager;
import com.robertx22.mine_and_slash.data_generation.base_gear_types.BaseGearTypeDatapackManager;
import com.robertx22.mine_and_slash.data_generation.compatible_items.CompatibleItemDataPackManager;
import com.robertx22.mine_and_slash.data_generation.mob_affixes.MobAffixDataPackManager;
import com.robertx22.mine_and_slash.data_generation.rarities.GearRarityManager;
import com.robertx22.mine_and_slash.data_generation.rarities.SkillGemRarityManager;
import com.robertx22.mine_and_slash.data_generation.tiers.TierDatapackManager;
import com.robertx22.mine_and_slash.data_generation.unique_gears.UniqueGearDatapackManager;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.SlashRecipeProvider;
import net.minecraft.data.DataGenerator;

public class OnGatherData {

    public void onGatherData(DataGenerator gen) {

        gen.install(new BaseGearTypeDatapackManager().getDataPackCreator(gen));
        gen.install(new MobAffixDataPackManager().getDataPackCreator(gen));
        gen.install(new TierDatapackManager().getDataPackCreator(gen));
        gen.install(new AffixDataPackManager().getDataPackCreator(gen));
        gen.install(new UniqueGearDatapackManager().getDataPackCreator(gen));
        gen.install(new CompatibleItemDataPackManager().getDataPackCreator(gen));
        gen.install(new DimConfigsDatapackManager().getDataPackCreator(gen));
        gen.install(new EntityConfigsDatapackManager().getDataPackCreator(gen));

        gen.install(new SlashRecipeProvider(gen));

        gen.install(new GearRarityManager().getProvider(gen));
        gen.install(new SkillGemRarityManager().getProvider(gen));

    }

    //TODO

    // gen.install(new ItemModelManager(gen, event.getExistingFileHelper()));

}


