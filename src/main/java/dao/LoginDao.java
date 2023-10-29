package dao;

import model.AppUser;
import model.LoginViewModel;
import model.Role;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LoginDao {
    private final DataSource dataSource;
    public LoginDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    // Helper method to get a connection
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public AppUser authenticateUser(LoginViewModel loginModel) {
        AppUser user = null;
        String identifier = loginModel.getIdentifier();
        String password = loginModel.getPassword();

        String query = "";
        // Determine whether the user is logging in with a username or email
        if (identifier.contains("@")) {
            query = "SELECT * FROM app_user WHERE email = ? AND password = ?";
        } else {
            query = "SELECT * FROM app_user WHERE user_name = ? AND password = ?";
        }

        try (Connection conn = getConnection() ;
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, identifier);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Authentication successful, return the user's ID or a success message
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String userName = rs.getString("user_name");
                String email = rs.getString("user_name");
                Role role = getUserRole(rs.getInt("role_id"));

                user = new AppUser();
                user.setId(userId);
                user.setName(name);
                user.setUserName(userName);
                user.setEmail(email);
                user.setRole(role);
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors appropriately
            System.out.println("Authentication failed. An error occurred.");
            return user;
        }
    }

    private Role getUserRole(int roleID){
        Role role = null;
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, roleID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = Role.valueOf(resultSet.getString("name"));
            }
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
