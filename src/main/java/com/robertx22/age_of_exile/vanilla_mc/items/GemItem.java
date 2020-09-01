package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotFamily;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GemItem extends Item implements IGUID, IWeighted, IAutoModel, IAutoLocName, IShapedRecipe {

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(gemType.format);
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return gemRank.locName + " " + gemType.locName;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    static float MIN_WEP_DMG = 0.5F;
    static float MAX_WEP_DMG = 1.5F;
    static float MIN_RES = 4;
    static float MAX_RES = 10;
    static float MIN_ELE_DMG = 4;
    static float MAX_ELE_DMG = 10;

    @Override
    public ShapedRecipeJsonFactory getRecipe() {

        if (this.gemRank.lower() == null) {
            return null;
        }

        return shaped(ModRegistry.GEMS.MAP.get(gemType)
            .get(gemRank))
            .input('g', ModRegistry.GEMS.MAP.get(gemType)
                .get(gemRank.lower()))
            .pattern("ggg")
            .criterion("player_level", trigger());
    }

    public static class EleGem extends GemStatPerTypes {
        public Elements ele;

        public EleGem(Elements ele) {
            this.ele = ele;
        }

        @Override
        public List<StatModifier> onArmor() {
            return Arrays.asList(new StatModifier(MIN_RES, MAX_RES, new ElementalResist(ele)));
        }

        @Override
        public List<StatModifier> onJewelry() {
            return Arrays.asList(new StatModifier(MIN_ELE_DMG, MAX_ELE_DMG, new ElementalSpellDamage(ele)));
        }

        @Override
        public List<StatModifier> onWeapons() {
            return Arrays.asList(new StatModifier(MIN_WEP_DMG, MAX_WEP_DMG, MIN_WEP_DMG * 2, MAX_WEP_DMG * 2, new WeaponDamage(ele), ModType.FLAT));
        }
    }

    public enum GemType {
        TOPAZ("topaz", "Topaz", Formatting.YELLOW, new EleGem(Elements.Thunder)),
        RUBY("ruby", "Ruby", Formatting.RED, new EleGem(Elements.Fire)),
        EMERALD("emerald", "Emerald", Formatting.GREEN, new EleGem(Elements.Nature)),
        SAPPHIRE("sapphire", "Sapphire", Formatting.BLUE, new EleGem(Elements.Nature));

        public String locName;
        public String id;
        public Formatting format;
        public GemStatPerTypes stats;

        GemType(String id, String locName, Formatting format, GemStatPerTypes stats) {
            this.locName = locName;
            this.id = id;
            this.format = format;
            this.stats = stats;
        }
    }

    public enum GemRank {
        CHIPPED("Chipped", 0, 0, 1),
        FLAWED("Flawed", 1, 0.1F, 1.2F),
        REGULAR("Regular", 2, 0.25F, 1.4F),
        FLAWLESS("Flawless", 3, 0.5F, 1.6F),
        PERFECT("Perfect", 4, 0.75F, 1.8F);

        public String locName;
        public int num;
        public float lvlreq;
        public float valMultiIfNonLvlScaledStat;

        GemRank(String locName, int num, float lvlreq, float valMultiIfNonLvlScaledStat) {
            this.locName = locName;
            this.num = num;
            this.lvlreq = lvlreq;
            this.valMultiIfNonLvlScaledStat = valMultiIfNonLvlScaledStat;
        }

        public GemRank lower() {
            if (this == CHIPPED) {
                return null;
            }
            if (this == FLAWED) {
                return CHIPPED;
            }
            if (this == REGULAR) {
                return FLAWED;
            }
            if (this == FLAWLESS) {
                return REGULAR;
            }
            if (this == PERFECT) {
                return FLAWLESS;
            }
            return null;
        }
    }

    public abstract static class GemStatPerTypes {

        public abstract List<StatModifier> onArmor();

        public abstract List<StatModifier> onJewelry();

        public abstract List<StatModifier> onWeapons();

        public final List<StatModifier> getFor(SlotFamily sfor) {
            if (sfor == SlotFamily.Armor) {
                return onArmor();
            }
            if (sfor == SlotFamily.Jewelry) {
                return onJewelry();
            }
            if (sfor == SlotFamily.Weapon) {
                return onWeapons();
            }

            return null;

        }

    }

    public GemItem(GemType type, GemRank gemRank) {
        super(new Settings().group(CreativeTabs.Gems)
            .maxCount(16));

        this.gemType = type;
        this.gemRank = gemRank;

        if (gemRank.num == 0) {
            weight = 20000;
            levelToStartDrop = 0;
        } else if (gemRank.num == 1) {
            weight = 5000;
            levelToStartDrop = 0.2F;
        } else if (gemRank.num == 2) {
            weight = 1000;
            levelToStartDrop = 0.4F;
        } else if (gemRank.num == 3) {
            weight = 250;
            levelToStartDrop = 0.6F;
        } else if (gemRank.num == 4) {
            weight = 50;
            levelToStartDrop = 0.8F;
        } else {
            throw new RuntimeException("Gem rank not accounted for?");
        }
    }

    public GemType gemType;
    int weight;
    public GemRank gemRank;
    public float levelToStartDrop;

    @Override
    public String GUID() {
        return "gems/" + gemType.id + "/" + gemRank.num;
    }

    @Override
    public int Weight() {
        return 0;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public List<StatModifier> getStatsForSerialization(SlotFamily statsfor) {

        List<StatModifier> list = new ArrayList<>();

        float multi = gemRank.valMultiIfNonLvlScaledStat;

        this.gemType.stats.getFor(statsfor)
            .forEach(x -> {
                if (x.GetStat()
                    .getScaling() == StatScaling.SCALING) {
                    list.add(x);
                } else {
                    if (x.GetStat()
                        .UsesSecondValue()) {
                        list.add(new StatModifier(
                            x.firstMin * multi, x.firstMax * multi, x.secondMin * multi, x.secondMax * multi,
                            x.GetStat(), x.getModType()));
                    } else {
                        list.add(new StatModifier(
                            x.firstMin * multi, x.firstMax * multi,
                            x.GetStat(), x.getModType()));
                    }

                }
            });

        return list;

    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {
            String id = Registry.ITEM.getId(this)
                .toString();

            Gem gem = SlashRegistry.Gems()
                .getList()
                .stream()
                .filter(x -> id.equals(x.item_id))
                .findFirst()
                .get();

            int efflvl = gem.getEffectiveLevel();

            tooltip.add(new LiteralText(""));
            List<StatModifier> wep = gem.getFor(SlotFamily.Weapon);
            tooltip.add(new LiteralText("On Weapon:").formatted(Formatting.RED));
            for (StatModifier x : wep) {
                tooltip.addAll(x.getEstimationTooltip(efflvl));
            }

            tooltip.add(new LiteralText(""));
            List<StatModifier> armor = gem.getFor(SlotFamily.Armor);
            tooltip.add(new LiteralText("On Armor:").formatted(Formatting.BLUE));
            for (StatModifier x : armor) {
                tooltip.addAll(x.getEstimationTooltip(efflvl));
            }

            tooltip.add(new LiteralText(""));
            List<StatModifier> jewelry = gem.getFor(SlotFamily.Jewelry);
            tooltip.add(new LiteralText("On Jewelry:").formatted(Formatting.LIGHT_PURPLE));
            for (StatModifier x : jewelry) {
                tooltip.addAll(x.getEstimationTooltip(efflvl));
            }

            tooltip.add(new LiteralText(""));
            tooltip.add(TooltipUtils.level(gem.getReqLevel()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
