package com.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConection {

    private static final String URL = "jdbc:postgresql://localhost:5432/germantech";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException
    {

        return DriverManager.getConnection(URL, USER, PASSWORD);


    }



}
