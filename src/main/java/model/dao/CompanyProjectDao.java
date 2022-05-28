package model.dao;

public class CompanyProjectDao {
    private long companyId;
    private long projectId;

    public CompanyProjectDao(long companyId,long projectId){
        this.companyId=companyId;
        this.projectId=projectId;
    }

    public CompanyProjectDao(){

    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
