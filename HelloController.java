package com.example.test3;

import com.example.test3.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    public TextField iid;
    public TextField iCustomerName;
    public TextField iMobileNumber;
    private AnchorPane iPizzaSize;
    public TextField iToppings;

    public enum PizzaSize {
        XL, L, M, S
    }

    // Define Checkboxes for Pizza Sizes
    @FXML
    private CheckBox xlCheckbox;
    @FXML
    private CheckBox lCheckbox;
    @FXML
    private CheckBox mCheckbox;
    @FXML
    private CheckBox sCheckbox;

    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, Integer> id;
    @FXML
    private TableColumn<Order, String> CustomerName;
    @FXML
    private TableColumn<Order, String> MobileNumber;
    @FXML
    private TableColumn<Order, String> PizzaSize;
    @FXML
    private TableColumn<Order, Integer> Toppings;
    @FXML
    private TableColumn<Order, Double> TotalBill;  // Add column for total bill calculation

    ObservableList<Order> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<Order, String>("CustomerName"));
        MobileNumber.setCellValueFactory(new PropertyValueFactory<Order, String>("MobileNumber"));
        PizzaSize.setCellValueFactory(new PropertyValueFactory<Order, String>("PizzaSize"));
        Toppings.setCellValueFactory(new PropertyValueFactory<Order, Integer>("Toppings"));
        TotalBill.setCellValueFactory(new PropertyValueFactory<Order, Double>("totalBill")); // Bind the totalBill property
        tableView.setItems(list);
    }

    @FXML
    protected void onHelloButtonClick() {
        list.clear();
        tableView.setItems(list);
        populateTable();
    }

    public void populateTable() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/test3";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM orders";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String CustomerName = resultSet.getString("CustomerName");
                String MobileNumber = resultSet.getString("MobileNumber");
                String PizzaSizeStr = resultSet.getString("PizzaSize");
                int Toppings = resultSet.getInt("Toppings");

                // Safely convert the pizza size string to the PizzaSize enum, with a fallback if the string is invalid
                Order.PizzaSize pizzaSizeEnum = null;
                try {
                    // Convert to upper case before enum conversion
                    pizzaSizeEnum = Order.PizzaSize.valueOf(PizzaSizeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Default to Medium if invalid pizza size in DB
                    pizzaSizeEnum = Order.PizzaSize.MEDIUM;
                    System.err.println("Invalid PizzaSize value in DB: " + PizzaSizeStr + ". Defaulting to Medium.");
                }

                // Create order object
                Order order = new Order(id, CustomerName, MobileNumber, pizzaSizeEnum, Toppings);

                // Add the order to the table view
                tableView.getItems().add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void LoadData(ActionEvent actionEvent) {
        String getid = iid.getText();
        String jdbcUrl = "jdbc:mysql://localhost:3306/test3";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM orders WHERE `id`= '" + getid + "' ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String CustomerName = resultSet.getString("CustomerName");
                String MobileNumber = resultSet.getString("MobileNumber");
                String PizzaSizeStr = resultSet.getString("PizzaSize");
                String Toppings = resultSet.getString("Toppings");

                iCustomerName.setText(CustomerName);
                iMobileNumber.setText(MobileNumber);
                iToppings.setText(Toppings);

                // Set the correct pizza size checkbox based on the database value
                if (PizzaSizeStr != null) {
                    xlCheckbox.setSelected("XL".equals(PizzaSizeStr));
                    lCheckbox.setSelected("L".equals(PizzaSizeStr));
                    mCheckbox.setSelected("M".equals(PizzaSizeStr));
                    sCheckbox.setSelected("S".equals(PizzaSizeStr));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteData(ActionEvent actionEvent) {
        String getid = iid.getText();
        String jdbcUrl = "jdbc:mysql://localhost:3306/test3";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM orders WHERE `id`= '" + getid + "' ";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertData(ActionEvent actionEvent) {
        String getid = iid.getText();
        String getCustomerName = iCustomerName.getText();
        String getMobileNumber = iMobileNumber.getText();
        String getToppings = iToppings.getText();

        // Determine the selected pizza size
        String pizzaSize = "";
        if (xlCheckbox.isSelected()) {
            pizzaSize = "XL";
        } else if (lCheckbox.isSelected()) {
            pizzaSize = "L";
        } else if (mCheckbox.isSelected()) {
            pizzaSize = "M";
        } else if (sCheckbox.isSelected()) {
            pizzaSize = "S";
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/test3";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO `orders`(`CustomerName`, `MobileNumber`, `PizzaSize`, `Toppings`) " +
                    "VALUES ('" + getCustomerName + "','" + getMobileNumber + "','" + pizzaSize + "','" + getToppings + "')";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateData(ActionEvent actionEvent) {
        String getid = iid.getText();
        String getCustomerName = iCustomerName.getText();
        String getMobileNumber = iMobileNumber.getText();
        String getToppings = iToppings.getText();

        // Determine the selected pizza size
        String pizzaSize = "";
        if (xlCheckbox.isSelected()) {
            pizzaSize = "XL";
        } else if (lCheckbox.isSelected()) {
            pizzaSize = "L";
        } else if (mCheckbox.isSelected()) {
            pizzaSize = "M";
        } else if (sCheckbox.isSelected()) {
            pizzaSize = "S";
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/test3";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE `orders` SET `CustomerName`='" + getCustomerName + "', `MobileNumber`='" + getMobileNumber + "', " +
                    "`PizzaSize`='" + pizzaSize + "', `Toppings`='" + getToppings + "' WHERE `id` = '" + getid + "'";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
