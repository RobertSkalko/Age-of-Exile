package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class RuneItem extends Item implements IGUID, IAutoModel, IAutoLocName, IWeighted {

    public int weight = 1000;

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(Formatting.GOLD);
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return type.locName + " Rune";
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    public RuneType type;

    public RuneItem(RuneType type) {
        super(new Settings().maxCount(64)
            .group(CreativeTabs.Runes));
        this.type = type;

        this.weight = type.weight;
    }

    @Override
    public String GUID() {
        return "runes/" + type.id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    public enum RuneType {
        YUN(100, "yun", "Yun", 0.75F),
        NOS(1000, "nos", "Nos", 0.2F),
        MOS(1000, "mos", "Mos", 0.2F),
        ITA(1000, "ita", "Ita", 0.2F),
        CEN(1000, "cen", "Cen", 0.2F),
        FEY(1000, "fey", "Fey", 0.2F),
        DOS(1000, "dos", "Dos", 0.2F),
        ANO(1000, "ano", "Ano", 0.2F),
        TOQ(1000, "toq", "Toq", 0.2F),
        ORU(500, "oru", "Oru", 0.6F),
        WIR(200, "wir", "Wir", 0.7F),
        ENO(1000, "eno", "Eno", 0.5F),
        HAR(1000, "har", "Har", 0.5F),
        XER(1000, "xer", "Xer", 0.5F);

        public String id;
        public String locName;
        public float lvl;
        public int weight;

        RuneType(int weight, String id, String locName, float lvl) {
            this.id = id;
            this.locName = locName;
            this.lvl = lvl;
            this.weight = weight;
        }

    }

    public Rune getRune() {
        return ExileDB.Runes()
            .get(type.id);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
