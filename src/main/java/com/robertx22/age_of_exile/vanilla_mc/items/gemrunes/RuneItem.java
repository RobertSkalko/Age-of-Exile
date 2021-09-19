package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class RuneItem extends Item implements IGUID, IAutoModel, IAutoLocName, IWeighted {

    public int weight = 1000;

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent(this.getDescriptionId()).withStyle(TextFormatting.GOLD);
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
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
        super(new Properties().stacksTo(64)
            .tab(CreativeTabs.GemRuneCurrency));
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
        YUN(100, "yun", "Yun", 5),
        NOS(1000, "nos", "Nos", 1),
        MOS(1000, "mos", "Mos", 1),
        ITA(1000, "ita", "Ita", 1),
        CEN(1000, "cen", "Cen", 2),
        FEY(1000, "fey", "Fey", 2),
        DOS(1000, "dos", "Dos", 2),
        ANO(1000, "ano", "Ano", 2),
        TOQ(1000, "toq", "Toq", 2),
        ORU(500, "oru", "Oru", 4),
        WIR(200, "wir", "Wir", 4),
        ENO(1000, "eno", "Eno", 3),
        HAR(1000, "har", "Har", 3),
        XER(1000, "xer", "Xer", 3);

        public String id;
        public String locName;
        public int tier;
        public int weight;

        RuneType(int weight, String id, String locName, int tier) {
            this.id = id;
            this.locName = locName;
            this.tier = tier;
            this.weight = weight;
        }

    }

    public Rune getRune() {
        return ExileDB.Runes()
            .get(type.id);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
