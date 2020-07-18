package com.robertx22.mine_and_slash.uncommon.localization;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Chats implements IAutoLocName {

    Dev_tools_enabled_contact_the_author("Devs tools enabled, if you see this please contact the author of Mine and Slash [robertx22], he forgot to disable them!"),

    Not_enough_time("Not enough time"),

    A_Piece_of_gear_is_too_high_level_for_you("You Don't meet requirements of a piece of current gear."),

    No_targets_found("No targets found"),

    You_are_too_low_level("You are too low level"),

    Cooldown_not_met("Cooldown not met"),

    Weapon_durability_is_too_low("Weapon durability is too low"),

    You_have_leveled_up("You have leveled up"),

    Teleport_canceled_due_to_movement("Teleport canceled due to movement"),

    Distance_too_high_to_teleport("Distance too high to teleport"),

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
        return Ref.MODID + ".chat." + formattedGUID();
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
