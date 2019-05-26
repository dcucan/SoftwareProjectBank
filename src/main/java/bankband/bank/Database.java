package bankband.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    /**
     * Použijeme návrhový vzor jedináček –> v aplikaci bude pouze jedna instance spojení do databáze
     */
    private static Database instance;

    private Connection connection;

    /**
     * Vrátí instanci spojení do databáze, pokud v aplikaci instance ještě není, tak ji vytvoří
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    /**
     * Konstruktor pro vytvoření spojení
     */
    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:" + Config.DB_CONNECTION + ":" + Config.DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
