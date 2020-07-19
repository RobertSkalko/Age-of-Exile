package com.robertx22.mine_and_slash.config.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.saveclasses.ListStringData;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SerializationUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.SyncConfigToClientPacket;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public interface ISerializedConfig<T extends ISlashRegistryInit> {

    public abstract String fileName();

    public default String getPath() {
        return folder() + "/" + fileName();
    }

    T getDefaultObject();

    default String getJsonFromFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    T loadFromString(String string);

    default List<String> getAllJsons() {
        return Arrays.asList(getJsonFromFile(getPath()));
    }

    ConfigRegister.Config getConfigType();

    // call on client too cus dedicated server dist executor doesnt work on singleplayer "servers"
    default void loadOnServer() {

        List<String> jsons = getAllJsons();

        ConfigRegister.SAVED_JSONS.put(getConfigType(), jsons);

        getAllJsons().forEach(x -> {
            loadFromString(x).registerAll();
        });

    }

    default boolean isSyncedToClient() {
        return true;
    }

    default void sendToClient(ServerPlayerEntity player) {
        if (isSyncedToClient()) {
            List<String> configs = ConfigRegister.SAVED_JSONS.get(getConfigType());

            SyncConfigToClientPacket pkt = new SyncConfigToClientPacket(new ListStringData(configs), getConfigType());

            Packets.sendToClient(player, pkt);
        }
    }

    default String folder() {
        return SerializationUtils.CONFIG_PATH;
    }

    default void autoFixProblems() {

    }

    default void generateIfEmpty() {
        Gson gson = new GsonBuilder().setPrettyPrinting()
            .create();
        String json = gson.toJson(getDefaultObject());
        SerializationUtils.makeFileAndDirAndWrite(folder(), fileName(), json);
    }

    default void loadFromJsons(List<String> list) {
        list.forEach(x -> loadFromString(x).registerAll());
    }
}
