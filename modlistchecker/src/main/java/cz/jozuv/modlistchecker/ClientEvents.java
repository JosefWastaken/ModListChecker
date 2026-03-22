package cz.jozuv.modlistchecker;

import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

public class ClientEvents {

    public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        ModListChecker.LOGGER.info("Klient se pripojil k serveru, posilam test payload...");
        ModListCheckerClient.sendTestPayload();
    }
}