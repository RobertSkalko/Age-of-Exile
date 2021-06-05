package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellDesc;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.NoManaPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Spell implements IGUID, IAutoGson<Spell>, ISerializedRegistryEntry<Spell>, IAutoLocName, IAutoLocDesc {
    public static Spell SERIALIZER = new Spell();

    public static String DEFAULT_EN_NAME = "default_entity_name";
    public static String CASTER_NAME = "caster";

    public static Gson GSON = new Gson();

    public int weight = 1000;
    public String identifier = "";
    public AttachedSpell attached = new AttachedSpell();
    public SpellConfiguration config = new SpellConfiguration();

    public boolean manual_tip = false;
    public List<String> disabled_dims = new ArrayList<>();
    public String effect_tip = "";

    public transient String locDesc = "";
    public transient List<StatModifier> statsForSkillGem = new ArrayList<>();

    public boolean isAllowedInDimension(World world) {
        if (disabled_dims.isEmpty()) {
            return true;
        }
        return disabled_dims.stream()
            .map(x -> new Identifier(x))
            .noneMatch(x -> x.equals(MapManager.getResourceLocation(world)));

    }

    public AttachedSpell getAttached() {
        return attached;
    }

    public AuraSpellData aura_data = null;

    public boolean isAura() {
        return aura_data != null;
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

    public final Identifier getIconLoc() {
        return getIconLoc(GUID());
    }

    public static final Identifier getIconLoc(String id) {
        return new Identifier(Ref.MODID, "textures/gui/spells/icons/" + id + ".png");
    }

    public WeaponTypes getWeapon(LivingEntity en) {
        try {
            if (getConfig().style.getAttackType() != AttackType.spell) {

                ItemStack stack = en.getMainHandStack();

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

    public final void onCastingTick(SpellCastContext ctx) {

        int timesToCast = (int) ctx.spell.getConfig().times_to_cast;

        if (timesToCast > 1) {

            int castTimeTicks = (int) getCastTimeTicks(ctx);

            // if i didnt do this then cast time reduction would reduce amount of spell hits.
            int castEveryXTicks = castTimeTicks / timesToCast;

            if (timesToCast > 1) {
                if (castEveryXTicks < 1) {
                    castEveryXTicks = 1;
                }
            }

            if (ctx.isLastCastTick) {
                this.cast(ctx);
            } else {
                if (ctx.ticksInUse > 0 && ctx.ticksInUse % castEveryXTicks == 0) {
                    this.cast(ctx);
                }
            }

        } else if (timesToCast < 1) {
            System.out.println("Times to cast spell is: " + timesToCast + " . this seems like a bug.");
        }

    }

    public void cast(SpellCastContext ctx) {
        if (config.cast_type == SpellCastType.USE_ITEM) {

            int use = ctx.caster.getItemUseTime();

            if (ctx.caster.getMainHandStack()
                .getItem() instanceof BowItem) {
                if (BowItem.getPullProgress(use) < 1F) {
                    return;
                }
            }
            if (ctx.caster.getActiveHand() != Hand.MAIN_HAND) {
                return; // must charge main hand weapon and have full charge
            }

            ctx.caster.clearActiveItem();
        }

        LivingEntity caster = ctx.caster;

        ctx.castedThisTick = true;

        if (ctx.caster instanceof PlayerEntity) {
            if (ctx.spell.config.tags.contains(SpellTag.technique)) {
                ctx.spellsCap
                    .getCastingData()
                    .onAction((PlayerEntity) ctx.caster, PlayerAction.TECHNIQUE);
            } else {
                ctx.spellsCap
                    .getCastingData()
                    .onAction((PlayerEntity) ctx.caster, PlayerAction.SPELL);
            }
        }

        if (this.config.swing_arm) {
            caster.handSwingTicks = -1; // this makes sure hand swings
            caster.swingHand(Hand.MAIN_HAND);
        }

        if (this.aura_data != null) {
            ctx.spellsCap.triggerAura(this);
        }

        attached.onCast(SpellCtx.onCast(caster, ctx.calcData));
    }

    public final int getCooldownTicks(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.COOLDOWN_TICKS).number;
    }

    public final int getCastTimeTicks(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.CAST_TICKS).number;
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

    public boolean canCast(SpellCastContext ctx) {

        LivingEntity caster = ctx.caster;

        if (caster instanceof PlayerEntity == false) {
            return true;
        }

        if (((PlayerEntity) caster).isCreative()) {
            return true;
        }

        if (this.isAura()) {
            if (!ctx.spellsCap.getCastingData().auras.getOrDefault(GUID(), new SpellCastingData.AuraData()).active) { // if not active
                if (ctx.spellsCap.getManaReservedByAuras() + aura_data.mana_reserved > 1) {
                    return false; // todo make affected by mana reserve reduction
                }
            }
        }

        if (!caster.world.isClient) {

            EntityCap.UnitData data = Load.Unit(caster);

            if (data != null) {

                if (!isAllowedInDimension(caster.world)) {
                    if (caster instanceof PlayerEntity) {
                        ((PlayerEntity) caster).sendMessage(new LiteralText("You feel an entity watching you. [Spell can not be casted in this dimension]"), false);
                    }
                    return false;
                }

                SpendResourceEvent rctx = getManaCostCtx(ctx);

                if (data.getResources()
                    .hasEnough(rctx)) {

                    if (!getConfig().castingWeapon.predicate.predicate.test(caster)) {
                        return false;
                    }

                    GearItemData wep = Gear.Load(ctx.caster.getMainHandStack());

                    if (wep == null) {
                        return false;
                    }

                    if (!wep.canPlayerWear(ctx.data)) {
                        if (ctx.caster instanceof PlayerEntity) {
                            OnScreenMessageUtils.sendMessage((ServerPlayerEntity) ctx.caster, new LiteralText("Weapon requirements not met"), TitleS2CPacket.Action.ACTIONBAR);
                        }
                        return false;
                    }

                    return true;
                } else {
                    if (caster instanceof ServerPlayerEntity) {
                        Packets.sendToClient((PlayerEntity) caster, new NoManaPacket());
                    }
                }
            }
        }
        return false;

    }

    public final int getCalculatedManaCost(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.MANA_COST).number;
    }

    public final List<Text> GetTooltipString(SkillGemData gem, Spell spell, TooltipInfo info) {

        SpellCastContext ctx = new SpellCastContext(gem, info.player, 0, spell);

        List<Text> list = new ArrayList<>();

        TooltipUtils.addEmpty(list);

        if (Screen.hasShiftDown()) {

            if (!this.manual_tip) {
                list.addAll(attached
                    .getTooltip(ctx.calcData));
            } else {
                SpellDesc.getTooltip(this, ctx.calcData.lvl)
                    .forEach(x -> list.add(new LiteralText(x)));
            }
        }

        TooltipUtils.addEmpty(list);

        if (!this.isAura()) {
            list.add(new LiteralText(Formatting.BLUE + "Mana Cost: " + getCalculatedManaCost(ctx)));
            if (config.usesCharges()) {
                list.add(new LiteralText(Formatting.YELLOW + "Max Charges: " + config.charges));
                list.add(new LiteralText(Formatting.YELLOW + "Charge Regen: " + config.charge_regen / 20 + "s"));

            } else {
                list.add(new LiteralText(Formatting.YELLOW + "Cooldown: " + (getCooldownTicks(ctx) / 20) + "s"));
            }
            list.add(new LiteralText(Formatting.GREEN + "Cast time: " + getCastTimeTicks(ctx) + "s"));

        } else {
            list.addAll(this.aura_data.GetTooltipString(this, ctx.skillGemData, new TooltipInfo((PlayerEntity) ctx.caster)));
        }

        TooltipUtils.addEmpty(list);
        if (config.hasActionRequirements()) {

            MutableText txt = new LiteralText("Cast Requirement: ");

            for (int i = 0; i < config.actions_needed.size(); i++) {
                PlayerAction x = config.actions_needed.get(i);
                if (i > 0 && i < config.actions_needed.size()) {
                    txt.append(" + ");
                }
                txt.append(x.word.locName());
            }

            list.add(txt);
        }
        list.add(getConfig().castingWeapon.predicate.text);

        TooltipUtils.addEmpty(list);

        if (this.config.times_to_cast > 1) {
            TooltipUtils.addEmpty(list);
            list.add(new LiteralText("Casted " + config.times_to_cast + " times during channel.").formatted(Formatting.RED));

        }

        if (Screen.hasShiftDown()) {

            Set<ExileEffect> effect = new HashSet<>();

            if (Database.ExileEffects()
                .isRegistered(effect_tip)) {
                effect.add(Database.ExileEffects()
                    .get(effect_tip));
            }

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TooltipUtils.removeDoubleBlankLines(list);

        return list;

    }

    @Override
    public Class<Spell> getClassForSerialization() {
        return Spell.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SPELL;
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
        return Ref.MODID + ".spell." + GUID();
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
