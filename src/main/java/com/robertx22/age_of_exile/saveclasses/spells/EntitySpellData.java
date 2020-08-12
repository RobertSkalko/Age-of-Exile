package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.EntityCalcSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.UUID;

@Storable
public class EntitySpellData {

    @Store
    public float charge = 1;

    @Store
    public boolean activated = false;

    @Store
    private String casterID;

    private LivingEntity caster;

    @Store
    public int lifeInTicks = 100;

    @Store
    public int ticksExisted = 0;

    @Store
    public EntityCalcSpellConfigs configs;

    @Store
    public SkillGemData skillgem;

    @Store
    private String spellGUID = "";

    private BaseSpell spell;

    public Elements getElement() {
        if (this.getSpell() != null) {
            return getSpell().getElement();
        }
        return Elements.Physical;
    }

    public int getRemainingLifeTicks() {
        return lifeInTicks - ticksExisted;
    }

    public EntitySpellData(SkillGemData skillgem, LivingEntity caster, EntityCalcSpellConfigs config) {
        this.skillgem = skillgem;
        this.spellGUID = skillgem.getSpell()
            .GUID();
        this.casterID = caster.getUuid()
            .toString();

        this.lifeInTicks = config.get(SC.DURATION_TICKS)
            .intValue();
        this.configs = config;
    }

    private UUID getCasterUUID() {
        return UUID.fromString(casterID);
    }

    public EntitySpellData() {
    }

    public BaseSpell getSpell() {
        if (spell == null) {
            spell = SlashRegistry.Spells()
                .get(spellGUID);
        }
        return spell;
    }

    public LivingEntity getCaster(World world) {
        if (caster == null) {
            caster = Utilities.getLivingEntityByUUID(world, getCasterUUID());
        }
        return caster;

    }
}
