package cz.jozuv.modlistchecker.client;

import cz.jozuv.modlistchecker.ModListPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class ModListCheckerClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {

			// Oprava: integrated server / singleplayer ignorovat
			if (client.getServer() != null) {
				return;
			}

			// Neposílej packet, pokud ho server neumí
			if (!ClientPlayNetworking.canSend(ModListPayload.TYPE)) {
				return;
			}

			List<String> mods = FabricLoader.getInstance()
					.getAllMods()
					.stream()
					.map(mod -> mod.getMetadata().getId())
					.sorted()
					.toList();

			System.out.println("[ModListChecker] Sending mod list: " + mods);
			ClientPlayNetworking.send(new ModListPayload(mods));
		});
	}
}