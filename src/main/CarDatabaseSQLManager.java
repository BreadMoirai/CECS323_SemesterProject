package main;

import java.math.BigDecimal;
import java.sql.*;

/**
 * The controller for the query, update, and insert of the car dealership mysql database cecs323sec5g5
 */
public class CarDatabaseSQLManager
{
    private static CarDatabaseSQLManager singletonInstance;
    private static Connection con;
    private CarDatabaseSQLManager() throws ClassNotFoundException, SQLException
    {
    
        String DB_URL = "jdbc:mysql://cecs-db01.coe.csulb.edu/cecs323sec5g5";
        String USER = "cecs323sec5n11";
        String PASS = "ai4tah";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(DB_URL, USER, PASS);
        con.setAutoCommit(false);
    }
    
    /**
     * Gets the instance of CarDatabaseManager or initializes it if called for the first time
     * @return An Instance of the CarDatabaseSQLManager
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static CarDatabaseSQLManager getInstance() throws SQLException, ClassNotFoundException
    {
        if (singletonInstance == null)
        {
            singletonInstance = new CarDatabaseSQLManager();
        }
        
        return singletonInstance;
    }
    
    /**
     * Method to add a row to the persons table.
     * @param lastName First name of person to be inputted
     * @param firstName Last name of person to be inputted
     * @param middleName Middle name of person to be inputted
     * @param address Street address of person
     * @param zip Zip code of person
     * @return The person's newly generated personID, their index in the database. Returns -1 if it fails.
     */
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
    
    /**
     * Method to add a row to phone_number table
     * @param personID Person's index in person table
     * @param phoneType The type of phone. Can be 'Home', 'Cellphone', or 'Work'
     * @param phoneNumber The phone number to be entered
     */
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
    
    /**
     * The method to add a sale
     * @param salespersonID The salesperson's ID from database
     * @param customerID The customer's ID from database
     * @param dateOfSale The Date this sale was finalized
     * @param price The price the car was sold at
     * @param tradeInValue The price of the car traded in during the sale
     * @param VIN The id of the car
     */
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
    
    /**
     * The method to add a car style to car_styles
     * @param make The manufacturer of the car
     * @param model The name of the product line for this car
     * @param modelYear The year this car was made for
     * @param bodyStyle The type of layout i.e. sedan, hatchback, convertible, etc...
     * @param engineSize The volume of engine in liters
     * @param fuelType The energy source i.e. gasoline, electric, hybrid, hydrogen
     */
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
    
    /**
     * Adds a row to the car_updgrades table
     * @param VIN The id of car
     * @param upgradeName Type of upgrade. 'leather seats', 'sunroof' etc.
     */
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
    
