package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;

;

public class SpellCastContext {

    public final LivingEntity caster;
    public final EntityCap.UnitData data;
    public final EntitySpellCap.ISpellsCap spellsCap;
    public final int ticksInUse;
    public final Spell spell;
    public boolean isLastCastTick;
    public boolean castedThisTick = false;
    public SpellStatsCalculationEvent event;
    public EntitySavedSpellData calcData;
    public SkillGemData skillGemData;

    public SpellCastContext(LivingEntity caster, int ticksInUse, Spell spell) {
        this(null, caster, ticksInUse, spell);
    }

    public SpellCastContext(@Nullable SkillGemData gem, LivingEntity caster, int ticksInUse, Spell spell) {
        this.caster = caster;
        this.ticksInUse = ticksInUse;
        this.spell = spell;
        this.data = Load.Unit(caster);

        Objects.requireNonNull(spell);

        if (caster instanceof PlayerEntity) {

            this.spellsCap = Load.spells(caster);

            try {

                if (gem == null) {

                    skillGemData = this.spellsCap.getSkillGemData()
                        .getSkillGemOfSpell(spell)
                        .orElseGet(() -> new SkillGemData());

                } else {
                    skillGemData = gem;
                }

            } catch (Exception e) {
                e.printStackTrace();

                if (gem == null) {
                    this.skillGemData = new SkillGemData();
                } else {
                    skillGemData = gem;
                }
            }

        } else {
            this.spellsCap = new EntitySpellCap.SpellCap(caster);
            this.skillGemData = new SkillGemData();
            skillGemData.lvl = data.getLevel();
        }

        this.calcData = EntitySavedSpellData.create(skillGemData.lvl, caster, spell);

        this.event = new SpellStatsCalculationEvent(this.calcData, skillGemData, caster, spell.GUID());
        event.Activate();

        int castTicks = (int) event.data.getNumber(EventData.CAST_TICKS).number;
        this.isLastCastTick = castTicks == ticksInUse;

    }
}
