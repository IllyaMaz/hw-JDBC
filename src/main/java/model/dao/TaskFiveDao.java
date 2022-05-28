package model.dao;

public class TaskFiveDao {
    private String nameProject;
    private String deadline;
    private int countDevelopers;

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getCountDevelopers() {
        return countDevelopers;
    }

    public void setCountDevelopers(int countDevelopers) {
        this.countDevelopers = countDevelopers;
    }

    @Override
    public String toString() {
        return "\nTaskFiveDao{" +
                "nameProject='" + nameProject + '\'' +
                ", deadline='" + deadline + '\'' +
                ", countDevelopers=" + countDevelopers +
                '}';
    }
}
