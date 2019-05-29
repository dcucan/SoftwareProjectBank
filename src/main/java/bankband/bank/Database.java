package bankband.bank;

import org.apache.commons.io.IOUtils;

import javax.xml.bind.SchemaOutputResolver;
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
     * Konstruktor pro vytvoření spojení
     */
    private Database() {
        try {
            String str = "jdbc:" + Config.DB_CONNECTION + ":" + Config.DB_NAME;

            connection = DriverManager.getConnection(str);

            System.out.println("Connected to: " + str);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Přečte SQL z init.sql a vytvoří databázi v db
     */
    public void install() throws Exception {
        Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("init.sql"));
        String content = IOUtils.toString(reader);

        // Rozdelime soubor na jednotlive SQL prikazy oddelene stredniky a postupne je jeden po druhem spustime
        for (String sql : content.split(";")) {
            // Trim odstrani prebytecne mezery na zacatku a na konci
            sql = sql.trim();

            // Preskocime prazdne prikazy
            if (sql.length() == 0) continue;

            System.out.println("\nExecuting sql:\n" + sql);

            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
