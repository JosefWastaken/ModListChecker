package cz.jozuv.modlistchecker;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {

    @SubscribeEvent
    public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        ModListChecker.LOGGER.info("Klient se pripojil k serveru, posilam modlist...");
        ModListCheckerClient.sendTestPayload();
    }
}