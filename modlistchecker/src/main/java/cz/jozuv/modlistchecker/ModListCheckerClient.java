package cz.jozuv.modlistchecker;

import net.minecraftforge.fml.loading.FMLLoader;

import java.util.List;

public class ModListCheckerClient {

    public static void sendTestPayload() {
        List<String> mods = FMLLoader.getLoadingModList().getMods().stream()
                .map(mod -> mod.getModId())
                .sorted()
                .toList();

        ModListChecker.LOGGER.info("Client odesila modlist: {}", mods);

        NetworkHandler.sendToServer(new ModListPayload(mods));
        ModListChecker.LOGGER.info("Client odeslal modlist payload.");
    }
}