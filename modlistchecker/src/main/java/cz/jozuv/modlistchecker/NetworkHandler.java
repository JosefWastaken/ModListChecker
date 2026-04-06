package cz.jozuv.modlistchecker;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import java.util.Set;
import java.util.TreeSet;

public class NetworkHandler {

    private static SimpleChannel CHANNEL;
    private static int packetId = 0;

    public static void register(FMLCommonSetupEvent event) {
        CHANNEL = ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(ModListChecker.MODID, "main"))
                .simpleChannel();

        CHANNEL.messageBuilder(ModListPayload.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ModListPayload::encode)
                .decoder(ModListPayload::decode)
                .consumerMainThread((payload, context) -> {
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

                        if (context.getSender() != null) {
                            context.getSender().connection.disconnect(Component.literal(message));
                        }
                    }
                })
                .add();
    }

    public static void sendToServer(ModListPayload payload) {
        if (CHANNEL == null) {
            ModListChecker.LOGGER.error("Network channel neni inicializovany.");
            return;
        }

        CHANNEL.send(payload, PacketDistributor.SERVER.noArg());
    }
}