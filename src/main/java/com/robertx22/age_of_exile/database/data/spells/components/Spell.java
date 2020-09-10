package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Mana;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.NoManaPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class Spell implements IGUID, IAutoGson<Spell>, ISerializedRegistryEntry<Spell>, IAutoLocName {
    public static Spell SERIALIZER = new Spell();

    public static Gson GSON = new Gson();

    private String identifier = "";
    private AttachedSpell attached = new AttachedSpell();
    private SpellConfiguration config = new SpellConfiguration();

    public SpellConfiguration getConfig() {
        return config;
    }

    transient String json;
    transient boolean isInit = false;

    public void validate() {
        for (ComponentPart x : this.attached.getAllComponents()) {
            x.validate();
        }
    }

    public final Identifier getIconLoc() {
        return new Identifier(Ref.MODID, "textures/gui/spells/icons/" + GUID() + ".png");
    }

    public void onInit() {

        if (!isInit) {
            json = GSON.toJson(attached);
            this.isInit = true;

            this.validate();
        }
    }

    public final void onCastingTick(SpellCastContext ctx) {

        int timesToCast = (int) ctx.calcData.spell.getConfig().times_to_cast;

        if (timesToCast > 1) {

            int castTimeTicks = (int) ctx.calcData.spell.getConfig().cast_time_ticks;

            // if i didnt do this then cast time reduction would reduce amount of spell hits.
            int castEveryXTicks = castTimeTicks / timesToCast;

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

    public AttachedSpell getAttachedSpell(LivingEntity caster) {

        onInit();

        //JsonElement json2 = GSON.toJsonTree(attached);
        //System.out.println(json2.toString());

        AttachedSpell calc = GSON.fromJson(json, AttachedSpell.class);
        // todo, lot player stats, and spell modifiers modify this

        return calc;

    }

    public void cast(SpellCastContext ctx) {
        LivingEntity caster = ctx.caster;

        AttachedSpell attached = getAttachedSpell(caster);
        EntitySavedSpellData data = EntitySavedSpellData.create(caster, this, attached);
        CalculatedSpellData calc = CalculatedSpellData.create(caster, this);

        ctx.castedThisTick = true;

        attached.onCast(SpellCtx.onCast(caster, data));
    }

    public final int getCooldownInSeconds(SpellCastContext ctx) {
        return ctx.calcData.spell.config.cooldown_ticks / 20;
    }

    public final float getUseDurationInSeconds(SpellCastContext ctx) {
        return (float) ctx.calcData.spell.config.cast_time_ticks / 20;
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
            ctx.data, ctx.caster, ResourcesData.Type.MANA, cost, ResourcesData.Use.SPEND);
    }

    public boolean canCast(SpellCastContext ctx) {

        LivingEntity caster = ctx.caster;

        if (caster instanceof PlayerEntity == false) {
            return true;
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
        return (int) Mana.getInstance()
            .scale(ctx.calcData.spell.getConfig().mana_cost, ctx.calcData.level);
    }

    public final List<Text> GetTooltipString(TooltipInfo info, CalculatedSpellData data) {

        SpellCastContext ctx = new SpellCastContext(info.player, 0, data);

        List<Text> list = new ArrayList<>();

        TooltipUtils.addEmpty(list);

        if (Screen.hasShiftDown()) {
            list.addAll(data.spell.getAttachedSpell(info.player)
                .getTooltip());
        }

        TooltipUtils.addEmpty(list);

        MutableText mana = new LiteralText(Formatting.BLUE + "Mana Cost: " + getCalculatedManaCost(ctx));
        MutableText cd = new LiteralText(Formatting.YELLOW + "Cooldown: " + getCooldownInSeconds(ctx) + "s");
        MutableText casttime = new LiteralText(Formatting.GREEN + "Cast time: " + getUseDurationInSeconds(ctx) + "s");

        list.add(mana);
        list.add(cd);
        list.add(casttime);

        TooltipUtils.addEmpty(list);

        list.add(getConfig().castingWeapon.predicate.text);

        TooltipUtils.addEmpty(list);

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

    public static class Builder {

        Spell spell;

        public static Builder of(String id, SpellConfiguration config) {
            Builder builder = new Builder();

            builder.spell = new Spell();
            builder.spell.identifier = id;
            builder.spell.config = config;

            return builder;

        }

        public Builder onCast(ComponentPart comp) {
            this.spell.attached.on_cast.add(comp);
            return this;
        }

        public static String DEFAULT_EN_NAME = "default_entity_name";

        public Builder onTick(ComponentPart comp) {
            return this.addEffect(DEFAULT_EN_NAME, EntityActivation.ON_TICK, comp);
        }

        public Builder onExpire(ComponentPart comp) {
            return this.addEffect(DEFAULT_EN_NAME, EntityActivation.ON_EXPIRE, comp);
        }

        public Builder onHit(ComponentPart comp) {
            return this.addEffect(DEFAULT_EN_NAME, EntityActivation.ON_HIT, comp);
        }

        public Builder onTick(String entity, ComponentPart comp) {
            return this.addEffect(entity, EntityActivation.ON_TICK, comp);
        }

        public Builder onExpire(String entity, ComponentPart comp) {
            return this.addEffect(entity, EntityActivation.ON_EXPIRE, comp);
        }

        public Builder onHit(String entity, ComponentPart comp) {
            return this.addEffect(entity, EntityActivation.ON_HIT, comp);
        }

        private Builder addEffect(String entity, EntityActivation data, ComponentPart comp) {
            Objects.requireNonNull(data);
            Objects.requireNonNull(comp);

            if (!spell.attached.entity_components.containsKey(entity)) {
                spell.attached.entity_components.put(entity, new HashMap<>());
            }

            if (!spell.attached.entity_components.get(entity)
                .containsKey(data)) {
                spell.attached.entity_components.get(entity)
                    .put(data, new ArrayList<>());
            }

            this.spell.attached.getDataForEntity(entity)
                .get(data)
                .add(comp);

            return this;
        }

        public Spell build() {
            Objects.requireNonNull(spell);
            this.spell.addToSerializables();
            return spell;
        }

    }

}
