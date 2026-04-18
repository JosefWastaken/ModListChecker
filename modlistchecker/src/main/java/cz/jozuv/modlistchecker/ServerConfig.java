package cz.jozuv.modlistchecker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class ServerConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("modlistchecker.json");

    private static ConfigData config = new ConfigData();

    private ServerConfig() {
    }

    public static void load() {
        if (Files.notExists(CONFIG_PATH)) {
            saveDefaultConfig();
            config = new ConfigData();
            return;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            ConfigData loaded = GSON.fromJson(reader, ConfigData.class);

            if (loaded == null || loaded.allowedMods == null || loaded.allowedMods.isEmpty()) {
                System.out.println("[ModListChecker] Config was empty or invalid, using default config.");
                config = new ConfigData();
                save();
                return;
            }

            config = loaded;
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("[ModListChecker] Failed to read config, using default config.");
            e.printStackTrace();
            config = new ConfigData();
            save();
        }
    }

    public static Set<String> getAllowedMods() {
        return new TreeSet<>(config.allowedMods);
    }

    private static void saveDefaultConfig() {
        config = new ConfigData();
        save();
    }

    private static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());

            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException e) {
            System.out.println("[ModListChecker] Failed to save config.");
            e.printStackTrace();
        }
    }

    private static final class ConfigData {
        List<String> allowedMods = List.of(
                "fabric-api",
                "fabric-api-base",
                "fabric-api-lookup-api-v1",
                "fabric-biome-api-v1",
                "fabric-block-api-v1",
                "fabric-block-view-api-v2",
                "fabric-blockrenderlayer-v1",
                "fabric-client-tags-api-v1",
                "fabric-command-api-v1",
                "fabric-command-api-v2",
                "fabric-commands-v0",
                "fabric-content-registries-v0",
                "fabric-convention-tags-v1",
                "fabric-convention-tags-v2",
                "fabric-crash-report-info-v1",
                "fabric-data-attachment-api-v1",
                "fabric-data-generation-api-v1",
                "fabric-dimensions-v1",
                "fabric-entity-events-v1",
                "fabric-events-interaction-v0",
                "fabric-game-rule-api-v1",
                "fabric-item-api-v1",
                "fabric-item-group-api-v1",
                "fabric-key-binding-api-v1",
                "fabric-keybindings-v0",
                "fabric-lifecycle-events-v1",
                "fabric-loot-api-v2",
                "fabric-loot-api-v3",
                "fabric-message-api-v1",
                "fabric-model-loading-api-v1",
                "fabric-networking-api-v1",
                "fabric-object-builder-api-v1",
                "fabric-particles-v1",
                "fabric-recipe-api-v1",
                "fabric-registry-sync-v0",
                "fabric-renderer-api-v1",
                "fabric-renderer-indigo",
                "fabric-renderer-registries-v1",
                "fabric-rendering-data-attachment-v1",
                "fabric-rendering-fluids-v1",
                "fabric-rendering-v0",
                "fabric-rendering-v1",
                "fabric-resource-conditions-api-v1",
                "fabric-resource-loader-v0",
                "fabric-screen-api-v1",
                "fabric-screen-handler-api-v1",
                "fabric-sound-api-v1",
                "fabric-transfer-api-v1",
                "fabric-transitive-access-wideners-v1",
                "fabricloader",
                "java",
                "minecraft",
                "mixinextras",
                "modlistchecker"
        );
    }
}