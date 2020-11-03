package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.event_hooks.entity.damage.DamageEventData;
import com.robertx22.age_of_exile.mixin_ducks.LivingEntityDuck;
import com.robertx22.age_of_exile.mixin_ducks.ProjectileEntityDuck;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.*;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.DmgNumPacket;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IOnBasicAttackPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IOnBasicAttackedPotion;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

public class DamageEffect extends EffectData implements IArmorReducable, IPenetrable, IDamageEffect,
    IElementalResistable, IElementalPenetrable, ICrittable {

    public DamageEffect(DamageEventData data, int dmg, EffectTypes effectType, WeaponTypes weptype, AttackPlayStyle style) {
        super(data.source, data.target, data.sourceData, data.targetData);
        this.event = data.event;
        this.setEffectType(effectType, weptype);
        this.number = dmg;
        this.style = style;
        calcBlock();
    }

    public DamageEffect(LivingHurtEvent event, LivingEntity source, LivingEntity target, int dmg,
                        EffectTypes effectType, WeaponTypes weptype, AttackPlayStyle style) {
        super(source, target, Load.Unit(source), Load.Unit(target));
        this.event = event;
        this.setEffectType(effectType, weptype);
        this.number = dmg;
        this.style = style;
        calcBlock();
    }

    public AttackPlayStyle style;
    LivingHurtEvent event;

    private HashMap<Elements, Integer> bonusElementDamageMap = new HashMap();

    public boolean isElemental() {
        return this.element != null && this.element != Elements.Physical;
    }

    public void addBonusEleDmg(Elements element, float dmg) {
        if (bonusElementDamageMap.containsKey(element)) {
            bonusElementDamageMap.put(element, (int) (bonusElementDamageMap.get(element) + dmg));

        } else {
            bonusElementDamageMap.put(element, (int) dmg);
        }
    }

    private void calcBlock() {
        // blocking check
        if (target.isBlocking() && event != null) {
            Vec3d vec3d = event.getSource()
                .getPosition();
            if (vec3d != null) {
                Vec3d vec3d2 = target.getRotationVec(1.0F);
                Vec3d vec3d3 = vec3d.reverseSubtract(target.getPos())
                    .normalize();
                vec3d3 = new Vec3d(vec3d3.x, 0.0D, vec3d3.z);
                if (vec3d3.dotProduct(vec3d2) < 0.0D) {
                    this.isBlocked = true;
                }
            }
        }
    }

    public float percentIncrease = 0;

    public static String dmgSourceName = Ref.MODID + ".custom_damage";
    public Elements element = Elements.Physical;
    public int armorPene;
    public int elementalPene;
    public boolean isBlocked = false;
    public float damageMultiplier = 1;

    public float healthHealed;
    public float magicShieldRestored;
    public float manaRestored;

    public float healthHealedOnKill;
    public float magicShieldRestoredOnKill;
    public float manaRestoredOnKill;

    public float manaBurn = 0;

    public boolean isDodged = false;

    public boolean isDmgAllowed() {
        return !isDodged;
    }

    public float getActualDamage() {
        float dmg = this.number * damageMultiplier;

        if (dmg <= 0) {
            return 0;
        }

        float percentMulti = 1F + this.percentIncrease / 100F;

        if (source instanceof PlayerEntity) {
            dmg = modifyByAttackSpeedIfMelee(dmg);
            dmg = modifyIfArrowDamage(dmg);

        }

        if (areBothPlayers()) {
            dmg *= ModConfig.get().Server.PVP_DMG_MULTI;
        }

        return dmg * percentMulti;
    }

    private float modifyByAttackSpeedIfMelee(float dmg) {

        if (this.weaponType.isMelee) {
            float cool = 1;
            if (this.source instanceof PlayerEntity) {

                GearItemData gear = Gear.Load(source.getMainHandStack());

                if (gear != null) {
                    float atkpersec = gear.GetBaseGearType()
                        .getAttacksPerSecondCalculated(sourceData);

                    float secWaited = (float) (source.age - source.getLastAttackTime()) / 20F;

                    if (secWaited > atkpersec) {
                        secWaited = atkpersec;
                    }

                    cool = secWaited / atkpersec;

                    dmg *= cool;

                }
            }
        }

        return dmg;

    }

    private float modifyIfArrowDamage(float dmg) {
        if (event != null && event.getSource() != null) {
            if (event.getSource()
                .getSource() instanceof ProjectileEntityDuck) {
                if (weaponType == WeaponTypes.Bow) {
                    // don't use this for crossbows, only bows need to be charged fully

                    ProjectileEntityDuck duck = (ProjectileEntityDuck) event.getSource()
                        .getSource();

                    float arrowmulti = duck.my$getDmgMulti();

                    dmg *= arrowmulti;
                    // multiply dmg by saved charge value
                }
            }
        }

        return dmg;

    }

    boolean removeKnockback = false;

    public void removeKnockback() {
        removeKnockback = true;
    }

    public boolean areBothPlayers() {
        if (source instanceof ServerPlayerEntity && target instanceof ServerPlayerEntity) {
            return true;
        }
        return false;
    }

    public void cancelDamage() {
        this.canceled = true;
        if (event != null) {
            event.setAmount(0);
            event.setCanceled(true);
        }
        return;
    }

    public boolean ifPlayersShouldNotDamageEachOther() {

        if (areBothPlayers()) {
            if (source.equals(target)) {
                return false; // it's the same player, let him hit himself
            }
            if (TeamUtils.areOnSameTeam((ServerPlayerEntity) source, (ServerPlayerEntity) target)) {
                return true;
            }
            PlayerEntity sp = (PlayerEntity) this.source;
            if (!sp.shouldDamagePlayer((PlayerEntity) target)) {
                return true;
            }
        } else {
            if (this instanceof SpellDamageEffect) {
                if (target instanceof TameableEntity) {
                    if (source instanceof PlayerEntity) {
                        TameableEntity tame = (TameableEntity) target;
                        if (tame.isOwner(source)) {
                            cancelDamage();
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    EntityAttributeModifier NO_KNOCKBACK = new EntityAttributeModifier(
        UUID.fromString("e926df30-c376-11ea-87d0-0242ac131053"),
        EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE.getTranslationKey(),
        100,
        EntityAttributeModifier.Operation.ADDITION
    );

    @Override
    protected void activate() {

        if (target.getHealth() <= 0F || !target.isAlive()) {
            return;
        }
        if (isBlocked) {
            return;
        }
        if (ifPlayersShouldNotDamageEachOther()) {
            return;
        }
        if (isDodged) {
            cancelDamage();
            SoundUtils.playSound(target, SoundEvents.ITEM_SHIELD_BLOCK, 1, 1.5F);
            return;
        }

        if (!this.isDmgAllowed()) {
            cancelDamage();
            return;
        }

        DmgByElement info = getDmgByElement();
        float dmg = info.totalDmg;

        if (this.canceled) {
            cancelDamage();
            return;
        }
        DamageSource ds = null;

        if (event != null) {
            ds = event.getSource();
        } else {
            ds = DamageSource.GENERIC; // todo unsure.
        }

        MyDamageSource dmgsource = new MyDamageSource(ds, source, element, dmg);

        this.sourceData.onAttackEntity(source, target);

        this.targetData.onDamagedBy(source, dmg, target);

        if (event == null || !(event.getSource() instanceof MyDamageSource)) { // todo wtf
            //int hurtResistantTime = target.timeUntilRegen;
            //target.timeUntilRegen = 0;

            EntityAttributeInstance attri = target.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);

            if (removeKnockback || this.effectType == EffectTypes.DOT_DMG) {
                if (!attri.hasModifier(NO_KNOCKBACK)) {
                    attri.addPersistentModifier(NO_KNOCKBACK);
                }
            }

            if (false) {
                // use apply damage so we don't get stack overflow
                // tell other mods to listen to entity.damage and not entity.applydamage
                LivingEntityDuck duck = (LivingEntityDuck) target;
                duck.myApplyDamage(dmgsource, dmg);
            } else {

                if (target instanceof EnderDragonEntity) {
                    try {
                        // Dumb vanilla hardcodings require dumb workarounds
                        EnderDragonEntity dragon = (EnderDragonEntity) target;
                        EnderDragonPart part = Arrays.stream(dragon.getBodyParts())
                            .filter(x -> x.name.equals("body"))
                            .findFirst()
                            .get();
                        dragon.damagePart(part, dmgsource, dmg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    target.damage(dmgsource, dmg);
                }

                // allow multiple dmg same tick
            }

            if (removeKnockback) {
                if (attri.hasModifier(NO_KNOCKBACK)) {
                    attri.removeModifier(NO_KNOCKBACK);
                }
            }

        }

        Heal(healthHealed);
        RestoreMana(manaRestored);
        restoreMagicShield(magicShieldRestored);

        doManaBurn();

        if (!target.isAlive()) {
            Heal(healthHealedOnKill);
            RestoreMana(manaRestoredOnKill);
            restoreMagicShield(magicShieldRestoredOnKill);
        }

        if (dmg > 0) {

            onEventPotions();

            if (source instanceof ServerPlayerEntity) {

                info.dmgmap.entrySet()
                    .forEach(x -> {
                        if (x.getValue()
                            .intValue() > 0) {
                            ServerPlayerEntity player = (ServerPlayerEntity) source;

                            String str = NumberUtils.formatDamageNumber(this, x.getValue()
                                .intValue());
                            DmgNumPacket packet = new DmgNumPacket(target, x.getKey(), str, x.getValue());
                            Packets.sendToClient(player, packet);
                        }
                    });

            }
        }

    }

    private void onEventPotions() {

        if (this.getEffectType() == EffectTypes.BASIC_ATTACK) {
            List<StatusEffectInstance> onAttacks = source.getStatusEffects()
                .stream()
                .filter(x -> x.getEffectType() instanceof IOnBasicAttackPotion)
                .collect(Collectors.toList());

            onAttacks.forEach(x -> ((IOnBasicAttackPotion) x.getEffectType()).OnBasicAttack(source, target));

            List<StatusEffectInstance> onAttackeds = target.getStatusEffects()
                .stream()
                .filter(x -> x.getEffectType() instanceof IOnBasicAttackedPotion)
                .collect(Collectors.toList());

            onAttackeds.forEach(x -> ((IOnBasicAttackedPotion) x.getEffectType()).onBasicAttacked(x, source, target));

        }
    }

    public void doManaBurn() {
        if (manaBurn > 0) {
            ResourcesData.Context ctx = new ResourcesData.Context(targetData, target,
                ResourcesData.Type.MANA, manaBurn,
                ResourcesData.Use.SPEND
            );

            targetData.getResources()
                .modify(ctx);
        }
    }

    public void RestoreMana(float healed) {
        if (healed > 0) {

            sourceData.getResources()
                .modify(new ResourcesData.Context(sourceData, source, ResourcesData.Type.MANA, healed,
                    ResourcesData.Use.RESTORE
                ));

        }
    }

    public void Heal(float healed) {
        if (healed > 0) {
            sourceData.getResources()
                .modify(new ResourcesData.Context(sourceData, source, ResourcesData.Type.HEALTH, healed,
                    ResourcesData.Use.RESTORE
                ));
        }
    }

    public void restoreMagicShield(float healed) {
        if (healed > 0) {
            sourceData.getResources()
                .modify(new ResourcesData.Context(sourceData, source, ResourcesData.Type.MAGIC_SHIELD, healed,
                    ResourcesData.Use.RESTORE
                ));
        }
    }

    public DamageEffect setMultiplier(float multi) {
        this.damageMultiplier = multi;
        return this;
    }

    public static class DmgByElement {

        public HashMap<Elements, Float> dmgmap = new HashMap<>();
        public Elements highestDmgElement;
        public float highestDmgValue;
        public float totalDmg = 0;

        public void addDmg(float dmg, Elements element) {

            Elements ele = element;

            if (ele == null) {
                ele = Elements.Physical;
            }

            float total = (dmgmap.getOrDefault(element, 0F) + dmg);

            dmgmap.put(ele, total);

            totalDmg += dmg;

            if (total > highestDmgValue) {
                highestDmgElement = ele;
                highestDmgValue = total;
            }

        }

    }

    public DmgByElement getDmgByElement() {
        DmgByElement info = new DmgByElement();

        for (Entry<Elements, Integer> entry : bonusElementDamageMap.entrySet()) {
            if (entry.getValue() > 0) {
                DamageEffect bonus = new DamageEffect(
                    event, source, target, entry.getValue(),
                    EffectTypes.BONUS_ATTACK, this.weaponType, style);
                bonus.element = entry.getKey();
                bonus.damageMultiplier = this.damageMultiplier;
                bonus.calculateEffects();
                float dmg = bonus.getActualDamage();

                info.addDmg(dmg, bonus.element);

            }
        }
        info.addDmg(this.getActualDamage(), this.element);

        return info;

    }

    @Override
    public LivingEntity Source() {
        return source;
    }

    @Override
    public LivingEntity Target() {
        return target;
    }

    @Override
    public float Number() {
        return number;
    }

    @Override
    public Elements GetElement() {
        return element;
    }

    @Override
    public void setElement(Elements ele) {
        this.element = ele;
    }

    @Override
    public void SetArmorPenetration(int val) {
        this.armorPene = val;

    }

    @Override
    public void addElementalPenetration(int val) {
        this.elementalPene += val;
    }

    @Override
    public int GetArmorPenetration() {
        return this.armorPene;
    }

    public boolean crit = false;

    @Override
    public void setCrit(boolean bool) {
        crit = bool;
    }

    @Override
    public boolean isCriticalHit() {
        return crit;
    }

    @Override
    public int GetElementalPenetration() {
        return this.elementalPene;
    }

}
