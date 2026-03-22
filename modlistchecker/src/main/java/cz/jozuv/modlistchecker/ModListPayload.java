package cz.jozuv.modlistchecker;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public record ModListPayload(List<String> mods) implements CustomPacketPayload {

    public static final Type<ModListPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(ModListChecker.MODID, "mod_list_payload"));

    public static final StreamCodec<ByteBuf, ModListPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.STRING_UTF8),
                    ModListPayload::mods,
                    ModListPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}