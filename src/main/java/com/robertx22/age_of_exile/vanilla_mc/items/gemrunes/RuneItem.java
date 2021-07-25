package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems.NoDuplicateRunes;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems.SocketLvlNotHigherThanItemLvl;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class RuneItem extends BaseGemRuneItem implements IGUID, IAutoModel, IAutoLocName, ICurrencyItemEffect {
    @Override
    public StationType forStation() {
        return StationType.SOCKET;
    }

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
        super(new Settings().maxCount(16)
            .group(CreativeTabs.Runes));
        this.type = type;

        this.weight = type.weight;
    }

    @Override
    public String GUID() {
        return "runes/" + type.id;
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack currency) {

        RuneItem ritem = (RuneItem) currency.getItem();
        Rune rune = ritem.getRune();
        GearItemData gear = Gear.Load(stack);

        SocketData socket = new SocketData();
        socket.rune = rune.identifier;
        socket.lvl = gear.lvl;
        socket.perc = RandomUtils.RandomRange(0, 100);

        gear.sockets.sockets.add(socket);

        ExileDB.Runewords()
            .getList()
            .forEach(x -> {
                if (x.HasRuneWord(gear)) {
                    gear.sockets.word = x.GUID();
                    gear.sockets.word_perc = RandomUtils.RandomRange(0, 100);
                }
            });

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_EMPTY_SOCKETS, new NoDuplicateRunes(), new SocketLvlNotHigherThanItemLvl());
    }

    public static StatModifier dmg(Elements ele) {
        return new
            StatModifier(0.5F, 1F, 1F, 2F, new AttackDamage(ele), ModType.FLAT);
    }

    public enum RuneType {
        YUN(100, "yun", "Yun", 0.75F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(2, 5, new ElementalResist(Elements.Elemental), ModType.FLAT));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(2, 5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Elemental), ModType.FLAT));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(1, 20, Stats.CRIT_DAMAGE.get(), ModType.FLAT));
            }
        }),
        NOS(1000, "nos", "Nos", 0.2F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(5, 20, Mana.getInstance(), ModType.LOCAL_INCREASE));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(1, 3, new AttackDamage(Elements.Physical), ModType.FLAT));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(5, 12, Stats.CRIT_DAMAGE.get()));
            }
        }),

        MOS(1000, "mos", "Mos", 0.2F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(5, 15, DodgeRating.getInstance(), ModType.LOCAL_INCREASE));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(5, 10, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(2, 6, Stats.CRIT_CHANCE.get()));
            }
        }),

        ITA(1000, "ita", "Ita", 0.2F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(5, 15, Armor.getInstance(), ModType.LOCAL_INCREASE));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(3, 8, SpellDamage.getInstance()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(0.5F, 2, Stats.LIFESTEAL.get()));
            }
        }),

        CEN(1000, "cen", "Cen", 0.2F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 4, new MaxElementalResist(Elements.Light)));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(3, 8, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light)));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(dmg(Elements.Light));
            }
        }),
        FEY(1000, "fey", "Fey", 0.2F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 4, new MaxElementalResist(Elements.Dark)));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(3, 8, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(dmg(Elements.Dark));
            }
        }),

        DOS(1000, "dos", "Dos", 0.2F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 4, new MaxElementalResist(Elements.Nature)));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(3, 8, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(dmg(Elements.Nature));
            }
        }),

        ANO(1000, "ano", "Ano", 0.2F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 4, new MaxElementalResist(Elements.Fire)));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(3, 8, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(dmg(Elements.Fire));
            }
        }),

        TOQ(1000, "toq", "Toq", 0.2F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 4, new MaxElementalResist(Elements.Water)));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(3, 8, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(dmg(Elements.Water));
            }
        }),

        ORU(500, "oru", "Oru", 0.6F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(3, 10, TreasureQuantity.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(5, 10, Mana.getInstance()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(2, 6, Stats.CRIT_CHANCE.get()));
            }
        }),

        WIR(200, "wir", "Wir", 0.7F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(6, 12, TreasureQuality.getInstance()));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(0.05F, 0.1F, AllAttributes.getInstance()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(4, 10, Stats.CRIT_DAMAGE.get()));
            }
        }),

        ENO(1000, "eno", "Eno", 0.5F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(0.1F, 0.2F, DatapackStats.DEX));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(
                    new StatModifier(2, 5, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(2, 5, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
                );
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(3, 5, Stats.CRIT_CHANCE.get()));
            }
        }),

        HAR(1000, "har", "Har", 0.5F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(0.1F, 0.2F, DatapackStats.STR));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(
                    new StatModifier(5, 10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
                );
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(5, 10, Stats.CRIT_DAMAGE.get()));
            }
        }),

        XER(1000, "xer", "Xer", 0.5F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(0.1F, 0.2F, DatapackStats.INT));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(5, 10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(0.3F, 1, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack))));

            }
        });

        public String id;
        public String locName;
        public float lvl;
        public GemStatPerTypes stats;
        public int weight;

        RuneType(int weight, String id, String locName, float lvl, GemStatPerTypes stats) {
            this.id = id;
            this.locName = locName;
            this.stats = stats;
            this.lvl = lvl;
            this.weight = weight;
        }

    }

    @Override
    public float getStatValueMulti() {
        return 1;
    }

    @Override
    public BaseRuneGem getBaseRuneGem() {
        return getRune();
    }

    public Rune getRune() {
        return ExileDB.Runes()
            .get(type.id);
    }

    @Override
    public List<StatModifier> getStatModsForSerialization(BaseGearType.SlotFamily family) {
        return type.stats.getFor(family);
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
