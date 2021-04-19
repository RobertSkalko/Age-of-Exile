package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class AttributeStat extends DatapackStat {

    public static String SER_ID = "vanilla_attribute_stat_ser";

    transient String locname;

    public UUID uuid;
    public String attributeId;
    public EntityAttribute attribute;

    public AttributeStat(String id, String locname, UUID uuid, EntityAttribute attribute, boolean perc) {
        super(SER_ID);
        this.id = id;
        this.locname = locname;
        this.uuid = uuid;
        this.attributeId = Registry.ATTRIBUTE.getId(attribute)
            .toString();
        this.attribute = attribute;
        this.is_percent = perc;
    }

    @Override
    public String locDescForLangFile() {
        return "Increase vanilla attribute.";
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    public void addToEntity(LivingEntity en, StatData data) {

        float val = data.getAverageValue();

        EntityAttributeModifier.Operation operation = EntityAttributeModifier.Operation.ADDITION;

        if (IsPercent()) {
            operation = EntityAttributeModifier.Operation.MULTIPLY_TOTAL;

            val = (00F + val) / 100F;
        }

        EntityAttributeModifier mod = new EntityAttributeModifier(
            uuid,
            attributeId,
            val,
            operation
        );

        EntityAttributeInstance atri = en.getAttributeInstance(attribute);

        if (atri.hasModifier(mod)) {
            atri.removeModifier(mod); // KEEP THIS OR UPDATE WONT MAKE HP CORRECT!!!
        }
        atri.addPersistentModifier(mod);

    }
}
