package cz.jozuv.modlistchecker;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ModListChecker.MODID)
public class ModListChecker {
    public static final String MODID = "modlistchecker";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModListChecker(FMLJavaModLoadingContext context) {
        context.getModEventBus().addListener(this::commonSetup);
        context.registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC, "modlistchecker-server.toml");

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.register(ClientEvents.class);
        });

        LOGGER.info("ModListChecker initialized");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NetworkHandler.register(event);
    }
}