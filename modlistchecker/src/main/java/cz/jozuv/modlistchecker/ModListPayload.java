package cz.jozuv.modlistchecker;

import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ModListPayload {

    private final List<String> mods;

    public ModListPayload(List<String> mods) {
        this.mods = mods;
    }

    public List<String> mods() {
        return mods;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeCollection(mods, FriendlyByteBuf::writeUtf);
    }

    public static ModListPayload decode(FriendlyByteBuf buf) {
        List<String> mods = buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf);
        return new ModListPayload(mods);
    }
}