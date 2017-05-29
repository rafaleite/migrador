package migracao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public final class PostgresConnect {
    public Connection conn;
    private Statement statement;
    public static PostgresConnect db;
    
    private PostgresConnect() {
        String url= "jdbc:postgresql://192.168.1.26:5432/";
        String dbName = "2-SSA-MIGRACAO";
        String driver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "---";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     *
     * @return PostgresConnect Database connection object
     */
    public static synchronized PostgresConnect getDbCon() {
        if ( db == null ) {
            db = new PostgresConnect();
        }
        return db;
 
    }
    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
 
    }
 
}