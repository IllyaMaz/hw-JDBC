import config.DBMC;
import dl.TaskHomeWorkRepository;
import model.dao.DevelopersDao;
import model.dao.ProjectsDao;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
        DevelopersDao developersDao = new DevelopersDao(1L,"Paule",20,"male",20000);
        DBMC dbConnection = new DBMC("localhost",5432,"postgres","VisaGold1234",
                "Goit_task_1");
        ProjectsDao projectsDao = new ProjectsDao(1,"site","20 april 2020",100000);
        TaskHomeWorkRepository taskHomeWorkRepository = new TaskHomeWorkRepository(dbConnection);

        //Task1
        System.out.println("Get sum salary developers from project : " + taskHomeWorkRepository.getSumSalaryDevelopersFromProject(projectsDao) + "\n");

        //Task2
        System.out.println("Get developers from project : " + taskHomeWorkRepository.getDevelopersFromProject(projectsDao) + "\n");

        //Task3
        System.out.println("Get all Java developers : " + taskHomeWorkRepository.getAllJavaDevelopers() + "\n");

        //Task4
        System.out.println("Get all Midle developers : " + taskHomeWorkRepository.getAllMidleDevelopers() + "\n");

        //Task5
        System.out.println("Get projects with name, deadline, count developers : " + taskHomeWorkRepository.getProgects());
    }
}
