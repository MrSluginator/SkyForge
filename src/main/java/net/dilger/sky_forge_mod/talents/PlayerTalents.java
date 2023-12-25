package net.dilger.sky_forge_mod.talents;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@AutoRegisterCapability
public class PlayerTalents {

    private byte[] talents;

    public PlayerTalents(){
        talents = new byte[1];
    }

    public byte[] getTalents() {
        return this.talents;
    }

    public void addTalent(byte talent){

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            output.write(this.talents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        output.write(talent);

        this.talents = output.toByteArray();
    }

    public void copyFrom(PlayerTalents source) {
        this.talents = source.getTalents();
    }

    public void saveNBTData(CompoundTag nbt) {

        nbt.putByteArray("TALENTS", talents);
    }

    public void loadNBTData(CompoundTag nbt) {

        this.talents = nbt.getByteArray("TALENTS");

    }

}
