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
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems.NoDuplicateSocketsReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
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
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.TotemAnimationPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;

public class GemItem extends BaseGemRuneItem implements IGUID, IAutoModel, IAutoLocName, ICurrencyItemEffect {

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent(this.getDescriptionId()).withStyle(gemType.format);
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

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        user.startUsingItem(hand);
        return ActionResult.success(itemStack);
    }

    @Override
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity en) {

        if (world.isClientSide) {
            return stack;
        }

        if (en instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) en;

            if (!getGem().hasHigherTierGem()) {
                p.displayClientMessage(new StringTextComponent(TextFormatting.RED + "These gems are already maximum rank."), false);
                return stack;
            }
            if (stack.getCount() < 3) {
                p.displayClientMessage(new StringTextComponent(TextFormatting.RED + "You need 3 gems to attempt upgrade."), false);
                return stack;
            }

            Gem gem = getGem();

            if (stack.getCount() > 2) {
                if (getGem().hasHigherTierGem()) {
                    boolean success = RandomUtils.roll(gem.perc_upgrade_chance);

                    stack.shrink(3);

                    Item old = stack.getItem();

                    if (success) {
                        ItemStack newstack = new ItemStack(getGem().getHigherTierGem()
                            .getItem());
                        Packets.sendToClient(p, new TotemAnimationPacket(newstack));
                        PlayerUtils.giveItem(newstack, p);
                        p.displayClientMessage(new StringTextComponent(TextFormatting.GREEN + "").append(old.getName(new ItemStack(old)))
                            .append(" has been upgraded to ")
                            .append(newstack.getDisplayName()), false);

                    } else {
                        SoundUtils.playSound(p, SoundEvents.VILLAGER_NO, 1, 1);

                        p.displayClientMessage(new StringTextComponent(TextFormatting.RED + "").append(old.getName(new ItemStack(old)))
                            .append(" has failed the upgrade and was destroyed."), false);
                    }
                }
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    static float MIN_WEP_DMG = 2;
    static float MAX_WEP_DMG = 15;
    static float MIN_RES = 4;
    static float MAX_RES = 12;
    static float MIN_ELE_DMG = 2;
    static float MAX_ELE_DMG = 10;

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency) {

        GemItem gitem = (GemItem) currency.getItem();
        Gem gem = gitem.getGem();
        GearItemData gear = Gear.Load(stack);

        SocketData socket = new SocketData();
        socket.gem = gem.identifier;
        socket.perc = RandomUtils.RandomRange(0, 100);

        gear.sockets.sockets.add(socket);

        ctx.player.displayClientMessage(new StringTextComponent("Gem Socketed"), false);

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_EMPTY_SOCKETS, new NoDuplicateSocketsReq());
    }

    @Override
    public BaseRuneGem getBaseRuneGem() {
        return getGem();
    }

    @Override
    public float getStatValueMulti() {
        return this.gemRank.statmulti;
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
            return Arrays.asList(new StatModifier(MIN_RES, MAX_RES, new ElementalResist(ele), ModType.PERCENT));
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
                return Arrays.asList(new StatModifier(1, 5, DatapackStats.VIT));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(2, 15, HealthRegen.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(1, 5, Stats.LIFESTEAL.get()));
            }
        }),
        AZURITE("azurite", "Azurite", TextFormatting.AQUA, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 5, DatapackStats.WIS));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(2, 15, ManaRegen.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(1, 3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack))));
            }
        }),

        GARNET("garnet", "Garnet", TextFormatting.GREEN, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 5, DatapackStats.DEX));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(2, 15, EnergyRegen.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(2, 8, Stats.CRIT_CHANCE.get()));
            }
        }),
        OPAL("opal", "Opal", TextFormatting.GOLD, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 5, DatapackStats.STR));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(1, 6, Stats.CRIT_CHANCE.get()));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(3, 12, Stats.CRIT_DAMAGE.get()));
            }
        }),
        TOPAZ("topaz", "Topaz", TextFormatting.YELLOW, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 5, DatapackStats.AGI));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(2, 15, Energy.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(1, 3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.energy, AttackType.all))));
            }
        }),
        AMETHYST("amethyst", "Amethyst", TextFormatting.DARK_PURPLE, new GemStatPerTypes() {
            @Override
            public List<StatModifier> onArmor() {
                return Arrays.asList(new StatModifier(1, 5, DatapackStats.INT));
            }

            @Override
            public List<StatModifier> onJewelry() {
                return Arrays.asList(new StatModifier(1, 6, SpellDamage.getInstance(), ModType.FLAT));
            }

            @Override
            public List<StatModifier> onWeapons() {
                return Arrays.asList(new StatModifier(2, 10, Stats.SPELL_CRIT_DAMAGE.get()));
            }
        }),
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
        CRACKED("Cracked", 0, 0.1F, 100, 100999, 0F),
        CHIPPED("Chipped", 1, 0.2F, 75, 25999, 0.1F),
        FLAWED("Flawed", 2, 0.3F, 50, 5000, 0.2F),
        REGULAR("Regular", 3, 0.4F, 25, 1000, 0.5F),
        GRAND("Grand", 4, 0.6F, 10, 200, 0.75F),
        GLORIOUS("Glorious", 5, 0.8F, 5, 25, 0.9F),
        DIVINE("Divine", 6, 1F, 0, 1, 0.95F);

        public String locName;
        public int tier;
        public float statmulti;
        public int upgradeChance;
        public int weight;
        public float lvlToDrop;

        GemRank(String locName, int tier, float statmulti, int upgradeChance, int weight, float lvlToDrop) {
            this.locName = locName;
            this.weight = weight;
            this.lvlToDrop = lvlToDrop;
            this.tier = tier;
            this.statmulti = statmulti;
            this.upgradeChance = upgradeChance;
        }

    }

    public GemItem(GemType type, GemRank gemRank) {
        super(new Properties().tab(CreativeTabs.GemRuneCurrency)
            .stacksTo(16));

        this.gemType = type;
        this.gemRank = gemRank;

        this.weight = gemRank.weight;
        this.levelToStartDrop = gemRank.lvlToDrop;

    }

    public GemType gemType;
    public GemRank gemRank;
    public float levelToStartDrop;

    @Override
    public String GUID() {
        return "gems/" + gemType.id + "/" + gemRank.tier;
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

            tooltip.add(new StringTextComponent(""));

            tooltip.add(new StringTextComponent("Hold 3 gems to attempt upgrade"));
            tooltip.add(new StringTextComponent("Upgrade chance: " + getGem().perc_upgrade_chance + "%"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
