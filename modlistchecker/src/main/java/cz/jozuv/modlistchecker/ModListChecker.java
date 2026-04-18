package cz.jozuv.modlistchecker;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModListChecker implements ModInitializer {
	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(ModListPayload.TYPE, ModListPayload.CODEC);
		NetworkHandler.registerReceivers();
	}
}