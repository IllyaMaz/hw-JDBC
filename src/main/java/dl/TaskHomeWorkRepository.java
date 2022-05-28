package dl;

import config.DBMC;
import model.dao.ProjectsDao;
import model.dao.TaskFiveDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaskHomeWorkRepository {
    private final DBMC connector;
    private static final String SUM_SALARY_FROM_PROJECT = "select sum(d.salary) as sal " +
            "from developer_project dp " +
            "inner join developers d on d.id=dp.developer_id " +
            "inner join projects p on p.id=dp.progect_id " +
            "where p.id=? " +
            "group by p.id ";
    private static final String DEVELOPERS_FROM_PROJECT = "select d.first_name as name " +
            "from developer_project dp " +
            "inner join developers d on d.id=dp.developer_id " +
            "inner join projects p on p.id=dp.progect_id  " +
            "where p.id = ?";
    private static final String GET_ALL_JAVA_DEVELOPERS = "select d.first_name as name " +
            "from developer_skills ds " +
            "inner join developers d on d.id=ds.developer_id " +
            "inner join skills s on s.id=ds.skill_id " +
            "where s.name_skill = 'Java'";
    private static final String GET_ALL_MIDLE_DEVELOPERS = "select d.first_name as name " +
            "from developer_skills ds " +
            "inner join developers d on d.id=ds.developer_id " +
            "inner join skills s on s.id=ds.skill_id " +
            "where s.level_skill='Midle'";
    private static final String GET_PROJECT_WITH_RULES = "select p.name_project as name_project, p.deadline as deadline, count(d.id) as count_developers " +
            "from developer_project dp " +
            "inner join developers d on d.id = dp.developer_id " +
            "inner join projects p on p.id = dp.progect_id " +
            "group by p.id";

    public TaskHomeWorkRepository(DBMC connection){
        this.connector=connection;
    }


    //Task1
    public int getSumSalaryDevelopersFromProject(ProjectsDao projectsDao){
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SUM_SALARY_FROM_PROJECT)){
            preparedStatement.setInt(1,projectsDao.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("sal");
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    //Task2
    public List<String> getDevelopersFromProject(ProjectsDao projectsDao){
        List<String> list = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DEVELOPERS_FROM_PROJECT)) {
            preparedStatement.setLong(1,projectsDao.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(resultSet.getString("name"));
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //Task3
    public List<String> getAllJavaDevelopers(){
        List<String> list = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(GET_ALL_JAVA_DEVELOPERS);
            while (resultSet.next()){
                list.add(resultSet.getString("name"));
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //Task4
    public List<String> getAllMidleDevelopers(){
        List<String> list = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(GET_ALL_MIDLE_DEVELOPERS);
            while(resultSet.next()){
                list.add(resultSet.getString("name"));
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //Task5
    public List<TaskFiveDao> getProgects(){
        List<TaskFiveDao> list = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(GET_PROJECT_WITH_RULES);
            while (resultSet.next()){
                TaskFiveDao taskFiveDao = new TaskFiveDao();
                taskFiveDao.setNameProject(resultSet.getString("name_project"));
                taskFiveDao.setDeadline(resultSet.getString("deadline"));
                taskFiveDao.setCountDevelopers(resultSet.getInt("count_developers"));
                list.add(taskFiveDao);
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
