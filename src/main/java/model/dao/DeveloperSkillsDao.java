package model.dao;

public class DeveloperSkillsDao {
    private long developerId;
    private long skillId;

    public DeveloperSkillsDao(long developerId, long skillId){
        this.developerId=developerId;
        this.skillId=skillId;
    }

    public DeveloperSkillsDao(){

    }

    public long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(long developerId) {
        this.developerId = developerId;
    }

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }
}
