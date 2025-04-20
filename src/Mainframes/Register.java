package Mainframes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author 63906
 */
public class Register extends javax.swing.JFrame {
    
    private Connection conn;
    private JTextField fullNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField contactNumberField;
    private JTextField emailField;
    
    /**
     * Creates new form Register
     */
    public Register() {
        initComponents();
        connectToDatabase();
    }
    
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/burger_machine", 
                "root", 
                "" // Enter your MySQL password here
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Database connection failed: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void initComponents() {
        // Set frame properties
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Burger Machine Registration");
        setResizable(false);
        
        // Main panel with orange background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 152, 66)); // Orange background
        mainPanel.setLayout(new GridBagLayout());
        
        // Green form container panel
        JPanel formContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25));
                g2.dispose();
            }
        };
        formContainer.setOpaque(false);
        formContainer.setBackground(new Color(44, 114, 60)); // Dark green
        formContainer.setLayout(new BorderLayout());
        formContainer.setPreferredSize(new Dimension(550, 650));
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("BURGER MACHINE");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Registration Form");
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalStrut(20));
        
        // Content panel with white background
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Wider padding
        
        // Burger icon (using a placeholder)
        JLabel burgerIcon = new JLabel("🍔");
        burgerIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        burgerIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(burgerIcon);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Create your account title
        JLabel createAccountLabel = new JLabel("CREATE YOUR ACCOUNT");
        createAccountLabel.setForeground(new Color(44, 114, 60));
        createAccountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        createAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(createAccountLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Form fields
        fullNameField = createTextField("Full Name *");
        usernameField = createTextField("Username *");
        passwordField = createPasswordField("Password *");
        confirmPasswordField = createPasswordField("Confirm Password *");
        contactNumberField = createTextField("Contact Number *");
        emailField = createTextField("Email Address *");
        
        contentPanel.add(createFieldContainer(fullNameField));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFieldContainer(usernameField));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFieldContainer(passwordField));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFieldContainer(confirmPasswordField));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFieldContainer(contactNumberField));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFieldContainer(emailField));
        contentPanel.add(Box.createVerticalStrut(25));
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        
        JButton registerButton = createButton("REGISTER", new Color(255, 87, 34), 120);
        registerButton.addActionListener(e -> registerUser());
        
        JButton resetButton = createButton("RESET", new Color(44, 114, 60), 120);
        resetButton.addActionListener(e -> resetForm());
        
        JButton backButton = createButton("BACK TO LOGIN", Color.GRAY, 160); // Wider for longer text
        backButton.addActionListener(e -> goBackToLogin());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(backButton);
        
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Required fields note
        JLabel requiredNote = new JLabel("All fields marked with * are required");
        requiredNote.setFont(new Font("Arial", Font.PLAIN, 12));
        requiredNote.setForeground(Color.GRAY);
        requiredNote.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(requiredNote);
        
        formContainer.add(headerPanel, BorderLayout.NORTH);
        formContainer.add(contentPanel, BorderLayout.CENTER);
        
        mainPanel.add(formContainer);
        
        // Copyright
        JLabel copyright = new JLabel("© 2025 Burger Machine - All rights reserved");
        copyright.setForeground(Color.WHITE);
        copyright.setFont(new Font("Arial", Font.PLAIN, 12));
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setOpaque(false);
        footerPanel.add(copyright);
        mainPanel.add(footerPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, 
                GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));
        
        getContentPane().add(mainPanel);
        setSize(800, 750);
        setLocationRelativeTo(null);
    }
    
    private JPanel createFieldContainer(JComponent field) {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setMaximumSize(new Dimension(400, 40));
        container.add(field);
        return container;
    }
    
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(400, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(44, 114, 60), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Simulate placeholder
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        
        return textField;
    }
    
    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(400, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(44, 114, 60), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Initially show placeholder
        passwordField.setEchoChar((char) 0);
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        
        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setText(placeholder);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
        
        return passwordField;
    }
    
    private JButton createButton(String text, Color color, int width) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(width, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void registerUser() {
        if (!validateForm()) {
            return;
        }
        
        try {
            String sql = "INSERT INTO users (full_name, username, password, contact_number, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, getFieldValue(fullNameField));
            pstmt.setString(2, getFieldValue(usernameField));
            pstmt.setString(3, new String(passwordField.getPassword()));
            pstmt.setString(4, getFieldValue(contactNumberField));
            pstmt.setString(5, getFieldValue(emailField));
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Registration successful!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                goBackToLogin();
            }
            
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, 
                "Username already exists!", 
                "Registration Error", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private boolean validateForm() {
        if (isFieldEmpty(fullNameField, "Full Name *") ||
            isFieldEmpty(usernameField, "Username *") ||
            passwordField.getPassword().length == 0 ||
            confirmPasswordField.getPassword().length == 0 ||
            isFieldEmpty(contactNumberField, "Contact Number *") ||
            isFieldEmpty(emailField, "Email Address *")) {
            
            JOptionPane.showMessageDialog(this, 
                "All fields marked with * are required!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        
        if (!new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, 
                "Passwords do not match!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        
        if (new String(passwordField.getPassword()).length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "Password must be at least 6 characters long!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        
        if (!isValidEmail(getFieldValue(emailField))) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid email address!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        
        return true;
    }
    
    private boolean isFieldEmpty(JTextField field, String placeholder) {
        return field.getText().isEmpty() || field.getText().equals(placeholder);
    }
    
    private String getFieldValue(JTextField field) {
        String text = field.getText();
        if (text.equals("Full Name *") || text.equals("Username *") ||
            text.equals("Contact Number *") || text.equals("Email Address *")) {
            return "";
        }
        return text;
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    private void resetForm() {
        fullNameField.setText("Full Name *");
        fullNameField.setForeground(Color.GRAY);
        
        usernameField.setText("Username *");
        usernameField.setForeground(Color.GRAY);
        
        passwordField.setText("Password *");
        passwordField.setEchoChar((char) 0);
        passwordField.setForeground(Color.GRAY);
        
        confirmPasswordField.setText("Confirm Password *");
        confirmPasswordField.setEchoChar((char) 0);
        confirmPasswordField.setForeground(Color.GRAY);
        
        contactNumberField.setText("Contact Number *");
        contactNumberField.setForeground(Color.GRAY);
        
        emailField.setText("Email Address *");
        emailField.setForeground(Color.GRAY);
    }
    
    private void goBackToLogin() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Assuming you have a Login frame class
        // new Login().setVisible(true);
        dispose();
    }
    
    @Override
    public void dispose() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        super.dispose();
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
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
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
