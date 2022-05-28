package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBMC {
    private final String url;
    private final Properties properties;

    public DBMC(String host,int port, String userName, String password,String databaseName){
        url=String.format("jdbc:postgresql://%s:%d/%s",host,port,databaseName);
        properties = new Properties();
        properties.setProperty("user",userName);
        properties.setProperty("password",password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,properties);
    }
}
