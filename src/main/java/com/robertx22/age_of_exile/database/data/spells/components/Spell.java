package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellDesc;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientToCastSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Spell implements IGUID, IAutoGson<Spell>, JsonExileRegistry<Spell>, IAutoLocName, IAutoLocDesc {
    public static Spell SERIALIZER = new Spell();

    public static String DEFAULT_EN_NAME = "default_entity_name";

    public static Gson GSON = new Gson();

    public int weight = 1000;
    public String identifier = "";
    public AttachedSpell attached = new AttachedSpell();
    public SpellConfiguration config = new SpellConfiguration();

    public List<String> disabled_dims = new ArrayList<>();

    public transient String locDesc = "";

    public boolean isAllowedInDimension(World world) {
        if (disabled_dims.isEmpty()) {
            return true;
        }
        return disabled_dims.stream()
            .map(x -> new ResourceLocation(x))
            .noneMatch(x -> x.equals(MapManager.getResourceLocation(world)));

    }

    public AttachedSpell getAttached() {
        return attached;
    }

    public boolean is(SpellTag tag) {
        return config.tags.contains(tag);
    }

    public SpellConfiguration getConfig() {
        return config;
    }

    public void validate() {
        for (ComponentPart x : this.attached.getAllComponents()) {
            x.validate();
        }
    }

    public final ResourceLocation getIconLoc() {
        return getIconLoc(GUID());
    }

    public static final ResourceLocation getIconLoc(String id) {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/spells/icons/" + id + ".png");
    }

    public WeaponTypes getWeapon(LivingEntity en) {
        try {
            if (getConfig().style.getAttackType() != AttackType.spell) {

                ItemStack stack = en.getMainHandItem();

                GearItemData gear = Gear.Load(stack);

                if (gear != null) {
                    return gear.GetBaseGearType().weapon_type;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return WeaponTypes.none;
    }

    public void cast(SpellCastContext ctx, boolean imbue) {

        if (ctx.caster.level.isClientSide) {
            if (!ctx.isCastFromClientPacket) {
                return;
            }
        }

        LivingEntity caster = ctx.caster;

        if (this.config.swing_arm) {
            if (!caster.level.isClientSide) {
                caster.swingTime = -1; // this makes sure hand swings
                caster.swing(Hand.MAIN_HAND, true);
            }
        }

        if (imbue && this.config.cast_type == SpellCastType.IMBUE) {
            ctx.spellsCap.getCastingData()
                .imbueSpell(this, config.imbues);
            SoundUtils.playSound(ctx.caster, SoundRefs.DING_LOW_PITCH);
        } else {
            attached.onCast(SpellCtx.onCast(caster, ctx.calcData));

            // todo make this send to all players and make the packet work
            if (caster instanceof ServerPlayerEntity) {
                ServerPlayerEntity sp = (ServerPlayerEntity) caster;
                Packets.sendToClient(sp, new TellClientToCastSpellPacket(sp, this));
            }

        }
    }

    public final int getCooldownTicks(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.COOLDOWN_TICKS).number;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    public void spendResources(SpellCastContext ctx) {
        getManaCostCtx(ctx).Activate();
    }

    public SpendResourceEvent getManaCostCtx(SpellCastContext ctx) {
        float cost = this.getCalculatedManaCost(ctx);
        SpendResourceEvent event = new SpendResourceEvent(ctx.caster, ResourceType.mana, cost);
        event.calculateEffects();
        return event;
    }

    public final int getCalculatedManaCost(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.MANA_COST).number;
    }

    public final List<ITextComponent> GetTooltipString(TooltipInfo info) {

        SpellCastContext ctx = new SpellCastContext(info.player, this);

        List<ITextComponent> list = new ArrayList<>();

        list.add(locName().withStyle(TextFormatting.RED, TextFormatting.BOLD));

        TooltipUtils.addEmpty(list);

        if (true || Screen.hasShiftDown()) {

            SpellDesc.getTooltip(ctx.caster, this)
                .forEach(x -> list.add(new StringTextComponent(x)));

        }

        TooltipUtils.addEmpty(list);

        list.add(new StringTextComponent(TextFormatting.BLUE + "Mana Cost: " + getCalculatedManaCost(ctx)));

        list.add(new StringTextComponent(TextFormatting.YELLOW + "Cooldown: " + (getCooldownTicks(ctx) / 20) + "s"));

        TooltipUtils.addEmpty(list);

        list.add(getConfig().castingWeapon.predicate.text);

        TooltipUtils.addEmpty(list);

        if (true || Screen.hasShiftDown()) {

            Set<ExileEffect> effect = new HashSet<>();

            try {
                this.getAttached()
                    .getAllComponents()
                    .forEach(x -> {
                        x.acts.forEach(a -> {
                            if (a.has(MapField.EXILE_POTION_ID)) {
                                effect.add(a.getExileEffect());
                            }
                        });
                    });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                effect.forEach(x -> list.addAll(x.GetTooltipString(info, ctx.calcData)));
                list.add(new StringTextComponent(""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (ValueCalculation calc : this.attached.getAllCalculations()) {
            list.addAll(calc.getTooltip(Load.Unit(ctx.caster)));
        }

        list.add(new StringTextComponent(""));

        boolean learned = Load.spells(info.player)
            .hasSpell(this);

        if (learned) {
            list.add(new StringTextComponent("Learned").withStyle(TextFormatting.GREEN));
        } else {

        }

        TooltipUtils.removeDoubleBlankLines(list);

        return list;

    }

    @Override
    public Class<Spell> getClassForSerialization() {
        return Spell.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SPELL;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".spell." + GUID();
    }

    public transient String locName;

    @Override
    public String locNameForLangFile() {
        return locName;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locDescLangFileGUID() {
        return "spell.desc." + GUID();
    }

    @Override
    public String locDescForLangFile() {
        return locDesc;
    }

}
