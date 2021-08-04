package conecaoBanco;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conexao {

    String connectionUrl
            
                = "jdbc:sqlserver://quality-sistem.database.windows.net:1433;"
                + "database=quality-sistem;"
                + "user=quality-sistem;"
                + "password=#Gfgrupo7;"
            + "encrypt=true;";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }
    
    public Connection getConnectionDocker() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/qualitySystem", "root", "urubu100");
    }
}

