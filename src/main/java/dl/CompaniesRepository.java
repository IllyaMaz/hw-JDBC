package dl;

import config.DBMC;
import model.dao.CompaniesDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompaniesRepository implements Repository<CompaniesDao>{

    private final DBMC connector ;
    private static final String FIND_BY_ID = "select * from companies c where c.id=?";
    private static final String SAVE = "insert into companies (id,name_company) values (?,?)";
    private static final String REMOVE = "delete from companies c where c.id=?";
    private static final String UPDATE = "update companies c " +
            "set name_company = ?"+
            "where id = ?";

    public CompaniesRepository(DBMC connection) {
        connector = connection;
    }

    @Override
    public CompaniesDao findByIndex(Integer index) throws SQLException {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setLong(1,index);
            ResultSet set = preparedStatement.executeQuery();
            return mapToSet(set);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CompaniesDao companiesDao) {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,companiesDao.getId());
            preparedStatement.setString(2,companiesDao.getNameCompany());
            preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(CompaniesDao companiesDao) {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)) {
            preparedStatement.setLong(1,companiesDao.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CompaniesDao companiesDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1,companiesDao.getNameCompany());
            preparedStatement.setLong(2,companiesDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public CompaniesDao mapToSet (ResultSet resultSet) throws SQLException {
        CompaniesDao companiesDao = new CompaniesDao();
        while(resultSet.next()){
            companiesDao.setId(resultSet.getInt("id"));
            companiesDao.setNameCompany(resultSet.getString("name_company"));
        }
        return companiesDao;
    }
}
