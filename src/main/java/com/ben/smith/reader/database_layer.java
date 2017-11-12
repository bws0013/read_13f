package com.ben.smith.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bensmith on 11/11/17.
 */
public class database_layer {

    public static void main(String[] args) {
        String db_name = "jdbc:sqlite:./database/test.db";
        connect(db_name);
    }

    public static void add_date(String db_name, List<Asset> assets) {
        Connection conn = null;
        Asset b = assets.get(0);
        try {
            String url = db_name;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            conn.setAutoCommit(false);

            PreparedStatement ps =
                    conn.prepareStatement("insert into Assets values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for(Asset a : assets) {
                b = a;
                int cash_val = Integer.parseInt(a.getCash_value());
                int num_shares = Integer.parseInt(a.getNum_shares());
                java.sql.Date conf_period = java.sql.Date.valueOf(a.getConfirmation_period());

                ps.setString(1, a.getCik());
                ps.setDate(2, conf_period);
                ps.setString(3, a.getName());
                ps.setString(4, a.getTitle());
                ps.setString(5, a.getCusip());
                ps.setString(6, a.getExcel_cusip());
                ps.setInt(7, cash_val);
                ps.setInt(8, num_shares);
                ps.setString(9, a.getType());
                ps.setString(10, a.getDiscretion());


                ps.executeUpdate(); //JDBC queues this for later execution
            }

            ps.executeBatch();
            conn.commit();

            ps.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            b.print_all_fields();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }


    public static void connect(String db_name) {
        Connection conn = null;
        try {
            // db parameters
            // String url = "jdbc:sqlite:C:/sqlite/db/chinook.db";
            String url = db_name;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


}
