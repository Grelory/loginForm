package com.codecool.loginform;

import java.sql.*;

public class Dao {

    private Connection connection;

    private void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/loginDB",
                "grzegorz", "29458173");
        System.out.println("Opened database successfully");
    }


    public String getNameFromLogin(String login, String password) {
        String name = "";
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM login WHERE login=? AND password=?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) name = rs.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }


}
