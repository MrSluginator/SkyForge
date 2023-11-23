package net.dilger.sky_forge_mod.skill;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Map;

@AutoRegisterCapability
public class PlayerSkillXp {
    private final Map<SKILL_TYPE, Long> skillsXpMap = Maps.newLinkedHashMap();

    public PlayerSkillXp(){
        for (SKILL_TYPE skill_type: SKILL_TYPE.values()){
            getSkillsXpMap().put(skill_type,0L);
        }
    }

    public Map<SKILL_TYPE, Long> getSkillsXpMap() {return this.skillsXpMap;}
    public long getSkillXp(SKILL_TYPE skill_type) {
        return  this.skillsXpMap.get(skill_type);
    }

    public void addSkillXp(long skillXp, SKILL_TYPE skill_type) {

        this.skillsXpMap.put(skill_type, this.skillsXpMap.get(skill_type) + skillXp);
    }

    public void subSkillXp(long skillXp, SKILL_TYPE skill_type) {
        addSkillXp(-skillXp, skill_type);
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
