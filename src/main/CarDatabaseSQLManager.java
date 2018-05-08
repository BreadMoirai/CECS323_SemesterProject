package main;

import java.math.BigDecimal;
import java.sql.*;

public class CarDatabaseSQLManager
{
    private static CarDatabaseSQLManager singletonInstance;
    private static Connection con;
    CarDatabaseSQLManager() throws ClassNotFoundException, SQLException
    {
    
        String DB_URL = "jdbc:mysql://cecs-db01.coe.csulb.edu/cecs323sec5g5";
        String USER = "cecs323sec5n11";
        String PASS = "ai4tah";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(DB_URL, USER, PASS);
        con.setAutoCommit(false);
    }
    
    public static CarDatabaseSQLManager getInstance() throws SQLException, ClassNotFoundException
    {
        if (singletonInstance == null)
        {
            singletonInstance = new CarDatabaseSQLManager();
        }
        
        return singletonInstance;
    }
    
    private int addPerson(String lastName, String firstName, String middleName,
                          String address, String zip)
    {
            String updateStatement = "INSERT INTO persons (lastName, firstName, middleName, address, zip)  " +
                    "Values (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setString(1, lastName);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, middleName);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, zip);
                
                int personID = preparedStatement.executeUpdate();
                preparedStatement.close();
                return personID;
            } catch (SQLException e)
            {
                System.out.println(e);
                return -1;
            }
    }
    
    public void addPhoneNumber(int personID, String phoneType, String phoneNumber)
    {
        String updateStatement = "INSERT INTO phone_numbers (personID, phone_type, phone_number)  " +
                "Values (?, ?, ?)";
        try(PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setInt(1, personID);
            preparedStatement.setString(2, phoneType);
            preparedStatement.setString(3, phoneNumber);
    
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e)
        {
            System.out.println(e);
        }
        
    }
    
    public void addSale(int salespersonID, int customerID, Date dateOfSale,
                        BigDecimal price, BigDecimal tradeInValue, String VIN)
    {
        String updateStatement = "INSERT INTO sales (salespersonID, customerID, date_of_sale, " +
                "price, trade_in_value, VIN) " +
                "VALUES (?, ?, ?, ?, ? ,?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setInt(1, salespersonID);
            preparedStatement.setInt(2, customerID);
            preparedStatement.setDate(3, dateOfSale);
            preparedStatement.setBigDecimal(4, price);
            preparedStatement.setBigDecimal(5, tradeInValue);
            preparedStatement.setString(6, VIN);
    
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public void addCarStyle(String make, String model, int modelYear,
                            String bodyStyle, int engineSize, String fuelType)
    {
        String updateStatement = "INSERT INTO car_styles (make, model, model_year, body_style, engine_size," +
                " fuel_type) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);
            preparedStatement.setInt(3, modelYear);
            preparedStatement.setString(4, bodyStyle);
            preparedStatement.setInt(5, engineSize);
            preparedStatement.setString(6, fuelType);
    
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public void addCarUpgrade(String VIN, String upgradeName)
    {
        String updateStatement = "INSERT INTO car_upgrades VALUES (?, ?)";
        
        try(PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setString(1, VIN);
            preparedStatement.setString(2, upgradeName);
    
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public void addCar(String VIN, int styleID, BigDecimal invoicePrice, BigDecimal retailPrice,
                        String color, int driving_range, String warrenty)
    {
        String updateStatement = "INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setString(1, VIN);
            preparedStatement.setInt(2, styleID);
            preparedStatement.setBigDecimal(3, invoicePrice);
            preparedStatement.setBigDecimal(4, retailPrice);
            preparedStatement.setString(5, color);
            preparedStatement.setInt(6, driving_range);
            preparedStatement.setString(7, warrenty);
    
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public int addCustomer(int personID, String emailAddr)
    {
        String updateStatement = "Insert INTO customers (personID, email_addr) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setInt(1, personID);
            preparedStatement.setString(2, emailAddr);
    
            int customerID = preparedStatement.executeUpdate();
            preparedStatement.close();
            return customerID;
        } catch (SQLException e)
        {
            System.out.println(e);
            return -1;
        }
    }
    public void addDependent(int employeeID, int personID, String socialSecurityNumber)
    {
        String updateStatement = "Insert INTO dependents(employeeID, personID, social_security_number)" +
                " VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setInt(1, employeeID);
            preparedStatement.setInt(2, personID);
            preparedStatement.setString(3, socialSecurityNumber);
    
            preparedStatement.executeUpdate();
            preparedStatement.close();
        
        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    public int addEmployee(int personID, Date hireDate, BigDecimal moSalary, int unusedVacationDays,
                            String socialSecurityNumber, String department, int emergencyContact)
    {
        String updateStatement = "Insert INTO employees(personID, hire_date, mo_salary, unused_vacation_days," +
                " social_security_number, department, emergency_contact) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setInt(1, personID);
            preparedStatement.setDate(2, hireDate);
            preparedStatement.setBigDecimal(3, moSalary);
            preparedStatement.setInt(4, unusedVacationDays);
            preparedStatement.setString(5, socialSecurityNumber);
            preparedStatement.setString(6, department);
            preparedStatement.setInt(7, emergencyContact);
            
            int employeeID = preparedStatement.executeUpdate();
            preparedStatement.close();
            
            return employeeID;
        } catch (SQLException e)
        {
            System.out.println(e);
            return -1;
        }
    }
    
    public void addLoan(String VIN, int customerID, Date dateOfLoan, BigDecimal principal,
                        int loanLength, Date dateOfLastPayment, BigDecimal monthlyPayment,
                        String socialSecurityNumber)
    {
        String updateStatement = "INSERT INTO loans VALUES (?, ?, ? ,? ,? ,? ,?, ?)";
        
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setString(1, VIN);
            preparedStatement.setInt(2, customerID);
            preparedStatement.setDate(3, dateOfLoan);
            preparedStatement.setBigDecimal(4, principal);
            preparedStatement.setInt(5, loanLength);
            preparedStatement.setDate(6, dateOfLastPayment);
            preparedStatement.setBigDecimal(7, monthlyPayment);
            preparedStatement.setString(8, socialSecurityNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public void addManager(int employeeID, String pastAssignements)
    {
        String updateStatement = "INSERT INTO managers VALUES (?, ?)";
        
        try(PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setInt(1, employeeID);
            preparedStatement.setString(2, pastAssignements);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public int addSalesperson(int employeeInt, double comissionRate)
    {
        String updateStatement = "INSERT INTO salespersons VALUES (?, ?)";
        
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement,
                Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setInt(1, employeeInt);
            preparedStatement.setDouble(2, comissionRate);
            
            int salespersonID = preparedStatement.executeUpdate();
            return salespersonID;
            
        } catch (SQLException e)
        {
            System.out.println(e);
            return -1;
        }
    }
    
    public void addTechnician(int employeeInt, String technicianCredential)
    {
        String updateStatement = "INSERT INTO techncians VALUES (?, ?)";
        
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement,
                Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setInt(1, employeeInt);
            preparedStatement.setString(2, technicianCredential);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public void addUsedCar(String VIN, String license_plate, int mileage, String carCondition )
    {
        String updateStatement = "INSERT INTO used_cars VALUES(?, ?, ?, ?)";
        
        try(PreparedStatement preparedStatement = con.prepareStatement(updateStatement))
        {
            preparedStatement.setString(1, VIN);
            preparedStatement.setString(2, license_plate);
            preparedStatement.setInt(3, mileage);
            preparedStatement.setString(4, carCondition);
            
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public int createNewSale(int salespersonID, String firstName, String lastName, String middleName, String address,
                              String zip, String emailAddress,
                              Date dateOfSale, BigDecimal price, BigDecimal tradeInValue, String VIN)
    {
        int customerID = getCustomerID(firstName, lastName, middleName, zip);
        if (customerID == -1)
        {
            int personID = addPerson(lastName, firstName, middleName, address, zip);
            customerID = addCustomer(personID, emailAddress);
        }
        addSale(salespersonID, customerID, dateOfSale, price, tradeInValue, VIN);
        return customerID;
    }
    
    public void createNewSaleWithLoan(int salespersonID, String firstName, String lastName, String middleName,
                                   String address, String zip, String emailAddress, Date dateOfSale, BigDecimal price,
                                   BigDecimal tradeInValue, String VIN, String socialSecurityNumber, Date dateOfLoan,
                                   BigDecimal principal, int loanLength, Date dateOfLastPayment,
                                   BigDecimal monthlyPayment)
    {
        int customerID = createNewSale(salespersonID, firstName, lastName, middleName, address, zip, emailAddress, dateOfSale, price,
                tradeInValue, VIN);
        addLoan(VIN, customerID, dateOfLoan, principal, loanLength, dateOfLastPayment, monthlyPayment,
                socialSecurityNumber);
    }
    
    private int getCustomerID(String firstName, String lastName, String middleName, String zip)
    {
        String query = String.format("SELECT personID FROM persons WHERE firstName = '%s' AND lastName = '%s' " +
                "AND middleName = '%s' AND zip = '%s'", firstName, lastName, middleName, zip);
        try (PreparedStatement preparedStatement = con.prepareStatement(query))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
            return -1;
        } catch (SQLException e)
        {
            System.out.println(e);
            return -1;
        }
    }
    
    public void resetVacationHours()
    {
        String update = "UPDATE employees SET unused_vacation_days = 0";
        try(PreparedStatement preparedStatement = con.prepareStatement(update))
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println();
        }
    }
    
    public void commitChanges()
    {
        try
        {
            con.commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public ResultSet executeSQLQuery(String sqlQuery)
    {
        try (PreparedStatement preparedStatement = con.prepareStatement(sqlQuery))
        {
            return preparedStatement.executeQuery();
        } catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }
    public ResultSet query1()
    {
        String sqlQuery = "SELECT firstName, middleName, lastName, address, zip, home, cellphone\n" +
"FROM joined_phones NATURAL JOIN persons NATURAL JOIN customers c NATURAL JOIN sales\n" +
"WHERE customerID NOT IN\n" +
"  (SELECT customerID FROM sales WHERE date_of_sale BETWEEN NOW() - INTERVAL 1 MONTH AND NOW())\n" +
"GROUP BY customerID;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query2(String color)
    {        
        String sqlQuery = "SELECT firstName, middleName, lastName, home, cellphone, make, model, model_year, date_of_sale\n" +
"FROM joined_phones NATURAL JOIN persons NATURAL JOIN customers NATURAL JOIN sales NATURAL JOIN cars NATURAL JOIN car_styles cs\n" +
"WHERE cars.color = ‘" + color + "’ AND customerID NOT IN\n" +
"    (SELECT customerID FROM sales WHERE date_of_sale BETWEEN NOW() - INTERVAL 3 YEAR AND NOW())\n" +
"GROUP BY customerID\n" +
"ORDER BY date_of_sale DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query3(String color)
    {
        String sqlQuery = "SELECT firstName, middleName, lastName, home, cellphone, make, model, model_year, date_of_sale\n" +
"FROM joined_phones NATURAL JOIN persons NATURAL JOIN customers NATURAL JOIN sales NATURAL JOIN cars NATURAL JOIN car_styles\n" +
"WHERE cars.color = '" + color + "’ AND customerID IN\n" +
"    (SELECT customerID FROM sales WHERE date_of_sale BETWEEN NOW() - INTERVAL 3 YEAR AND NOW())\n" +
"GROUP BY customerID\n" +
"ORDER BY date_of_sale DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query4()
    {
        String sqlQuery = "SELECT firstName, middleName, lastName, home, cellphone, make, model, model_year, date_of_sale\n" +
"FROM joined_phones NATURAL JOIN persons NATURAL JOIN customers NATURAL JOIN sales NATURAL JOIN car_styles\n" +
"ORDER BY date_of_sale DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query5()
    {
        String sqlQuery = "SELECT firstName, middleName, lastName, department, unused_vacation_days\n" +
"FROM persons NATURAL JOIN employees\n" +
"WHERE unused_vacation_days > 0;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query6()
    {
        String sqlQuery = "SELECT employee_id, firstName, middleName, lastName, mo_salary, COUNT(technician_credentials) as numberOfCertificates\n" +
"FROM persons NATURAL JOIN employees NATURAL JOIN techncians\n" +
"GROUP BY employee_id\n" +
"ORDER BY mo_salary DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query7()
    {
        String sqlQuery = "SELECT firstName, middleName, lastName, COUNT(*) as salesCount \n" +
"FROM persons NATURAL JOIN employees NATURAL JOIN salespersons\n" +
"WHERE employeeID IN \n" +
"    (SELECT employeeID FROM sales NATURAL JOIN employees WHERE date_of_sale BETWEEN NOW() - INTERVAL 1 MONTH AND NOW())\n" +
"GROUP BY employeeID\n" +
"ORDER BY salesCount DESC\n" +
"LIMIT 3;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query8()
    {
        String sqlQuery = "SELECT firstName, middleName, lastName, SUM(price) as grossSales\n" +
"  FROM persons pe\n" +
"    INNER JOIN employees e ON pe.personID = e.personID\n" +
"    INNER JOIN salespersons s ON e.employeeID = s.employeeID\n" +
"    INNER JOIN sales s2 ON s.employeeID = s2.salespersonID\n" +
"WHERE date_of_sale >= DATE_SUB(NOW(), INTERVAL 1 MONTH)\n" +
"GROUP BY e.employeeID\n" +
"ORDER BY grossSales DESC\n" +
"LIMIT 3;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query9()
    {
        String sqlQuery = "SELECT firstName, middleName, lastName, COUNT(DISTINCT customerID) as repeatedSales\n" +
"    FROM persons pe\n" +
"        INNER JOIN employees e ON pe.personID = e.personID\n" +
"        INNER JOIN salespersons s ON e.employeeID = s.employeeID\n" +
"        INNER JOIN sales s2 ON s.employeeID = s2.salespersonID\n" +
"WHERE date_of_sale >= DATE_SUB(NOW(), INTERVAL 1 MONTH)\n" +
"GROUP BY e.employeeID\n" +
"ORDER BY repeatedSales ASC\n" +
"LIMIT 3;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query10()
    {
        String sqlQuery = "SELECT make, model, model_year, COUNT(styleID) as numberOfCars\n" +
"FROM sales NATURAL JOIN cars NATURAL JOIN car_styles\n" +
"WHERE date_of_sale >= DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
"GROUP BY make, model\n" +
"ORDER BY model_year ASC\n" +
"LIMIT 5;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query11()
    {
        String sqlQuery = "SELECT make, model, model_year, COUNT(*) as numberOfSales\n" +
"FROM sales NATURAL JOIN cars NATURAL JOIN car_styles cs\n" +
"WHERE cs.fuel_type = \"electric\" AND sales.date_of_sale >= DATE_SUB(NOW(), INTERVAL 1 YEAR)\n" +
"GROUP BY make, model\n" +
"ORDER BY numberOfSales DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query12()
    {
        String sqlQuery = "SELECT make, model, model_year, COUNT(*) as numberOfCars\n" +
"FROM sales s NATURAL JOIN cars NATURAL JOIN car_styles cs\n" +
"WHERE cs.fuel_type != \"gasoline\" AND cs.fuel_type != \"hybrid\"\n" +
"GROUP BY cs.fuel_type\n" +
"ORDER BY make DESC, model DESC, model_year DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query13()
    {
        String sqlQuery = "SELECT date_of_sale\n" +
"FROM sales NATURAL JOIN cars NATURAL JOIN car_styles \n" +
"WHERE body_style = \"convertible\";";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query14(String make, String model)
    {
        String sqlQuery = "SELECT make, model, model_year, MAX(invoice_price) as highestPrice, MIN(invoice_price) as lowestPrice, COUNT(*) as numberOfCars\n" +
"FROM cars NATURAL JOIN car_styles cs\n" +
"WHERE cs.make = \"" + make + "\" AND cs.model = \"" + model + "\"\n" +
"ORDER BY make DESC, model DESC, model_year DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    public ResultSet query15(String make, String model, String color)
    {
        String sqlQuery = "SELECT make, model, model_year, MAX(invoice_price) as highestPrice, MIN(invoice_price) as lowestPrice, COUNT(*) as numberOfCars\n" +
"FROM cars NATURAL JOIN car_styles cs\n" +
"WHERE cs.make = \"" + make + "\" AND cs.model = \"" + model + "\" AND cars.color = \"" + color + "\"\n" +
"ORDER BY make DESC, model DESC, model_year DESC;";
        return executeSQLQuery(sqlQuery);
    }
}

