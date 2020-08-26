package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.datapacks.models.ModelHelper;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.CLOC;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class SkillGemItem extends Item implements IAutoModel, IAutoLocName {

    public BaseSpell spell;

    public SkillGemItem(BaseSpell spell) {
        super(new Item.Settings().maxCount(1)
            .group(CreativeTabs.MyModTab)
            .maxDamage(0));
        this.spell = spell;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        new ModelHelper(this, ModelHelper.Type.GENERATED).generate();
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return CLOC.translate(spell.getLocName());
    }

    @Override
    public String GUID() {
        return "";
    }
}
