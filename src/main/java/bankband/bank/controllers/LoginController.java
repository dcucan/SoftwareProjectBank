package bankband.bank.controllers;

import bankband.bank.Database;
import bankband.bank.util.Password;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private Connection con = Database.getInstance().getConnection();

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    public void onLogin() throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, email.getText());
        ResultSet rs = stmt.executeQuery();

        if(rs.next() == false) {
            System.out.println("Invalid email or password.");
        } else {
            String hash = rs.getString("password");


            boolean ver = Password.checkPassword(password.getText(), hash);
            if (ver==true) {
                System.out.println("Login succesful");
            } else {
                System.out.println("Nope");
            }
        }


    }

}
