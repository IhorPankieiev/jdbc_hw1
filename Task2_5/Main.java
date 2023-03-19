package Task2_5;


import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/myjoinsdb";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    //joins for task 5
    private static final String GET_CONTACT_INFO = "SELECT Contact_Info.FName, Contact_Info.LName, Contact_Info.PhoneNo, Personal_Info.Address " +
            "FROM Contact_Info JOIN Personal_Info ON Contact_Info.id = Personal_Info.id;";

    private static final String GET_SINGLE_INFO = "SELECT Contact_Info.FName, Contact_Info.LName, Personal_Info.Birthday " +
            "FROM Contact_Info JOIN Personal_Info ON Contact_Info.id = Personal_Info.id " +
            "WHERE Personal_Info.FamilyStatus = 'Single'";

    private static final String GET_MANAGER_INFO = "SELECT Contact_Info.FName, Contact_Info.LName, Contact_Info.PhoneNo, Personal_Info.Birthday " +
            "FROM Contact_Info JOIN Personal_Info ON Contact_Info.id = Personal_Info.id " +
            "JOIN Payment_Info ON Personal_Info.id = Payment_Info.id WHERE Payment_Info.Title = 'Manager'";

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

        Connection  connection = null;
        Statement statement = null;
        PreparedStatement statementContInfo = null;
        PreparedStatement statementSingleInfo = null;
        PreparedStatement statementManagerInfo = null;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();
            statementContInfo = connection.prepareStatement(GET_CONTACT_INFO);
            statementSingleInfo = connection.prepareStatement(GET_SINGLE_INFO);
            statementManagerInfo = connection.prepareStatement(GET_MANAGER_INFO);
            //adding values to Contact_Info table
            statement.addBatch("INSERT INTO Contact_Info (FName, LName, PhoneNo) VALUES ('Graham', 'Nash', '0996547898')");
            statement.addBatch("INSERT INTO Contact_Info (FName, LName, PhoneNo) VALUES ('Stephen', 'Stills', '0946547898')");
            statement.addBatch("INSERT INTO Contact_Info (FName, LName, PhoneNo) VALUES ('Levon', 'Helm', '04467898898')");
            //adding values to Payment_Info table
            statement.addBatch("INSERT INTO Payment_Info (id, Salary, Title) VALUES (1, 20000, 'General Director')");
            statement.addBatch("INSERT INTO Payment_Info (id, Salary, Title) VALUES (2, 10000, 'Manager')");
            statement.addBatch("INSERT INTO Payment_Info (id, Salary, Title) VALUES (3, 5000, 'Worker')");
            //adding values to Personal_Info table
            statement.addBatch("INSERT INTO Personal_Info(id, FamilyStatus, Birthday, Address) VALUES (1, 'Single', '26.11.1973', 'Shevchenko st. 25')");
            statement.addBatch("INSERT INTO Personal_Info(id, FamilyStatus, Birthday, Address) VALUES (2, 'Single', '04.01.1984', 'Kashtanova 47')");
            statement.addBatch("INSERT INTO Personal_Info(id, FamilyStatus, Birthday, Address) VALUES (3, 'Married', '05.05.1969', 'Evergreen st 66')");

            statement.executeBatch();

            System.out.println("-----------------");

            //1) Получите контактные данные сотрудников (номера телефонов, место жительства)
            ResultSet resSet1 = statementContInfo.executeQuery();

            while (resSet1.next()){
                String fName = resSet1.getString("Contact_Info.FName");
                String lName = resSet1.getString("Contact_Info.LName");
                String phone = resSet1.getString("Contact_Info.PhoneNo");
                String address = resSet1.getString("Personal_Info.Address");

                System.out.println(fName + " " + lName + " " + phone + " " + address);
            }
            System.out.println("------------------------------");

            //2) Получите информацию о дате рождения всех холостых сотрудников и их номера.
            ResultSet resSet2 = statementSingleInfo.executeQuery();

            while (resSet2.next()){
                String fName = resSet2.getString("Contact_Info.FName");
                String lName = resSet2.getString("Contact_Info.LName");
                String birthday = resSet2.getString("Personal_Info.Birthday");

                System.out.println(fName + " " + lName + " " + birthday);
            }
            System.out.println("------------------------------");

            //3) Получите информацию обо всех менеджерах компании: дату рождения и номер телефона.
            ResultSet resSet3 = statementManagerInfo.executeQuery();

            while (resSet3.next()){
                String fName = resSet3.getString("Contact_Info.FName");
                String lName = resSet3.getString("Contact_Info.LName");
                String phone = resSet3.getString("Contact_Info.PhoneNo");
                String birthday = resSet3.getString("Personal_Info.Birthday");

                System.out.println(fName + " " + lName + " " + phone + " " + birthday);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                connection.close();
                statement.close();
                statementContInfo.close();
                statementManagerInfo.close();
                statementSingleInfo.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }//main
}//Class
