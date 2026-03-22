package cz.jozuv.modlistchecker;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(ModListChecker.MODID)
public class ModListChecker {
    public static final String MODID = "modlistchecker";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModListChecker(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("ModListChecker se nacetl!");

        // Mod bus -> registrace payloadu
        modEventBus.addListener(NetworkHandler::register);

        // Game bus -> client-only event po pripojeni
        if (FMLEnvironment.getDist().isClient()) {
            NeoForge.EVENT_BUS.addListener(ClientEvents::onClientLogin);
        }
    }
}