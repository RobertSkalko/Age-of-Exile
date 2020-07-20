package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.data.mob_affixes.base.MobAffix;
import com.robertx22.mine_and_slash.database.registrators.MobAffixes;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;

public class MobAffixDataPackLoader extends BaseDataPackLoader<MobAffix> {
    static String ID = "mob_affix";

    public MobAffixDataPackLoader() {
        super(SlashRegistryType.MOB_AFFIX, ID, x -> MobAffixes.EMPTY
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<MobAffix>(SlashRegistry.MobAffixes()
            .getSerializable(), ID);
    }
}
