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

            Set<String> allowedMods = ServerConfig.getAllowedMods();

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