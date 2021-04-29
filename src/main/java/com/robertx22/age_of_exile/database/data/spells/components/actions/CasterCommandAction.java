package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.COMMAND;

public class CasterCommandAction extends SpellAction {

    public CasterCommandAction() {
        super(Arrays.asList(COMMAND));
    }

    public static ServerCommandSource getCommandSource(Entity entity) {
        return new ServerCommandSource(
            // this doesnt send messages to spam server
            CommandOutput.DUMMY,
            entity.getPos(),
            entity.getRotationClient(),
            entity.world instanceof ServerWorld ? (ServerWorld) entity.world : null,
            4,
            entity.getName()
                .getString(),
            entity.getDisplayName(),
            entity.world.getServer(),
            entity);
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        try {
            String command = data.get(COMMAND);

            Entity entity = ctx.caster;

            ServerCommandSource source = getCommandSource(entity);

            ctx.caster
                .getServer()
                .getCommandManager()
                .execute(source, command);
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

