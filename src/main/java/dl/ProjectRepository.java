package dl;

import config.DBMC;
import model.dao.ProjectsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRepository implements Repository<ProjectsDao>{

    private final DBMC connector;
    private static final String FIND_BY_ID = "select * from projects p where p.id=?";
    private static final String SAVE = "insert into projects(id,name_project,deadline,cost) values (?,?,?,?)";
    private static final String REMOVE = "delete from projects p where p.id=?";
    private static final String UPDATE = "update projects p set name_project=?, deadline=?, cost=? where p.id=?";


    public ProjectRepository(DBMC connection){
        this.connector=connection;
    }


    @Override
    public ProjectsDao findByIndex(Integer index) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setLong(1,index);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToSet(resultSet);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(ProjectsDao projectsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,projectsDao.getId());
            preparedStatement.setString(2,projectsDao.getNameProject());
            preparedStatement.setString(3,projectsDao.getDeadline());
            preparedStatement.setInt(3,projectsDao.getCost());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(ProjectsDao projectsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            preparedStatement.setLong(1,projectsDao.getId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(ProjectsDao projectsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1,projectsDao.getNameProject());
            preparedStatement.setString(2,projectsDao.getDeadline());
            preparedStatement.setInt(3,projectsDao.getCost());
            preparedStatement.setLong(4,projectsDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private ProjectsDao mapToSet (ResultSet resultSet) throws SQLException {
        ProjectsDao projectsDao = new ProjectsDao();
        while (resultSet.next()){
            projectsDao.setId(resultSet.getInt("id"));
            projectsDao.setNameProject(resultSet.getString("name_project"));
            projectsDao.setDeadline(resultSet.getString("deadline"));
            projectsDao.setCost(resultSet.getInt("cost"));
        }
        return projectsDao;
    }
}
