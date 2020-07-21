package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

import com.robertx22.exiled_lib.registry.ISlashRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.spells.IAbility;
import com.robertx22.mine_and_slash.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePotionEffect extends StatusEffect implements ISlashRegistryEntry<BasePotionEffect>, IAutoLocName, ITooltipList, IAbility {

    public PreCalcSpellConfigs getConfig(LivingEntity caster) {
        return getCtx(caster).getConfigFor(getAbilityThatDeterminesLevel());
    }

    public SpellCalcData getCalc(LivingEntity caster) {
        return getConfig(caster).getCalc(Load.spells(caster), getAbilityThatDeterminesLevel());
    }

    public SpellCastContext getCtx(LivingEntity caster) {
        return new SpellCastContext(caster, 0, getAbilityThatDeterminesLevel());
    }

    @Override
    public MutableText getLocName() {
        return this.locName();
    }

    @Override
    public Identifier getIconLoc() {
        return getIconTexture();
    }

    @Override
    public Type getAbilityType() {
        return Type.EFFECT;
    }

    public Elements getElement() {
        if (getSpell() != null) {
            return getSpell().getElement();
        }

        return Elements.Physical;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.EFFECT;
    }

    public IAbility getAbilityThatDeterminesLevel() {
        if (getSpell() != null) {
            return getSpell();
        }
        return this;
    }

    protected List<OnTickAction> tickActions = new ArrayList<>();

    public int getMaxStacks() {
        return 1;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Potions;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.STATUS_EFFECT.getId(this)
            .toString();
    }

    public abstract String GUID();

    @Override
    public MutableText locName() {
        return CLOC.blank("effect." + Ref.MODID + "." + GUID());
    }

    //public abstract List<ITextComponent> getEffectTooltip(TooltipInfo info);

    public List<Text> getEffectTooltip(TooltipInfo info) {
        return new ArrayList<>();
    }

    @Override
    public final List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText(Formatting.LIGHT_PURPLE + "" + Formatting.BOLD).append(
            locName()));

        list.addAll(getEffectTooltip(info));

        if (this instanceof IApplyStatPotion) {
            list.addAll(((IApplyStatPotion) this).getStatTooltip(info, this));
        }

        list.add(new SText(""));

        tickActions.forEach(x -> list.addAll(x.getTooltip(info, this)));

        list.addAll(getMaxStacksTooltip());
        list.addAll(getDurationTooltip(info));

        if (info.showAbilityExtraInfo) {
            finishTooltip(list, new SpellCastContext(info.player, 0, this), info);
        }

        return list;
    }

    public Identifier getIconTexture() {
        return new Identifier(Ref.MODID, "textures/mob_effect/" + GUID() + ".png");
    }

    private List<Text> getMaxStacksTooltip() {
        List<Text> list = new ArrayList<>();

        TooltipUtils.addEmpty(list);
        list.add(new LiteralText(
            Formatting.LIGHT_PURPLE + "Max Stacks: " + Formatting.DARK_PURPLE + getMaxStacks()));

        return list;

    }

    private List<Text> getDurationTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        TooltipUtils.addEmpty(list);
        list.add(new LiteralText(
            Formatting.GOLD + "Duration: " + Formatting.YELLOW + getDurationInSeconds(info.player) + "s"));

        return list;

    }

    public final int getDurationInSeconds(LivingEntity en) {
        return getDurationInTicks(en) / 20;
    }

    public final int getDurationInTicks(LivingEntity en) {
        IAbility ability = this.getAbilityThatDeterminesLevel();

        return (int) new SpellCastContext(en, 0, ability).getConfigFor(ability)
            .get(SC.DURATION_TICKS)
            .getMax();

    }

    public int getTickRate(LivingEntity en) {
        IAbility ability = this.getAbilityThatDeterminesLevel();

        return (int) new SpellCastContext(en, 0, ability).getConfigFor(ability)
            .get(SC.TICK_RATE)
            .getMax();

    }

    @Override
    public void applyUpdateEffect(LivingEntity en, int amplifier) {

        try {

            boolean delete = false;

            if (tickActions.size() > 0) {

                int tickrate = getTickRate(en);

                if (en.age % tickrate == 0) {
                    for (OnTickAction x : this.tickActions) {

                        StatusEffectInstance instance = en.getStatusEffect(this);

                        if (instance == null) {
                            //Log.error("potion instance is null, Deleting potion");
                            delete = true;
                            return;
                        }

                        ExtraPotionData data = PotionDataSaving.getData(instance);

                        if (data == null) {
                            //Log.error("Extra potion data is null. Deleting potion");
                            delete = true;
                            return;
                        }

                        LivingEntity caster = data.getCaster(en.world);

                        if (caster == null) {
                            //Log.error("Potion can't find caster. Deleting potion");
                            delete = true;
                            return;
                        }

                        x.onTick(new PotionContext(en, data, caster));

                    }
                }
            }

            if (delete) {
                en.removeStatusEffect(this);
            }

        } catch (Exception e) {
            en.removeStatusEffect(this);
            e.printStackTrace();
        }
    }

    protected BasePotionEffect(StatusEffectType type, int liquidColorIn) {
        super(type, liquidColorIn);

    }

    public StatusEffectInstance getInstanceFromEntity(LivingEntity entity) {
        return entity.getStatusEffect(this);
    }

    protected boolean isServerSideOnly() {
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplitude) {
        // Controls whether performEffect is called
        // System.out.printf("SpellPotionBase - isReady %d %d\n", duration, amplitude);
        return duration >= 1;
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    // Called when the potion is being applied by an
    // AreaEffect or thrown potion bottle
    @Override
    public void applyInstantEffect(Entity applier, Entity caster, LivingEntity target, int amplifier,
                                   double health) {

        if (target.world.isClient && isServerSideOnly())
            return;

        onEffectApplied(applier, caster, target, amplifier);
    }

    @Override
    public void onApplied(LivingEntity target, AttributeContainer attributes,
                          int amplifier) {

        if (!target.world.isClient || !isServerSideOnly()) {

            onPotionAdd(target);

        }

        // Called on application
        super.onApplied(target, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity target, AttributeContainer attributes,
                          int amplifier) {
        // called at end
        super.onRemoved(target, attributes, amplifier);

        this.onPotionRemove(target);
    }

    public void onPotionAdd(LivingEntity target) {
    }

    public void onPotionRemove(LivingEntity target) {
    }

    public void onEffectApplied(Entity applier, Entity caster, LivingEntity target, int amplifier) {

    }

    protected boolean shouldShowParticles() {
        return false;
    }

    protected boolean isAmbient() {
        return false;
    }

}