package dl;

import config.DBMC;
import model.dao.CompaniesDao;
import model.dao.CustomersDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CustomersRepository implements Repository<CustomersDao>{

    private final DBMC connector ;
    private static final String FIND_BY_ID = "select * from customers c where c.id=?";
    private static final String SAVE = "insert into customers (id,first_name) values (?,?)";
    private static final String REMOVE = "delete from customers c where c.id=?";
    private static final String UPDATE = "update customers c " +
            "set name_company = ?"+
            "where id = ?";

    public CustomersRepository (DBMC connection){
        this.connector=connection;
    }

    @Override
    public CustomersDao findByIndex(Integer index) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
             preparedStatement.setLong(1,index);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToSet(resultSet);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CustomersDao customersDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,customersDao.getId());
            preparedStatement.setString(2,customersDao.getName());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(CustomersDao customersDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            preparedStatement.setLong(1,customersDao.getId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(CustomersDao customersDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1,customersDao.getName());
            preparedStatement.setLong(2,customersDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public CustomersDao mapToSet (ResultSet resultSet) throws SQLException {
        CustomersDao customersDao = new CustomersDao();
        while(resultSet.next()){
            customersDao.setId(resultSet.getInt("id"));
            customersDao.setName(resultSet.getString("first_name"));
        }
        return customersDao;
    }
}
