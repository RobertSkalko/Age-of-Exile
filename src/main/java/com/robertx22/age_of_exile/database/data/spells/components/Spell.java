package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.NoManaPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Spell implements IGUID, IAutoGson<Spell>, ISerializedRegistryEntry<Spell>, IAutoLocName {
    public static Spell SERIALIZER = new Spell();

    public static String DEFAULT_EN_NAME = "default_entity_name";
    public static String CASTER_NAME = "caster";

    public static Gson GSON = new Gson();

    public String identifier = "";
    public AttachedSpell attached = new AttachedSpell();
    public SpellConfiguration config = new SpellConfiguration();

    public AttachedSpell getAttached() {
        return attached;
    }

    public AuraSpellData aura_data = null;

    public boolean isAura() {
        return aura_data != null;
    }

    public boolean is(SkillGemTag tag) {
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

    public final void onCastingTick(SpellCastContext ctx) {

        int timesToCast = (int) ctx.calcData.getSpell()
            .getConfig().times_to_cast;

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

        EntitySavedSpellData data = EntitySavedSpellData.create(ctx.calcData.level, caster, this, ctx.spellConfig);

        ctx.castedThisTick = true;

        if (this.config.swing_arm) {
            caster.swingHand(Hand.MAIN_HAND);
        }

        if (this.aura_data != null) {
            ctx.spellsCap.triggerAura(this);
        }

        attached.onCast(SpellCtx.onCast(caster, data));
    }

    public final int getCooldownTicks(SpellCastContext ctx) {

        float multi = ctx.spellConfig.getMulti(SpellModEnum.COOLDOWN);

        float ticks = config.cooldown_ticks * multi;

        if (config.getCastTimeTicks() == 0) {
            float castspeed = ctx.spellConfig.getMulti(SpellModEnum.CAST_SPEED);
            ticks *= castspeed;
        }

        if (ticks < 1) {
            return 1; // cant go lower than 1 tick!!!
        }

        return (int) ticks;
    }

    public final int getCastTimeTicks(SpellCastContext ctx) {
        float multi = ctx.spellConfig.getMulti(SpellModEnum.CAST_SPEED);
        return (int) (config.getCastTimeTicks() * multi);
    }

    @Override
    public String GUID() {
        return identifier;
    }

    public void spendResources(SpellCastContext ctx) {
        ctx.data.getResources()
            .modify(getManaCostCtx(ctx));
    }

    public ResourcesData.Context getManaCostCtx(SpellCastContext ctx) {

        float cost = 0;

        cost += this.getCalculatedManaCost(ctx);

        return new ResourcesData.Context(
            ctx.data, ctx.caster, ResourceType.MANA, cost, ResourcesData.Use.SPEND);
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

                ResourcesData.Context rctx = getManaCostCtx(ctx);

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
        float manaCostMulti = ctx.spellConfig.getMulti(SpellModEnum.MANA_COST);

        int lvl = ctx.calcData.level;

        if (config.scale_mana_cost_to_player_lvl) {
            lvl = ctx.data.getLevel();
        }

        float scaling = GameBalanceConfig.get().MANA_COST_SCALING.getMultiFor(lvl);

        return (int) (getConfig().mana_cost * manaCostMulti * scaling);
    }

    public final List<Text> GetTooltipString(SkillGemData gem, TooltipInfo info, CalculatedSpellData data) {

        SpellCastContext ctx = new SpellCastContext(gem, info.player, 0, data);

        List<Text> list = new ArrayList<>();

        TooltipUtils.addEmpty(list);

        if (Screen.hasShiftDown()) {
            list.addAll(attached
                .getTooltip(data));
        }

        TooltipUtils.addEmpty(list);

        if (!this.isAura()) {
            list.add(new LiteralText(Formatting.BLUE + "Mana Cost: " + getCalculatedManaCost(ctx)));
            list.add(new LiteralText(Formatting.YELLOW + "Cooldown: " + (getCooldownTicks(ctx) / 20) + "s"));
            list.add(new LiteralText(Formatting.GREEN + "Cast time: " + getCastTimeTicks(ctx) + "s"));

        } else {
            list.addAll(this.aura_data.GetTooltipString(this, ctx.skillGemData, new TooltipInfo((PlayerEntity) ctx.caster)));
        }

        TooltipUtils.addEmpty(list);
        if (config.isTechnique()) {

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
                effect.forEach(x -> list.addAll(x.GetTooltipString(info, data)));
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
    public String locNameLangFileGUID() {
        return Ref.MODID + ".spell." + GUID();
    }

    public transient String locName;

    @Override
    public String locNameForLangFile() {
        return locName;
    }

}
