package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

;

public class SpellCastContext {

    public final LivingEntity caster;
    public final EntityCap.UnitData data;
    public final PlayerSpellCap.ISpellsCap spellsCap;
    public final int ticksInUse;
    public final Spell spell;
    public boolean isLastCastTick;
    public boolean castedThisTick = false;
    public CalculatedSpellData calcData;

    public SkillGemData skillGemData;

    public SpellStatsCalcEffect.CalculatedSpellConfiguration spellConfig;

    private void calcSpellData() {
        this.calcData = CalculatedSpellData.create(caster, spell, spellConfig);
    }

    public SpellCastContext(LivingEntity caster, int ticksInUse, CalculatedSpellData spell) {
        this(caster, ticksInUse, spell.getSpell());
    }

    public SpellCastContext(LivingEntity caster, int ticksInUse, Spell spell) {
        this.caster = caster;
        this.ticksInUse = ticksInUse;

        this.spell = spell;

        this.data = Load.Unit(caster);

        SpellStatsCalcEffect effect = new SpellStatsCalcEffect(caster, spell.GUID());
        effect.Activate();
        this.spellConfig = effect.data;

        if (caster instanceof PlayerEntity) {
            this.spellsCap = Load.spells((PlayerEntity) caster);

            try {

                Optional<SkillGemData> opt = spellsCap.getSkillGemData().stacks.stream()
                    .map(x -> {
                        return SkillGemData.fromStack(x);
                    })
                    .filter(x -> {
                        return x != null && x.getSkillGem() != null && x.getSkillGem().spell_id.equals(spell.GUID());
                    })
                    .findAny();

                if (caster.world.isClient) {
                    if (opt.isPresent()) {
                        skillGemData = opt.get();
                    } else {
                        this.skillGemData = new SkillGemData();
                    }
                } else {
                    skillGemData = opt.get();
                }

            } catch (Exception e) {
                e.printStackTrace();
                this.skillGemData = new SkillGemData();
            }

        } else {
            this.spellsCap = new PlayerSpellCap.DefaultImpl(caster);
            this.skillGemData = new SkillGemData();
            skillGemData.lvl = data.getLevel();
        }

        calcSpellData();

        if (spell != null) {
            int castTicks = (int) this.calcData.getSpell()
                .getConfig()
                .getCastTimeTicks();
            this.isLastCastTick = castTicks == ticksInUse;
        }

    }
}
