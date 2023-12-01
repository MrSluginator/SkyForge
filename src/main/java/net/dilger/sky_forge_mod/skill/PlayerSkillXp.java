package net.dilger.sky_forge_mod.skill;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Map;

@AutoRegisterCapability
public class PlayerSkillXp {
    private final Map<SKILL_TYPE, Long> skillsXpMap = Maps.newLinkedHashMap();

    public PlayerSkillXp(){}

    public Map<SKILL_TYPE, Long> getSkillsXpMap() {return this.skillsXpMap;}
    public long getSkillXp(SKILL_TYPE skill_type) {
        return  this.skillsXpMap.get(skill_type);
    }

    final int LINEAR_GROWTH = 5;
    final double EXPONENTIAL_GROWTH = 0.5;
    public int getSkillLevel(SKILL_TYPE skill_type) {
        return (int) Math.floor(Math.log(getSkillXp(skill_type) + 1)*Math.pow(getSkillXp(skill_type), EXPONENTIAL_GROWTH)/ LINEAR_GROWTH);
    }

    public void addSkillXp(long skillXp, SKILL_TYPE skill_type) {

        this.skillsXpMap.put(skill_type, this.skillsXpMap.get(skill_type) + skillXp);
    }

    public void copyFrom(PlayerSkillXp source) {
        for (SKILL_TYPE skill: SKILL_TYPE.values()) {
            this.skillsXpMap.put(skill, source.skillsXpMap.get(skill));
        }
    }

    public void saveNBTData(CompoundTag nbt) {
        for (SKILL_TYPE skill : SKILL_TYPE.values()) {
            if (this.skillsXpMap.containsKey(skill)) {
                // creating a simple key for resource location: affectPlayerData.offense
                nbt.putLong("affectPlayerData." + skill.toString().toLowerCase(), this.skillsXpMap.get(skill));
            }
            else {
                nbt.putLong("affectPlayerData." + skill.toString().toLowerCase(), 0);
            }
        }


    }

    public void loadNBTData(CompoundTag nbt) {
        for (SKILL_TYPE skill: SKILL_TYPE.values()) {
            this.skillsXpMap.put(skill, nbt.getLong("affectPlayerData." + skill.toString().toLowerCase()));
        }
    }

}
