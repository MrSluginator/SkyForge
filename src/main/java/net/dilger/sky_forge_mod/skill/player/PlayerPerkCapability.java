package net.dilger.sky_forge_mod.skill.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerPerkCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerPerks> PLAYER_TALENTS = CapabilityManager.get(new CapabilityToken<>() {});

    private PlayerPerks talents = null;
    private final LazyOptional<PlayerPerks> optional = LazyOptional.of(this::createPlayerTalents);

    private PlayerPerks createPlayerTalents() {
        if(this.talents == null) {
            this.talents = new PlayerPerks();
        }
        return this.talents;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_TALENTS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerTalents().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerTalents().loadNBTData(nbt);
    }
}
