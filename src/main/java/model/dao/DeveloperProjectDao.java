package model.dao;

public class DeveloperProjectDao {
    private long developerId;
    private long projectId;

    public DeveloperProjectDao(long developerId, long projectId){
        this.developerId=developerId;
        this.projectId=projectId;
    }

    public DeveloperProjectDao(){

    }

    public long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(long developerId) {
        this.developerId = developerId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
