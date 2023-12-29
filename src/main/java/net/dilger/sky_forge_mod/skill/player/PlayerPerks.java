package net.dilger.sky_forge_mod.skill.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@AutoRegisterCapability
public class PlayerPerks {

    private byte[] perks;

    public PlayerPerks(){
        perks = new byte[1];
    }

    public byte[] getPerks() {
        return this.perks;
    }

    public void addPerk(byte talent){

        ByteArrayOutputStream writenPerks = new ByteArrayOutputStream();

        try {
            writenPerks.write(this.perks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writenPerks.write(talent);

        this.perks = writenPerks.toByteArray();
    }

    public void copyFrom(PlayerPerks source) {
        this.perks = source.getPerks();
    }

    public void saveNBTData(CompoundTag nbt) {

        nbt.putByteArray("perks", perks);
    }

    public void loadNBTData(CompoundTag nbt) {

        this.perks = nbt.getByteArray("perks");

    }

}
