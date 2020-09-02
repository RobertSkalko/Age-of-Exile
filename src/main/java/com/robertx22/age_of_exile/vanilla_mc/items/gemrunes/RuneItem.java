package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems.NoDuplicateRunes;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems.SocketLvlNotHigherThanItemLvl;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaRegen;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
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
        socket.rune_id = rune.identifier;
        socket.level = rune.getEffectiveLevel();
        socket.percent = RandomUtils.RandomRange(0, 100);
        socket.slot_family = gear.GetBaseGearType()
            .family();

        gear.sockets.sockets.add(socket);

        SlashRegistry.Runewords()
            .getList()
            .forEach(x -> {
                if (x.canItemHave(gear) && x.HasRuneWord(gear)) {
                    gear.sockets.activated_runeword = x.GUID();
                    gear.sockets.runeword_percent = RandomUtils.RandomRange(0, 100);
                }
            });

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_EMPTY_SOCKETS, new NoDuplicateRunes(), new SocketLvlNotHigherThanItemLvl());
    }

    public enum RuneType {
        ENO(1000, "eno", "Eno", 0.5F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(0.1F, 0.2F, Dexterity.INSTANCE));
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
                return Arrays.asList(new StatModifier(3, 5, CriticalHit.getInstance()));

            }
        }),
        HAR(1000, "har", "Har", 0.5F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(0.1F, 0.2F, Strength.INSTANCE));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(
                    new StatModifier(5, 10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
                );
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(5, 10, CriticalDamage.getInstance()));
            }
        }),
        XER(1000, "xer", "Xer", 0.5F, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(0.1F, 0.2F, Intelligence.INSTANCE));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(5, 10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(0.3F, 1, ManaOnHit.getInstance()));

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
    public float getStatMultiForNonLvlScaledStat() {
        return 1;
    }

    @Override
    public BaseRuneGem getBaseRuneGem() {
        return getRune();
    }

    public Rune getRune() {
        return SlashRegistry.Runes()
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
