package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Chats implements IAutoLocName {

    Dev_tools_enabled_contact_the_author("Devs tools enabled, if you see this please contact the author of Age of Exile [robertx22], he forgot to disable them!"),

    Not_enough_time("Not enough time"),

    No_targets_found("No targets found"),

    You_are_too_low_level("You are too low level"),

    Cooldown_not_met("Cooldown not met"),

    Weapon_durability_is_too_low("Weapon durability is too low"),

    You_have_leveled_up("You have leveled up"),

    Not_enough_experience("Not enough experience"),

    Time_ran_out_due_to_Permadeath("Time ran out due to Permadeath"),

    Remaining_Map_Time_is("Remaining Map Time is"),

    Can_not_go_over_maximum_level("Can not go over maximum level");

    private String localization = "";

    Chats(String str) {
        this.localization = str;

    }

    @Override
    public IAutoLocName.AutoLocGroup locNameGroup() {
        return AutoLocGroup.Chat_Messages;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".chat." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return localization;
    }

    @Override
    public String GUID() {
        return this.name()
            .toLowerCase(Locale.ROOT);
    }
}
