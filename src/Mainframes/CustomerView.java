package Mainframes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * CustomerView class - implements the customer dashboard for Burger Machine
 * @author 63906
 */
public class CustomerView extends javax.swing.JFrame {
    // Main panels
    private JPanel sidePanel;
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    
    // Dashboard components
    private JPanel currentOrderPanel;
    private JPanel recentOrdersPanel;
    private JPanel favoritesPanel;
    private JPanel specialOffersPanel;
    
    // Navigation buttons
    private JButton dashboardButton;
    private JButton burgerCreationButton;
    private JButton myCartButton;
    private JButton orderHistoryButton;
    private JButton settingsButton;
    private JButton logoutButton;
    
    // Current order status variables
    private int currentOrderNumber = 5283;
    private String currentOrderStatus = "Being Prepared";
    
    /**
     * Creates new form CustomerView
     */
    public CustomerView() {
        initComponents();
        this.setTitle("Burger Machine Customer Dashboard");
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Initializes the components for the CustomerView
     */
    private void initComponents() {
        // Set up the main content pane with BorderLayout
        getContentPane().setLayout(new BorderLayout());
        
        // Create the side panel
        createSidePanel();
        getContentPane().add(sidePanel, BorderLayout.WEST);
        
        // Create the main panel
        createMainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates the side panel with navigation buttons
     */
    private void createSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(23, 107, 83));
        sidePanel.setPreferredSize(new Dimension(200, 600));
        sidePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Add logo or brand name
        JLabel logoLabel = new JLabel("BURGER MACHINE");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(new EmptyBorder(10, 10, 30, 10));
        sidePanel.add(logoLabel);
        
        // Create and add navigation buttons
        dashboardButton = createSideButton("Dashboard");
        dashboardButton.setBackground(new Color(242, 101, 50));
        dashboardButton.setForeground(Color.WHITE);
        sidePanel.add(dashboardButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        burgerCreationButton = createSideButton("Burger Creation");
        sidePanel.add(burgerCreationButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        myCartButton = createSideButton("My Cart");
        sidePanel.add(myCartButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        orderHistoryButton = createSideButton("Order History");
        sidePanel.add(orderHistoryButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        settingsButton = createSideButton("Settings");
        sidePanel.add(settingsButton);
        
        // Create a glue component that takes up extra space
        sidePanel.add(Box.createVerticalGlue());
        
        // Add logout button at the bottom
        logoutButton = createSideButton("Logout");
        sidePanel.add(logoutButton);
        
        // Add copyright at the bottom
        JLabel copyrightLabel = new JLabel("© 2025 Burger Machine");
        copyrightLabel.setForeground(Color.WHITE);
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        copyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyrightLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        sidePanel.add(copyrightLabel);
        
        // Add action listeners to buttons
        setupButtonListeners();
    }
    
    /**
     * Creates buttons with consistent styling for the side panel
     * @param text The button text
     * @return The styled JButton
     */
    private JButton createSideButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(160, 40));
        button.setPreferredSize(new Dimension(160, 40));
        button.setBackground(new Color(23, 107, 83));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    /**
     * Creates the main panel with the dashboard content
     */
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Create the header panel with the dashboard title and user menu
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(242, 101, 50));
        headerPanel.setPreferredSize(new Dimension(700, 70));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel dashboardTitle = new JLabel("Burger Machine Customer Dashboard");
        dashboardTitle.setForeground(Color.WHITE);
        dashboardTitle.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(dashboardTitle, BorderLayout.WEST);
        
        // Add user avatar button to the header
        JButton userButton = new JButton("M");
        userButton.setFont(new Font("Arial", Font.BOLD, 16));
        userButton.setBackground(Color.WHITE);
        userButton.setForeground(new Color(242, 101, 50));
        userButton.setPreferredSize(new Dimension(40, 40));
        userButton.setBorderPainted(false);
        userButton.setFocusPainted(false);
        userButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerPanel.add(userButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create dashboard panel
        createDashboardPanel();
        mainPanel.add(dashboardPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates the dashboard panel with all dashboard components
     */
    private void createDashboardPanel() {
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBackground(new Color(245, 245, 245));
        dashboardPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Add Dashboard Overview Label
        JLabel overviewLabel = new JLabel("Dashboard Overview");
        overviewLabel.setFont(new Font("Arial", Font.BOLD, 20));
        overviewLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        dashboardPanel.add(overviewLabel, BorderLayout.NORTH);
        
        // Create the center panel with grid layout for dashboard widgets
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        centerPanel.setBackground(new Color(245, 245, 245));
        
        // Create dashboard widgets
        createCurrentOrderPanel();
        createRecentOrdersPanel();
        createFavoriteBurgersPanel();
        createSpecialOffersPanel();
        
        centerPanel.add(currentOrderPanel);
        centerPanel.add(recentOrdersPanel);
        centerPanel.add(favoritesPanel);
        centerPanel.add(specialOffersPanel);
        
        dashboardPanel.add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates the current order status panel
     */
    private void createCurrentOrderPanel() {
        currentOrderPanel = new JPanel();
        currentOrderPanel.setLayout(new BorderLayout());
        currentOrderPanel.setBackground(Color.WHITE);
        currentOrderPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        // Panel header
        JLabel headerLabel = new JLabel("Current Order Status");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setBorder(new EmptyBorder(15, 15, 10, 15));
        currentOrderPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Order Status Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        
        // Status Icon
        JPanel statusIcon = new JPanel();
        statusIcon.setPreferredSize(new Dimension(60, 60));
        statusIcon.setBackground(new Color(242, 101, 50));
        JLabel iconLabel = new JLabel("!");
        iconLabel.setFont(new Font("Arial", Font.BOLD, 30));
        iconLabel.setForeground(Color.WHITE);
        statusIcon.add(iconLabel);
        contentPanel.add(statusIcon);
        
        // Order Details
        JPanel orderDetails = new JPanel();
        orderDetails.setLayout(new BoxLayout(orderDetails, BoxLayout.Y_AXIS));
        orderDetails.setBackground(Color.WHITE);
        
        JLabel orderNumberLabel = new JLabel("Order #" + currentOrderNumber);
        orderNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel statusLabel = new JLabel(currentOrderStatus);
        statusLabel.setForeground(new Color(242, 101, 50));
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        orderDetails.add(orderNumberLabel);
        orderDetails.add(Box.createRigidArea(new Dimension(0, 5)));
        orderDetails.add(statusLabel);
        
        contentPanel.add(orderDetails);
        currentOrderPanel.add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates the recent orders panel
     */
    private void createRecentOrdersPanel() {
        recentOrdersPanel = new JPanel();
        recentOrdersPanel.setLayout(new BorderLayout());
        recentOrdersPanel.setBackground(Color.WHITE);
        recentOrdersPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        // Panel header
        JLabel headerLabel = new JLabel("Recent Orders");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setBorder(new EmptyBorder(15, 15, 10, 15));
        recentOrdersPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Recent orders list
        JPanel ordersListPanel = new JPanel();
        ordersListPanel.setLayout(new BoxLayout(ordersListPanel, BoxLayout.Y_AXIS));
        ordersListPanel.setBackground(Color.WHITE);
        ordersListPanel.setBorder(new EmptyBorder(10, 15, 15, 15));
        
        // First order
        JPanel order1 = createOrderItem("Classic Cheeseburger Meal", "$12.99", "April 18, 2025");
        ordersListPanel.add(order1);
        
        // Add separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        ordersListPanel.add(separator);
        
        // Second order
        JPanel order2 = createOrderItem("Veggie Supreme Burger", "$9.99", "April 15, 2025");
        ordersListPanel.add(order2);
        
        recentOrdersPanel.add(ordersListPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates an order item panel for the recent orders list
     * @param itemName Name of the ordered item
     * @param price Price of the item
     * @param date Order date
     * @return JPanel containing the order information
     */
    private JPanel createOrderItem(String itemName, String price, String date) {
        JPanel orderItem = new JPanel();
        orderItem.setLayout(new BorderLayout());
        orderItem.setBackground(Color.WHITE);
        orderItem.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JLabel nameLabel = new JLabel(itemName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateLabel.setForeground(Color.GRAY);
        
        orderItem.add(nameLabel, BorderLayout.WEST);
        orderItem.add(priceLabel, BorderLayout.EAST);
        orderItem.add(dateLabel, BorderLayout.SOUTH);
        
        return orderItem;
    }
    
    /**
     * Creates the favorite burgers panel
     */
    private void createFavoriteBurgersPanel() {
        favoritesPanel = new JPanel();
        favoritesPanel.setLayout(new BorderLayout());
        favoritesPanel.setBackground(Color.WHITE);
        favoritesPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        // Panel header
        JLabel headerLabel = new JLabel("Saved Favorite Burgers");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setBorder(new EmptyBorder(15, 15, 10, 15));
        favoritesPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Favorites list
        JPanel favoritesListPanel = new JPanel();
        favoritesListPanel.setLayout(new BoxLayout(favoritesListPanel, BoxLayout.Y_AXIS));
        favoritesListPanel.setBackground(Color.WHITE);
        favoritesListPanel.setBorder(new EmptyBorder(10, 15, 15, 15));
        
        // Add favorite burger items
        JPanel favorite1 = createFavoriteItem(1, "The Classic", "Beef, Cheddar, Lettuce, Tomato");
        favoritesListPanel.add(favorite1);
        favoritesListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel favorite2 = createFavoriteItem(2, "Spicy Deluxe", "Chicken, Pepper Jack, Jalapeños");
        favoritesListPanel.add(favorite2);
        favoritesListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel favorite3 = createFavoriteItem(3, "Veggie Supreme", "Plant-based, Avocado, Sprouts");
        favoritesListPanel.add(favorite3);
        
        favoritesPanel.add(favoritesListPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates a favorite burger item panel
     * @param number The favorite number
     * @param name Name of the burger
     * @param ingredients List of ingredients
     * @return JPanel containing the favorite burger information
     */
    private JPanel createFavoriteItem(int number, String name, String ingredients) {
        JPanel favoriteItem = new JPanel();
        favoriteItem.setLayout(new BorderLayout(15, 0));
        favoriteItem.setBackground(Color.WHITE);
        favoriteItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        favoriteItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        // Number icon
        JPanel numberIcon = new JPanel();
        numberIcon.setPreferredSize(new Dimension(50, 50));
        numberIcon.setBackground(new Color(242, 101, 50));
        JLabel numberLabel = new JLabel(String.valueOf(number));
        numberLabel.setFont(new Font("Arial", Font.BOLD, 20));
        numberLabel.setForeground(Color.WHITE);
        numberIcon.add(numberLabel);
        
        // Burger details
        JPanel burgerDetails = new JPanel();
        burgerDetails.setLayout(new BoxLayout(burgerDetails, BoxLayout.Y_AXIS));
        burgerDetails.setBackground(Color.WHITE);
        burgerDetails.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel ingredientsLabel = new JLabel(ingredients);
        ingredientsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        ingredientsLabel.setForeground(Color.GRAY);
        ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        burgerDetails.add(nameLabel);
        burgerDetails.add(Box.createRigidArea(new Dimension(0, 3)));
        burgerDetails.add(ingredientsLabel);
        
        favoriteItem.add(numberIcon, BorderLayout.WEST);
        favoriteItem.add(burgerDetails, BorderLayout.CENTER);
        
        // Make the item clickable
        favoriteItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        favoriteItem.addMouseListener(new FavoriteItemClickListener(name));
        
        return favoriteItem;
    }
    
    /**
     * Creates the special offers panel
     */
    private void createSpecialOffersPanel() {
        specialOffersPanel = new JPanel();
        specialOffersPanel.setLayout(new BorderLayout());
        specialOffersPanel.setBackground(Color.WHITE);
        specialOffersPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        // Panel header
        JLabel headerLabel = new JLabel("Special Offers");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setBorder(new EmptyBorder(15, 15, 10, 15));
        specialOffersPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Offers list
        JPanel offersListPanel = new JPanel();
        offersListPanel.setLayout(new BoxLayout(offersListPanel, BoxLayout.Y_AXIS));
        offersListPanel.setBackground(Color.WHITE);
        offersListPanel.setBorder(new EmptyBorder(10, 15, 15, 15));
        
        // Tuesday Special Offer
        JPanel tuesdayOffer = createSpecialOfferItem(
                "BURGER TUESDAY", 
                "Get 20% off on any burger today!", 
                new Color(255, 240, 230), 
                new Color(242, 101, 50)
        );
        offersListPanel.add(tuesdayOffer);
        offersListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Meal Deal Offer
        JPanel mealDealOffer = createSpecialOfferItem(
                "MEAL DEAL", 
                "Add fries & drink for just $3.99", 
                new Color(230, 245, 230), 
                new Color(23, 107, 83)
        );
        offersListPanel.add(mealDealOffer);
        
        specialOffersPanel.add(offersListPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates a special offer item panel
     * @param title Offer title
     * @param description Offer description
     * @param backgroundColor Background color for the offer panel
     * @param buttonColor Color for the claim button
     * @return JPanel containing the special offer information
     */
    private JPanel createSpecialOfferItem(String title, String description, Color backgroundColor, Color buttonColor) {
        JPanel offerItem = new JPanel();
        offerItem.setLayout(new BoxLayout(offerItem, BoxLayout.Y_AXIS));
        offerItem.setBackground(backgroundColor);
        offerItem.setBorder(new EmptyBorder(15, 15, 15, 15));
        offerItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton claimButton = new JButton("Claim");
        claimButton.setBackground(buttonColor);
        claimButton.setForeground(Color.WHITE);
        claimButton.setFocusPainted(false);
        claimButton.setBorderPainted(false);
        claimButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        claimButton.setMaximumSize(new Dimension(100, 30));
        claimButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add action listener to claim button
        claimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                    CustomerView.this,
                    "Offer '" + title + "' has been claimed and added to your account!",
                    "Offer Claimed",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        offerItem.add(titleLabel);
        offerItem.add(Box.createRigidArea(new Dimension(0, 5)));
        offerItem.add(descriptionLabel);
        offerItem.add(Box.createRigidArea(new Dimension(0, 10)));
        offerItem.add(claimButton);
        
        return offerItem;
    }
    
    /**
     * Set up action listeners for all navigation buttons
     */
    private void setupButtonListeners() {
        // Dashboard button listener
        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                    CustomerView.this,
                    "You are already on the Dashboard.",
                    "Navigation",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        // Burger Creation button listener
        burgerCreationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                    CustomerView.this,
                    "Navigating to Burger Creation page...\n(Feature to be implemented)",
                    "Navigation",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        // My Cart button listener
        myCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                    CustomerView.this,
                    "Navigating to My Cart page...\n(Feature to be implemented)",
                    "Navigation",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        // Order History button listener
        orderHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                    CustomerView.this,
                    "Navigating to Order History page...\n(Feature to be implemented)",
                    "Navigation",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        // Settings button listener
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                    CustomerView.this,
                    "Navigating to Settings page...\n(Feature to be implemented)",
                    "Navigation",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        // Logout button listener
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                    CustomerView.this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (response == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(
                        CustomerView.this,
                        "You have been logged out successfully.",
                        "Logout Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    // Here you would typically close this window and open the login window
                    dispose();
                    // LoginView loginView = new LoginView();
                    // loginView.setVisible(true);
                }
            }
        });
    }
    
    /**
     * Mouse listener for favorite burger items
     */
    private class FavoriteItemClickListener extends MouseAdapter {
        private String burgerName;
        
        public FavoriteItemClickListener(String burgerName) {
            this.burgerName = burgerName;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            int response = JOptionPane.showConfirmDialog(
                CustomerView.this,
                "Would you like to add '" + burgerName + "' to your cart?",
                "Add to Cart",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (response == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                    CustomerView.this,
                    "'" + burgerName + "' has been added to your cart!",
                    "Added to Cart",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            JPanel panel = (JPanel) e.getSource();
            panel.setBorder(BorderFactory.createLineBorder(new Color(242, 101, 50), 2));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            JPanel panel = (JPanel) e.getSource();
            panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
    }
    
    /**
     * Main method to launch the application
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerView().setVisible(true);
            }
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
            java.util.logging.Logger.getLogger(CustomerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
