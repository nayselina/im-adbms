package Mainframes;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * AdminView - Enhanced Dashboard for Burger Machine Administration
 */
public class AdminView extends JFrame {
    
    // UI Components
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JPanel headerPanel;
    private JPanel dashboardPanel;
    
    // Menu buttons
    private JButton dashboardBtn;
    private JButton menuManagementBtn;
    private JButton orderManagementBtn;
    private JButton inventoryManagementBtn;
    private JButton salesReportingBtn;
    private JButton settingsBtn;
    private JButton logoutBtn;
    
    // Dashboard components
    private JPanel statsPanel;
    private JPanel chartsPanel;
    private JPanel ordersPanel;
    
    // Data models
    private DefaultTableModel ordersTableModel;
    private ArrayList<HashMap<String, Object>> salesData;
    private ArrayList<HashMap<String, Object>> popularItems;
    
    // Constants for styling
    private final Color PRIMARY_GREEN = new Color(23, 133, 76);
    private final Color PRIMARY_ORANGE = new Color(242, 113, 33);
    private final Color BG_LIGHT = new Color(245, 245, 245);
    private final Color TEXT_DARK = new Color(51, 51, 51);
    private final Color TEXT_LIGHT = new Color(255, 255, 255);
    
    /**
     * Constructor - Initialize the Admin Dashboard
     */
    public AdminView() {
        initializeData();
        setupUI();
        attachListeners();
    }
    
    /**
     * Initialize sample data for demonstration
     */
    private void initializeData() {
        // Sales data for dashboard
        salesData = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            HashMap<String, Object> dataPoint = new HashMap<>();
            String date = "Apr " + (13 + i);
            if (i == 8) date = "Today";
            
            double sales = 600 + Math.random() * 600;
            dataPoint.put("date", date);
            dataPoint.put("sales", sales);
            salesData.add(dataPoint);
        }
        
        // Popular items
        popularItems = new ArrayList<>();
        HashMap<String, Object> item1 = new HashMap<>();
        item1.put("id", 1);
        item1.put("name", "Classic Cheeseburger");
        item1.put("sold", 38);
        popularItems.add(item1);
        
        HashMap<String, Object> item2 = new HashMap<>();
        item2.put("id", 2);
        item2.put("name", "Bacon Deluxe");
        item2.put("sold", 29);
        popularItems.add(item2);
        
