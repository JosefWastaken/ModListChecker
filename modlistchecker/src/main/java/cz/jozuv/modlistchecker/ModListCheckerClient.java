package cz.jozuv.modlistchecker;

import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.List;

public class ModListCheckerClient {

    public static void sendTestPayload() {
        List<String> mods = LoadingModList.get().getMods().stream()
                .map(mod -> mod.getModId())
                .sorted()
                .toList();

        ModListChecker.LOGGER.info("Client odesila modlist: {}", mods);

        ClientPacketDistributor.sendToServer(new ModListPayload(mods));
        ModListChecker.LOGGER.info("Client odeslal modlist payload.");
    }
}