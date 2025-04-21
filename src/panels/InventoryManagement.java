package panels;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Order Management panel for restaurant admin interface
 * @author 63906
 */
public class OrderManagement extends javax.swing.JPanel {
    // Order model class
    private static class Order {
        String id;
        String customerName;
        Date orderTime;
        double totalAmount;
        String status;
        ArrayList<OrderItem> items;
        String paymentMethod;
        
        public Order(String id, String customerName, Date orderTime, 
                double totalAmount, String status, String paymentMethod) {
            this.id = id;
            this.customerName = customerName;
            this.orderTime = orderTime;
            this.totalAmount = totalAmount;
            this.status = status;
            this.paymentMethod = paymentMethod;
            this.items = new ArrayList<>();
        }
        
        public void addItem(OrderItem item) {
            items.add(item);
        }
    }
    
    // Order item model class
    private static class OrderItem {
        String itemId;
        String itemName;
        int quantity;
        double price;
        String specialInstructions;
        
        public OrderItem(String itemId, String itemName, int quantity, 
                double price, String specialInstructions) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
            this.specialInstructions = specialInstructions;
        }
    }
    
    // UI Components
    private JPanel headerPanel;
    private JPanel statusPanel;
    private JPanel actionPanel;
    private JPanel searchPanel;
    private JScrollPane tableScrollPane;
    private JTable ordersTable;
    private JPanel paginationPanel;
    
    private JButton btnProcessOrder;
    private JButton btnViewDetails;
    private JButton btnCancelOrder;
    private JButton btnPrintReceipt;
    private JButton btnMarkCompleted;
    
    private JTextField txtSearch;
    private JComboBox<String> cmbSort;
    private JComboBox<String> cmbFilter;
    
    // Data
    private ArrayList<Order> orders;
    private String activeStatus = "All Orders";
    private String[] statusOptions = {"All Orders", "New", "In Progress", "Ready", "Completed", "Cancelled"};
    private ArrayList<JButton> statusButtons;
    
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    
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
        
        // Create some sample orders
        Calendar cal = Calendar.getInstance();
        
        // Order 1 - New
        Order order1 = new Order("ORD001", "John Smith", cal.getTime(), 25.98, "New", "Credit Card");
        order1.addItem(new OrderItem("BG001", "Classic Cheeseburger", 2, 9.99, "No pickles"));
        order1.addItem(new OrderItem("BN001", "Fries", 1, 3.99, "Extra salt"));
        order1.addItem(new OrderItem("DR001", "Coke", 1, 2.01, ""));
        orders.add(order1);
        
        // Order 2 - In Progress
        cal.add(Calendar.MINUTE, -15);
        Order order2 = new Order("ORD002", "Emily Johnson", cal.getTime(), 18.99, "In Progress", "Cash");
        order2.addItem(new OrderItem("BG003", "Veggie Supreme", 1, 10.99, ""));
        order2.addItem(new OrderItem("BN002", "Onion Rings", 1, 4.99, ""));
        order2.addItem(new OrderItem("DR002", "Sprite", 1, 3.01, "No ice"));
        orders.add(order2);
        
        // Order 3 - Ready
        cal.add(Calendar.MINUTE, -10);
        Order order3 = new Order("ORD003", "Michael Williams", cal.getTime(), 32.97, "Ready", "Credit Card");
        order3.addItem(new OrderItem("BG002", "Bacon Deluxe", 3, 12.99, "Well done"));
        orders.add(order3);
        
        // Order 4 - Completed
        cal.add(Calendar.HOUR, -1);
        Order order4 = new Order("ORD004", "Sarah Davis", cal.getTime(), 14.99, "Completed", "Credit Card");
        order4.addItem(new OrderItem("BG004", "Double Trouble", 1, 14.99, ""));
        orders.add(order4);
        
        // Order 5 - Cancelled
        cal.add(Calendar.MINUTE, -30);
        Order order5 = new Order("ORD005", "Robert Brown", cal.getTime(), 21.98, "Cancelled", "Cash");
        order5.addItem(new OrderItem("BG001", "Classic Cheeseburger", 1, 9.99, ""));
        order5.addItem(new OrderItem("BG003", "Veggie Supreme", 1, 10.99, ""));
        order5.addItem(new OrderItem("DR001", "Coke", 1, 2.01, ""));
        orders.add(order5);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        createActionButtonsPanel();
        createStatusTabsPanel();
        createSearchPanel();
        createTablePanel();
        createPaginationPanel();
        
        // Add components to card panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(actionPanel, BorderLayout.NORTH);
        
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.WHITE);
        middlePanel.add(statusPanel, BorderLayout.NORTH);
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
     * Create action buttons panel
     */
    private void createActionButtonsPanel() {
        actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        btnProcessOrder = createButton("Process Order", new Color(21, 128, 61));
        btnViewDetails = createButton("View Details", new Color(21, 128, 61));
        btnCancelOrder = createButton("Cancel Order", new Color(234, 88, 12));
        btnPrintReceipt = createButton("Print Receipt", new Color(21, 128, 61));
        btnMarkCompleted = createButton("Mark as Completed", new Color(21, 128, 61));
        
        actionPanel.add(btnProcessOrder);
        actionPanel.add(btnViewDetails);
        actionPanel.add(btnCancelOrder);
        actionPanel.add(btnPrintReceipt);
        actionPanel.add(btnMarkCompleted);
        
        // Add action listeners
        btnProcessOrder.addActionListener(e -> processSelectedOrder());
        btnViewDetails.addActionListener(e -> viewOrderDetails());
        btnCancelOrder.addActionListener(e -> cancelSelectedOrder());
        btnPrintReceipt.addActionListener(e -> printReceipt());
        btnMarkCompleted.addActionListener(e -> markOrderAsCompleted());
    }
    
    /**
     * Create status tabs panel
     */
    private void createStatusTabsPanel() {
        statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        statusButtons = new ArrayList<>();
        
        for (String status : statusOptions) {
            JButton btnStatus = new JButton(status);
            btnStatus.setFocusPainted(false);
            btnStatus.setBorderPainted(false);
            btnStatus.setPreferredSize(new Dimension(120, 35));
            
            if (status.equals(activeStatus)) {
                btnStatus.setBackground(new Color(21, 128, 61));
                btnStatus.setForeground(Color.WHITE);
            } else {
                btnStatus.setBackground(new Color(229, 231, 235));
                btnStatus.setForeground(new Color(55, 65, 81));
            }
            
            btnStatus.addActionListener(e -> {
                activeStatus = status;
                updateStatusButtons();
                updateTableData();
            });
            
            statusButtons.add(btnStatus);
            statusPanel.add(btnStatus);
        }
    }
    
    /**
     * Create search and filter panel
     */
    private void createSearchPanel() {
        searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        
        txtSearch = new JTextField("Search orders...");
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Search orders...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Search orders...");
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
        
        JPanel rightPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        rightPanel.setBackground(Color.WHITE);
        
        String[] sortOptions = {"Sort by: Newest", "Sort by: Oldest", "Sort by: Amount (High to Low)", "Sort by: Amount (Low to High)"};
        cmbSort = new JComboBox<>(sortOptions);
        cmbSort.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        cmbSort.addActionListener(e -> updateTableData());
        
        String[] filterOptions = {"Filter: All payments", "Filter: Credit Card only", "Filter: Cash only"};
        cmbFilter = new JComboBox<>(filterOptions);
        cmbFilter.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        cmbFilter.addActionListener(e -> updateTableData());
        
        rightPanel.add(cmbSort);
        rightPanel.add(cmbFilter);
        
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
                return false;
            }
        };
        
        // Add columns
        model.addColumn("ORDER ID");
        model.addColumn("CUSTOMER");
        model.addColumn("TIME");
        model.addColumn("ITEMS");
        model.addColumn("TOTAL");
        model.addColumn("PAYMENT");
        model.addColumn("STATUS");
        
        // Create table
        ordersTable = new JTable(model);
        ordersTable.setRowHeight(50);
        ordersTable.setShowVerticalLines(false);
        ordersTable.setIntercellSpacing(new Dimension(0, 0));
        ordersTable.getTableHeader().setReorderingAllowed(false);
        ordersTable.getTableHeader().setBackground(new Color(243, 244, 246));
        ordersTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        
        // Set column widths
        TableColumnModel columnModel = ordersTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        
        // Set custom renderers
        columnModel.getColumn(6).setCellRenderer(new StatusRenderer());
        
        // Create scroll pane
        tableScrollPane = new JScrollPane(ordersTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Add row selection listener
        ordersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
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
     * Update the table data based on active status, search, sort and filter
     */
    private void updateTableData() {
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.setRowCount(0);
        
        String searchText = txtSearch.getText();
        if (searchText.equals("Search orders...")) {
            searchText = "";
        }
        
        String filterOption = (String) cmbFilter.getSelectedItem();
        
        for (Order order : getFilteredOrders(searchText, filterOption)) {
            StringBuilder itemsSummary = new StringBuilder();
            int itemCount = 0;
            
            for (OrderItem item : order.items) {
                if (itemCount < 2) {
                    if (itemCount > 0) itemsSummary.append(", ");
                    itemsSummary.append(item.quantity).append(" × ").append(item.itemName);
                } else {
                    itemsSummary.append(" + ").append(order.items.size() - 2).append(" more");
                    break;
                }
                itemCount++;
            }
            
            model.addRow(new Object[]{
                order.id,
                order.customerName,
                dateFormat.format(order.orderTime),
                itemsSummary.toString(),
                "$" + new DecimalFormat("0.00").format(order.totalAmount),
                order.paymentMethod,
                order.status
            });
        }
    }
    
    /**
     * Filter and sort orders based on search text, filter options and active status
     */
    private ArrayList<Order> getFilteredOrders(String searchText, String filterOption) {
        ArrayList<Order> filteredOrders = new ArrayList<>();
        
        for (Order order : orders) {
            // Apply status filter
            boolean matchesStatus = activeStatus.equals("All Orders") || order.status.equals(activeStatus);
            
            // Apply search filter
            boolean matchesSearch = searchText.isEmpty() || 
                    order.id.toLowerCase().contains(searchText.toLowerCase()) ||
                    order.customerName.toLowerCase().contains(searchText.toLowerCase());
            
            // Apply payment filter
            boolean matchesPaymentFilter = true;
            if (filterOption.equals("Filter: Credit Card only")) {
                matchesPaymentFilter = order.paymentMethod.equals("Credit Card");
            } else if (filterOption.equals("Filter: Cash only")) {
                matchesPaymentFilter = order.paymentMethod.equals("Cash");
            }
            
            if (matchesStatus && matchesSearch && matchesPaymentFilter) {
                filteredOrders.add(order);
            }
        }
        
        // Apply sorting
        String sortOption = (String) cmbSort.getSelectedItem();
        if (sortOption.equals("Sort by: Newest")) {
            filteredOrders.sort((o1, o2) -> o2.orderTime.compareTo(o1.orderTime));
        } else if (sortOption.equals("Sort by: Oldest")) {
            filteredOrders.sort(Comparator.comparing(o -> o.orderTime));
        } else if (sortOption.equals("Sort by: Amount (High to Low)")) {
            filteredOrders.sort((o1, o2) -> Double.compare(o2.totalAmount, o1.totalAmount));
        } else if (sortOption.equals("Sort by: Amount (Low to High)")) {
            filteredOrders.sort(Comparator.comparingDouble(o -> o.totalAmount));
        }
        
        return filteredOrders;
    }
    
    /**
     * Update status button styles based on active status
     */
    private void updateStatusButtons() {
        for (int i = 0; i < statusOptions.length; i++) {
            JButton button = statusButtons.get(i);
            if (statusOptions[i].equals(activeStatus)) {
                button.setBackground(new Color(21, 128, 61));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(229, 231, 235));
                button.setForeground(new Color(55, 65, 81));
            }
        }
    }
    
    /**
     * Update button states based on selection
     */
    private void updateButtonStates() {
        int selectedRow = ordersTable.getSelectedRow();
        boolean hasSelection = selectedRow != -1;
        
        btnViewDetails.setEnabled(hasSelection);
        btnPrintReceipt.setEnabled(hasSelection);
        
        if (hasSelection) {
            String status = (String) ordersTable.getValueAt(selectedRow, 6);
            btnProcessOrder.setEnabled(status.equals("New"));
            btnCancelOrder.setEnabled(status.equals("New") || status.equals("In Progress"));
            btnMarkCompleted.setEnabled(status.equals("In Progress") || status.equals("Ready"));
        } else {
            btnProcessOrder.setEnabled(false);
            btnCancelOrder.setEnabled(false);
            btnMarkCompleted.setEnabled(false);
        }
    }
    
    /**
     * Setup table cell renderers and editors
     */
    private void setupTable() {
        // Set up selection mode
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Initially disable action buttons
        btnViewDetails.setEnabled(false);
        btnProcessOrder.setEnabled(false);
        btnCancelOrder.setEnabled(false);
        btnPrintReceipt.setEnabled(false);
        btnMarkCompleted.setEnabled(false);
    }
    
    /**
     * Process the selected order
     */
    private void processSelectedOrder() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to process", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String orderId = (String) ordersTable.getValueAt(selectedRow, 0);
        
        // Find and update the order
        for (Order order : orders) {
            if (order.id.equals(orderId)) {
                if (order.status.equals("New")) {
                    order.status = "In Progress";
                    
                    // Update table
                    updateTableData();
                    
                    // Update button states
                    updateButtonStates();
                    
                    // Show success message
                    JOptionPane.showMessageDialog(this, "Order " + orderId + " is now in progress", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "This order cannot be processed as it is not in 'New' status", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                break;
            }
        }
    }
    
    /**
     * View order details
     */
    private void viewOrderDetails() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to view", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String orderId = (String) ordersTable.getValueAt(selectedRow, 0);
        
        // Find the order
        for (Order order : orders) {
            if (order.id.equals(orderId)) {
                // Create and show details dialog
                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Order Details - " + orderId, true);
                dialog.setLayout(new BorderLayout());
                dialog.setSize(500, 450);
                dialog.setLocationRelativeTo(this);
                
                // Create header panel
                JPanel headerPanel = new JPanel(new BorderLayout());
                headerPanel.setBackground(Color.WHITE);
                headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                
                // Customer and time info
                JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
                infoPanel.setBackground(Color.WHITE);
                
                JLabel lblCustomer = new JLabel("Customer:");
                JLabel lblCustomerValue = new JLabel(order.customerName);
                lblCustomerValue.setFont(new Font("Arial", Font.BOLD, 12));
                
                JLabel lblTime = new JLabel("Order Time:");
                JLabel lblTimeValue = new JLabel(dateFormat.format(order.orderTime));
                
                JLabel lblStatus = new JLabel("Status:");
                JLabel lblStatusValue = new JLabel(order.status);
                if (order.status.equals("New")) {
                    lblStatusValue.setForeground(new Color(59, 130, 246));
                } else if (order.status.equals("In Progress")) {
                    lblStatusValue.setForeground(new Color(234, 88, 12));
                } else if (order.status.equals("Ready")) {
                    lblStatusValue.setForeground(new Color(16, 185, 129));
                } else if (order.status.equals("Completed")) {
                    lblStatusValue.setForeground(new Color(21, 128, 61));
                } else if (order.status.equals("Cancelled")) {
                    lblStatusValue.setForeground(new Color(239, 68, 68));
                }
                
                infoPanel.add(lblCustomer);
                infoPanel.add(lblCustomerValue);
                infoPanel.add(lblTime);
                infoPanel.add(lblTimeValue);
                infoPanel.add(lblStatus);
                infoPanel.add(lblStatusValue);
                
                headerPanel.add(infoPanel, BorderLayout.CENTER);
                
                // Create items panel
                JPanel itemsPanel = new JPanel(new BorderLayout());
                itemsPanel.setBackground(Color.WHITE);
                itemsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                
                JLabel lblItems = new JLabel("Order Items");
                lblItems.setFont(new Font("Arial", Font.BOLD, 14));
                itemsPanel.add(lblItems, BorderLayout.NORTH);
                
                // Create items table
                DefaultTableModel itemsModel = new DefaultTableModel(new String[]{"Item", "Qty", "Price", "Total"}, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                
                JTable itemsTable = new JTable(itemsModel);
                itemsTable.setRowHeight(30);
                itemsTable.setIntercellSpacing(new Dimension(0, 0));
                itemsTable.getTableHeader().setReorderingAllowed(false);
                
                // Add items to table
                double subtotal = 0;
                for (OrderItem item : order.items) {
                    double itemTotal = item.price * item.quantity;
                    subtotal += itemTotal;
                    
                    itemsModel.addRow(new Object[]{
                        item.itemName,
                        item.quantity,
                        "$" + new DecimalFormat("0.00").format(item.price),
                        "$" + new DecimalFormat("0.00").format(itemTotal)
                    });
                }
                
                JScrollPane itemsScroll = new JScrollPane(itemsTable);
                itemsScroll.setBorder(BorderFactory.createEmptyBorder());
                itemsPanel.add(itemsScroll, BorderLayout.CENTER);
                
                // Create summary panel
                JPanel summaryPanel = new JPanel(new BorderLayout());
                summaryPanel.setBackground(Color.WHITE);
                summaryPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                
                JPanel totalsPanel = new JPanel(new GridLayout(3, 2, 10, 5));
                totalsPanel.setBackground(Color.WHITE);
                
                JLabel lblSubtotal = new JLabel("Subtotal:");
                JLabel lblSubtotalValue = new JLabel("$" + new DecimalFormat("0.00").format(subtotal));
                lblSubtotalValue.setHorizontalAlignment(SwingConstants.RIGHT);
                
                JLabel lblTax = new JLabel("Tax (7%):");
                double tax = subtotal * 0.07;
                JLabel lblTaxValue = new JLabel("$" + new DecimalFormat("0.00").format(tax));
                lblTaxValue.setHorizontalAlignment(SwingConstants.RIGHT);
                
                JLabel lblTotal = new JLabel("Total:");
                lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
                JLabel lblTotalValue = new JLabel("$" + new DecimalFormat("0.00").format(order.totalAmount));
                lblTotalValue.setFont(new Font("Arial", Font.BOLD, 14));
                lblTotalValue.setHorizontalAlignment(SwingConstants.RIGHT);
                
                totalsPanel.add(lblSubtotal);
                totalsPanel.add(lblSubtotalValue);
                totalsPanel.add(lblTax);
                totalsPanel.add(lblTaxValue);
                totalsPanel.add(lblTotal);
                totalsPanel.add(lblTotalValue);
                
                JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                paymentPanel.setBackground(Color.WHITE);
                JLabel lblPayment = new JLabel("Payment Method: " + order.paymentMethod);
                paymentPanel.add(lblPayment);
                
                summaryPanel.add(totalsPanel, BorderLayout.NORTH);
                summaryPanel.add(paymentPanel, BorderLayout.CENTER);
                
                // Create button panel
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.setBackground(Color.WHITE);
                
                JButton btnClose = new JButton("Close");
                btnClose.addActionListener(e -> dialog.dispose());
                
                buttonPanel.add(btnClose);
                
                // Add panels to dialog
                dialog.add(headerPanel, BorderLayout.NORTH);
                dialog.add(itemsPanel, BorderLayout.CENTER);
                dialog.add(summaryPanel, BorderLayout.SOUTH);
                dialog.add(buttonPanel, BorderLayout.SOUTH);
                
                dialog.setVisible(true);
                break;
            }
        }
    }
    
    /**
     * Cancel the selected order
     */
    private void cancelSelectedOrder() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to cancel", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String orderId = (String) ordersTable.getValueAt(selectedRow, 0);
        String status = (String) ordersTable.getValueAt(selectedRow, 6);
        
        // Check if the order can be cancelled
        if (!status.equals("New") && !status.equals("In Progress")) {
            JOptionPane.showMessageDialog(this, 
                    "Only orders with 'New' or 'In Progress' status can be cancelled", 
                    "Cannot Cancel", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirm cancellation
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to cancel order " + orderId + "?", 
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Find and update the order
            for (Order order : orders) {
                if (order.id.equals(orderId)) {
                    order.status = "Cancelled";
                    
                    // Update table
                    updateTableData();
                    
                    // Update button states
                    updateButtonStates();
                    
                    // Show success message
                    JOptionPane.showMessageDialog(this, 
                            "Order " + orderId + " has been cancelled", 
                            "Order Cancelled", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }
    
    /**
     * Print receipt for the selected order
     */
    private void printReceipt() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Please select an order to print receipt", 
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String orderId = (String) ordersTable.getValueAt(selectedRow, 0);
        
        // Find the order
        for (Order order : orders) {
            if (order.id.equals(orderId)) {
                // In a real application, this would connect to a printer
                // For now, we'll just show a success message
                JOptionPane.showMessageDialog(this, 
                        "Receipt for order " + orderId + " has been sent to printer", 
                        "Receipt Printed", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }
    
    /**
     * Mark the selected order as completed
     */
    private void markOrderAsCompleted() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Please select an order to mark as completed", 
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String orderId = (String) ordersTable.getValueAt(selectedRow, 0);
        String status = (String) ordersTable.getValueAt(selectedRow, 6);
        
        // Check if the order can be marked as completed
        if (!status.equals("In Progress") && !status.equals("Ready")) {
            JOptionPane.showMessageDialog(this, 
                    "Only orders with 'In Progress' or 'Ready' status can be marked as completed", 
                    "Cannot Complete", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Find and update the order
        for (Order order : orders) {
            if (order.id.equals(orderId)) {
                order.status = "Completed";
                
                // Update table
                updateTableData();
                
                // Update button states
                updateButtonStates();
                
                // Show success message
                JOptionPane.showMessageDialog(this, 
                        "Order " + orderId + " has been marked as completed", 
                        "Order Completed", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }
    
    /**
     * Create a styled button with given text and color
     */
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setEnabled(false);  // Initially disabled
        
        return button;
    }
    
    /**
     * Create a rounded button for pagination
     */
    private JButton createRoundButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(30, 30));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
        if (isActive) {
            button.setBackground(new Color(21, 128, 61));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(229, 231, 235));
            button.setForeground(new Color(55, 65, 81));
        }
        
        button.addActionListener(e -> {
            // In a real application, this would load the corresponding page
            JOptionPane.showMessageDialog(this, 
                    "Navigating to page " + text, 
                    "Pagination", JOptionPane.INFORMATION_MESSAGE);
        });
        
        return button;
    }
    
    /**
     * Custom renderer for the status column
     */
    private class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, 
                int row, int column) {
            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            
            if (value != null) {
                String status = value.toString();
                
                JLabel label = (JLabel) c;
                label.setHorizontalAlignment(SwingConstants.CENTER);
                
                // Create a rounded border panel
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                
                JLabel statusLabel = new JLabel(status);
                statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
                statusLabel.setFont(new Font("Arial", Font.BOLD, 11));
                
                // Set color based on status
                if (status.equals("New")) {
                    panel.setBackground(new Color(219, 234, 254));
                    statusLabel.setForeground(new Color(29, 78, 216));
                } else if (status.equals("In Progress")) {
                    panel.setBackground(new Color(254, 240, 219));
                    statusLabel.setForeground(new Color(194, 65, 12));
                } else if (status.equals("Ready")) {
                    panel.setBackground(new Color(209, 250, 229));
                    statusLabel.setForeground(new Color(16, 185, 129));
                } else if (status.equals("Completed")) {
                    panel.setBackground(new Color(187, 247, 208));
                    statusLabel.setForeground(new Color(21, 128, 61));
                } else if (status.equals("Cancelled")) {
                    panel.setBackground(new Color(254, 226, 226));
                    statusLabel.setForeground(new Color(220, 38, 38));
                }
                
                panel.add(statusLabel, BorderLayout.CENTER);
                
                // If not selected, return the custom panel
                if (!isSelected) {
                    return panel;
                }
            }
            
            return c;
        }
    }
    
    /**
     * Main method for testing purposes
     */
    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderManagement.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Order Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            frame.add(new OrderManagement());
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