        HashMap<String, Object> item3 = new HashMap<>();
        item3.put("id", 3);
        item3.put("name", "Veggie Supreme");
        item3.put("sold", 19);
        popularItems.add(item3);
    }
    
    /**
     * Setup the main UI components
     */
    private void setupUI() {
        // Frame setup
        setTitle("Burger Machine Admin Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main layout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_LIGHT);
        
        // Setup sidebar, header, and content areas
        setupSidebar();
        setupHeader();
        setupDashboardContent();
        
        // Add components to main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        setContentPane(mainPanel);
    }
    
    /**
     * Setup the sidebar with navigation buttons
     */
    private void setupSidebar() {
        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setPreferredSize(new Dimension(220, getHeight()));
        sidebarPanel.setBackground(PRIMARY_GREEN);
        
        // Logo panel
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(PRIMARY_GREEN);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel logoLabel = new JLabel("BURGER MACHINE");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logoLabel.setForeground(TEXT_DARK);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setOpaque(true);
        logoLabel.setBackground(Color.WHITE);
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        logoLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        
        // Navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(PRIMARY_GREEN);
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create menu buttons
        dashboardBtn = createSidebarButton("Dashboard", true);
        menuManagementBtn = createSidebarButton("Menu Management", false);
        orderManagementBtn = createSidebarButton("Order Management", false);
        inventoryManagementBtn = createSidebarButton("Inventory Management", false);
        salesReportingBtn = createSidebarButton("Sales Reporting", false);
        settingsBtn = createSidebarButton("Settings", false);
        
        // Add buttons to panel
        navPanel.add(dashboardBtn);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(menuManagementBtn);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(orderManagementBtn);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(inventoryManagementBtn);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(salesReportingBtn);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(settingsBtn);
        
        // Logout panel
        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.setBackground(PRIMARY_GREEN);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(21, 101, 64));
        logoutBtn.setForeground(TEXT_LIGHT);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        logoutPanel.add(logoutBtn, BorderLayout.CENTER);
        
        // Copyright panel
        JPanel copyrightPanel = new JPanel(new BorderLayout());
        copyrightPanel.setBackground(PRIMARY_GREEN);
        
        JLabel copyrightLabel = new JLabel("© 2025 Burger Machine - All rights reserved");
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        copyrightLabel.setForeground(new Color(200, 200, 200));
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        copyrightPanel.add(copyrightLabel, BorderLayout.CENTER);
        copyrightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Add components to sidebar
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(PRIMARY_GREEN);
        footerPanel.add(logoutPanel, BorderLayout.NORTH);
        footerPanel.add(copyrightPanel, BorderLayout.SOUTH);
        
        sidebarPanel.add(logoPanel, BorderLayout.NORTH);
        sidebarPanel.add(navPanel, BorderLayout.CENTER);
        sidebarPanel.add(footerPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Helper method to create sidebar buttons with consistent styling
     */
    private JButton createSidebarButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(TEXT_LIGHT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setPreferredSize(new Dimension(200, 40));
        
        if (isActive) {
            button.setBackground(PRIMARY_ORANGE);
        } else {
            button.setBackground(PRIMARY_GREEN);
        }
        
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    /**
     * Setup the header area
     */
    private void setupHeader() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_ORANGE);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Burger Machine Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_LIGHT);
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(PRIMARY_ORANGE);
        
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        adminLabel.setForeground(TEXT_LIGHT);
        
        JLabel avatarLabel = new JLabel("A");
        avatarLabel.setFont(new Font("Arial", Font.BOLD, 16));
        avatarLabel.setForeground(PRIMARY_ORANGE);
        avatarLabel.setBackground(Color.WHITE);
        avatarLabel.setOpaque(true);
        avatarLabel.setPreferredSize(new Dimension(36, 36));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setVerticalAlignment(SwingConstants.CENTER);
        avatarLabel.setBorder(new RoundedBorder(Color.WHITE, 18));
        
        userPanel.add(adminLabel);
        userPanel.add(Box.createHorizontalStrut(10));
        userPanel.add(avatarLabel);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);
        
        // Add header to content panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    /**
     * Setup the dashboard content
     */
    private void setupDashboardContent() {
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
        dashboardPanel.setBackground(BG_LIGHT);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Dashboard title
        JLabel dashboardTitle = new JLabel("Dashboard Overview");
        dashboardTitle.setFont(new Font("Arial", Font.BOLD, 18));
        dashboardTitle.setForeground(PRIMARY_GREEN);
        dashboardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Statistics cards
        setupStatsPanel();
        
        // Charts and popular items
        setupChartsPanel();
        
        // Recent orders table
        setupOrdersPanel();
        
        // Add components to dashboard panel
        dashboardPanel.add(dashboardTitle);
        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(statsPanel);
        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(chartsPanel);
        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(ordersPanel);
        
        // Add dashboard to content panel with scroll support
        JScrollPane scrollPane = new JScrollPane(dashboardPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Setup the statistics panel with key metrics
     */
    private void setupStatsPanel() {
        statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(BG_LIGHT);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel totalSalesCard = createStatCard("Total Sales Today", "$1,240.50", "+12% from yesterday", false);
        JPanel ordersCard = createStatCard("Orders Today", "84", "+5% from yesterday", false);
        JPanel pendingCard = createStatCard("Pending Orders", "12", "Need attention", true);
        JPanel stockCard = createStatCard("Low Stock Items", "5", "Require reordering", true);
        
        statsPanel.add(totalSalesCard);
        statsPanel.add(ordersCard);
        statsPanel.add(pendingCard);
        statsPanel.add(stockCard);
    }
    
    /**
     * Helper method to create statistic cards with consistent styling
     */
    private JPanel createStatCard(String title, String value, String subtext, boolean isAlert) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 5),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(TEXT_DARK);
        
        JPanel subtextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        subtextPanel.setBackground(Color.WHITE);
        
        JLabel subtextLabel = new JLabel(subtext);
        subtextLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        if (isAlert) {
            subtextLabel.setForeground(Color.GRAY);
            
            // Add alert icon
            JLabel alertIcon = new JLabel("!");
            alertIcon.setFont(new Font("Arial", Font.BOLD, 12));
            alertIcon.setForeground(Color.WHITE);
            alertIcon.setBackground(PRIMARY_ORANGE);
            alertIcon.setOpaque(true);
            alertIcon.setPreferredSize(new Dimension(16, 16));
            alertIcon.setHorizontalAlignment(SwingConstants.CENTER);
            alertIcon.setVerticalAlignment(SwingConstants.CENTER);
            alertIcon.setBorder(new RoundedBorder(PRIMARY_ORANGE, 8));
            
            subtextPanel.add(alertIcon);
            subtextPanel.add(Box.createHorizontalStrut(5));
        } else {
            subtextLabel.setForeground(new Color(76, 175, 80)); // Green for positive change
        }
        
        subtextPanel.add(subtextLabel);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(subtextPanel);
        
        return card;
    }
    
    /**
     * Setup the charts panel with sales trend and popular items
     */
    private void setupChartsPanel() {
        chartsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        chartsPanel.setBackground(BG_LIGHT);
        chartsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        chartsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Sales chart panel (spans 2 columns)
        JPanel salesChartPanel = new JPanel(new BorderLayout());
        salesChartPanel.setBackground(Color.WHITE);
        salesChartPanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 5),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel chartTitle = new JLabel("Sales Trend (Last 7 Days)");
        chartTitle.setFont(new Font("Arial", Font.BOLD, 16));
        chartTitle.setForeground(PRIMARY_GREEN);
        
        // Create simple chart visualization
        JPanel chartContent = new SalesChartPanel();
        chartContent.setPreferredSize(new Dimension(0, 200));
        
        salesChartPanel.add(chartTitle, BorderLayout.NORTH);
        salesChartPanel.add(chartContent, BorderLayout.CENTER);
        
        // Popular items panel
        JPanel popularItemsPanel = new JPanel();
        popularItemsPanel.setLayout(new BoxLayout(popularItemsPanel, BoxLayout.Y_AXIS));
        popularItemsPanel.setBackground(Color.WHITE);
        popularItemsPanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 5),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel itemsTitle = new JLabel("Most Popular Items");
        itemsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        itemsTitle.setForeground(PRIMARY_GREEN);
        itemsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel itemsList = new JPanel();
        itemsList.setLayout(new BoxLayout(itemsList, BoxLayout.Y_AXIS));
        itemsList.setBackground(Color.WHITE);
        
        // Add popular items
        for (HashMap<String, Object> item : popularItems) {
            JPanel itemRow = createPopularItemRow(
                    (int) item.get("id"),
                    (String) item.get("name"),
                    (int) item.get("sold"));
            itemsList.add(itemRow);
            itemsList.add(Box.createVerticalStrut(15));
        }
        
        popularItemsPanel.add(itemsTitle);
        popularItemsPanel.add(Box.createVerticalStrut(15));
        popularItemsPanel.add(itemsList);
        
        // Add both panels to charts panel
        chartsPanel.add(salesChartPanel);
        chartsPanel.add(salesChartPanel); // This takes 2 columns
        chartsPanel.add(popularItemsPanel);
    }
    
    /**
     * Helper method to create popular item rows
     */
    private JPanel createPopularItemRow(int id, String name, int sold) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        // Item number badge
        JLabel idLabel = new JLabel(String.valueOf(id));
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBackground(PRIMARY_ORANGE);
        idLabel.setOpaque(true);
        idLabel.setPreferredSize(new Dimension(30, 30));
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idLabel.setVerticalAlignment(SwingConstants.CENTER);
        idLabel.setBorder(new RoundedBorder(PRIMARY_ORANGE, 15));
        
        // Item details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(TEXT_DARK);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel soldLabel = new JLabel("sold " + sold + " today");
        soldLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        soldLabel.setForeground(Color.GRAY);
        soldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        detailsPanel.add(nameLabel);
        detailsPanel.add(soldLabel);
        
        row.add(idLabel, BorderLayout.WEST);
        row.add(detailsPanel, BorderLayout.CENTER);
        
        return row;
    }
    
    /**
     * Setup the orders panel with recent orders table
     */
    private void setupOrdersPanel() {
        ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBackground(Color.WHITE);
        ordersPanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 5),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        ordersPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel ordersTitle = new JLabel("Recent Orders");
        ordersTitle.setFont(new Font("Arial", Font.BOLD, 16));
        ordersTitle.setForeground(PRIMARY_GREEN);
        
        // Create table model
        String[] columns = {"Order ID", "Customer", "Items", "Total", "Status", "Action"};
        ordersTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only action column is editable
            }
        };
        
        // Add sample data
        ordersTableModel.addRow(new Object[] {
            "#5283", "John Smith", "2x Bacon Deluxe, 1x Fries", "$24.95", "Processing", "Process"
        });
        ordersTableModel.addRow(new Object[] {
            "#5247", "Emma Johnson", "1x Veggie Supreme, 1x Soda", "$19.50", "Completed", "View"
        });
        
        // Create table
        JTable ordersTable = new JTable(ordersTableModel);
        ordersTable.setRowHeight(40);
        ordersTable.setShowVerticalLines(false);
        ordersTable.setIntercellSpacing(new Dimension(0, 10));
        ordersTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));
        ordersTable.getTableHeader().setForeground(Color.GRAY);
        ordersTable.getTableHeader().setBackground(Color.WHITE);
        ordersTable.setSelectionBackground(new Color(240, 240, 240));
        
        // Custom renderers
        ordersTable.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());
        ordersTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonCellRenderer());
        
        // Add button editor
        ordersTable.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor());
        
        // Adjust column widths
        ordersTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        ordersTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        ordersTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        ordersTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        ordersTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        ordersTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        JScrollPane tableScrollPane = new JScrollPane(ordersTable);
        tableScrollPane.setBorder(null);
        
        ordersPanel.add(ordersTitle, BorderLayout.NORTH);
        ordersPanel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        ordersPanel.add(tableScrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Helper method to attach listeners to buttons
     */
    private void attachListeners() {
        // Sidebar button listeners
        dashboardBtn.addActionListener(e -> {
            resetSidebarButtons();
            dashboardBtn.setBackground(PRIMARY_ORANGE);
        });
        
        menuManagementBtn.addActionListener(e -> {
            resetSidebarButtons();
            menuManagementBtn.setBackground(PRIMARY_ORANGE);
            JOptionPane.showMessageDialog(this, "Menu Management - Feature coming soon!");
        });
        
        orderManagementBtn.addActionListener(e -> {
            resetSidebarButtons();
            orderManagementBtn.setBackground(PRIMARY_ORANGE);
            JOptionPane.showMessageDialog(this, "Order Management - Feature coming soon!");
        });
        
        inventoryManagementBtn.addActionListener(e -> {
            resetSidebarButtons();
            inventoryManagementBtn.setBackground(PRIMARY_ORANGE);
            JOptionPane.showMessageDialog(this, "Inventory Management - Feature coming soon!");
        });
        
        salesReportingBtn.addActionListener(e -> {
            resetSidebarButtons();
            salesReportingBtn.setBackground(PRIMARY_ORANGE);
            JOptionPane.showMessageDialog(this, "Sales Reporting - Feature coming soon!");
        });
        
        settingsBtn.addActionListener(e -> {
            resetSidebarButtons();
            settingsBtn.setBackground(PRIMARY_ORANGE);
            JOptionPane.showMessageDialog(this, "Settings - Feature coming soon!");
        });
        
        logoutBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to logout?", 
                    "Confirm Logout", 
                    JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION) {
                dispose();
                // Navigate to login page or perform logout operations
                JOptionPane.showMessageDialog(null, "You have been logged out successfully!");
                // new LoginView().setVisible(true);
            }
        });
    }
    
    /**
     * Helper method to reset sidebar button styling
     */
    private void resetSidebarButtons() {
        dashboardBtn.setBackground(PRIMARY_GREEN);
        menuManagementBtn.setBackground(PRIMARY_GREEN);
        orderManagementBtn.setBackground(PRIMARY_GREEN);
        inventoryManagementBtn.setBackground(PRIMARY_GREEN);
        salesReportingBtn.setBackground(PRIMARY_GREEN);
        settingsBtn.setBackground(PRIMARY_GREEN);
    }
    
    /**
     * Inner class for rendering status cells with colored labels
     */
    private class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = new JLabel(value.toString());
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            
            if (value.toString().equals("Completed")) {
                label.setBackground(new Color(230, 255, 230));
                label.setForeground(new Color(76, 175, 80));
            } else {
                label.setBackground(new Color(255, 248, 225));
                label.setForeground(new Color(255, 152, 0));
            }
            
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            
            return label;
        }
    }
    
    /**
     * Inner class for rendering action button cells
     */
    private class ButtonCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JButton button = new JButton(value.toString());
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setForeground(Color.WHITE);
            
            if (table.getValueAt(row, 4).toString().equals("Completed")) {
                button.setBackground(Color.GRAY);
            } else {
                button.setBackground(PRIMARY_GREEN);
            }
            
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            
            return button;
        }
    }
    
    /**
     * Inner class for handling button cell editing
     */
    private class ButtonCellEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        
        public ButtonCellEditor() {
            super(new JTextField());
            setClickCountToStart(1);
            
            button = new JButton();
            button.setOpaque(true);
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            
            button.addActionListener(e -> {
                fireEditingStopped();
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {
            
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            
            if (table.getValueAt(row, 4).toString().equals("Completed")) {
                button.setBackground(Color.GRAY);
            } else {
                button.setBackground(PRIMARY_GREEN);
            }
            
            isPushed = true;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if (label.equals("Process")) {
                    JOptionPane.showMessageDialog(button, "Processing order...");
                } else {
                    JOptionPane.showMessageDialog(button, "Viewing order details...");
                }
            }
            isPushed = false;
            return label;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    
    /**
     * Inner class for creating sales chart visualization
     */
    private class SalesChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            int padding = 40;
            int chartWidth = width - 2 * padding;
            int chartHeight = height - 2 * padding;
            
            // Draw grid
            g2d.setColor(new Color(240, 240, 240));
            for (int i = 1; i <= 4; i++) {
                int y = padding + chartHeight * i / 4;
                g2d.drawLine(padding, y, width - padding, y);
            }
            
            // Calculate points
            int numPoints = salesData.size();
            int xStep = chartWidth / (numPoints - 1);
            Point[] points = new Point[numPoints];
            
            double maxValue = 0;
            for (HashMap<String, Object> data : salesData) {
                double sales = (double) data.get("sales");
                if (sales > maxValue) maxValue = sales;
            }
            
            for (int i = 0; i < numPoints; i++) {
                HashMap<String, Object> data = salesData.get(i);
                double sales = (double) data.get("sales");
                int x = padding + i * xStep;
                int y = padding + chartHeight - (int)(chartHeight * sales / maxValue);
                points[i] = new Point(x, y);
            }
            
            // Draw line
            g2d.setColor(PRIMARY_GREEN);
            g2d.setStroke(new BasicStroke(2));
            for (int i = 0; i < numPoints - 1; i++) {
                g2d.drawLine(points[i].x, points[i].y, points[i+1].x, points[i+1].y);
            }
            
            // Draw points and values
            for (int i = 0; i < numPoints; i++) {
                // Draw point
                g2d.setColor(PRIMARY_GREEN);
                g2d.fillOval(points[i].x - 4, points[i].y - 4, 8, 8);
                
                // Draw date label
                g2d.setColor(TEXT_DARK);
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                HashMap<String, Object> data = salesData.get(i);
                String date = (String) data.get("date");
                double sales = (double) data.get("sales");
                
                FontMetrics fm = g2d.getFontMetrics();
                int labelWidth = fm.stringWidth(date);
                g2d.drawString(date, points[i].x - labelWidth/2, height - 20);
                
                // Draw value label
                String valueStr = String.format("$%.0f", sales);
                int valueWidth = fm.stringWidth(valueStr);
                g2d.drawString(valueStr, points[i].x - valueWidth/2, points[i].y - 10);
            }
        }
    }
    
    /**
     * Custom rounded border class
     */
    private class RoundedBorder extends AbstractBorder {
        private Color color;
        private int radius;
        
        public RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }
    
    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        try {
            // Set the look and feel to the system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            AdminView adminView = new AdminView();
            adminView.setVisible(true);
        });
    }
}
                            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
