/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author By Van Lung, IDStudent: SE140193
 */
public class DBUltis {

   public static Connection openConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=ProductManagement;";
            Connection con = DriverManager.getConnection(url, "sa", "123");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
