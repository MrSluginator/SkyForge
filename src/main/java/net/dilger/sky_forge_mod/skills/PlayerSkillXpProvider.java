package net.dilger.sky_forge_mod.skills;

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

public class PlayerSkillXpProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSkillXp> PLAYER_SKILL_XP = CapabilityManager.get(new CapabilityToken<PlayerSkillXp>() {});

    private PlayerSkillXp skillXp = null;
    private final LazyOptional<PlayerSkillXp> optional = LazyOptional.of(this::createPlayerSkillXp);

    private PlayerSkillXp createPlayerSkillXp() {
        if(this.skillXp == null) {
            this.skillXp = new PlayerSkillXp();
        }

        return this.skillXp;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_SKILL_XP) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSkillXp().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSkillXp().loadNBTData(nbt);
    }
}
