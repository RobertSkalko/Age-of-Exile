package com.robertx22.mine_and_slash.data_generation.affixes;

import com.robertx22.mine_and_slash.data_generation.BaseDataPackManager;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.SlashDataProvider;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.exiled_lib.registry.empty_entries.EmptyAffix;
import net.minecraft.data.DataGenerator;

public class AffixDataPackManager extends BaseDataPackManager<Affix> {
    static String ID = "affixes";

    public AffixDataPackManager() {
        super(SlashRegistryType.AFFIX, ID, x -> EmptyAffix.getInstance()
            .fromJson(x));
    }

    @Override
    public SlashDataProvider getDataPackCreator(DataGenerator gen) {
        return new SlashDataProvider<Affix>(gen, SlashRegistry.Affixes()
            .getSerializable(), ID);
    }
}
