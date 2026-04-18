package cz.jozuv.modlistchecker;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public final class NetworkHandler {

    private NetworkHandler() {
    }

    public static void registerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(ModListPayload.TYPE, (payload, context) -> {
            ServerPlayerEntity player = context.player();

            Set<String> clientMods = new TreeSet<>(payload.mods());

            System.out.println("[ModListChecker] Received mod list from " + player.getName().getString() + ": " + clientMods);

            Set<String> allowedMods = new TreeSet<>(Set.of(
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
            ));

            if (!clientMods.equals(allowedMods)) {
                Set<String> missing = new TreeSet<>(allowedMods);
                missing.removeAll(clientMods);

                Set<String> extra = new TreeSet<>(clientMods);
                extra.removeAll(allowedMods);

                StringBuilder msg = new StringBuilder("Mod list does not match server.");

                if (!missing.isEmpty() || !extra.isEmpty()) {
                    String message = "Mas jine mody nez jsou povolene.\n"
                            + "Chybejici: " + missing + "\n"
                            + "Nepovolene: " + extra;

                    player.networkHandler.disconnect(Text.literal(message));
                }

                player.networkHandler.disconnect(Text.literal(msg.toString()));
            }
        });
    }
}