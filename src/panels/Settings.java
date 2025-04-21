package panels;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.Preferences;

/**
 * Settings panel for Burger Machine Admin Dashboard
 * @author 63906
 */
public class Settings extends javax.swing.JPanel {
    // UI Components
    private JPanel headerPanel;
    private JPanel tabPanel;
    private JPanel contentPanel;
    private JPanel restaurantInfoPanel;
    private JPanel systemSettingsPanel;
    private JPanel buttonPanel;
    
    // Tab buttons
    private JButton btnGeneral;
    private JButton btnUserManagement;
    private JButton btnPaymentMethods;
    private JButton btnBackupRestore;
    
    // Restaurant Info fields
    private JLabel lblRestaurantInfo;
    private JLabel lblRestaurantName;
    private JTextField txtRestaurantName;
    private JLabel lblAddress;
    private JTextField txtAddress;
    private JLabel lblContactPhone;
    private JTextField txtContactPhone;
    private JLabel lblContactEmail;
    private JTextField txtContactEmail;
    
    // System Settings fields
    private JLabel lblSystemSettings;
    private JLabel lblBusinessHours;
    private JTextField txtBusinessHours;
    private JLabel lblTaxRate;
    private JTextField txtTaxRate;
    private JLabel lblOrderNotifications;
    private JToggleButton tglOrderNotifications;
    
    // Action button
    private JButton btnSave;
    
    // Preferences for saving settings
    private Preferences prefs;
    
    // Active tab
    private String activeTab = "General";
    
    /**
     * Creates new form Settings
     */
    public Settings() {
        prefs = Preferences.userNodeForPackage(Settings.class);
        initComponents();
        loadSettings();
    }
    
    /**
     * Initialize UI components
     */
    private void initComponents() {
        // Set layout
        setLayout(new BorderLayout());
        setBackground(new Color(243, 244, 246)); // Light gray background
        
        // Create header panel with title
        createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(243, 244, 246));
        
        // Create white card panel with shadow effect
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        // Create tab panel
        createTabPanel();
        cardPanel.add(tabPanel, BorderLayout.NORTH);
        
        // Create content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Create restaurant info panel
        createRestaurantInfoPanel();
        
        // Create system settings panel
        createSystemSettingsPanel();
        
        // Add components to content panel
        contentPanel.add(restaurantInfoPanel, BorderLayout.NORTH);
        contentPanel.add(systemSettingsPanel, BorderLayout.CENTER);
        
        // Create button panel
        createButtonPanel();
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        cardPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Create header panel with title
     */
    private void createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        headerPanel.setBackground(new Color(243, 244, 246));
        
