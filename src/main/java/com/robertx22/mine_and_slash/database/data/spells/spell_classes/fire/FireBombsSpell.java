package com.robertx22.mine_and_slash.database.data.spells.spell_classes.fire;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.entities.proj.FireBombEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
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
import net.minecraft.text.Text;

public class FireBombsSpell extends BaseSpell {

    private FireBombsSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.PROJECTILE;
                }

                @Override
                public SoundEvent sound() {
                    return SoundEvents.BLOCK_FIRE_EXTINGUISH;
                }

                @Override
                public Elements element() {
                    return Elements.Fire;
                }
            }.cooldownIfCanceled(true)
                .summonsEntity(w -> new FireBombEntity(w))
                .setSwingArmOnCast());
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    public static FireBombsSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 10, 15);
        c.set(SC.BASE_VALUE, 5, 20);
        c.set(SC.SHOOT_SPEED, 0.7F, 1F);
        c.set(SC.PROJECTILE_COUNT, 1, 1);
        c.set(SC.CAST_TIME_TICKS, 50, 40);
        c.set(SC.COOLDOWN_SECONDS, 30, 20);
        c.set(SC.DURATION_TICKS, 100, 120);
        c.set(SC.TIMES_TO_CAST, 3, 3);
        c.set(SC.RADIUS, 1.5F, 2.25F);

        c.setMaxLevel(12);

        return c;
    }

    @Override
    public String GUID() {
        return "fire_bombs";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Throw out Fire bombs that explode when near mobs: "));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.FireBomb;
    }

    private static class SingletonHolder {
        private static final FireBombsSpell INSTANCE = new FireBombsSpell();
    }
}
