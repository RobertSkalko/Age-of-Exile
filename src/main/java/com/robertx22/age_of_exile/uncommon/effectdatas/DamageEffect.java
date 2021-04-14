package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.mixin_ducks.LivingEntityAccesor;
import com.robertx22.age_of_exile.mixin_ducks.ProjectileEntityDuck;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PlayerDeathStatistics;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.*;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class DamageEffect extends EffectData implements IArmorReducable, IPenetrable, IDamageEffect,
    IElementalResistable, IElementalPenetrable, ICrittable {

    public DamageEffect(AttackInformation data, int dmg, AttackType effectType, WeaponTypes weptype, AttackPlayStyle style) {
        super(data.getAttackerEntity(), data.getTargetEntity(), data.getAttackerEntityData(), data.getTargetEntityData());
        this.attackInfo = data;
        this.attackType = effectType;
        this.weaponType = weptype;
        this.number = dmg;
        this.style = style;
        this.originalNumber = number;
        calcBlock();
    }

    public DamageEffect(AttackInformation attackInfo, LivingEntity source, LivingEntity target, int dmg,
                        AttackType effectType, WeaponTypes weptype, AttackPlayStyle style) {
        super(source, target, Load.Unit(source), Load.Unit(target));
        this.attackInfo = attackInfo;
        this.attackType = effectType;
        this.weaponType = weptype;
        this.number = dmg;
        this.style = style;
        this.originalNumber = number;
        calcBlock();
    }

    public AttackPlayStyle style;
    AttackInformation attackInfo;

    private HashMap<Elements, Integer> bonusElementDamageMap = new HashMap();

    public List<RestoreResource> toRestore = new ArrayList<>();

    public void addToRestore(RestoreResource data) {
        this.toRestore.add(data);
    }

    public boolean isElemental() {
        return this.element != null && this.element != Elements.Physical;
    }

    public void increaseByPercent(float perc) {
        this.number += this.originalNumber * perc / 100F;
    }

    public void addBonusEleDmg(Elements element, float dmg) {
        bonusElementDamageMap.put(element, (int) (bonusElementDamageMap.getOrDefault(element, 0) + dmg));
    }

    private void calcBlock() {
        // blocking check
        if (target.isBlocking() && attackInfo != null) {
            Vec3d vec3d = attackInfo.getSource()
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

    public float originalNumber;

    public static String dmgSourceName = Ref.MODID + ".custom_damage";
    public Elements element = Elements.Physical;
    public int armorPene;
    public int elementalPene;
    public boolean isBlocked = false;
    public boolean accuracyCritRollFailed = false;
    public float damageMultiplier = 1;
    public float attackerAccuracy = 0;
    public boolean ignoresResists = false;

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

        if (source instanceof PlayerEntity) {
            dmg = modifyByAttackSpeedIfMelee(dmg);
            dmg = modifyIfArrowDamage(dmg);

        }

        if (areBothPlayers()) {
            dmg *= ModConfig.get().Server.PVP_DMG_MULTI;
        }

        if (!isDodged && target instanceof PlayerEntity) { // todo this code sucks
            // a getter should not modify anything
            dmg = DamageAbsorbedByMana.modifyEntityDamage(this, dmg);
        }

        return dmg;
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

                    float secNeededToWaitForFull = 1F / atkpersec;

                    cool = secWaited / secNeededToWaitForFull;

                    cool = MathHelper.clamp(cool, 0F, 1F);

                    dmg *= cool;

                    if (cool < 1) { // TODO
                        this.cancelDamage();
                    }

                }
            }
        }

        return dmg;

    }

    private float modifyIfArrowDamage(float dmg) {
        if (attackInfo != null && attackInfo.getSource() != null) {
            if (attackInfo.getSource()
                .getSource() instanceof ProjectileEntityDuck) {
                if (weaponType == WeaponTypes.Bow) {
                    // don't use this for crossbows, only bows need to be charged fully

                    ProjectileEntityDuck duck = (ProjectileEntityDuck) attackInfo.getSource()
                        .getSource();

                    float arrowmulti = duck.my$getDmgMulti();

                    dmg *= arrowmulti;
                    // multiply dmg by saved charge value
                }
            }
        }

        return dmg;

    }

    public boolean knockback = true;

    public boolean areBothPlayers() {
        if (source instanceof ServerPlayerEntity && target instanceof ServerPlayerEntity) {
            return true;
        }
        return false;
    }

    public void cancelDamage() {
        this.canceled = true;
        if (attackInfo != null) {
            attackInfo.setAmount(0);
            attackInfo.setCanceled(true);
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
            if (target instanceof PlayerEntity) {
                Load.spells(target)
                    .getCastingData()
                    .onAction((PlayerEntity) target, PlayerAction.BLOCK);
            }
            return;
        }
        if (ifPlayersShouldNotDamageEachOther()) {
            return;
        }

        DmgByElement info = getDmgByElement();

        if (isDodged) {
            cancelDamage();
            sendDamageParticle(info);
            SoundUtils.playSound(target, SoundEvents.ITEM_SHIELD_BLOCK, 1, 1.5F);
            return;
        }

        if (!this.isDmgAllowed()) {
            cancelDamage();
            return;
        }

        float dmg = info.totalDmg;
        float vanillaDamage = HealthUtils.realToVanilla(target, dmg);

        if (this.canceled) {
            cancelDamage();
            return;
        }
        DamageSource ds = null;

        if (attackInfo != null) {
            ds = attackInfo.getSource();
        } else {
            ds = DamageSource.GENERIC; // todo unsure.
        }

        if (target instanceof PlayerEntity) {
            info.dmgmap.forEach((key, value) -> {
                PlayerDeathStatistics.record((PlayerEntity) target, key, value);
            });
        }

        MyDamageSource dmgsource = new MyDamageSource(ds, source, element, dmg);

        if (attackInfo == null || !(attackInfo.getSource() instanceof MyDamageSource)) { // todo wtf
            //int hurtResistantTime = target.timeUntilRegen;
            //target.timeUntilRegen = 0;

            EntityAttributeInstance attri = target.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);

            if (!knockback || this.attackType == AttackType.DOT) {
                if (!attri.hasModifier(NO_KNOCKBACK)) {
                    attri.addPersistentModifier(NO_KNOCKBACK);
                }
            }

            if (this instanceof SpellDamageEffect) {
                if (knockback && dmg <= 0 && !isDodged) {
                    // if magic shield absorbed the damage, still do knockback
                    DashUtils.knockback(source, target);
                }

                // play spell hurt sounds or else spells will feel like they do nothing
                LivingEntityAccesor duck = (LivingEntityAccesor) target;
                SoundEvent sound = duck.myGetHurtSound(ds);
                float volume = duck.myGetHurtVolume();
                float pitch = duck.myGetHurtPitch();
                SoundUtils.playSound(target, sound, volume, pitch);
            }

            if (target instanceof EnderDragonEntity) {
                try {
                    // Dumb vanilla hardcodings require dumb workarounds
                    EnderDragonEntity dragon = (EnderDragonEntity) target;
                    EnderDragonPart part = Arrays.stream(dragon.getBodyParts())
                        .filter(x -> x.name.equals("body"))
                        .findFirst()
                        .get();
                    dragon.damagePart(part, dmgsource, vanillaDamage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                target.damage(dmgsource, vanillaDamage);
            }

            // allow multiple dmg same tick

            if (attri.hasModifier(NO_KNOCKBACK)) {
                attri.removeModifier(NO_KNOCKBACK);
            }

        }

        this.toRestore.forEach(x -> x.tryRestore(this));

        doManaBurn();

        if (dmg > 0) {

            onEventPotions();
            sendDamageParticle(info);

        }

    }

    private void sendDamageParticle(DmgByElement info) {

        String text = "";

        if (source instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) source;

            if (this.isDodged) {
                if (attackType.isAttack()) {
                    text = "Dodge";
                } else {
                    text = "Resist";
                }

                DmgNumPacket packet = new DmgNumPacket(target, this.element, text, 0);
                Packets.sendToClient(player, packet);
                return;
            }

            for (Entry<Elements, Float> entry : info.dmgmap.entrySet()) {
                if (entry.getValue()
                    .intValue() > 0) {

                    text = NumberUtils.formatDamageNumber(this, entry.getValue()
                        .intValue());

                    DmgNumPacket packet = new DmgNumPacket(target, entry.getKey(), text, entry.getValue());
                    Packets.sendToClient(player, packet);
                }
            }

        }
    }

    private void onEventPotions() {

        if (this.getAttackType() == AttackType.ATTACK) {
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
                ResourceType.MANA, manaBurn,
                ResourcesData.Use.SPEND
            );

            targetData.getResources()
                .modify(ctx);
        }
    }

    public void RestoreMana(float healed) {
        if (healed > 0) {

            sourceData.getResources()
                .modify(new ResourcesData.Context(sourceData, source, ResourceType.MANA, healed,
                    ResourcesData.Use.RESTORE
                ));

        }
    }

    public void Heal(float healed) {
        if (healed > 0) {
            sourceData.getResources()
                .modify(new ResourcesData.Context(sourceData, source, ResourceType.HEALTH, healed,
                    ResourcesData.Use.RESTORE
                ));
        }
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
                    attackInfo, source, target, entry.getValue(),
                    AttackType.BASIC_ELE_ATK_DMG, this.weaponType, style);
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
