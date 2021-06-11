package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Storable
public class ChargeData {
    @Store
    private HashMap<String, Integer> charges = new HashMap<>();

    @Store
    private HashMap<String, Integer> charge_regen = new HashMap<>();

    public int getCurrentTicksChargingOf(String id) {
        return charge_regen.getOrDefault(id, 0);
    }

    public boolean hasCharge(String id) {
        return charges.getOrDefault(id, 0) > 0;
    }

    public void spendCharge(PlayerEntity player, String id) {
        charges.put(id, MathHelper.clamp(charges.getOrDefault(id, 0) - 1, 0, 100000));

        if (!player.world.isClient) {
            Load.spells(player)
                .syncToClient(player);
        }
    }

    public int getCharges(String id) {
        return charges.getOrDefault(id, 0);
    }

    public void addCharge(String id, Spell spell) {
        int charge = MathHelper.clamp(charges.getOrDefault(id, 0) + 1, 0, spell.config.charges);
        charges.put(id, charge);
    }

    public void onTicks(PlayerEntity player, int ticks) {

        if (player.world.isClient) {
            return;
        }

        EntitySpellCap.ISpellsCap sdata = Load.spells(player);

        List<String> chargesadded = new ArrayList<>(); // no duplicate charge regen

        for (SkillGemData x : sdata.getSkillGemData().gems) {
            if (x != null && x.getSkillGem() != null) {
                if (Database.Spells()
                    .isRegistered(x.getSkillGem().spell_id)) {
                    Spell s = Database.Spells()
                        .get(x.getSkillGem().spell_id);

                    String id = s.config.charge_name;

                    if (getCharges(id) >= s.config.charges) {
                        continue;
                    }

                    if (!chargesadded.contains(id)) {

                        if (s.config.charges > 0) {

                            chargesadded.add(id);

                            charge_regen.put(s.config.charge_name, ticks + charge_regen.getOrDefault(s.config.charge_name, 0));

                            if (charge_regen.get(id) >= s.config.charge_regen) {
                                charge_regen.put(id, 0);
                                addCharge(id, s);

                                Load.spells(player)
                                    .syncToClient(player);
                            }

                        }

                    }
                }
            }

        }
    }
}
