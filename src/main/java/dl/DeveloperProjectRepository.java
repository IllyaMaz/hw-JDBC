package dl;

import config.DBMC;
import model.dao.DeveloperProjectDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeveloperProjectRepository implements Repository<DeveloperProjectDao>{

    private final DBMC connector;
    private static final String FIND_BY_DEVELOPER = "select * from developer_project dp where dp.developer_id = ?";
    private static final String SAVE = "insert into developer_project(developer_id,progect_id) values (?,?)";
    private static final String REMOVE = "delete from developer_project dp where dp.developer_id = ?";
    private static final String UPDATE = "update developer_project dp set progect_id=? where dp.developer_id = ?";


    public DeveloperProjectRepository(DBMC connection){
        this.connector=connection;
    }

    @Override
    public DeveloperProjectDao findByIndex(Integer index) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_DEVELOPER)){
            preparedStatement.setLong(1,index);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToSet(resultSet);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(DeveloperProjectDao developerProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,developerProjectDao.getDeveloperId());
            preparedStatement.setLong(2,developerProjectDao.getProjectId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(DeveloperProjectDao developerProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            preparedStatement.setLong(1,developerProjectDao.getDeveloperId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(DeveloperProjectDao developerProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setLong(1,developerProjectDao.getProjectId());
            preparedStatement.setLong(2,developerProjectDao.getDeveloperId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private DeveloperProjectDao mapToSet(ResultSet resultSet) throws SQLException {
        DeveloperProjectDao developerProjectDao = new DeveloperProjectDao();
        while(resultSet.next()){
            developerProjectDao.setDeveloperId(resultSet.getLong("developer_id"));
            developerProjectDao.setProjectId(resultSet.getLong("progect_id"));
        }
        return developerProjectDao;
    }
}
