package com.robertx22.mine_and_slash.database.data.spells.spell_classes.hunting;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.entities.proj.RangerArrowEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellPredicates;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class MultiShotSpell extends BaseSpell {

    private MultiShotSpell() {
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
            }.cooldownIfCanceled(true)
                .summonsEntity(w -> new RangerArrowEntity(w))
                .addCastRequirement(SpellPredicates.REQUIRE_SHOOTABLE));
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.DEX;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 5, 12);
        c.set(SC.BASE_VALUE, 4, 12);
        c.set(SC.ATTACK_SCALE_VALUE, 0.2F, 0.75F);
        c.set(SC.SHOOT_SPEED, 1F, 1.5F);
        c.set(SC.PROJECTILE_COUNT, 3, 6);
        c.set(SC.CAST_TIME_TICKS, 20, 10);
        c.set(SC.COOLDOWN_SECONDS, 10, 5);
        c.set(SC.DURATION_TICKS, 100, 160);

        c.setMaxLevel(16);

        return c;
    }

    public static MultiShotSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "multi_shot";
    }

    @Override
    public List<MutableText> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<MutableText> list = new ArrayList<>();

        list.add(new LiteralText("Shoots multiple arrows in an arc: "));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.WideShot;
    }

    private static class SingletonHolder {
        private static final MultiShotSpell INSTANCE = new MultiShotSpell();
    }
}

