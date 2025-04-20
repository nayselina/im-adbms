package Mainframes;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class AdminView extends javax.swing.JFrame {
    
    // Components declaration
    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel contentPanel;
    private JPanel topPanel;
    private JPanel dashboardOverviewPanel;
    private JPanel salesChartPanel;
    private JPanel popularItemsPanel;
    private JPanel recentOrdersPanel;
    
    private JButton dashboardButton;
    private JButton menuManagementButton;
    private JButton orderManagementButton;
    private JButton inventoryManagementButton;
    private JButton salesReportingButton;
    private JButton settingsButton;
    private JButton logoutButton;
    
    public AdminView() {
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Burger Machine Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panel
        mainPanel = new JPanel(new BorderLayout());
        
        // Create side panel (navigation)
        createSidePanel();
        
        // Create content panel
        createContentPanel();
        
        // Add panels to main panel
        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Set frame size and center
        setSize(1400, 800);
        setLocationRelativeTo(null);
    }
    
    private void createSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(38, 128, 95)); // Green background
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        
        // Add logo/title
        JLabel logoLabel = new JLabel("BURGER MACHINE", SwingConstants.CENTER);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidePanel.add(logoLabel);
        
        // Dashboard button (highlighted)
        dashboardButton = createSideButton("Dashboard", true);
        menuManagementButton = createSideButton("Menu Management", false);
        orderManagementButton = createSideButton("Order Management", false);
        inventoryManagementButton = createSideButton("Inventory Management", false);
        salesReportingButton = createSideButton("Sales Reporting", false);
        settingsButton = createSideButton("Settings", false);
        
        // Add buttons to side panel
        sidePanel.add(dashboardButton);
        sidePanel.add(menuManagementButton);
        sidePanel.add(orderManagementButton);
        sidePanel.add(inventoryManagementButton);
        sidePanel.add(salesReportingButton);
        sidePanel.add(settingsButton);
        
        // Add spacer
        sidePanel.add(Box.createVerticalGlue());
        
        // Logout button
        logoutButton = createSideButton("Logout", false);
        sidePanel.add(logoutButton);
        
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }
    
    private JButton createSideButton(String text, boolean isSelected) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        if (isSelected) {
            button.setBackground(new Color(241, 98, 58)); // Orange for selected
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(38, 128, 95));
            button.setForeground(Color.WHITE);
        }
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    button.setBackground(new Color(45, 145, 108));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    button.setBackground(new Color(38, 128, 95));
                }
            }
        });
        
        return button;
    }
    
    private void createContentPanel() {
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        // Create top panel (header)
        createTopPanel();
        
        // Create dashboard content
        JPanel dashboardContent = new JPanel();
        dashboardContent.setLayout(new BoxLayout(dashboardContent, BoxLayout.Y_AXIS));
        dashboardContent.setBackground(Color.WHITE);
        
        // Dashboard overview
        createDashboardOverview();
        
        // Sales chart and popular items
        JPanel middlePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        createSalesChart();
        createPopularItems();
        
        middlePanel.add(salesChartPanel);
        middlePanel.add(popularItemsPanel);
        
        // Recent orders
        createRecentOrders();
        
        // Add all to dashboard content
        dashboardContent.add(dashboardOverviewPanel);
        dashboardContent.add(middlePanel);
        dashboardContent.add(recentOrdersPanel);
        
        // Add to content panel
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(dashboardContent, BorderLayout.CENTER);
    }
    
    private void createTopPanel() {
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(241, 98, 58));
        topPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel titleLabel = new JLabel("Burger Machine Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        // Admin profile - right side
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(241, 98, 58));
        
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton profileButton = new JButton("A");
        profileButton.setPreferredSize(new Dimension(35, 35));
        profileButton.setBackground(Color.WHITE);
        profileButton.setForeground(new Color(241, 98, 58));
        profileButton.setFocusPainted(false);
        profileButton.setBorderPainted(false);
        
        rightPanel.add(adminLabel);
        rightPanel.add(profileButton);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
    }
    
    private void createDashboardOverview() {
        dashboardOverviewPanel = new JPanel();
        dashboardOverviewPanel.setLayout(new GridLayout(1, 4, 20, 0));
        dashboardOverviewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dashboardOverviewPanel.setBackground(Color.WHITE);
        
        // Total Sales Today
        JPanel salesPanel = createStatCard("Total Sales Today", "$1,240.50", "+12% from yesterday");
        
        // Orders Today
        JPanel ordersPanel = createStatCard("Orders Today", "84", "+8% from yesterday");
        
        // Pending Orders
        JPanel pendingPanel = createStatCard("Pending Orders", "12", "Need attention");
        
        // Low Stock Items
        JPanel stockPanel = createStatCard("Low Stock Items", "5", "Require reordering");
        
        dashboardOverviewPanel.add(salesPanel);
        dashboardOverviewPanel.add(ordersPanel);
        dashboardOverviewPanel.add(pendingPanel);
        dashboardOverviewPanel.add(stockPanel);
    }
    
    private JPanel createStatCard(String title, String value, String subtitle) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(240, 240, 240)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(100, 100, 100));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(new Color(33, 33, 33));
        
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        if (subtitle.contains("+")) {
            subtitleLabel.setForeground(new Color(76, 175, 80)); // Green for positive
        } else {
            subtitleLabel.setForeground(new Color(150, 150, 150));
        }
        
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(subtitleLabel);
        
        return card;
    }
    
    private void createSalesChart() {
        salesChartPanel = new JPanel(new BorderLayout());
        salesChartPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(240, 240, 240)), 
            "Sales Trend (Last 7 Days)",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        salesChartPanel.setBackground(Color.WHITE);
        
        // Simulate chart area (in a real application, you'd use a charting library)
        JPanel chartArea = new JPanel();
        chartArea.setBackground(Color.WHITE);
        chartArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        salesChartPanel.add(chartArea, BorderLayout.CENTER);
    }
    
    private void createPopularItems() {
        popularItemsPanel = new JPanel();
        popularItemsPanel.setLayout(new BorderLayout());
        popularItemsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(240, 240, 240)), 
            "Most Popular Items",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        popularItemsPanel.setBackground(Color.WHITE);
        
        JPanel itemList = new JPanel();
        itemList.setLayout(new BoxLayout(itemList, BoxLayout.Y_AXIS));
        itemList.setBackground(Color.WHITE);
        
        itemList.add(createPopularItem("Classic Cheeseburger", "sold 35 today", 1));
        itemList.add(createPopularItem("Bacon Deluxe", "sold 29 today", 2));
        itemList.add(createPopularItem("Veggie Supreme", "sold 19 today", 3));
        
        popularItemsPanel.add(itemList, BorderLayout.CENTER);
    }
    
    private JPanel createPopularItem(String name, String sales, int position) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        itemPanel.setBackground(Color.WHITE);
        
        JLabel positionLabel = new JLabel(String.valueOf(position));
        positionLabel.setPreferredSize(new Dimension(30, 30));
        positionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        positionLabel.setOpaque(true);
        positionLabel.setBackground(new Color(241, 98, 58));
        positionLabel.setForeground(Color.WHITE);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel salesLabel = new JLabel(sales);
        salesLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        salesLabel.setForeground(new Color(100, 100, 100));
        
        textPanel.add(nameLabel);
        textPanel.add(salesLabel);
        
        itemPanel.add(positionLabel, BorderLayout.WEST);
        itemPanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.CENTER);
        itemPanel.add(textPanel, BorderLayout.EAST);
        
        return itemPanel;
    }
    
    private void createRecentOrders() {
        recentOrdersPanel = new JPanel(new BorderLayout());
        recentOrdersPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(240, 240, 240)), 
            "Recent Orders",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        recentOrdersPanel.setBackground(Color.WHITE);
        recentOrdersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create table
        String[] columnNames = {"Order ID", "Customer", "Items", "Total", "Status", "Action"};
        Object[][] data = {
            {"#2542", "John Smith", "2x Bacon Deluxe, 1x Fries", "$24.95", "Pending", "Process"},
            {"#2543", "Emma Johnson", "1x Veggie Supreme, 1x Soda", "$10.50", "Completed", "View"}
        };
        
        JTable table = new JTable(data, columnNames);
        table.setRowHeight(40);
        table.getTableHeader().setBackground(new Color(248, 248, 248));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        recentOrdersPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new AdminView().setVisible(true);
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
