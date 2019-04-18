package ru.rencredit;

import java.sql.*;

public class Conn {
    private Connection conn=null;

    public void connectDB(String server, String dbname, String user, String password) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://" + server + "\\SQLEXPRESS;databaseName=" + dbname + ";", user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStepDB(String step, Timestamp dateStart, Timestamp dateEnd) {
        try {
            if(conn!=null) {
                String sql = "INSERT INTO InfoSteps ([Дата и время начала шага], [Дата и время окончания шага], [Название шага]) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setTimestamp(1, dateStart);
                pstmt.setTimestamp(2, dateEnd);
                pstmt.setString(3, step);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            if(conn!=null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn=null;
    }

}