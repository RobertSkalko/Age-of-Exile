package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.BaseRuneGem;
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
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.GemItems;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;

public class GemItem extends BaseGemRuneItem implements IGUID, IAutoModel, IAutoLocName, IShapelessRecipe, ICurrencyItemEffect {

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent(this.getDescriptionId()).withStyle(gemType.format);
    }

    @Override
    public StationType forStation() {
        return StationType.MODIFY;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
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

    static float MIN_WEP_DMG = 2;
    static float MAX_WEP_DMG = 15;
    static float MIN_RES = 4;
    static float MAX_RES = 12;
    static float MIN_ELE_DMG = 4;
    static float MAX_ELE_DMG = 8;

    @Override
    public ShapelessRecipeBuilder getRecipe() {

        if (this.gemRank.lower() == null) {
            return null;
        }
        return ShapelessRecipeBuilder.shapeless(GemItems.MAP.get(gemType)
                .get(gemRank)
                .get())
            .requires(GemItems.MAP.get(gemType)
                .get(gemRank.lower())
                .get(), 3)
            .unlockedBy("player_level", trigger());
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack currency) {

        GemItem gitem = (GemItem) currency.getItem();
        Gem gem = gitem.getGem();
        GearItemData gear = Gear.Load(stack);

        SocketData socket = new SocketData();
        socket.gem = gem.identifier;
        socket.lvl = gear.lvl;
        socket.perc = RandomUtils.RandomRange(0, 100);

        gear.sockets.sockets.add(socket);

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_EMPTY_SOCKETS, new NoDuplicateSocketsReq(), new SocketLvlNotHigherThanItemLvl());
    }

    @Override
    public float getStatValueMulti() {
        return gemRank.valmulti;
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
            return Arrays.asList(new StatModifier(MIN_ELE_DMG, MAX_ELE_DMG, Stats.ELEMENTAL_SPELL_DAMAGE.get(ele)));
        }

        @Override
        public List<StatModifier> onWeapons() {
            return Arrays.asList(new StatModifier(MIN_WEP_DMG, MAX_WEP_DMG, Stats.ELEMENTAL_DAMAGE.get(ele), ModType.FLAT));
        }
    }

    public enum GemType {

        TOURMALINE("tourmaline", "Tourmaline", TextFormatting.LIGHT_PURPLE, new GemStatPerTypes() {
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
                return Arrays.asList(new StatModifier(2, 5, Stats.LIFESTEAL.get()));
            }
        }),
        AZURITE("azurite", "Azurite", TextFormatting.AQUA, new GemStatPerTypes() {
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
                return Arrays.asList(new StatModifier(1, 3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack))));
            }
        }),

        GARNET("garnet", "Garnet", TextFormatting.GREEN, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 3, DodgeRating.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(3, 10, Stats.CRIT_DAMAGE.get()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(2, 6, Stats.CRIT_CHANCE.get()));
            }
        }),
        OPAL("opal", "Opal", TextFormatting.GOLD, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 3, Armor.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(2, 6, Stats.CRIT_CHANCE.get()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(3, 10, Stats.CRIT_DAMAGE.get()));
            }
        }),
        //TOPAZ("topaz", "Topaz", Formatting.YELLOW, new EleGem(Elements.Air)),
        // AMETHYST("amethyst", "Amethyst", Formatting.DARK_PURPLE, new EleGem(Elements.Dark)),
        RUBY("ruby", "Ruby", TextFormatting.RED, new EleGem(Elements.Fire)),
        EMERALD("emerald", "Emerald", TextFormatting.GREEN, new EleGem(Elements.Earth)),
        SAPPHIRE("sapphire", "Sapphire", TextFormatting.BLUE, new EleGem(Elements.Water));

        public String locName;
        public String id;
        public TextFormatting format;
        public GemStatPerTypes stats;

        GemType(String id, String locName, TextFormatting format, GemStatPerTypes stats) {
            this.locName = locName;
            this.id = id;
            this.format = format;
            this.stats = stats;
        }
    }

    public enum GemRank {
        CHIPPED("Chipped", 0, 1, 1),
        FLAWED("Flawed", 1, 2, 1.2F),
        REGULAR("Regular", 2, 3, 1.4F),
        FLAWLESS("Flawless", 3, 4, 1.6F),
        PERFECT("Perfect", 4, 5, 1.8F);

        public String locName;
        public int num;
        public int tier;
        public float valmulti;

        GemRank(String locName, int num, int tier, float valmulti) {
            this.locName = locName;
            this.num = num;
            this.tier = tier;
            this.valmulti = valmulti;
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
        super(new Properties().tab(CreativeTabs.GemRuneCurrency)
            .stacksTo(16));

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

    public Gem getGem() {
        String id = Registry.ITEM.getKey(this)
            .toString();

        Gem gem = ExileDB.Gems()
            .getList()
            .stream()
            .filter(x -> id.equals(x.item_id))
            .findFirst()
            .get();
        return gem;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        try {

            tooltip.addAll(getBaseTooltip());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
