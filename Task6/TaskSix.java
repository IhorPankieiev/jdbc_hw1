package Task6;

import java.sql.*;

public class TaskSix {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    private static final String GET_ALL = "SELECT * FROM testtable";

    private static final String DELETE = "DELETE FROM testtable WHERE LName = ?";

    public static void regDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        regDriver();

        Connection connection = null;
        Statement statement = null;
        PreparedStatement getAllInfo = null;
        PreparedStatement deleteInfo = null;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();
            getAllInfo = connection.prepareStatement(GET_ALL);
            deleteInfo = connection.prepareStatement(DELETE);

            //inserting values into table
            statement.execute("INSERT INTO testtable (FName, LName, age) VALUES ('Peter', 'Parker', 25)");
            statement.execute("INSERT INTO testtable (FName, LName, age) VALUES ('Matt', 'Murdock', 32)");

            //updating age
            int res = statement.executeUpdate("UPDATE testtable SET age = age + 2 WHERE FName = 'Peter'");
            System.out.println(res);
            System.out.println("____________________");

            //getting all info from the table
            ResultSet resultSet1 = getAllInfo.executeQuery();
            while (resultSet1.next()){
                String fName = resultSet1.getString("FName");
                String lName = resultSet1.getString("LName");
                int age = resultSet1.getInt("age");

                System.out.println(fName + " " + lName + " " + age);
            }
            System.out.println("____________________");

            //Deleting info from the table
            deleteInfo.setString(1, "Murdock");
            deleteInfo.executeUpdate();

            //Get info after deleting
            ResultSet resultSet2 = getAllInfo.executeQuery();
            while (resultSet2.next()){
                String fName = resultSet2.getString("FName");
                String lName = resultSet2.getString("LName");
                int age = resultSet2.getInt("age");

                System.out.println(fName + " " + lName + " " + age);
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                connection.close();
                statement.close();
                getAllInfo.close();
                deleteInfo.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }//main
}//Class
