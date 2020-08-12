package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.database.data.mob_affixes.base.MobAffix;
import com.robertx22.age_of_exile.database.registrators.MobAffixes;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

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
