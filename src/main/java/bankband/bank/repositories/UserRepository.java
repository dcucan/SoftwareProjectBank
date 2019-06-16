package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private Connection conn = Database.getInstance().getConnection();

    public User findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("first_name"));
                user.setSurname(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("first_name"));
                user.setSurname(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAll() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }

    public Integer create(User user) {
        String sql = "INSERT INTO users (first_name, last_name, email, password)"
                + "VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        sql = "SELECT last_insert_rowid();";
        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);

            user.setId(rs.getInt(1));
            return user.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(User user) {
        String sql  = "UPDATE users SET first_name = ?, last_name = ? , email = ?, password = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2,user.getSurname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getId());
            stmt.execute();

            return true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean delete(User user) {
        return false;
    }

}
