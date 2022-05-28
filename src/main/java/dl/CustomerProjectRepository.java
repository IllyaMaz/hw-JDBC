package dl;

import config.DBMC;
import model.dao.CustomerProjectDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerProjectRepository implements Repository<CustomerProjectDao>{

    private final DBMC connector;
    private static final String FIND_BY_CUSTOMER = "select * from customer_project cp where cp.customer_id=? ";
    private static final String SAVE = "insert into customer_project(customer_id,project_id) values (?,?) ";
    private static final String REMOVE = "delete from customer_project cp where cp.customer_id=?";
    private static final String UPDATE = "update customer_project cp set project_id = ? where cp.customer_id = ?";

    public CustomerProjectRepository(DBMC connection){
        this.connector=connection;
    }

    @Override
    public CustomerProjectDao findByIndex(Integer index) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CUSTOMER)){
            preparedStatement.setLong(1,index);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToSet(resultSet);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CustomerProjectDao customerProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,customerProjectDao.getCustomerId());
            preparedStatement.setLong(2,customerProjectDao.getProjectID());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(CustomerProjectDao customerProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            preparedStatement.setLong(1,customerProjectDao.getCustomerId());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CustomerProjectDao customerProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setLong(1,customerProjectDao.getProjectID());
            preparedStatement.setLong(2,customerProjectDao.getCustomerId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private CustomerProjectDao mapToSet(ResultSet resultSet) throws SQLException {
        CustomerProjectDao customerProjectDao = new CustomerProjectDao();
        while(resultSet.next()){
            customerProjectDao.setCustomerId(resultSet.getLong("customer_id"));
            customerProjectDao.setProjectID(resultSet.getLong("project_id"));
        }
        return customerProjectDao;
    }
}
