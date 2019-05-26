package bankband.bank.controllers;

import bankband.bank.Database;
import bankband.bank.util.Password;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    private Connection con;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    public void initialize() {
        con = Database.getInstance().getConnection();
    }

    public void onRegister() throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, email, password)"
                + "VALUES (?, ?, ?, ?);";

        String hash = Password.hashPassword(password.getText());

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, name.getText());
        stmt.setString(2, surname.getText());
        stmt.setString(3, email.getText());
        stmt.setString(4, hash);
        stmt.execute();
    }

}
