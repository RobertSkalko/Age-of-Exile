package com.robertx22.age_of_exile.database.data.spells.spell_classes.divine;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.SpellUtils;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class HealingAuraSpell extends BaseSpell {

    private HealingAuraSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.SELF_HEAL;
                }

                @Override
                public SoundEvent sound() {
                    return null;
                }

                @Override
                public Elements element() {
                    return Elements.Elemental;
                }
            }.cooldownIfCanceled(true)
        );
    }

    @Override
    public void castExtra(SpellCastContext ctx) {

        float RADIUS = ctx.getConfigFor(this)
            .get(SC.RADIUS)
            .get(ctx.skillGem);

        List<LivingEntity> list = EntityFinder.start(ctx.caster, LivingEntity.class, ctx.caster.getPos())
            .finder(EntityFinder.Finder.RADIUS)
            .radius(RADIUS)
            .searchFor(EntityFinder.SearchFor.ALLIES)
            .build();

        for (LivingEntity en : list) {

            int num = ctx.getConfigFor(this)
                .getCalc(ctx.skillGem)
                .getCalculatedValue(ctx.data, ctx.skillGem);

            SpellUtils.heal(this, en, num);

            ParticleEnum.sendToClients(
                en.getBlockPos(), en.world,
                new ParticlePacketData(en.getPos(), ParticleEnum.AOE).radius(RADIUS)
                    .motion(new Vec3d(0, 0, 0))
                    .type(ParticleTypes.COMPOSTER)
                    .amount((int) (55)));

        }
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 5, 12);
        c.set(SC.BASE_VALUE, 3, 6);
        c.set(SC.CAST_TIME_TICKS, 80, 60);
        c.set(SC.COOLDOWN_SECONDS, 60, 30);
        c.set(SC.TIMES_TO_CAST, 4, 4);
        c.set(SC.RADIUS, 2, 3.5F);

        c.setMaxLevel(12);

        return c;
    }

    public static HealingAuraSpell getInstance() {
        return HealingAuraSpell.SingletonHolder.INSTANCE;
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    @Override
    public String GUID() {
        return "healing_aura";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Restore health and magic shield,"));
        list.add(new LiteralText("to everyone around you:"));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.HealingAura;
    }

    private static class SingletonHolder {
        private static final HealingAuraSpell INSTANCE = new HealingAuraSpell();
    }
}

