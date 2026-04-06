package cz.jozuv.modlistchecker;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ServerConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ALLOWED_MODS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        ALLOWED_MODS = builder
                .comment("Seznam povolenych mod ID")
                .defineListAllowEmpty(
                        "allowedMods",
                        List.of("minecraft", "forge", "modlistchecker"),
                        obj -> obj instanceof String
                );

        SPEC = builder.build();
    }
}