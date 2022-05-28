package dl;

import config.DBMC;
import model.dao.DeveloperSkillsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeveloperSkillsRepository implements Repository<DeveloperSkillsDao>{

    private final DBMC connector;
    private static final String FIND_BY_DEVELOPER = "select * from developer_skills ds where ds.developer_id=?";
    private static final String SAVE = "insert into developer_skills (developer_id,skill_id) values (?,?)";
    private static final String REMOVE = "delete from developer_skills ds where ds.developer_id = ?";
    private static final String UPDATE = "update developer_skills ds set skill_id = ? where ds.developer_id=? ";

    public DeveloperSkillsRepository(DBMC connection){
        this.connector=connection;
    }

    @Override
    public DeveloperSkillsDao findByIndex(Integer index) throws SQLException {
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
    public void save(DeveloperSkillsDao developerSkillsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,developerSkillsDao.getDeveloperId());
            preparedStatement.setLong(2,developerSkillsDao.getSkillId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(DeveloperSkillsDao developerSkillsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            preparedStatement.setLong(1,developerSkillsDao.getDeveloperId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(DeveloperSkillsDao developerSkillsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setLong(1,developerSkillsDao.getSkillId());
            preparedStatement.setLong(2,developerSkillsDao.getDeveloperId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private DeveloperSkillsDao mapToSet(ResultSet resultSet) throws SQLException {
        DeveloperSkillsDao developerSkillsDao = new DeveloperSkillsDao();
        while(resultSet.next()){
            developerSkillsDao.setDeveloperId(resultSet.getLong("developer_id"));
            developerSkillsDao.setSkillId(resultSet.getLong("skill_id"));
        }
        return developerSkillsDao;
    }
}
