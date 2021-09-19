package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.utils.CommandUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.COMMAND;

public class CasterCommandAction extends SpellAction {

    public CasterCommandAction() {
        super(Arrays.asList(COMMAND));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        try {

            if (ctx.world.isClientSide) {
                return;
            }

            String command = data.get(COMMAND);

            Entity entity = ctx.caster;

            CommandSource source = CommandUtils.getCommandSource(entity);

            ctx.caster
                .getServer()
                .getCommands()
                .performCommand(source, command);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MapHolder create(String command) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(COMMAND, command);
        return d;
    }

    @Override
    public String GUID() {
        return "caster_command";
    }
}

