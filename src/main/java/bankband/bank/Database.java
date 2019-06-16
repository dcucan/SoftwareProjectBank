package bankband.bank;

import org.apache.commons.io.IOUtils;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;

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
     * Odpojí se od databáze a zahodí instanci. Používá se v testech pro zamezení zamykání databáze.
     *
     * @throws SQLException
     */
    public static void trashInstance() throws SQLException {
        if (instance == null) return;

        instance.getConnection().close();

        instance = null;
    }

    /**
     * Konstruktor pro vytvoření spojení
     */
    private Database() {
        try {
            String str = "jdbc:" + Config.DB_CONNECTION + ":" + Config.DB_NAME;

            connection = DriverManager.getConnection(str);

            if (Config.DEBUG) {
                System.out.println("Connected to: " + str);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Přečte SQL z init.sql a vytvoří databázi v database.sqlite
     */
    public boolean install() {
        Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("init.sql"));

        String content = null;
        try {
            content = IOUtils.toString(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Rozdelime soubor na jednotlive SQL prikazy oddelene stredniky a postupne je jeden po druhem spustime
        for (String sql : content.split(";")) {
            // Trim odstrani prebytecne mezery na zacatku a na konci
            sql = sql.trim();

            // Preskocime prazdne prikazy
            if (sql.length() == 0) continue;

            if (Config.DEBUG) {
                System.out.println("\nExecuting sql:\n" + sql);
            }

            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        if (Config.DEBUG) {
            System.out.println("Database installed successfully.");
        }

        return true;
    }

    public Connection getConnection() {
        return connection;
    }
}
