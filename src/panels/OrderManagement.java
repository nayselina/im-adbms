package panels;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Order Management panel for restaurant admin interface
 * @author 63906
 */
public class OrderManagement extends javax.swing.JPanel {
    // Order model class
    private static class Order {
        String orderId;
        String customerName;
        String status;
        ArrayList<OrderItem> items;
        double total;
        Date orderTime;
        String pickupType;
        Date pickupTime;
        
        public Order(String orderId, String customerName, String status, 
                ArrayList<OrderItem> items, double total, Date orderTime, 
                String pickupType, Date pickupTime) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.status = status;
            this.items = items;
            this.total = total;
            this.orderTime = orderTime;
            this.pickupType = pickupType;
            this.pickupTime = pickupTime;
        }
        
        public String getItemsAsString() {
            StringBuilder sb = new StringBuilder();
            for (OrderItem item : items) {
                sb.append(item.quantity).append(" × ").append(item.name).append("<br>");
            }
            return "<html>" + sb.toString() + "</html>";
        }
        
        public String getTimeAgo() {
            long diff = new Date().getTime() - orderTime.getTime();
            long minutes = diff / (60 * 1000);
            
            return minutes + " mins ago";
        }
    }
    
    // Order item model class
    private static class OrderItem {
        String name;
        int quantity;
        double price;
        
        public OrderItem(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
    }
    
    // UI Components
    private JPanel headerPanel;
    private JPanel actionPanel;
    private JPanel searchPanel;
    private JScrollPane tableScrollPane;
    private JTable ordersTable;
    private JPanel paginationPanel;
    
    private JButton btnPendingOrders;
    private JButton btnInProgress;
    private JButton btnCompleted;
    private JButton btnDeclined;
    
    private JTextField txtSearch;
    private JComboBox<String> cmbSort;
    
    // Data
    private ArrayList<Order> orders;
    private String activeStatus = "Pending";
    private String[] statusOptions = {"Pending", "In Progress", "Completed", "Declined"};
    private ArrayList<JButton> statusButtons;
    
    /**
     * Creates new form OrderManagement
     */
    public OrderManagement() {
        initializeData();
        initComponents();
        setupTable();
        updateTableData();
    }
    
    /**
     * Initialize sample data
     */
    private void initializeData() {
        orders = new ArrayList<>();
        
        // Create sample orders
        // Order #1
        ArrayList<OrderItem> items1 = new ArrayList<>();
        items1.add(new OrderItem("Classic Cheeseburger", 2, 9.99));
        items1.add(new OrderItem("Fries", 1, 2.99));
        items1.add(new OrderItem("Soda", 2, 1.99));
        
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.MINUTE, -8);
        
        Calendar pickupTime1 = Calendar.getInstance();
        pickupTime1.set(Calendar.HOUR_OF_DAY, 10);
        pickupTime1.set(Calendar.MINUTE, 15);
        pickupTime1.set(Calendar.SECOND, 0);
        
        orders.add(new Order("#ORD1002", "John Smith", "Pending", items1, 26.97, 
                cal1.getTime(), "Pickup", pickupTime1.getTime()));
        
        // Order #2
        ArrayList<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem("Bacon Deluxe", 1, 12.99));
        items2.add(new OrderItem("Veggie Supreme", 1, 10.99));
        items2.add(new OrderItem("Soda", 3, 2.99));
        
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.MINUTE, -12);
        
        Calendar pickupTime2 = Calendar.getInstance();
        pickupTime2.set(Calendar.HOUR_OF_DAY, 10);
        pickupTime2.set(Calendar.MINUTE, 30);
        pickupTime2.set(Calendar.SECOND, 0);
        
        orders.add(new Order("#ORD1001", "Emma Johnson", "Pending", items2, 32.87, 
                cal2.getTime(), "Delivery", pickupTime2.getTime()));
        
        // Order #3
        ArrayList<OrderItem> items3 = new ArrayList<>();
        items3.add(new OrderItem("Double Trouble", 1, 14.99));
        items3.add(new OrderItem("Fries", 1, 2.99));
        items3.add(new OrderItem("Milkshake", 1, 1.99));
        
        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.MINUTE, -15);
        
        Calendar pickupTime3 = Calendar.getInstance();
        pickupTime3.set(Calendar.HOUR_OF_DAY, 10);
        pickupTime3.set(Calendar.MINUTE, 45);
        pickupTime3.set(Calendar.SECOND, 0);
        
        orders.add(new Order("#ORD1000", "David Chen", "Pending", items3, 19.98, 
                cal3.getTime(), "Pickup", pickupTime3.getTime()));
    }
    
    /**
     * Initialize UI components
     */
    private void initComponents() {
        // Set layout
        setLayout(new BorderLayout());
        setBackground(new Color(243, 244, 246)); // Light gray background
        
        // Create header panel
        createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create white card panel with shadow effect
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        // Create components for card panel
        createStatusTabsPanel();
        createSearchPanel();
        createTablePanel();
        createPaginationPanel();
        
        // Add components to card panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.WHITE);
        middlePanel.add(actionPanel, BorderLayout.NORTH);
        middlePanel.add(searchPanel, BorderLayout.CENTER);
        
        topPanel.add(middlePanel, BorderLayout.CENTER);
        
        cardPanel.add(topPanel, BorderLayout.NORTH);
        cardPanel.add(tableScrollPane, BorderLayout.CENTER);
        cardPanel.add(paginationPanel, BorderLayout.SOUTH);
        
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Create header panel with title
     */
    private void createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        headerPanel.setBackground(new Color(243, 244, 246));
        
        JLabel lblTitle = new JLabel("Order Management");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(21, 128, 61)); // Green color
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
    }
    
    /**
     * Create status tabs panel
     */
    private void createStatusTabsPanel() {
        actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        statusButtons = new ArrayList<>();
        
        btnPendingOrders = createStatusButton("Pending Orders", "Pending");
        btnInProgress = createStatusButton("In Progress", "In Progress");
        btnCompleted = createStatusButton("Completed", "Completed");
        btnDeclined = createStatusButton("Declined", "Declined");
        
        actionPanel.add(btnPendingOrders);
        actionPanel.add(btnInProgress);
        actionPanel.add(btnCompleted);
        actionPanel.add(btnDeclined);
        
        // Set initial active status
        updateStatusButtons();
    }
    
    /**
     * Create search and sort panel
     */
    private void createSearchPanel() {
        searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        
        txtSearch = new JTextField("Search by order number or customer...");
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Search by order number or customer...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Search by order number or customer...");
                    txtSearch.setForeground(Color.GRAY);
                }
            }
        });
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTableData();
            }
        });
        
        leftPanel.add(txtSearch, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        
        String[] sortOptions = {"Sort by: Newest First", "Sort by: Oldest First", "Sort by: Total (High to Low)"};
        cmbSort = new JComboBox<>(sortOptions);
        cmbSort.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        cmbSort.addActionListener(e -> updateTableData());
        
        rightPanel.add(cmbSort, BorderLayout.CENTER);
        
        searchPanel.add(leftPanel, BorderLayout.CENTER);
        searchPanel.add(rightPanel, BorderLayout.EAST);
    }
    
    /**
     * Create table panel for orders
     */
    private void createTablePanel() {
        // Create table model with non-editable cells
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only actions column is editable
            }
        };
        
        // Add columns
        model.addColumn("ORDER #");
        model.addColumn("CUSTOMER");
        model.addColumn("ITEMS");
        model.addColumn("TOTAL");
        model.addColumn("ACTIONS");
        
        // Create table
        ordersTable = new JTable(model);
        ordersTable.setRowHeight(60);
        ordersTable.setShowVerticalLines(false);
        ordersTable.setIntercellSpacing(new Dimension(0, 0));
        ordersTable.getTableHeader().setReorderingAllowed(false);
        ordersTable.getTableHeader().setBackground(new Color(243, 244, 246));
        ordersTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        
        // Set column widths
        TableColumnModel columnModel = ordersTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(180);
        
        // Set custom renderers
        columnModel.getColumn(4).setCellRenderer(new ButtonsRenderer());
        columnModel.getColumn(4).setCellEditor(new ButtonsEditor(ordersTable));
        
        // Create scroll pane
        tableScrollPane = new JScrollPane(ordersTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
    }
    
    /**
     * Create pagination panel
     */
    private void createPaginationPanel() {
        paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15));
        paginationPanel.setBackground(Color.WHITE);
        
        JButton btn1 = createRoundButton("1", true);
        JButton btn2 = createRoundButton("2", false);
        JButton btn3 = createRoundButton("3", false);
        
        paginationPanel.add(btn1);
        paginationPanel.add(btn2);
        paginationPanel.add(btn3);
    }
    
    /**
     * Update the table data based on active status, search and sort
     */
    private void updateTableData() {
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.setRowCount(0);
        
        String searchText = txtSearch.getText();
        if (searchText.equals("Search by order number or customer...")) {
            searchText = "";
        }
        
        ArrayList<Order> filteredOrders = getFilteredOrders(searchText);
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        DecimalFormat decimalFormat = new DecimalFormat("$0.00");
        
        for (Order order : filteredOrders) {
            if (order.status.equals(activeStatus)) {
                String customerInfo = "<html><b>" + order.customerName + "</b><br><font size='-2'>" + 
                                      order.pickupType + " - " + timeFormat.format(order.pickupTime) + "</font></html>";
                
                StringBuilder itemsInfo = new StringBuilder("<html>");
                for (int i = 0; i < order.items.size(); i++) {
                    OrderItem item = order.items.get(i);
                    itemsInfo.append(item.quantity).append(" × ").append(item.name);
                    if (i < order.items.size() - 1) {
                        itemsInfo.append("<br>");
                    }
                }
                itemsInfo.append("</html>");
                
                String orderInfo = "<html><b>" + order.orderId + "</b><br><font size='-2'>" + 
                                  order.getTimeAgo() + "</font></html>";
                
                model.addRow(new Object[]{
                    orderInfo,
                    customerInfo,
                    itemsInfo.toString(),
                    decimalFormat.format(order.total),
                    "actions" // Placeholder for button renderer
                });
            }
        }
    }
    
    /**
     * Filter and sort orders based on search text and sort option
     */
    private ArrayList<Order> getFilteredOrders(String searchText) {
        ArrayList<Order> filteredOrders = new ArrayList<>();
        
        for (Order order : orders) {
            boolean matchesSearch = searchText.isEmpty() || 
                    order.orderId.toLowerCase().contains(searchText.toLowerCase()) ||
                    order.customerName.toLowerCase().contains(searchText.toLowerCase());
            
            if (matchesSearch) {
                filteredOrders.add(order);
            }
        }
        
        // Apply sorting
        String sortOption = (String) cmbSort.getSelectedItem();
        if (sortOption.equals("Sort by: Oldest First")) {
            filteredOrders.sort(Comparator.comparing(o -> o.orderTime));
        } else if (sortOption.equals("Sort by: Total (High to Low)")) {
            filteredOrders.sort((o1, o2) -> Double.compare(o2.total, o1.total));
        } else {
            // Default: Newest First
            filteredOrders.sort((o1, o2) -> o2.orderTime.compareTo(o1.orderTime));
        }
        
        return filteredOrders;
    }
    
    /**
     * Setup table cell renderers and editors
     */
    private void setupTable() {
        // Additional setup for table if needed
    }
    
    /**
     * Update status button styles based on active status
     */
    private void updateStatusButtons() {
        if (btnPendingOrders.getText().contains(activeStatus)) {
            btnPendingOrders.setBackground(new Color(21, 128, 61));
            btnPendingOrders.setForeground(Color.WHITE);
        } else {
            btnPendingOrders.setBackground(new Color(229, 231, 235));
            btnPendingOrders.setForeground(new Color(55, 65, 81));
        }
        
        if (btnInProgress.getText().contains(activeStatus)) {
            btnInProgress.setBackground(new Color(21, 128, 61));
            btnInProgress.setForeground(Color.WHITE);
        } else {
            btnInProgress.setBackground(new Color(229, 231, 235));
            btnInProgress.setForeground(new Color(55, 65, 81));
        }
        
        if (btnCompleted.getText().contains(activeStatus)) {
            btnCompleted.setBackground(new Color(21, 128, 61));
            btnCompleted.setForeground(Color.WHITE);
        } else {
            btnCompleted.setBackground(new Color(229, 231, 235));
            btnCompleted.setForeground(new Color(55, 65, 81));
        }
        
        if (btnDeclined.getText().contains(activeStatus)) {
            btnDeclined.setBackground(new Color(21, 128, 61));
            btnDeclined.setForeground(Color.WHITE);
        } else {
            btnDeclined.setBackground(new Color(229, 231, 235));
            btnDeclined.setForeground(new Color(55, 65, 81));
        }
    }
    
    /**
     * Accept an order
     */
    private void acceptOrder(String orderId) {
        for (Order order : orders) {
            if (order.orderId.equals(orderId)) {
                order.status = "In Progress";
                break;
            }
        }
        updateTableData();
        JOptionPane.showMessageDialog(this, "Order " + orderId + " has been accepted", "Order Accepted", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Decline an order
     */
    private void declineOrder(String orderId) {
        for (Order order : orders) {
            if (order.orderId.equals(orderId)) {
                order.status = "Declined";
                break;
            }
        }
        updateTableData();
        JOptionPane.showMessageDialog(this, "Order " + orderId + " has been declined", "Order Declined", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Complete an order
     */
    private void completeOrder(String orderId) {
        for (Order order : orders) {
            if (order.orderId.equals(orderId)) {
                order.status = "Completed";
                break;
            }
        }
        updateTableData();
        JOptionPane.showMessageDialog(this, "Order " + orderId + " has been completed", "Order Completed", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * View order details
     */
    private void viewOrderDetails(String orderId) {
        Order selectedOrder = null;
        for (Order order : orders) {
            if (order.orderId.equals(orderId)) {
                selectedOrder = order;
                break;
            }
        }
        
        if (selectedOrder == null) return;
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Order Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Order header info
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel lblOrderId = new JLabel(selectedOrder.orderId);
        lblOrderId.setFont(new Font("Arial", Font.BOLD, 18));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        JLabel lblOrderTime = new JLabel("Ordered on " + dateFormat.format(selectedOrder.orderTime));
        lblOrderTime.setForeground(Color.GRAY);
        
        headerPanel.add(lblOrderId, BorderLayout.NORTH);
        headerPanel.add(lblOrderTime, BorderLayout.SOUTH);
        
        // Customer info
        JPanel customerPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer Information"));
        
        customerPanel.add(new JLabel("Name:"));
        customerPanel.add(new JLabel(selectedOrder.customerName));
        
        customerPanel.add(new JLabel("Pickup Type:"));
        customerPanel.add(new JLabel(selectedOrder.pickupType));
        
        customerPanel.add(new JLabel("Pickup Time:"));
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        customerPanel.add(new JLabel(timeFormat.format(selectedOrder.pickupTime)));
        
        // Order items
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Order Items"));
        
        String[] columns = {"Item", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        DecimalFormat decimalFormat = new DecimalFormat("$0.00");
        
        for (OrderItem item : selectedOrder.items) {
            model.addRow(new Object[]{
                item.name,
                item.quantity,
                decimalFormat.format(item.price * item.quantity)
            });
        }
        
        JTable itemsTable = new JTable(model);
        itemsTable.setRowHeight(25);
        itemsTable.setIntercellSpacing(new Dimension(0, 0));
        itemsTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setPreferredSize(new Dimension(450, 150));
        
        itemsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Total panel
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel lblTotal = new JLabel("Total: " + decimalFormat.format(selectedOrder.total));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        
        totalPanel.add(lblTotal, BorderLayout.SOUTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> dialog.dispose());
        buttonPanel.add(btnClose);
        
        // Add all panels to main content panel
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.add(customerPanel, BorderLayout.NORTH);
        centerPanel.add(itemsPanel, BorderLayout.CENTER);
        centerPanel.add(totalPanel, BorderLayout.SOUTH);
        
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    /**
     * Create a status button with specified text and tag
     */
    private JButton createStatusButton(String text, String tag) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        
        if (tag.equals(activeStatus)) {
            button.setBackground(new Color(21, 128, 61));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(229, 231, 235));
            button.setForeground(new Color(55, 65, 81));
        }
        
        button.addActionListener(e -> {
            activeStatus = tag;
            updateStatusButtons();
            updateTableData();
        });
        
        return button;
    }
    
    /**
     * Create a round button for pagination
     */
    private JButton createRoundButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(30, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        
        if (isActive) {
            button.setBackground(new Color(21, 128, 61));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(229, 231, 235));
            button.setForeground(new Color(55, 65, 81));
        }
        
        button.addActionListener(e -> {
            // Update pagination - this would load different data
            // For this example, we just change the button styling
            for (Component comp : paginationPanel.getComponents()) {
                if (comp instanceof JButton) {
                    JButton btn = (JButton) comp;
                    if (btn == button) {
                        btn.setBackground(new Color(21, 128, 61));
                        btn.setForeground(Color.WHITE);
                    } else {
                        btn.setBackground(new Color(229, 231, 235));
                        btn.setForeground(new Color(55, 65, 81));
                    }
                }
            }
        });
        
        return button;
    }
    
    /**
     * Custom renderer for action buttons column
     */
    private class ButtonsRenderer extends JPanel implements TableCellRenderer {
        private JButton acceptButton;
        private JButton declineButton;
        
        public ButtonsRenderer() {
            setLayout(new GridLayout(1, 2, 5, 0));
            setBackground(Color.WHITE);
            
            acceptButton = new JButton("Accept");
            acceptButton.setBackground(new Color(21, 128, 61));
            acceptButton.setForeground(Color.WHITE);
            acceptButton.setFocusPainted(false);
            acceptButton.setBorderPainted(false);
            acceptButton.setFont(new Font("Arial", Font.BOLD, 12));
            
            declineButton = new JButton("Decline");
            declineButton.setBackground(new Color(234, 88, 12));
            declineButton.setForeground(Color.WHITE);
            declineButton.setFocusPainted(false);
            declineButton.setBorderPainted(false);
            declineButton.setFont(new Font("Arial", Font.BOLD, 12));
            
            add(acceptButton);
            add(declineButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    
    /**
     * Custom editor for action buttons column
     */
    private class ButtonsEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton acceptButton;
        private JButton declineButton;
        private String orderId;
        
        public ButtonsEditor(JTable table) {
            super(new JTextField());
            
            panel = new JPanel(new GridLayout(1, 2, 5, 0));
            panel.setBackground(Color.WHITE);
            
            acceptButton = new JButton("Accept");
            acceptButton.setBackground(new Color(21, 128, 61));
            acceptButton.setForeground(Color.WHITE);
            acceptButton.setFocusPainted(false);
            acceptButton.setBorderPainted(false);
            acceptButton.setFont(new Font("Arial", Font.BOLD, 12));
            
            acceptButton.addActionListener(e -> {
                fireEditingStopped();
                if (activeStatus.equals("Pending")) {
                    acceptOrder(orderId);
                } else if (activeStatus.equals("In Progress")) {
                    completeOrder(orderId);
                } else {
                    viewOrderDetails(orderId);
                }
            });
            
            declineButton = new JButton("Decline");
            declineButton.setBackground(new Color(234, 88, 12));
            declineButton.setForeground(Color.WHITE);
            declineButton.setFocusPainted(false);
            declineButton.setBorderPainted(false);
            declineButton.setFont(new Font("Arial", Font.BOLD, 12));
            
            declineButton.addActionListener(e -> {
                fireEditingStopped();
                if (activeStatus.equals("Pending") || activeStatus.equals("In Progress")) {
                    declineOrder(orderId);
                } else {
                    viewOrderDetails(orderId);
                }
            });
            
            panel.add(acceptButton);
            panel.add(declineButton);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {
            // Update button labels based on status
            if (activeStatus.equals("Pending")) {
                acceptButton.setText("Accept");
                acceptButton.setBackground(new Color(21, 128, 61));
                declineButton.setText("Decline");
                declineButton.setBackground(new Color(234, 88, 12));
            } else if (activeStatus.equals("In Progress")) {
                acceptButton.setText("Complete");
                acceptButton.setBackground(new Color(21, 128, 61));
                declineButton.setText("Decline");
                declineButton.setBackground(new Color(234, 88, 12));
            } else {
                acceptButton.setText("View");
                acceptButton.setBackground(new Color(59, 130, 246));
                declineButton.setText("View");
                declineButton.setBackground(new Color(59, 130, 246));
            }
            
            // Get order ID from the first column
            String html = (String) table.getValueAt(row, 0);
            int startIndex = html.indexOf("<b>") + 3;
            int endIndex = html.indexOf("</b>");
            orderId = html.substring(startIndex, endIndex);
            
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "actions";
        }
        
        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
        
        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    
    /**
     * Main method for testing
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Order Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new OrderManagement());
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
