package cz.jozuv.modlistchecker;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record ModListPayload(List<String> mods) implements CustomPayload {
    public static final Identifier ID = Identifier.of("modlistchecker", "modlist");
    public static final CustomPayload.Id<ModListPayload> TYPE = new CustomPayload.Id<>(ID);

    public static final PacketCodec<PacketByteBuf, ModListPayload> CODEC = PacketCodec.of(
            (ModListPayload payload, PacketByteBuf buf) -> {
                buf.writeVarInt(payload.mods().size());
                for (String mod : payload.mods()) {
                    buf.writeString(mod);
                }
            },
            (PacketByteBuf buf) -> {
                int size = buf.readVarInt();
                List<String> mods = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    mods.add(buf.readString());
                }
                return new ModListPayload(mods);
            }
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return TYPE;
    }
}