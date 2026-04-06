package cz.jozuv.modlistchecker;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ModListChecker.MODID)
public class ModListChecker {
    public static final String MODID = "modlistchecker";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ModListChecker(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        LOGGER.info("ModListChecker initialized");
    }
}