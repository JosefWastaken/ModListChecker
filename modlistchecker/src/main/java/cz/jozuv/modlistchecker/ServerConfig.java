package cz.jozuv.modlistchecker;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ServerConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ALLOWED_MODS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        ALLOWED_MODS = builder
                .comment("Seznam povolenych mod ID")
                .defineListAllowEmpty(
                        "allowedMods",
                        List.of(
                                "minecraft", "neoforge", "modlistchecker"
                        ),
                        obj -> obj instanceof String
                );

        SPEC = builder.build();
    }
}