    /**
     * Adds a row to the cars table
     * @param VIN id of car
     * @param styleID id of car_style from table car_styles
     * @param invoicePrice Price of car wholesale
     * @param retailPrice Price of car at retail
     * @param color The color of the car
     * @param driving_range The maximum distance he car can travel without refueling
     * @param warranty The warranty of the car
     */
    public void addCar(String VIN, int styleID, BigDecimal invoicePrice, BigDecimal retailPrice,
                        String color, int driving_range, String warranty)
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
            preparedStatement.setString(7, warranty);
    
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    
    /**
     * Adds a row to the customers table
     * @param personID The id generated from inserting a person to persons
     * @param emailAddr The email of customer
     * @return The automaticallly generated ID of customers. Returns -1 if it fails
     */
    public int addCustomer(int personID, String emailAddr)
    {
        String updateStatement = "Insert INTO customers (personID, email_addr) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement,
                Statement.RETURN_GENERATED_KEYS))
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
    
    
    /**
     * Adds a row to dependents table
     * @param employeeID The ID of employee from employees who has dependent.
     * @param personID The ID of person from persons of dependent.
     * @param socialSecurityNumber The social security number of dependent.
     */
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
    
    
    /**
     * Adds a row to employees table
     * @param personID The person id from persons
     * @param hireDate The Date when employee was hired
     * @param moSalary The monthly salary of employee
     * @param unusedVacationDays The number of unused vacation days of employee
     * @param socialSecurityNumber The social security number of employee
     * @param department The department the employee works in
     * @param emergencyContact The person who is the employee's contact in case of emergency
     * @return The automatically generated id made when employee is inputted
     */
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
    
    /**
     * Adds a row to the loans table
     * @param VIN The id of car
     * @param customerID The id of customer from customers
     * @param dateOfLoan The Date the loan is finalized
     * @param principal The starting amount of loan
     * @param loanLength The length of loan in years
     * @param dateOfLastPayment The Date of calculated last payment
     * @param monthlyPayment The amount the customer is to pay monthly
     * @param socialSecurityNumber The customer's social security number
     */
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
    
    /**
     * Adds a row to managers table.
     * @param employeeID The employeeID of manager.
     * @param pastAssignements The manager's previous assignment.
     */
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
    
    /**
     * Adds a row to the salespersons class.
     * @param employeeInt The employeeID of salespersons from employees.
     * @param commissionRate The commission rate of the salesperson makes each sale.
     * @return The automatically generated id of salesperson. Returns -1 if this fails.
     */
    public int addSalesperson(int employeeInt, double commissionRate)
    {
        String updateStatement = "INSERT INTO salespersons VALUES (?, ?)";
        
        try (PreparedStatement preparedStatement = con.prepareStatement(updateStatement,
                Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setInt(1, employeeInt);
            preparedStatement.setDouble(2, commissionRate);
    
            return preparedStatement.executeUpdate();
            
        } catch (SQLException e)
        {
            System.out.println(e);
            return -1;
        }
    }
    
    /**
     * Adds a row to technicians
     * @param employeeInt The employee id of technician from employees
     * @param technicianCredential The credential of technician.
     */
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
    
    /**
     * Adds a row to used_cars
     * @param VIN The id of car.
     * @param license_plate The license_plate of car. May be null.
     * @param mileage The current mileage from odometer.
     * @param carCondition The current condition of car.
     */
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
    
    /**
     * Creates a new sale based on customer's details. Checks if customer is in database and adds a new customer
     * if they are not.
     * @param salespersonID ID of salesperson from salespersons.
     * @param firstName First name of customer.
     * @param lastName Last name of customer.
     * @param middleName Middle name of customer.
     * @param address Address of customer.
     * @param zip Zip code of customer.
     * @param emailAddress Email address of customer.
     * @param dateOfSale Date this sale was finalized.
     * @param price The price of the car sold.
     * @param tradeInValue The value of car traded in for this sale.
     * @param VIN The id of car.
     * @return The id of the customer either generated or already in table.
     */
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
    
    /**
     * Creates a new sale by calling createNewSale() then creates a loan.
     * @param salespersonID ID of salesperson from salespersons.
     * @param firstName First name of customer.
     * @param lastName Last name of customer.
     * @param middleName Middle name of customer.
     * @param address Address of customer.
     * @param zip Zip code of customer.
     * @param emailAddress Email address of customer.
     * @param dateOfSale Date this sale was finalized.
     * @param price The price of the car sold.
     * @param tradeInValue The value of car traded in for this sale.
     * @param VIN The id of car.
     * @param socialSecurityNumber The social security number of customer.
     * @param dateOfLoan The date the loan was finalized.
     * @param principal The starting amount of loan.
     * @param loanLength The length of loan in years.
     * @param dateOfLastPayment The date of last payment.
     * @param monthlyPayment The amount the user pays each month on loan.
     */
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
    
    /**
     * Returns the customer id obtained from a query or -1 if not found.
     * @param firstName Customer's first name
     * @param lastName Customer's last name
     * @param middleName Customer's middle name
     * @param zip Customer's zip code.
     * @return customer's id or -1
     */
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
    
    /**
     * Changes all values in column unused_vacation_days in employees to 0.
     */
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
    
    /**
     * Commits the changes that have been added so that it is all one transaction.
     */
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
    
    /**
     * Executes the given query
     * @param sqlQuery The query to be executed
     * @return The Result Set of SQL query
     */
    private ResultSet executeSQLQuery(String sqlQuery)
    {
        try {PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
        
            return preparedStatement.executeQuery();
        } catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }
    
    /*
    *   Query execution for:
    *   1. All prospective customers who visited the dealership but have not made a purchase with your
    *       dealership within the past month (full name, mailing address, home phone, cell phone)
    */
    public ResultSet query1()
    {
        String sqlQuery =   "SELECT firstName, middleName, lastName, address, zip, home, cellphone\n" +
                            "FROM joined_phones NATURAL JOIN persons NATURAL JOIN customers c NATURAL JOIN sales\n" +
                            "WHERE customerID NOT IN\n" +
                            "  (SELECT customerID FROM sales WHERE date_of_sale BETWEEN NOW() - INTERVAL 1 MONTH AND NOW())\n" +
                            "GROUP BY customerID;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for: 
    *   2. Customers who had not purchased a vehicle of a certain color (user input) within the past 3 to 5
    *       years (full name, home phone, cell phone, make, model, year (of car), last purchase date: sorted
    *       descending by purchase date)
    *   @param color
    */
    public ResultSet query2(String color)
    {        
        String sqlQuery =   String.format("SELECT firstName, middleName, lastName, home, cellphone, make, model, model_year, date_of_sale  FROM customers\n" +
                "  INNER JOIN sales s ON customers.customerID = s.customerID\n" +
                "  INNER JOIN cars c ON s.VIN = c.VIN\n" +
                "  INNER JOIN persons p ON customers.personID = p.personID\n" +
                "  INNER JOIN joined_phones jp ON jp.personID = s.customerID\n" +
                "  INNER JOIN car_styles cs ON c.styleID = cs.styleID\n" +
                "WHERE date_of_sale >= DATE_SUB(NOW(), INTERVAL 5 YEAR)\n" +
                "AND  date_of_sale <= DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                "AND s.VIN NOT IN   (SELECT c2.VIN FROM customers cu2\n" +
                "  INNER JOIN sales s2 ON cu2.customerID = s2.customerID\n" +
                "  INNER JOIN cars c2 ON s2.VIN = c2.VIN\n" +
                "WHERE date_of_sale >= DATE_SUB(NOW(), INTERVAL 5 YEAR)\n" +
                "      AND  date_of_sale <= DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                "      AND color = '%s')\n", color);
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   3. Customers who had purchased a vehicle of a certain color (user input) within the past 3 to 5
    *       years (full name, home phone, cell phone, make, model, year (of car), last purchase date: sorted
    *       descending by purchase date)
    *   @param color
    */
    public ResultSet query3(String color)
    {
        String sqlQuery =   String.format(
                "SELECT firstName, middleName, lastName, home, cellphone, make, model, model_year, date_of_sale  " +
                        "FROM customers\n" +
                "  INNER JOIN sales s ON customers.customerID = s.customerID\n" +
                "  INNER JOIN cars c ON s.VIN = c.VIN\n" +
                "  INNER JOIN persons p ON customers.personID = p.personID\n" +
                "  INNER JOIN joined_phones jp ON jp.personID = s.customerID\n" +
                "  INNER JOIN car_styles cs ON c.styleID = cs.styleID\n" +
                "WHERE date_of_sale >= DATE_SUB(NOW(), INTERVAL 5 YEAR)\n" +
                "      AND  date_of_sale <= DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                "      AND color = '%s'\n" +
                "ORDER BY date_of_sale DESC", color);
        return executeSQLQuery(sqlQuery);
    }
    
    /* 
    *   Query execution for:
    *   4. Frequent customers who make a purchase on average every 2 years or less (full name, home
    *       phone, cell phone, make, model, year (of car), last purchase date: sorted descending by
    *       purchase date)
    */
    public ResultSet query4()
    {
        String sqlQuery =   "SELECT firstName, middleName, lastName, home, cellphone, make, model, model_year, date_of_sale\n" +
                            "FROM joined_phones NATURAL JOIN persons NATURAL JOIN customers NATURAL JOIN sales NATURAL JOIN car_styles\n" +
                            "ORDER BY date_of_sale DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   5. Employees with unused vacation time (full name, department, unused days)
    */
    public ResultSet query5()
    {
        String sqlQuery =   "SELECT firstName, middleName, lastName, department, unused_vacation_days\n" +
                            "FROM persons NATURAL JOIN employees\n" +
                            "WHERE unused_vacation_days > 0;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   6. Pay rates of all technicians possessing certificates (full name, pay rates, number of certificates:
    *       sorted descending by pay rates)
    */
    public ResultSet query6()
    {
        String sqlQuery =   "SELECT employee_id, firstName, middleName, lastName, mo_salary, COUNT(technician_credentials) as numberOfCertificates\n" +
                            "FROM persons NATURAL JOIN employees NATURAL JOIN techncians\n" +
                            "GROUP BY employee_id\n" +
                            "ORDER BY mo_salary DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   7. Top three salespeople with the highest number of sales in the past 30 days (full name, number
    *       of sales)
    */
    public ResultSet query7()
    {
        String sqlQuery =   "SELECT firstName, middleName, lastName, COUNT(*) as salesCount \n" +
                            "FROM persons NATURAL JOIN employees NATURAL JOIN salespersons\n" +
                            "WHERE employeeID IN \n" +
                            "    (SELECT employeeID FROM sales NATURAL JOIN employees WHERE date_of_sale BETWEEN NOW() - INTERVAL 1 MONTH AND NOW())\n" +
                            "GROUP BY employeeID\n" +
                            "ORDER BY salesCount DESC\n" +
                            "LIMIT 3;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   8. Top three salespeople with the highest gross sales ($$$) in the past 30 days (full name, gross
    *       sales)
    */
    public ResultSet query8()
    {
        String sqlQuery =   "SELECT firstName, middleName, lastName, SUM(price) as grossSales\n" +
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
    
    /*
    *   Query execution for:
    *   9. Top three salespeople with the most number of repeated sales to the same (loyal) customers
    *       (full name, number of repeated sales)
    */
    public ResultSet query9()
    {
        String sqlQuery =   "SELECT firstName, middleName, lastName, COUNT(DISTINCT customerID) as repeatedSales\n" +
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
    
    /*
    *   Query execution for:
    *   10. Top five most popular car models in the past 3 years (make, model, year, number of cars: sorted
    *       ascending by year)
    */
    public ResultSet query10()
    {
        String sqlQuery =   "SELECT make, model, model_year, COUNT(styleID) as numberOfCars\n" +
                            "FROM sales NATURAL JOIN cars NATURAL JOIN car_styles\n" +
                            "WHERE date_of_sale >= DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                            "GROUP BY make, model\n" +
                            "ORDER BY model_year ASC\n" +
                            "LIMIT 5;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   11. All electric cars sold within the last year (make, model, year, number of cars: sorted descending
    *       by count)
    */
    public ResultSet query11()
    {
        String sqlQuery =   "SELECT make, model, model_year, COUNT(*) as numberOfSales\n" +
                            "FROM sales NATURAL JOIN cars NATURAL JOIN car_styles cs\n" +
                            "WHERE cs.fuel_type = \"electric\" AND sales.date_of_sale >= DATE_SUB(NOW(), INTERVAL 1 YEAR)\n" +
                            "GROUP BY make, model\n" +
                            "ORDER BY numberOfSales DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   12. All non-fossil fuel cars sold within the last year (make, model, year, number of cars in each
    *       row: sorted descending by make and model and year)
    */
    public ResultSet query12()
    {
        String sqlQuery =   "SELECT make, model, model_year, COUNT(*) as numberOfCars\n" +
                            "FROM sales s NATURAL JOIN cars NATURAL JOIN car_styles cs\n" +
                            "WHERE cs.fuel_type != \"gasoline\" AND cs.fuel_type != \"hybrid\"\n" +
                            "GROUP BY cs.fuel_type\n" +
                            "ORDER BY make DESC, model DESC, model_year DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   13. The month with the highest number of convertible cars sold (month, number of cars) 
    */
    public ResultSet query13()
    {
        String sqlQuery =   "SELECT date_of_sale\n" +
                            "FROM sales NATURAL JOIN cars NATURAL JOIN car_styles \n" +
                            "WHERE body_style = \"convertible\";";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   14. List of cars of a certain (user input) make and model (model, make, year, highest price, lowest
    *       price, number of cars in each row: sorted descending make, model and year)
    *   @param make
    *   @param model
    */
    public ResultSet query14(String make, String model)
    {
        String sqlQuery =   "SELECT make, model, model_year, MAX(invoice_price) as highestPrice, MIN(invoice_price) as lowestPrice, COUNT(*) as numberOfCars\n" +
                            "FROM cars NATURAL JOIN car_styles cs\n" +
                            "WHERE cs.make = \"" + make + "\" AND cs.model = \"" + model + "\"\n" +
                            "ORDER BY make DESC, model DESC, model_year DESC;";
        return executeSQLQuery(sqlQuery);
    }
    
    /*
    *   Query execution for:
    *   15. List of cars of a certain (user input) make, model and color (model, make, year, color, price:
    *       sorted descending by year)
    *   @param make
    *   @param model
    *   @param color
    */
    public ResultSet query15(String make, String model, String color)
    {
        String sqlQuery =   "SELECT make, model, model_year, MAX(invoice_price) as highestPrice, MIN(invoice_price) as lowestPrice, COUNT(*) as numberOfCars\n" +
                            "FROM cars NATURAL JOIN car_styles cs\n" +
                            "WHERE cs.make = \"" + make + "\" AND cs.model = \"" + model + "\" AND cars.color = \"" + color + "\"\n" +
                            "ORDER BY make DESC, model DESC, model_year DESC;";
        return executeSQLQuery(sqlQuery);
    }
}

