package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class OnScrollHotbar {

    public static void clickSlot(double scrollAmount, CallbackInfo ci) {

        if (KeybindsRegister.CHOOSE_SPELL_KEY.isPressed()) {

            if (scrollAmount > 0.0D) {
                scrollAmount = 1.0D;
            }

            if (scrollAmount < 0.0D) {
                scrollAmount = -1.0D;
            }

            SpellCastingData.selectedSpell -= (int) scrollAmount;

            if (SpellCastingData.selectedSpell > 8) {
                SpellCastingData.selectedSpell = 0;
            }
            if (SpellCastingData.selectedSpell < 0) {
                SpellCastingData.selectedSpell = 8;
            }

            ci.cancel();

        }
    }
}
