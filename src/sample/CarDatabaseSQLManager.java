package sample;

import java.math.BigDecimal;
import java.sql.*;

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
    
    public void createNewSale(int salespersonID, String firstName, String lastName, String middleName, String address,
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
        
    }
    
    public void createSaleWithLoan(int salespersonID, String firstName, String lastName, String middleName,
                                   String address, String zip, String emailAddress, Date dateOfSale, BigDecimal price,
                                   BigDecimal tradeInValue, String VIN, String socialSecurityNumber, Date dateOfLoan,
                                   BigDecimal principal, int loanLength, Date dateOfLastPayment,
                                   BigDecimal monthlyPayment)
    {   int customerID = getCustomerID(firstName, lastName, middleName, zip);
        createNewSale(salespersonID, firstName, lastName, middleName, address, zip, emailAddress, dateOfSale, price,
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
}

