package dl;

import config.DBMC;
import model.dao.DevelopersDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DevelopersRepository implements Repository<DevelopersDao> {

    private final DBMC connector ;
    private static final String FIND_BY_ID = "select * from developers d where d.id=?";
    private static final String SAVE = "insert into developers (id,first_name,age,gender,salary) values (?,?,?,?,?)";
    private static final String REMOVE = "delete from developers d where d.id=?";
    private static final String UPDATE = "update developers d " +
                                          "set first_name = ?," +
                                          "age = ?," +
                                          "gender = ?," +
                                          "salary = ?" +
                                          "where id = ?";


    public DevelopersRepository(DBMC connection){
        this.connector=connection;
    }
    @Override
    public DevelopersDao findByIndex(Integer index)  {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1,index);
            ResultSet set = preparedStatement.executeQuery();
            return mapToSet(set);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(DevelopersDao developersDao) {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
                preparedStatement.setLong(1,developersDao.getId());
                preparedStatement.setString(2,developersDao.getFirstName());
                preparedStatement.setInt(3,developersDao.getAge());
                preparedStatement.setString(4,developersDao.getGender());
                preparedStatement.setInt(5,developersDao.getSalary());
                preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(DevelopersDao developersDao) {
        try(Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)) {
            preparedStatement.setLong(1,developersDao.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(DevelopersDao developersDao) {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1,developersDao.getFirstName());
            preparedStatement.setInt(2,developersDao.getAge());
            preparedStatement.setString(3,developersDao.getGender());
            preparedStatement.setInt(4,developersDao.getSalary());
            preparedStatement.setLong(5,developersDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public DevelopersDao mapToSet (ResultSet resultSet) throws SQLException {
        DevelopersDao developersDao = new DevelopersDao();
        while(resultSet.next()){
            developersDao.setId(resultSet.getLong("id"));
            developersDao.setAge(resultSet.getInt("age"));
            developersDao.setFirstName(resultSet.getString("first_name"));
            developersDao.setGender(resultSet.getString("gender"));
            developersDao.setSalary(resultSet.getInt("salary"));
        }
        return developersDao;
    }


}
