package dl;

import config.DBMC;
import model.dao.SkillsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillsRepository implements Repository<SkillsDao>{

    private final DBMC connector;
    private static final String FIND_BY_ID = "select * from skills s where s.id=?";
    private static final String SAVE = "insert into skills (id,name_skill,level_skill) values (?,?,?)";
    private static final String REMOVE = "delete from skills s where s.id=?";
    private static final String UPDATE = "update skills s set name_skill=?, level_skill=? where s.id=?";

    public SkillsRepository(DBMC connection){
        this.connector=connection;
    }

    @Override
    public SkillsDao findByIndex(Integer index) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setLong(1,index);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(SkillsDao skillsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,skillsDao.getId());
            preparedStatement.setString(2,skillsDao.getNameSkill());
            preparedStatement.setString(3,skillsDao.getLevelSkill());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(SkillsDao skillsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            preparedStatement.setLong(1,skillsDao.getId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(SkillsDao skillsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1,skillsDao.getNameSkill());
            preparedStatement.setString(2,skillsDao.getLevelSkill());
            preparedStatement.setLong(3,skillsDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private  SkillsDao mapToSet(ResultSet resultSet) throws SQLException {
        SkillsDao skillsDao = new SkillsDao();
        while (resultSet.next()){
            skillsDao.setId(resultSet.getLong("id"));
            skillsDao.setNameSkill(resultSet.getString("name_skill"));
            skillsDao.setLevelSkill(resultSet.getString("level_skill"));
        }
        return skillsDao;
    }
}
