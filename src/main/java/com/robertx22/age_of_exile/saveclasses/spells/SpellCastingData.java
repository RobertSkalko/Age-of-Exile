package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.Database;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class SpellCastingData {

    @Store
    public int castingTicksLeft = 0;

    @Store
    public int castingTicksDone = 0;

    @Store
    public int lastSpellCastTimeInTicks = 0;

    public static Integer selectedSpell = 0; // this is just used on client, so client tells server which spell to cast

    @Store
    public String spellBeingCast = "";

    @Store
    public Boolean casting = false;

    @Store
    public HashMap<String, AuraData> auras = new HashMap<>();

    @Storable
    public static class AuraData {
        @Store
        public boolean active = false;
        @Store
        public int place = 0;
        @Store
        public float mana_reserved = 0;

        public AuraData() {
        }

        public AuraData(boolean active, int place, float mana_reserved) {
            this.active = active;
            this.place = place;
            this.mana_reserved = mana_reserved;
        }
    }

    @Store
    private HashMap<String, SpellData> spellDatas = new HashMap<>();

    public void cancelCast(PlayerEntity player) {
        try {
            SpellCastContext ctx = new SpellCastContext(player, 0, getSpellBeingCast());

            Spell spell = getSpellBeingCast();
            if (spell != null) {
                SpellData data = spellDatas.getOrDefault(spell.GUID(), new SpellData());

                int cd = ctx.spell.getCooldownTicks(ctx);
                data.setCooldown(cd);
            }

            spellBeingCast = "";
            castingTicksLeft = 0;
            lastSpellCastTimeInTicks = 0;
            castingTicksDone = 0;
            this.casting = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isCasting() {
        return casting && Database.Spells()
            .isRegistered(spellBeingCast);
    }

    public void onTimePass(PlayerEntity player, PlayerSpellCap.ISpellsCap spells, int ticks) {

        try {

            if (spellBeingCast != null && spellBeingCast.length() > 0) {
                Spell spell = Database.Spells()
                    .get(spellBeingCast);

                SpellCastContext ctx = new SpellCastContext(player, castingTicksDone, spell);

                if (spell != null && spells != null && Database.Spells()
                    .isRegistered(spell)) {
                    spell.onCastingTick(ctx);
                }

                tryCast(player, spells, ctx);

                castingTicksLeft--;
                castingTicksDone++;

                if (castingTicksLeft < 0) {
                    this.spellBeingCast = "";
                }
            }

            spellDatas.values()
                .forEach(x -> x.tickCooldown(ticks));

        } catch (Exception e) {
            e.printStackTrace();
            this.cancelCast(player);
            // cancel when error, cus this is called on tick, so it doesn't crash servers when 1 spell fails
        }
    }

    public List<String> getSpellsOnCooldown() {
        return spellDatas.entrySet()
            .stream()
            .filter(x -> !x.getValue()
                .cooldownIsReady())
            .map(x -> x.getKey())
            .collect(Collectors.toList());

    }

    public void setToCast(Spell spell, PlayerEntity player) {
        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        this.spellBeingCast = spell.GUID();
        this.castingTicksLeft = ctx.spell.getCastTimeTicks(ctx);
        this.lastSpellCastTimeInTicks = this.castingTicksLeft;
        this.castingTicksDone = 0;
        this.casting = true;
    }

    private void tryCast(PlayerEntity player, PlayerSpellCap.ISpellsCap spells, SpellCastContext ctx) {

        if (!spellBeingCast.isEmpty()) {
            if (castingTicksLeft <= 0) {
                Spell spell = Database.Spells()
                    .get(spellBeingCast);

                int timesToCast = ctx.spell.getConfig().times_to_cast;

                if (timesToCast == 1) {
                    spell.cast(ctx);
                }

                /* // channel spells DESTROY weapon durability lol
                player.getMainHandStack()
                    .damage(1, player, x -> {
                        player.sendToolBreakStatus(player.getActiveHand());
                    });

                 */

                onSpellCast(ctx);

                spellBeingCast = "";

            }
        }

    }

    public Spell getSpellBeingCast() {
        return Database.Spells()
            .get(spellBeingCast);
    }

    public boolean canCast(Spell spell, PlayerEntity player) {

        if (isCasting()) {
            return false;
        }

        if (spell == null) {
            return false;
        }

        SpellData data = getDataBySpell(spell);

        if (data.cooldownIsReady() == false) {
            return false;
        }

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        return spell.canCast(ctx);

    }

    public void onSpellCast(SpellCastContext ctx) {
        SpellData data = spellDatas.getOrDefault(ctx.spell.GUID(), new SpellData());

        int cd = ctx.spell.getCooldownTicks(ctx);

        data.setCooldown(cd);

        if (ctx.caster instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) ctx.caster;
            if (p.isCreative()) {
                if (cd > 20) {
                    data.setCooldown(20);
                }
            }
        }

        spellDatas.put(ctx.spell.GUID(), data);
        this.casting = false;
    }

    public SpellData getDataBySpell(Spell spell) {

        String id = spell.GUID();

        if (spellDatas.containsKey(id) == false) {
            spellDatas.put(id, new SpellData());
        }

        return spellDatas.get(id);

    }

}
