package dl;

import config.DBMC;
import model.dao.CompanyProjectDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyProjectRepository implements Repository<CompanyProjectDao> {

    private final DBMC connector;
    private static final String FIND_BY_COMPANY = "select * from company_project cp where cp.company_id=?";
    private static final String SAVE = "insert into company_project (company_id,project_id) values (?,?)";
    private static final String REMOVE = "delete from company_project cp where cp.company_id=? ";
    private static final String UPDATE = "update company_project set project_id = ? where company_id = ?";


    public CompanyProjectRepository(DBMC connection){
        this.connector=connection;
    }

    @Override
    public CompanyProjectDao findByIndex(Integer index) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_COMPANY)){
            preparedStatement.setLong(1,index);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToSet(resultSet);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CompanyProjectDao companyProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,companyProjectDao.getCompanyId());
            preparedStatement.setLong(2,companyProjectDao.getProjectId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(CompanyProjectDao companyProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            preparedStatement.setLong(1,companyProjectDao.getCompanyId());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(CompanyProjectDao companyProjectDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setLong(1,companyProjectDao.getProjectId());
            preparedStatement.setLong(2,companyProjectDao.getCompanyId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private CompanyProjectDao mapToSet(ResultSet resultSet) throws SQLException {
        CompanyProjectDao companyProjectDao = new CompanyProjectDao();
        while (resultSet.next()){
            companyProjectDao.setCompanyId(resultSet.getLong("company_id"));
            companyProjectDao.setCompanyId(resultSet.getLong("project_id"));
        }
        return companyProjectDao;
    }
}
