package com.example.kursinisprojektas.fxControllers;

import com.example.kursinisprojektas.HelloApplication;
import com.example.kursinisprojektas.hibernateControllers.*;
import com.example.kursinisprojektas.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    @FXML
    public ListView<Product> productList;
    @FXML
    public TextField productTitle;
    @FXML
    public TextArea productDescription;
    @FXML
    public RadioButton consoleRadio;
    @FXML
    public RadioButton gameRadio;
    @FXML
    public RadioButton accessoryRadio;
    public TextField qtyField;
    public ComboBox<consolePlatform> consoleType;
    public TextField consoleManufacturer;
    public DatePicker releaseDate;
    public ToggleGroup productType;
    public ComboBox<consolePlatform> gamePlatform;
    public ComboBox<videogameCategory> gameCategory;
    public TextField accessoryType;
    public TextField accessoryPlatform;
    public TextField accessoryManufacturer;
    public TextField gamePublisher;
    public TextField userName;
    public TextField userSurname;
    public TextField userLogin;
    public TextField userPassword;
    public DatePicker customerDOB;
    public TextField customerBilling;
    public TextField customerDelivery;
    public RadioButton isAdmin;
    public TextField customerCard;
    public Tab warehouseTab;
    public Tab usersTab;
    public Tab productTab;
    public Tab ordersTab;
    public Tab shopTab;
    public TabPane tabPane;
    public TableView<UserTableParameters> userTable;
    public TableColumn<UserTableParameters, Integer> userColId;
    public TableColumn<UserTableParameters, String> userColType;
    public TableColumn<UserTableParameters, String> userColName;
    public TableColumn<UserTableParameters, String> userColSurname;
    public TableColumn<UserTableParameters, String> userColLogin;
    public TableColumn<UserTableParameters, String> userColPassword;
    public TableColumn<UserTableParameters, Date> userColDate;
    public ToggleGroup userType;
    public RadioButton isManager;
    public RadioButton isCustomer;
    public ListView<Product> shopList;
    public ListView<Product> cartList;
    public ListView<Cart> customerOrders;
    public ListView<Product> orderContents;
    public ListView<Manager> employeeList;
    public ComboBox<OrderStatus> orderStatus;
    public TextField employeeID;
    public Button addUserButton;
    public Button editUserButton;
    public Button deleteUserButton;
    public Button editOrderButton;
    public ComboBox<Warehouse> selectWarehouse;
    public ListView<Manager> freeEmployeeList;
    public ListView<Manager> warehouseEmployeeList;
    public ListView<Product> freeProductList;
    public ListView<Product> warehouseProductList;
    public ListView<Cart> rushOrders;
    public Button orderHistoryButton;
    public ComboBox<City> cityBox;
    public TextField addressField;
    public DatePicker dateFieldFilter;
    public Button filterButton;
    public Button removeFilterButton;
    private ObservableList<UserTableParameters> data = FXCollections.observableArrayList();
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private GenericHibernate genericHibernate;
    private ShopHibernate shopHibernate;
    private OrderHibernate orderHibernate;
    private WarehouseHibernate warehouseHibernate;
    private User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        this.shopHibernate = new ShopHibernate(entityManagerFactory);
        this.orderHibernate = new OrderHibernate(entityManagerFactory);
        this.warehouseHibernate = new WarehouseHibernate(entityManagerFactory);
        productList.getItems().clear();
        productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
        shopList.getItems().clear();
        shopList.getItems().addAll(shopHibernate.loadAvailableProducts());
        selectWarehouse.getItems().addAll(genericHibernate.getAllRecords(Warehouse.class));
        freeProductList.getItems().addAll(warehouseHibernate.loadAvailableProducts());
        freeEmployeeList.getItems().addAll(warehouseHibernate.loadAvailableEmployees());
        this.user = user;
        setCustomerView();
        fillUserTable();
    }
    EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    //<editor-fold desc="SHOP TAB LOGIC">
    public void loadReviewForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("product-review.fxml"));
        Parent parent = fxmlLoader.load();
        //Access controller of main window. Each form has its own controller, so make sure that you make no mistake here
        ProductReview productReview = fxmlLoader.getController();
        productReview.setData(entityManagerFactory, user);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Product Reviews");
        FxUtils.setStageParameters(stage, scene, false);
    }

    public void addCart() {
        Product product = shopList.getSelectionModel().getSelectedItem();
        cartList.getItems().add(product);
        shopList.getItems().remove(product);

    }

    public void removeCart() {
        Product product = cartList.getSelectionModel().getSelectedItem();
        shopList.getItems().add(product);
        cartList.getItems().remove(product);
    }

    public void buyItems() {
        shopHibernate.createCart(cartList.getItems(), user);
        cartList.getItems().clear();
    }
    //</editor-fold>

    //<editor-fold> desc="PRODUCT TAB LOGIC"
    public void createProduct() {
        if (consoleRadio.isSelected()) {
            Console console = new Console(
                    productTitle.getText(),
                    productDescription.getText(),
                    Integer.parseInt(qtyField.getText()),
                    consoleType.getValue(),
                    consoleManufacturer.getText(),
                    releaseDate.getValue());
            genericHibernate.create(console);
            productList.getItems().clear();
            productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
        }
        if (gameRadio.isSelected()) {
            Videogame videogame = new Videogame(
                    productTitle.getText(),
                    productDescription.getText(),
                    Integer.parseInt(qtyField.getText()),
                    gamePlatform.getValue(),
                    gameCategory.getValue(),
                    gamePublisher.getText(),
                    releaseDate.getValue());
            productList.getItems().add(videogame);
            genericHibernate.create(videogame);
            productList.getItems().clear();
            productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
        }
        if (accessoryRadio.isSelected()) {
            Accessory accessory = new Accessory(
                    productTitle.getText(),
                    productDescription.getText(),
                    Integer.parseInt(qtyField.getText()),
                    accessoryType.getText(),
                    accessoryPlatform.getText(),
                    accessoryManufacturer.getText());
            productList.getItems().add(accessory);
            genericHibernate.create(accessory);
            productList.getItems().clear();
            productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
        }

    }

    public void updateProduct() {
        Product product = productList.getSelectionModel().getSelectedItem();
        product.setTitle(productTitle.getText());
        product.setDescription(productDescription.getText());
        product.setQty(Integer.parseInt(qtyField.getText()));
        if (product instanceof Console console) {
            ((Console) product).setConsoleManufacturer(consoleManufacturer.getText());
            ((Console) product).setConsoleType(consoleType.getValue());
            ((Console) product).setReleaseDate(releaseDate.getValue());
            genericHibernate.update(console);
            productList.getItems().clear();
            productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
        } else if (product instanceof Videogame videogame) {
            ((Videogame) product).setGamePlatform(gamePlatform.getValue());
            ((Videogame) product).setGameCategory(gameCategory.getValue());
            ((Videogame) product).setGamePublisher(gamePublisher.getText());
            ((Videogame) product).setReleaseDate(releaseDate.getValue());
            genericHibernate.update(videogame);
            productList.getItems().clear();
            productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
        } else if (product instanceof Accessory accessory) {
            ((Accessory) product).setAccessoryType(accessoryType.getText());
            ((Accessory) product).setPlatformType(accessoryPlatform.getText());
            ((Accessory) product).setAccessoryManufacturer(accessoryManufacturer.getText());
            genericHibernate.update(accessory);
            productList.getItems().clear();
            productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
        }
    }

    public void deleteProduct() {
        Product product = productList.getSelectionModel().getSelectedItem();
        productList.getItems().remove(product);
        genericHibernate.delete(Product.class, product.getId());
        productList.getItems().clear();
        productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
    }

    public void loadProductData() {
        Product product = productList.getSelectionModel().getSelectedItem();

        productTitle.setText(product.getTitle());
        productDescription.setText(product.getDescription());
        qtyField.setText(String.valueOf(product.getQty()));

        if (product instanceof Console) {
            Console console = genericHibernate.getEntityByID(Console.class, product.getId());
            consoleManufacturer.setText(((Console) product).getConsoleManufacturer());
            consoleType.setValue(((Console) product).getConsoleType());
            releaseDate.setValue(((Console) product).getReleaseDate());

            consoleRadio.setSelected(true);
            disableProductFields();
        } else if (product instanceof Videogame) {
            Videogame videogame = genericHibernate.getEntityByID(Videogame.class, product.getId());
            gamePlatform.setValue(((Videogame) product).getGamePlatform());
            gameCategory.setValue(((Videogame) product).getGameCategory());
            gamePublisher.setText(((Videogame) product).getGamePublisher());
            releaseDate.setValue(((Videogame) product).getReleaseDate());

            gameRadio.setSelected(true);
            disableProductFields();
        } else if (product instanceof Accessory) {
            Accessory accessory = genericHibernate.getEntityByID(Accessory.class, product.getId());
            accessoryType.setText(((Accessory) product).getAccessoryType());
            accessoryPlatform.setText(((Accessory) product).getAccessoryType());
            accessoryManufacturer.setText(((Accessory) product).getAccessoryManufacturer());

            accessoryRadio.setSelected(true);
            disableProductFields();
        }
    }

    public void disableProductFields() {
        productTitle.setVisible(true);
        productDescription.setVisible(true);
        qtyField.setVisible(true);
        if (consoleRadio.isSelected()) {
            consoleType.setVisible(true);
            consoleManufacturer.setVisible(true);
            releaseDate.setVisible(true);

            gamePlatform.setVisible(false);
            gameCategory.setVisible(false);
            gamePublisher.setVisible(false);
            accessoryType.setVisible(false);
            accessoryPlatform.setVisible(false);
            accessoryManufacturer.setVisible(false);
        } else if (gameRadio.isSelected()) {
            consoleType.setVisible(false);
            consoleManufacturer.setVisible(false);

            releaseDate.setVisible(true);

            gamePlatform.setVisible(true);
            gameCategory.setVisible(true);
            gamePublisher.setVisible(true);

            accessoryType.setVisible(false);
            accessoryPlatform.setVisible(false);
            accessoryManufacturer.setVisible(false);
        } else if (accessoryRadio.isSelected()) {
            consoleType.setVisible(false);
            consoleManufacturer.setVisible(false);
            releaseDate.setVisible(false);
            gamePlatform.setVisible(false);
            gameCategory.setVisible(false);
            gamePublisher.setVisible(false);

            accessoryType.setVisible(true);
            accessoryPlatform.setVisible(true);
            accessoryManufacturer.setVisible(true);
        }
    }

    //</editor-fold> desc

    //<editor-fold> desc="USER TAB LOGIC"
    private void fillUserTable() {
        data.clear();
        userTable.setItems(data);
        List<User> users = genericHibernate.getAllRecords(User.class);
        for (User m : users) {
            UserTableParameters userTableParameters = new UserTableParameters();
            userTableParameters.setId(m.getId());
            userTableParameters.setType(m.getClass().getSimpleName());
            userTableParameters.setName(m.getName());
            userTableParameters.setSurname(m.getSurname());
            userTableParameters.setLogin(m.getLogin());
            userTableParameters.setPassword(m.getPassword());
            userTableParameters.setDate(m.getDateCreated());
            data.add(userTableParameters);
        }
        userTable.setItems(null);
        userTable.setItems(data);
    }

    public void addUser() {
        if (isCustomer.isSelected()) {
            User user = new Customer(
                    userName.getText(),
                    userSurname.getText(),
                    userLogin.getText(),
                    userPassword.getText(),
                    customerCard.getText(),
                    customerBilling.getText(),
                    customerDelivery.getText(),
                    customerDOB.getValue()
            );
            genericHibernate.create(user);
            fillUserTable();
        } else if (isManager.isSelected()) {
            User user = new Manager(
                    userName.getText(),
                    userSurname.getText(),
                    userLogin.getText(),
                    userPassword.getText(),
                    isAdmin.isSelected()
            );
            genericHibernate.create(user);
            fillUserTable();
        }
    }

    public void editUser() {
        Manager currentUser = genericHibernate.getEntityByID(Manager.class, user.getId());
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (currentUser.isAdmin()) {
            if (genericHibernate.getEntityByID(User.class, selectedUser.getId()).getClass().getSimpleName().equals("Customer")) {
                Customer customer = genericHibernate.getEntityByID(Customer.class, selectedUser.getId());
                customer.setName(userName.getText());
                customer.setSurname(userSurname.getText());
                customer.setLogin(userLogin.getText());
                customer.setPassword(userPassword.getText());
                customer.setCardNo(customerCard.getText());
                customer.setBirthDate(customerDOB.getValue());
                customer.setBillingAddress(customerBilling.getText());
                customer.setDeliveryAddress(customerDelivery.getText());
                genericHibernate.update(customer);
                fillUserTable();
            } else if (genericHibernate.getEntityByID(User.class, selectedUser.getId()).getClass().getSimpleName().equals("Manager")) {
                Manager manager = genericHibernate.getEntityByID(Manager.class, selectedUser.getId());
                manager.setName(userName.getText());
                manager.setSurname(userSurname.getText());
                manager.setLogin(userLogin.getText());
                manager.setPassword(userPassword.getText());
                manager.setAdmin(isAdmin.isSelected());
                genericHibernate.update(manager);
                fillUserTable();
            }
        } else if (currentUser.getId() == selectedUser.getId()) {
            currentUser.setName(userName.getText());
            currentUser.setSurname(userSurname.getText());
            currentUser.setLogin(userLogin.getText());
            currentUser.setPassword(userPassword.getText());
            genericHibernate.update(currentUser);
            fillUserTable();
        } else {
            FxUtils.generateAlert(Alert.AlertType.ERROR, "BAD CREDENTIALS", "Edit error", "Employees cannot edit other user information");
        }
    }

    public void deleteUser() {
        User user = userTable.getSelectionModel().getSelectedItem();
        userTable.getItems().remove(user);
        genericHibernate.delete(User.class, user.getId());
        fillUserTable();
    }

    public void loadUserData() {
        User user = userTable.getSelectionModel().getSelectedItem();
        userName.setText(user.getName());
        userSurname.setText(user.getSurname());
        userLogin.setText(user.getLogin());
        userPassword.setText(user.getPassword());
        if (genericHibernate.getEntityByID(User.class, user.getId()).getClass().getSimpleName().equals("Customer")) {
            Customer customer = genericHibernate.getEntityByID(Customer.class, user.getId());
            customerCard.setText(customer.getCardNo());
            customerDOB.setValue(customer.getBirthDate());
            customerBilling.setText(customer.getBillingAddress());
            customerDelivery.setText(customer.getDeliveryAddress());
            isCustomer.setSelected(true);
            isManager.setSelected(false);
            isAdmin.setSelected(false);
        } else if (genericHibernate.getEntityByID(User.class, user.getId()).getClass().getSimpleName().equals("Manager")) {
            Manager manager = genericHibernate.getEntityByID(Manager.class, user.getId());
            isManager.setSelected(true);
            isCustomer.setSelected(false);
            isAdmin.setSelected(manager.isAdmin());
            customerCard.setText(null);
            customerDOB.setValue(null);
            customerBilling.setText(null);
            customerDelivery.setText(null);
        }
    }

    //</editor-fold>

    //<editor-fold> desc="VIEWING AND TAB LOGIC"

    public void loadTabData() {
        if (shopTab.isSelected()) {
            shopList.getItems().clear();
            shopList.getItems().addAll(shopHibernate.loadAvailableProducts());
        }
        if (usersTab.isSelected()) {
            Manager manager = genericHibernate.getEntityByID(Manager.class, user.getId());
            if (!manager.isAdmin()) {
                customerCard.setDisable(true);
                customerDOB.setDisable(true);
                customerDelivery.setDisable(true);
                customerBilling.setDisable(true);
                isCustomer.setDisable(true);
                isManager.setDisable(true);
                isAdmin.setDisable(true);
                addUserButton.setDisable(true);
                deleteUserButton.setDisable(true);
            }
            fillUserTable();
        }
        if (productTab.isSelected()) {
            productList.getItems().clear();
            productList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
        }
        if (ordersTab.isSelected()) {
            if (user instanceof Customer) {
                loadOrders();
                orderStatus.setVisible(false);
                employeeList.setVisible(false);
                employeeID.setVisible(false);
                editOrderButton.setVisible(false);
                filterButton.setVisible(false);
                removeFilterButton.setVisible(false);
                dateFieldFilter.setVisible(false);
            } else {
                loadOrders();
                employeeList.getItems().clear();
                employeeList.getItems().addAll(genericHibernate.getAllRecords(Manager.class));
            }
        }
        if (warehouseTab.isSelected()) {
            selectWarehouse.getItems().clear();
            selectWarehouse.getItems().addAll(genericHibernate.getAllRecords(Warehouse.class));
            warehouseProductList.getItems().clear();
            warehouseEmployeeList.getItems().clear();
            freeEmployeeList.getItems().clear();
            freeEmployeeList.getItems().addAll(warehouseHibernate.loadAvailableEmployees());
            freeProductList.getItems().clear();
            freeProductList.getItems().addAll(warehouseHibernate.loadAvailableProducts());
        } else {
            selectWarehouse.setValue(null);
        }
    }

    private void setCustomerView() {
        if (user instanceof Customer) {
            tabPane.getTabs().remove(usersTab);
            tabPane.getTabs().remove(productTab);
            tabPane.getTabs().remove(warehouseTab);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consoleType.getItems().addAll(consolePlatform.values());
        gamePlatform.getItems().addAll(consolePlatform.values());
        gameCategory.getItems().addAll(videogameCategory.values());
        orderStatus.getItems().addAll(OrderStatus.values());
        cityBox.getItems().addAll(City.values());
        userTable.setEditable(true);
        userColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColType.setCellValueFactory(new PropertyValueFactory<>("type"));
        userColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        userColLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        userColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        userColDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }
    //</editor-fold>

    //<editor-fold> desc="ORDER TAB LOGIC"

    public void editOrder() {
        List<Product> orderProducts = orderContents.getItems();
        Cart cart = orderProducts.getFirst().getCart();
        if(!employeeID.getText().isEmpty()) {
            Manager manager = shopHibernate.getEntityByID(Manager.class, Integer.parseInt(employeeID.getText()));
            cart.setManager(manager);
            cart.setOrderStatus(orderStatus.getValue());
        }
        else {
            cart.setManager(null);
            cart.setOrderStatus(orderStatus.getValue());
        }
        genericHibernate.update(cart);
        loadOrders();
    }

    public void loadOrderData() {
        Cart cart;
        if (customerOrders.isFocused()) {
            cart = customerOrders.getSelectionModel().getSelectedItem();
        } else {
            cart = rushOrders.getSelectionModel().getSelectedItem();
        }
        orderContents.getItems().clear();
        orderContents.getItems().addAll(cart.getProductList());
        orderStatus.setValue(cart.getOrderStatus());
        if (cart.getManager() != null) {
            employeeID.setText(String.valueOf(cart.getManager().getId()));
        }
        else{
            employeeID.setText(null);
        }
    }

    public void loadOrders() {
        User currentUser = genericHibernate.getEntityByID(User.class, user.getId());
        if (currentUser instanceof Manager) {
            rushOrders.getItems().clear();
            orderHistoryButton.setVisible(false);
            customerOrders.getItems().clear();
            for (Cart c : genericHibernate.getAllRecords(Cart.class)) {
                if (!LocalDate.now().equals(c.getDateCreated()) && c.getManager() == null) {
                    rushOrders.getItems().add(c);
                    rushOrders.setCellFactory(param -> new ListCell<>() {
                        @Override
                        protected void updateItem(Cart cart, boolean empty) {
                            super.updateItem(cart, empty);
                            if (empty || cart == null) {
                                setText(null);
                            } else {
                                setText("!!!Order #" + cart.getId() + " Status: " + c.getOrderStatus() +"!!!");
                            }
                        }
                    });
                }else {
                    customerOrders.getItems().add(c);
                }
                if(Objects.equals(c.getOrderStatus().toString(), "COMPLETED")){
                    rushOrders.getItems().remove(c);
                    customerOrders.getItems().remove(c);
                }
            }
        } else {
            customerOrders.setLayoutX(14);
            customerOrders.setLayoutY(46);
            customerOrders.setPrefWidth(262);
            customerOrders.setPrefHeight(345);
            rushOrders.setVisible(false);
            customerOrders.getItems().clear();
            customerOrders.getItems().addAll(genericHibernate.getAllRecords(Cart.class));
            customerOrders.getItems().removeIf(c -> c.getCustomer().getId() != currentUser.getId());
            customerOrders.getItems().removeIf(c -> Objects.equals(c.getOrderStatus().toString(), "COMPLETED"));
        }
    }
    public void filterOrders() {
        rushOrders.getItems().clear();
        customerOrders.getItems().clear();
        Manager manager = null;
        if (!employeeID.getText().isEmpty()) {
            manager = genericHibernate.getEntityByID(Manager.class, Integer.parseInt(employeeID.getText()));
        }
        List<Cart> filteredOrders = orderHibernate.filterOrders(dateFieldFilter.getValue(), orderStatus.getValue(), manager);
        for (Cart c : filteredOrders) {
            if (!LocalDate.now().equals(c.getDateCreated()) && c.getManager() == null) {
                rushOrders.getItems().add(c);
                rushOrders.setCellFactory(param -> new ListCell<>() {
                    @Override
                    protected void updateItem(Cart cart, boolean empty) {
                        super.updateItem(cart, empty);
                        if (empty || cart == null) {
                            setText(null);
                        } else {
                            setText("!!!Order #" + cart.getId() + " Status: " + c.getOrderStatus() +"!!!");
                        }
                    }
                });
            }else {
                customerOrders.getItems().add(c);
            }
            if(Objects.equals(c.getOrderStatus().toString(), "COMPLETED")){
                rushOrders.getItems().remove(c);
                customerOrders.getItems().remove(c);
            }
        }
        orderStatus.getItems().clear();
        orderStatus.getItems().addAll(OrderStatus.values());
    }
    public void viewOrderHistory() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("order-history.fxml"));
        Parent parent = fxmlLoader.load();
        //Access controller of main window. Each form has its own controller, so make sure that you make no mistake here
        OrderHistory orderHistory = fxmlLoader.getController();
        orderHistory.setData(entityManagerFactory, user);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Order History");
        FxUtils.setStageParameters(stage, scene, false);
    }

    public void cancelOrder() {
        Cart cart = customerOrders.getSelectionModel().getSelectedItem();
        shopHibernate.deleteCart(cart.getId());
        orderContents.getItems().clear();
        loadOrders();
    }

    public void getEmployeeID() {
        User user = employeeList.getSelectionModel().getSelectedItem();
        employeeID.setText(String.valueOf(user.getId()));
    }

    public void openChat() throws IOException {
        List<Product> orderProducts = orderContents.getItems();
        Cart cart = orderProducts.getFirst().getCart();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("order-chat.fxml"));
        Parent parent = fxmlLoader.load();
        //Access controller of main window. Each form has its own controller, so make sure that you make no mistake here
        OrderChat orderChat = fxmlLoader.getController();
        orderChat.setData(entityManagerFactory, user, genericHibernate.getEntityByID(Cart.class, cart.getId()));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Chat");
        FxUtils.setStageParameters(stage, scene, false);
    }
    //</editor-fold>

    //<editor-fold> desc="WAREHOUSE LOGIC"

    public void addEmployeeToWarehouse() {
        Manager employee = freeEmployeeList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = selectWarehouse.getSelectionModel().getSelectedItem();
        warehouseHibernate.addEmployee(employee, warehouse);
        refreshWarehouse();
    }

    public void removeEmployeeFromWarehouse() {
        Manager employee = warehouseEmployeeList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = selectWarehouse.getSelectionModel().getSelectedItem();
        warehouseHibernate.removeEmployee(employee, warehouse);
        refreshWarehouse();
    }

    public void addProductToWarehouse() {
        Product product = freeProductList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = selectWarehouse.getSelectionModel().getSelectedItem();
        warehouseHibernate.addProduct(product, warehouse);
        refreshWarehouse();
    }

    public void removeProductFromWarehouse() {
        Product product = warehouseProductList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = selectWarehouse.getSelectionModel().getSelectedItem();
        warehouseHibernate.removeProduct(product, warehouse);
        refreshWarehouse();
    }

    public void createWarehouse() {
        Warehouse warehouse = new Warehouse(
                addressField.getText(),
                cityBox.getValue()
        );
        genericHibernate.create(warehouse);
    }

    public void editWarehouse() {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            var selectedWarehouse = selectWarehouse.getSelectionModel().getSelectedItem();
            var warehouseId = selectedWarehouse.getId(); // Assuming getId() returns the ID as an Integer
            var warehouse = entityManager.find(Warehouse.class, warehouseId);

            // Update warehouse properties
            warehouse.setCity(cityBox.getValue());
            warehouse.setAddress(addressField.getText());
            warehouse.setDateModified(LocalDate.now());

            // Merge updated warehouse entity
            entityManager.merge(warehouse);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }


    public void deleteWarehouse() {
        Warehouse warehouse = selectWarehouse.getSelectionModel().getSelectedItem();
        warehouseHibernate.deleteWarehouse(warehouse.getId());
    }

    public void loadWarehouseData() {
        Warehouse warehouse = selectWarehouse.getSelectionModel().getSelectedItem();
        warehouseEmployeeList.getItems().clear();
        warehouseEmployeeList.getItems().addAll(warehouse.getEmployees());
        warehouseProductList.getItems().clear();
        warehouseProductList.getItems().addAll(warehouse.getProductList());
        addressField.setText(warehouse.getAddress());
        cityBox.setValue(warehouse.getCity());
    }

    public void refreshWarehouse() {
        Warehouse warehouse = selectWarehouse.getSelectionModel().getSelectedItem();
        freeEmployeeList.getItems().clear();
        freeEmployeeList.getItems().addAll(warehouseHibernate.loadAvailableEmployees());
        warehouseEmployeeList.getItems().clear();
        warehouseEmployeeList.getItems().addAll(warehouse.getEmployees());
        freeProductList.getItems().clear();
        freeProductList.getItems().addAll(warehouseHibernate.loadAvailableProducts());
        warehouseProductList.getItems().clear();
        warehouseProductList.getItems().addAll(warehouse.getProductList());
    }
    //</editor-fold>
}