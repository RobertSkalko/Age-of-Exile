package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems.NoDuplicateSocketsReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems.SocketLvlNotHigherThanItemLvl;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotFamily;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.*;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class GemItem extends BaseGemRuneItem implements IGUID, IAutoModel, IAutoLocName, IShapelessRecipe, ICurrencyItemEffect {

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(gemType.format);
    }

    @Override
    public StationType forStation() {
        return StationType.SOCKET;
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
    static float MAX_RES = 12;
    static float MIN_ELE_DMG = 4;
    static float MAX_ELE_DMG = 8;

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {

        if (this.gemRank.lower() == null) {
            return null;
        }
        return ShapelessRecipeJsonFactory.create(ModRegistry.GEMS.MAP.get(gemType)
            .get(gemRank))
            .input(ModRegistry.GEMS.MAP.get(gemType)
                .get(gemRank.lower()), 3)
            .criterion("player_level", trigger());
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack currency) {

        GemItem gitem = (GemItem) currency.getItem();
        Gem gem = gitem.getGem();
        GearItemData gear = Gear.Load(stack);

        SocketData socket = new SocketData();
        socket.gem_id = gem.identifier;
        socket.level = gem.getEffectiveLevel();
        socket.percent = RandomUtils.RandomRange(0, 100);
        socket.slot_family = gear.GetBaseGearType()
            .family();

        gear.sockets.sockets.add(socket);

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_EMPTY_SOCKETS, new NoDuplicateSocketsReq(), new SocketLvlNotHigherThanItemLvl());
    }

    @Override
    public float getStatMultiForNonLvlScaledStat() {
        return gemRank.valMultiIfNonLvlScaledStat;
    }

    @Override
    public BaseRuneGem getBaseRuneGem() {
        return getGem();
    }

    @Override
    public List<StatModifier> getStatModsForSerialization(SlotFamily family) {
        return gemType.stats.getFor(family);
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

        TOURMALINE("tourmaline", "Tourmaline", Formatting.LIGHT_PURPLE, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 2, Health.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(0.4F, 1.5F, HealthRegen.getInstance()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(2, 5, Lifesteal.getInstance()));
            }
        }),
        AZURITE("azurite", "Azurite", Formatting.AQUA, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(2, 4, Mana.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(0.5F, 1.5F, ManaRegen.getInstance()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(1, 3, ManaOnHit.getInstance()));
            }
        }),
        AMETHYST("amethyst", "Amethyst", Formatting.DARK_PURPLE, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(2, 4, MagicShield.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(0.5F, 1.5F, MagicShieldRegen.getInstance()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(2, 4, MagicSteal.getInstance()));
            }
        }),
        GARNET("garnet", "Garnet", Formatting.GREEN, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(4, 15, DodgeRating.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(3, 10, CriticalDamage.getInstance()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(2, 6, CriticalHit.getInstance()));
            }
        }),
        OPAL("opal", "Opal", Formatting.GOLD, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(5, 15, Armor.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(2, 6, CriticalHit.getInstance()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(3, 10, CriticalDamage.getInstance()));
            }
        }),
        TOPAZ("topaz", "Topaz", Formatting.YELLOW, new EleGem(Elements.Thunder)),
        RUBY("ruby", "Ruby", Formatting.RED, new EleGem(Elements.Fire)),
        EMERALD("emerald", "Emerald", Formatting.GREEN, new EleGem(Elements.Nature)),
        SAPPHIRE("sapphire", "Sapphire", Formatting.BLUE, new EleGem(Elements.Water));

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
        PERFECT("Perfect", 4, 0.8F, 1.8F);

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
            levelToStartDrop = 0.9F;
        } else {
            throw new RuntimeException("Gem rank not accounted for?");
        }
    }

    public GemType gemType;
    public GemRank gemRank;
    public float levelToStartDrop;

    @Override
    public String GUID() {
        return "gems/" + gemType.id + "/" + gemRank.num;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public Gem getGem() {
        String id = Registry.ITEM.getId(this)
            .toString();

        Gem gem = SlashRegistry.Gems()
            .getList()
            .stream()
            .filter(x -> id.equals(x.item_id))
            .findFirst()
            .get();
        return gem;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {

            tooltip.addAll(getBaseTooltip());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
