package mainframes;

import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.RoundRectangle2D;
import mainframes.AdminView;
import mainframes.CustomerView;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkShowPassword;
    private JButton btnLogin, btnRegister;
    private final Color PRIMARY_GREEN = new Color(38, 133, 65);
    private final Color PRIMARY_ORANGE = new Color(247, 92, 30);
    private final Color BACKGROUND_CREAM = new Color(252, 248, 232);
    private final Font HEADER_FONT = new Font("Arial", Font.BOLD, 28);
    private final Font SUBHEADER_FONT = new Font("Arial", Font.ITALIC, 16);
    private final Font LABEL_FONT = new Font("Arial", Font.BOLD, 16);
    private final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    private final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    
    // Card panel dimensions
    private final int CARD_WIDTH = 360;
    private final int CARD_HEIGHT = 520;
    private final int CARD_X = 45;  // (450 - 360) / 2
    private final int CARD_Y = 60;
    
    public LoginView() {
        setTitle("Burger Machine");
        setSize(450, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with custom painting
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Paint background
                GradientPaint backgroundGradient = new GradientPaint(
                    0, 0, new Color(255, 153, 51), 
                    0, getHeight(), new Color(255, 129, 0)
                );
                g2d.setPaint(backgroundGradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw card panel shadow
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(CARD_X + 5, CARD_Y + 5, CARD_WIDTH, CARD_HEIGHT, 20, 20);
                
                // Draw card panel
                g2d.setColor(BACKGROUND_CREAM);
                g2d.fillRoundRect(CARD_X, CARD_Y, CARD_WIDTH, CARD_HEIGHT, 20, 20);
                
                // Draw card border
                g2d.setColor(PRIMARY_GREEN);
                g2d.setStroke(new BasicStroke(3f));
                g2d.drawRoundRect(CARD_X, CARD_Y, CARD_WIDTH, CARD_HEIGHT, 20, 20);
                
                // Draw burger logo (centered in card)
                drawBurgerLogo(g2d, CARD_X + CARD_WIDTH / 2 - 40, CARD_Y + 60);
                
                // Draw app name
                g2d.setFont(HEADER_FONT);
                g2d.setColor(PRIMARY_GREEN);
                FontMetrics fm = g2d.getFontMetrics();
                String appName = "BURGER MACHINE";
                int textWidth = fm.stringWidth(appName);
                g2d.drawString(appName, CARD_X + (CARD_WIDTH - textWidth) / 2, CARD_Y + 160);
                
                // Draw tagline
                g2d.setFont(SUBHEADER_FONT);
                g2d.setColor(PRIMARY_ORANGE);
                String tagline = "Unleash the Flavor!";
                fm = g2d.getFontMetrics();
                textWidth = fm.stringWidth(tagline);
                g2d.drawString(tagline, CARD_X + (CARD_WIDTH - textWidth) / 2, CARD_Y + 185);
            }
            
            private void drawBurgerLogo(Graphics2D g2d, int x, int y) {
                // Enable anti-aliasing
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Top bun
                g2d.setColor(new Color(235, 156, 60));
                g2d.fill(new RoundRectangle2D.Float(x, y, 80, 25, 30, 30));
                
                // Lettuce
                g2d.setColor(new Color(58, 170, 53));
                g2d.fillRect(x + 5, y + 25, 70, 8);
                
                // Cheese
                g2d.setColor(new Color(255, 190, 30));
                g2d.fillRect(x + 5, y + 33, 70, 6);
                
                // Patty
                g2d.setColor(new Color(140, 70, 20));
                g2d.fill(new RoundRectangle2D.Float(x + 5, y + 39, 70, 18, 10, 10));
                
                // Bottom bun
                g2d.setColor(new Color(245, 190, 100));
                g2d.fill(new RoundRectangle2D.Float(x, y + 57, 80, 20, 20, 20));
            }
        };
        mainPanel.setLayout(null);
        
        // Calculate the margins for component positioning within the card
        int componentMargin = 30;
        int componentX = CARD_X + componentMargin;
        int componentWidth = CARD_WIDTH - (componentMargin * 2);
        
        // Username field
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(componentX, CARD_Y + 220, componentWidth, 20);
        lblUsername.setForeground(PRIMARY_GREEN);
        lblUsername.setFont(LABEL_FONT);
        mainPanel.add(lblUsername);
        
        txtUsername = createStyledTextField();
        txtUsername.setBounds(componentX, CARD_Y + 245, componentWidth, 45);
        mainPanel.add(txtUsername);
        
        // Password field
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(componentX, CARD_Y + 305, componentWidth, 20);
        lblPassword.setForeground(PRIMARY_GREEN);
        lblPassword.setFont(LABEL_FONT);
        mainPanel.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setBounds(componentX, CARD_Y + 330, componentWidth - 60, 45);
        txtPassword.setFont(NORMAL_FONT);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_GREEN, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtPassword.setEchoChar('•');
        mainPanel.add(txtPassword);
        
        // Show password checkbox
        chkShowPassword = new JCheckBox("Show");
        chkShowPassword.setBounds(componentX + componentWidth - 55, CARD_Y + 330, 60, 45);
        chkShowPassword.setOpaque(false);
        chkShowPassword.setForeground(PRIMARY_GREEN);
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        chkShowPassword.addActionListener(e -> txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '•'));
        mainPanel.add(chkShowPassword);
        
        // Login button
        btnLogin = createStyledButton("LOGIN", PRIMARY_ORANGE, Color.WHITE);
        btnLogin.setBounds(componentX, CARD_Y + 395, componentWidth, 50);
        btnLogin.addActionListener(e -> authenticateUser());
        mainPanel.add(btnLogin);
        
        // Register button
        btnRegister = createStyledButton("REGISTER", PRIMARY_GREEN, Color.WHITE);
        btnRegister.setBounds(componentX, CARD_Y + 455, componentWidth, 50);
        btnRegister.addActionListener(e -> openRegistrationForm());
        mainPanel.add(btnRegister);
        
        // Copyright text
        JLabel lblCopyright = new JLabel("© 2025 Burger Machine - All rights reserved", SwingConstants.CENTER);
        lblCopyright.setBounds(0, 610, 450, 20);
        lblCopyright.setForeground(Color.WHITE);
        lblCopyright.setFont(new Font("Arial", Font.PLAIN, 12));
        mainPanel.add(lblCopyright);
        
        add(mainPanel);
        
        // Add key listener for Enter key
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    authenticateUser();
                }
            }
        };
        txtUsername.addKeyListener(enterKeyListener);
        txtPassword.addKeyListener(enterKeyListener);
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(NORMAL_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_GREEN, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void authenticateUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Input validation
        if (username.isEmpty() || password.isEmpty()) {
            showWarningMessage("Please enter both username and password", "Login Error");
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null || conn.isClosed()) {
                showErrorMessage("Database not connected!", "Connection Error");
                return;
            }
            
            // Check if database has the correct table
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "users", null);
            if (!tables.next()) {
                showErrorMessage("Users table not found in database. Please check database setup.", "Database Error");
                return;
            }
            
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
                int userId = rs.getInt("id");
                
                if ("admin".equalsIgnoreCase(role)) {
                    showInfoMessage("Welcome Admin! 🎉", "Login Successful");
                    dispose();
                    SwingUtilities.invokeLater(() -> new AdminView().setVisible(true));
                } else {
                    showInfoMessage("Welcome " + username + "! 🎉", "Login Successful");
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        try {
                            // Try with userId parameter first
                            new CustomerView(userId).setVisible(true);
                        } catch (NoSuchMethodError e) {
                            // Fallback to default constructor if no userId parameter exists
                            new CustomerView().setVisible(true);
                        }
                    });
                }
            } else {
                showErrorMessage("Invalid Username or Password", "Login Failed");
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Database Error: " + e.getMessage(), "Login Error");
        } finally {
            // Close all resources properly
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void openRegistrationForm() {
        try {
            // First try if RegisterView exists
            Class.forName("mainframes.RegisterView");
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    // Use reflection to avoid compile-time errors if RegisterView doesn't exist yet
                    Class<?> registerClass = Class.forName("mainframes.RegisterView");
                    Object registerView = registerClass.getDeclaredConstructor().newInstance();
                    registerClass.getMethod("setVisible", boolean.class).invoke(registerView, true);
                } catch (Exception ex) {
                    showErrorMessage("Error opening registration form: " + ex.getMessage(), "Error");
                }
            });
        } catch (ClassNotFoundException e) {
            // RegisterView doesn't exist yet
            showInfoMessage("Registration form will be implemented soon.", "Coming Soon");
        }
    }
    
    private void showInfoMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showWarningMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        try {
            // Set system look and feel for better integration with OS
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Custom UI adjustments
            UIManager.put("OptionPane.background", new Color(252, 248, 232));
            UIManager.put("Panel.background", new Color(252, 248, 232));
            UIManager.put("OptionPane.messageForeground", new Color(38, 133, 65));
            UIManager.put("Button.background", new Color(38, 133, 65));
            UIManager.put("Button.foreground", Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                // Test database connection on startup
                Connection testConn = DatabaseConnection.getConnection();
                if (testConn == null) {
                    System.err.println("Warning: Could not establish database connection");
                    JOptionPane.showMessageDialog(null, 
                        "Could not connect to database. Please check your database configuration.",
                        "Database Error", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    testConn.close();
                }
            } catch (Exception e) {
                System.err.println("Database connection error: " + e.getMessage());
                e.printStackTrace();
            }
            
            new LoginView().setVisible(true);
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
