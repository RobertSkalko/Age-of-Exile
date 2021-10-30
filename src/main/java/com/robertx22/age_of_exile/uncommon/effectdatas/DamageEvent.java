package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.PlayerDamageChart;
import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.capability.player.data.ScalingPlayerDiffData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.mixin_ducks.LivingEntityAccesor;
import com.robertx22.age_of_exile.mixin_ducks.ProjectileEntityDuck;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.DmgNumPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class DamageEvent extends EffectEvent {

    public static String ID = "on_damage";

    @Override
    public String GUID() {
        return ID;
    }

    protected DamageEvent(AttackInformation attackInfo, LivingEntity source, LivingEntity target, float dmg) {
        super(dmg, source, target);
        this.attackInfo = attackInfo;
        calcBlock();
    }

    public static String dmgSourceName = SlashRef.MODID + ".custom_damage";

    AttackInformation attackInfo;
    private HashMap<Elements, Integer> bonusElementDamageMap = new HashMap();

    public AttackType getAttackType() {
        return data.getAttackType();
    }

    public Elements getElement() {
        return data.getElement();
    }

    public void addBonusEleDmg(Elements element, float dmg) {
        bonusElementDamageMap.put(element, (int) (bonusElementDamageMap.getOrDefault(element, 0) + dmg));
    }

    private void calcBlock() {

        if (targetData
            .getResources()
            .getEnergy() < 1) {
            return;
        }

        // blocking check
        if (target.isBlocking() && attackInfo != null) {
            Vector3d vec3d = attackInfo.getSource()
                .getSourcePosition();
            if (vec3d != null) {
                Vector3d vec3d2 = target.getViewVector(1.0F);
                Vector3d vec3d3 = vec3d.vectorTo(target.position())
                    .normalize();
                vec3d3 = new Vector3d(vec3d3.x, 0.0D, vec3d3.z);
                if (vec3d3.dot(vec3d2) < 0.0D) {
                    this.data.setBoolean(EventData.IS_BLOCKED, true);
                }
            }
        }
    }

    public float getActualDamage() {
        float dmg = this.data.getNumber();

        if (dmg <= 0) {
            return 0;
        }

        if (source instanceof PlayerEntity) {
            if (data.isBasicAttack()) {
                dmg = modifyByAttackSpeedIfMelee(dmg);
                dmg = modifyIfArrowDamage(dmg);
            }
        }

        if (areBothPlayers()) {
            dmg *= ServerContainer.get().PVP_DMG_MULTI.get();
        }

        if (!data.isDodged() && target instanceof PlayerEntity) { // todo this code sucks
            // a getter should not modify anything
            dmg = DamageAbsorbedByMana.modifyEntityDamage(this, dmg);

            if (dmg > 0) {

                int reduced = targetData.getResources().shields.spendShieldsToReduceDamage(dmg);
                dmg -= reduced;
            }

        }

        return dmg;
    }

    private void calcAttackCooldown() {
        float cool = 1;

        WeaponTypes weaponType = data.getWeaponType();

        if (weaponType.isMelee()) {

            if (this.source instanceof PlayerEntity) {

                GearItemData gear = Gear.Load(source.getMainHandItem());

                if (gear != null) {
                    float atkpersec = gear.GetBaseGearType()
                        .getAttacksPerSecondCalculated(sourceData);

                    float secWaited = (float) (source.tickCount - source.getLastHurtMobTimestamp()) / 20F;

                    float secNeededToWaitForFull = 1F / atkpersec;

                    cool = secWaited / secNeededToWaitForFull;

                    cool = MathHelper.clamp(cool, 0F, 1F);

                }
            }
        }
        data.setupNumber(EventData.ATTACK_COOLDOWN, cool);
    }

    private float modifyByAttackSpeedIfMelee(float dmg) {

        float cool = data.getNumber(EventData.ATTACK_COOLDOWN).number;

        dmg *= cool;

        if (cool < 0.2F) { // TODO
            this.cancelDamage();
        }

        if (cool > 0.8F) {
            //ParticleUtils.spawnDefaultSlashingWeaponParticles(source);
        }

        return dmg;

    }

    private float modifyIfArrowDamage(float dmg) {
        if (attackInfo != null && attackInfo.getSource() != null) {
            if (attackInfo.getSource()
                .getDirectEntity() instanceof ProjectileEntityDuck) {
                if (data.getWeaponType() == WeaponTypes.bow) {
                    // don't use this for crossbows, only bows need to be charged fully

                    ProjectileEntityDuck duck = (ProjectileEntityDuck) attackInfo.getSource()
                        .getDirectEntity();

                    float arrowmulti = duck.my$getDmgMulti();

                    dmg *= arrowmulti;
                    // multiply dmg by saved charge value
                }
            }
        }

        return dmg;

    }

    public boolean areBothPlayers() {
        if (source instanceof ServerPlayerEntity && target instanceof ServerPlayerEntity) {
            return true;
        }
        return false;
    }

    public void cancelDamage() {
        this.data.getNumber(EventData.NUMBER).number = 0;

        this.data.setBoolean(EventData.CANCELED, true);
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
            if (!sp.canHarmPlayer((PlayerEntity) target)) {
                return true;
            }
        } else {
            if (this.data.isSpellEffect()) {
                if (target instanceof TameableEntity) {
                    if (source instanceof PlayerEntity) {
                        TameableEntity tame = (TameableEntity) target;
                        if (tame.isOwnedBy(source)) {
                            cancelDamage();
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    AttributeModifier NO_KNOCKBACK = new AttributeModifier(
        UUID.fromString("e926df30-c376-11ea-87d0-0242ac131053"),
        Attributes.KNOCKBACK_RESISTANCE.getDescriptionId(),
        100,
        AttributeModifier.Operation.ADDITION
    );

    @Override
    public void initBeforeActivating() {
        calcAttackCooldown();
    }

    @Override
    protected void activate() {

        if (target.getHealth() <= 0F || !target.isAlive()) {
            return;
        }

        if (ifPlayersShouldNotDamageEachOther()) {
            return;
        }

        DmgByElement info = getDmgByElement();

        if (data.isDodged()) {
            cancelDamage();
            sendDamageParticle(info);
            SoundUtils.playSound(target, SoundEvents.SHIELD_BLOCK, 1, 1.5F);
            return;
        }

        float dmg = info.totalDmg;

        if (source instanceof PlayerEntity == false) {
            dmg *= ScalingPlayerDiffData.getDMGMulti(sourceData.mobScalingDiff);
        }

        float vanillaDamage = HealthUtils.realToVanilla(target, dmg);

        if (this.data.isCanceled()) {
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
                DeathStatsData.record((PlayerEntity) target, key, value);
            });
        }

        MyDamageSource dmgsource = new MyDamageSource(ds, source, getElement(), dmg);

        if (attackInfo == null || !(attackInfo.getSource() instanceof MyDamageSource)) { // todo wtf

            ModifiableAttributeInstance attri = target.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

            if (data.getBoolean(EventData.DISABLE_KNOCKBACK) || this.getAttackType() == AttackType.dot) {
                if (!attri.hasModifier(NO_KNOCKBACK)) {
                    attri.addPermanentModifier(NO_KNOCKBACK);
                }
            }

            if (target instanceof PlayerEntity == false) {
                target.invulnerableTime = 0; // disable iframes hopefully
                target.hurtTime = 0;
            }

            if (this.data.isSpellEffect()) {
                if (!data.getBoolean(EventData.DISABLE_KNOCKBACK) && dmg > 0 && !data.isDodged()) {
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
                    EnderDragonPartEntity part = Arrays.stream(dragon.getSubEntities())
                        .filter(x -> x.name.equals("body"))
                        .findFirst()
                        .get();
                    dragon.hurt(part, dmgsource, vanillaDamage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                target.hurt(dmgsource, vanillaDamage);
            }

            if (target instanceof PlayerEntity == false) {
                if (getAttackType() == AttackType.dot) {
                    target.invulnerableTime = 0; // disable iframes hopefully
                    target.hurtTime = 0;
                }
            }

            // allow multiple dmg same tick

            if (attri.hasModifier(NO_KNOCKBACK)) {
                attri.removeModifier(NO_KNOCKBACK);
            }

        }

        if (this.target.isDeadOrDying()) {
            OnMobKilledByDamageEvent event = new OnMobKilledByDamageEvent(this);
            event.Activate();
        }

        if (dmg > 0) {
            if (source instanceof PlayerEntity) {
                sourceData.getCooldowns()
                    .setOnCooldown(CooldownsData.IN_COMBAT, 20 * 10);

                if (target instanceof MobEntity) {
                    PlayerDamageChart.onDamage((PlayerEntity) source, dmg);

                    GenerateThreatEvent threatEvent = new GenerateThreatEvent((PlayerEntity) source, (MobEntity) target, ThreatGenType.deal_dmg, dmg);
                    threatEvent.Activate();
                }
            } else if (source instanceof MobEntity) {
                if (target instanceof PlayerEntity) {
                    targetData.getCooldowns()
                        .setOnCooldown(CooldownsData.IN_COMBAT, 20 * 10);

                    GenerateThreatEvent threatEvent = new GenerateThreatEvent((PlayerEntity) target, (MobEntity) source, ThreatGenType.take_dmg, dmg);
                    threatEvent.Activate();
                }
            }

            sendDamageParticle(info);

        }

    }

    private void sendDamageParticle(DmgByElement info) {

        String text = "";

        if (source instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) source;

            if (data.isDodged()) {
                if (getAttackType().isAttack()) {
                    text = "Dodge";
                } else {
                    text = "Resist";
                }

                DmgNumPacket packet = new DmgNumPacket(target, text, false, TextFormatting.GOLD);
                Packets.sendToClient(player, packet);
                return;
            }

            for (Entry<Elements, Float> entry : info.dmgmap.entrySet()) {
                if (entry.getValue()
                    .intValue() > 0) {

                    text = entry.getKey().format + NumberUtils.formatDamageNumber(this, entry.getValue()
                        .intValue());

                    DmgNumPacket packet = new DmgNumPacket(target, text, data.isCrit(), entry.getKey().format);
                    Packets.sendToClient(player, packet);
                }
            }

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

                DamageEvent bonus = EventBuilder.ofDamage(attackInfo, source, target, entry.getValue())
                    .setupDamage(AttackType.attack, data.getWeaponType(), data.getStyle())
                    .set(x -> x.setElement(entry.getKey()))
                    .build();

                bonus.data.setBoolean(EventData.IS_BASIC_ATTACK, this.data.getBoolean(EventData.IS_BASIC_ATTACK));
                bonus.data.setBoolean(EventData.IS_ATTACK_FULLY_CHARGED, this.data.getBoolean(EventData.IS_ATTACK_FULLY_CHARGED));
                bonus.data.setupNumber(EventData.ATTACK_COOLDOWN, this.data.getNumber(EventData.ATTACK_COOLDOWN).number);
                bonus.calculateEffects();

                bonus.setElement(entry.getKey());
                bonus.calculateEffects();
                float dmg = bonus.getActualDamage();

                info.addDmg(dmg, bonus.getElement());

            }
        }
        info.addDmg(this.getActualDamage(), this.getElement());

        return info;

    }

    public Elements GetElement() {
        return getElement();
    }

    public void setElement(Elements ele) {
        this.data.setElement(ele);
    }

    public void setPenetration(float val) {
        this.data.getNumber(EventData.PENETRATION).number = val;
    }

    public float getPenetration() {
        return this.data.getNumber(EventData.PENETRATION).number;
    }

}
