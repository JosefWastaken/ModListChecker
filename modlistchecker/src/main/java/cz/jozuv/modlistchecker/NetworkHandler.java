package cz.jozuv.modlistchecker;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Set;
import java.util.TreeSet;

public class NetworkHandler {

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                ModListPayload.TYPE,
                ModListPayload.STREAM_CODEC,
                (payload, context) -> {
                    if (!(context.player() instanceof ServerPlayer serverPlayer)) {
                        return;
                    }

                    if (!serverPlayer.server.isDedicatedServer()) {
                        ModListChecker.LOGGER.info("Integrated server detected, preskakuji kontrolu modlistu.");
                        return;
                    }

                    Set<String> clientMods = new TreeSet<>(payload.mods());

                    Set<String> allowedMods = new TreeSet<>(
                            ServerConfig.ALLOWED_MODS.get().stream()
                                    .map(String::valueOf)
                                    .toList()
                    );

                    Set<String> missing = new TreeSet<>(allowedMods);
                    missing.removeAll(clientMods);

                    Set<String> extra = new TreeSet<>(clientMods);
                    extra.removeAll(allowedMods);

                    ModListChecker.LOGGER.info("=== KONTROLA POVOLENYCH MODU ===");
                    ModListChecker.LOGGER.info("Client mods: {}", clientMods);
                    ModListChecker.LOGGER.info("Allowed mods: {}", allowedMods);
                    ModListChecker.LOGGER.info("Chybejici mody: {}", missing);
                    ModListChecker.LOGGER.info("Nepovolene mody: {}", extra);
                    ModListChecker.LOGGER.info("================================");

                    if (!missing.isEmpty() || !extra.isEmpty()) {
                        String message = "Mas jine mody nez jsou povolene.\n"
                                + "Chybejici: " + missing + "\n"
                                + "Nepovolene: " + extra;

                        ModListChecker.LOGGER.warn("Hrac bude odpojen: {}", message);

                        context.enqueueWork(() -> {
                            context.disconnect(Component.literal(message));
                        });
                    }
                }
        );
    }
}