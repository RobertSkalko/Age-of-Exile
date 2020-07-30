package com.robertx22.mine_and_slash.database.data.spells.spell_classes.hunting;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.entities.proj.RangerArrowEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ArrowBarrageSpell extends BaseSpell {

    private ArrowBarrageSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.PROJECTILE;
                }

                @Override
                public SoundEvent sound() {
                    return SoundEvents.ENTITY_ARROW_SHOOT;
                }

                @Override
                public Elements element() {
                    return Elements.Elemental;
                }
            }
                .castingWeapon(CastingWeapon.RANGED)
                .cooldownIfCanceled(true)
                .summonsEntity(world -> new RangerArrowEntity(world)));
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.DEX;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 15, 25);
        c.set(SC.BASE_VALUE, 2, 6);
        c.set(SC.ATTACK_SCALE_VALUE, 0.2F, 0.4F);
        c.set(SC.PROJECTILE_COUNT, 1, 1);
        c.set(SC.SHOOT_SPEED, 1, 1.3F);
        c.set(SC.CAST_TIME_TICKS, 40, 30);
        c.set(SC.COOLDOWN_SECONDS, 30, 25);
        c.set(SC.TIMES_TO_CAST, 4, 6);
        c.set(SC.DURATION_TICKS, 100, 160);

        c.setMaxLevel(16);

        return c;
    }

    public static ArrowBarrageSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "arrow_barrage";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Shoot out many arrows while casting: "));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.ArrowBarrage;
    }

    private static class SingletonHolder {
        private static final ArrowBarrageSpell INSTANCE = new ArrowBarrageSpell();
    }
}