        JLabel lblTitle = new JLabel("Settings");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(21, 128, 61)); // Green color
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
    }
    
    /**
     * Create tab panel for settings categories
     */
    private void createTabPanel() {
        tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabPanel.setBackground(Color.WHITE);
        tabPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        btnGeneral = createTabButton("General", activeTab.equals("General"));
        btnUserManagement = createTabButton("User Management", activeTab.equals("User Management"));
        btnPaymentMethods = createTabButton("Payment Methods", activeTab.equals("Payment Methods"));
        btnBackupRestore = createTabButton("Backup & Restore", activeTab.equals("Backup & Restore"));
        
        btnGeneral.addActionListener(e -> {
            activeTab = "General";
            updateTabButtons();
        });
        
        btnUserManagement.addActionListener(e -> {
            activeTab = "User Management";
            updateTabButtons();
            JOptionPane.showMessageDialog(this, "User Management section is under development.", 
                "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnPaymentMethods.addActionListener(e -> {
            activeTab = "Payment Methods";
            updateTabButtons();
            JOptionPane.showMessageDialog(this, "Payment Methods section is under development.", 
                "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnBackupRestore.addActionListener(e -> {
            activeTab = "Backup & Restore";
            updateTabButtons();
            JOptionPane.showMessageDialog(this, "Backup & Restore section is under development.", 
                "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
        });
        
        tabPanel.add(btnGeneral);
        tabPanel.add(btnUserManagement);
        tabPanel.add(btnPaymentMethods);
        tabPanel.add(btnBackupRestore);
    }
    
    /**
     * Create restaurant information panel
     */
    private void createRestaurantInfoPanel() {
        restaurantInfoPanel = new JPanel();
        restaurantInfoPanel.setLayout(new GridBagLayout());
        restaurantInfoPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Restaurant Information section title
        lblRestaurantInfo = new JLabel("Restaurant Information");
        lblRestaurantInfo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        restaurantInfoPanel.add(lblRestaurantInfo, gbc);
        
        // Restaurant Name
        lblRestaurantName = new JLabel("Restaurant Name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        restaurantInfoPanel.add(lblRestaurantName, gbc);
        
        txtRestaurantName = new JTextField(20);
        txtRestaurantName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        restaurantInfoPanel.add(txtRestaurantName, gbc);
        
        // Address
        lblAddress = new JLabel("Address");
        gbc.gridx = 0;
        gbc.gridy = 3;
        restaurantInfoPanel.add(lblAddress, gbc);
        
        txtAddress = new JTextField(20);
        txtAddress.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 0;
        gbc.gridy = 4;
        restaurantInfoPanel.add(txtAddress, gbc);
        
        // Contact Phone
        lblContactPhone = new JLabel("Contact Phone");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.5;
        restaurantInfoPanel.add(lblContactPhone, gbc);
        
        txtContactPhone = new JTextField(15);
        txtContactPhone.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 0;
        gbc.gridy = 6;
        restaurantInfoPanel.add(txtContactPhone, gbc);
        
        // Contact Email
        lblContactEmail = new JLabel("Contact Email");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 0.5;
        restaurantInfoPanel.add(lblContactEmail, gbc);
        
        txtContactEmail = new JTextField(15);
        txtContactEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        gbc.gridy = 6;
        restaurantInfoPanel.add(txtContactEmail, gbc);
    }
    
    /**
     * Create system settings panel
     */
    private void createSystemSettingsPanel() {
        systemSettingsPanel = new JPanel();
        systemSettingsPanel.setLayout(new GridBagLayout());
        systemSettingsPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // System Settings section title
        lblSystemSettings = new JLabel("System Settings");
        lblSystemSettings.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        systemSettingsPanel.add(lblSystemSettings, gbc);
        
        // Business Hours
        lblBusinessHours = new JLabel("Business Hours");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        systemSettingsPanel.add(lblBusinessHours, gbc);
        
        txtBusinessHours = new JTextField(15);
        txtBusinessHours.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 0;
        gbc.gridy = 2;
        systemSettingsPanel.add(txtBusinessHours, gbc);
        
        // Tax Rate
        lblTaxRate = new JLabel("Tax Rate (%)");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        systemSettingsPanel.add(lblTaxRate, gbc);
        
        txtTaxRate = new JTextField(10);
        txtTaxRate.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        systemSettingsPanel.add(txtTaxRate, gbc);
        
        // Order Notifications
        lblOrderNotifications = new JLabel("Order Notifications");
        gbc.gridx = 0;
        gbc.gridy = 3;
        systemSettingsPanel.add(lblOrderNotifications, gbc);
        
        JPanel notificationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        notificationPanel.setBackground(Color.WHITE);
        
        tglOrderNotifications = new JToggleButton();
        tglOrderNotifications.setPreferredSize(new Dimension(50, 24));
        tglOrderNotifications.setBackground(new Color(21, 128, 61));
        
        JLabel lblToggleText = new JLabel("Enable email notifications for new orders");
        lblToggleText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        notificationPanel.add(tglOrderNotifications);
        notificationPanel.add(lblToggleText);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        systemSettingsPanel.add(notificationPanel, gbc);
    }
    
    /**
     * Create button panel for save action
     */
    private void createButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
        
        btnSave = new JButton("Save");
        btnSave.setPreferredSize(new Dimension(120, 40));
        btnSave.setBackground(new Color(21, 128, 61));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnSave.addActionListener(e -> saveSettings());
        
        buttonPanel.add(btnSave);
    }
    
    /**
     * Create a tab button with the specified text
     */
    private JButton createTabButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        
        if (isActive) {
            button.setBackground(new Color(21, 128, 61));
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
        } else {
            button.setBackground(new Color(229, 231, 235));
            button.setForeground(new Color(55, 65, 81));
            button.setBorderPainted(false);
        }
        
        return button;
    }
    
    /**
     * Update tab button styles based on active tab
     */
    private void updateTabButtons() {
        btnGeneral.setBackground(activeTab.equals("General") ? new Color(21, 128, 61) : new Color(229, 231, 235));
        btnGeneral.setForeground(activeTab.equals("General") ? Color.WHITE : new Color(55, 65, 81));
        
        btnUserManagement.setBackground(activeTab.equals("User Management") ? new Color(21, 128, 61) : new Color(229, 231, 235));
        btnUserManagement.setForeground(activeTab.equals("User Management") ? Color.WHITE : new Color(55, 65, 81));
        
        btnPaymentMethods.setBackground(activeTab.equals("Payment Methods") ? new Color(21, 128, 61) : new Color(229, 231, 235));
        btnPaymentMethods.setForeground(activeTab.equals("Payment Methods") ? Color.WHITE : new Color(55, 65, 81));
        
        btnBackupRestore.setBackground(activeTab.equals("Backup & Restore") ? new Color(21, 128, 61) : new Color(229, 231, 235));
        btnBackupRestore.setForeground(activeTab.equals("Backup & Restore") ? Color.WHITE : new Color(55, 65, 81));
    }
    
    /**
     * Load settings from preferences
     */
    private void loadSettings() {
        txtRestaurantName.setText(prefs.get("restaurant.name", "Downtown Burger Machine"));
        txtAddress.setText(prefs.get("restaurant.address", "123 Main Street, Anytown, ST 12345"));
        txtContactPhone.setText(prefs.get("restaurant.phone", "(555) 123-4567"));
        txtContactEmail.setText(prefs.get("restaurant.email", "info@burgermachine.com"));
        txtBusinessHours.setText(prefs.get("system.hours", "8:00 AM - 10:00 PM"));
        txtTaxRate.setText(prefs.get("system.taxrate", "7.25"));
        tglOrderNotifications.setSelected(prefs.getBoolean("system.notifications", true));
    }
    
    /**
     * Save settings to preferences
     */
    private void saveSettings() {
        try {
            // Validate tax rate
            double taxRate = Double.parseDouble(txtTaxRate.getText().trim());
            if (taxRate < 0 || taxRate > 100) {
                JOptionPane.showMessageDialog(this, 
                        "Tax rate must be between 0 and 100", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Save all settings
            prefs.put("restaurant.name", txtRestaurantName.getText().trim());
            prefs.put("restaurant.address", txtAddress.getText().trim());
            prefs.put("restaurant.phone", txtContactPhone.getText().trim());
            prefs.put("restaurant.email", txtContactEmail.getText().trim());
            prefs.put("system.hours", txtBusinessHours.getText().trim());
            prefs.put("system.taxrate", txtTaxRate.getText().trim());
            prefs.putBoolean("system.notifications", tglOrderNotifications.isSelected());
            
            // Show success message
            JOptionPane.showMessageDialog(this, 
                    "Settings saved successfully", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "Please enter a valid tax rate", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Custom toggle button UI with slider appearance
     */
    private class ToggleButtonUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            JToggleButton button = (JToggleButton) c;
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Paint background
            if (button.isSelected()) {
                g2d.setColor(new Color(21, 128, 61)); // Green when selected
            } else {
                g2d.setColor(new Color(209, 213, 219)); // Gray when not selected
            }
            g2d.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 16, 16);
            
            // Paint slider
            if (button.isSelected()) {
                g2d.setColor(Color.WHITE);
                g2d.fillOval(button.getWidth() - 22, 2, 20, 20);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.fillOval(2, 2, 20, 20);
            }
            
            g2d.dispose();
        }
    }
    
    /**
     * Main method for testing the panel
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JFrame frame = new JFrame("Settings Panel Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().add(new Settings());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
