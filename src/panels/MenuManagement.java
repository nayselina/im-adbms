package panels;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.text.DecimalFormat;

/**
 * Menu Management panel for restaurant admin interface
 * @author 63906
 */
public class MenuManagement extends javax.swing.JPanel {
    // Menu item model class
    private static class MenuItem {
        String id;
        String name;
        String description;
        double price;
        String category;
        String status;
        ImageIcon image;
        
        public MenuItem(String id, String name, String description, double price, 
                String category, String status) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.category = category;
            this.status = status;
            // Create placeholder image (would be replaced with actual image loading)
            this.image = createPlaceholderIcon(80, 80);
        }
    }
    
    // UI Components
    private JPanel headerPanel;
    private JPanel categoryPanel;
    private JPanel actionPanel;
    private JPanel searchPanel;
    private JScrollPane tableScrollPane;
    private JTable menuItemsTable;
    private JPanel paginationPanel;
    
    private JButton btnAddItem;
    private JButton btnRemoveItem;
    private JButton btnEditItem;
    private JButton btnUploadImage;
    private JButton btnMarkOutOfStock;
    
    private JTextField txtSearch;
    private JComboBox<String> cmbSort;
    private JComboBox<String> cmbFilter;
    
    // Data
    private ArrayList<MenuItem> menuItems;
    private String activeCategory = "Burgers";
    private String[] categories = {"Burgers", "Buns", "Cheeses", "Toppings", "Sauces", "Add-ons"};
    private ArrayList<JButton> categoryButtons;
    
    /**
     * Creates new form MenuManagement
     */
    public MenuManagement() {
        initComponents(); // Call the NetBeans-generated method first
        initializeData();
        setupComponents();
        setupTable();
        updateTableData();
    }
    
    /**
     * Initialize sample data
     */
    private void initializeData() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("BG001", "Classic Cheeseburger", 
                "Beef patty, cheese, lettuce, tomato, and special sauce on a sesame bun", 
                9.99, "Burgers", "Available"));
        menuItems.add(new MenuItem("BG002", "Bacon Deluxe", 
                "Beef patty, bacon, cheddar cheese, BBQ sauce, onion rings", 
                12.99, "Burgers", "Available"));
        menuItems.add(new MenuItem("BG003", "Veggie Supreme", 
                "Veggie patty, avocado, roasted peppers, vegan cheese, herb mayo", 
                10.99, "Burgers", "Low Stock"));
        menuItems.add(new MenuItem("BG004", "Double Trouble", 
                "Double beef patties, double cheese, bacon, special sauce", 
                14.99, "Burgers", "Out of Stock"));
        
        // Add items for other categories to demonstrate filtering
        menuItems.add(new MenuItem("BN001", "Sesame Bun", 
                "Traditional sesame seed bun", 
                1.99, "Buns", "Available"));
        menuItems.add(new MenuItem("CH001", "American Cheese", 
                "Classic processed American cheese slice", 
                0.99, "Cheeses", "Available"));
    }
    
    /**
     * Initialize UI components - renamed from initComponents() to avoid conflict
     */
    private void setupComponents() {
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
        createCategoryTabsPanel();
        createSearchPanel();
        createTablePanel();
        createPaginationPanel();
        
        // Add components to card panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(actionPanel, BorderLayout.NORTH);
        
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.WHITE);
        middlePanel.add(categoryPanel, BorderLayout.NORTH);
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
        
        JLabel lblTitle = new JLabel("Menu Management");
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
        
        btnAddItem = createButton("Add Item", new Color(21, 128, 61));
        btnRemoveItem = createButton("Remove Item", new Color(234, 88, 12));
        btnEditItem = createButton("Edit Item", new Color(21, 128, 61));
        btnUploadImage = createButton("Upload Image", new Color(21, 128, 61));
        btnMarkOutOfStock = createButton("Mark as Out of Stock", new Color(21, 128, 61));
        
        actionPanel.add(btnAddItem);
        actionPanel.add(btnRemoveItem);
        actionPanel.add(btnEditItem);
        actionPanel.add(btnUploadImage);
        actionPanel.add(btnMarkOutOfStock);
        
        // Add action listeners
        btnAddItem.addActionListener(e -> showAddItemDialog());
        btnRemoveItem.addActionListener(e -> removeSelectedItem());
        btnEditItem.addActionListener(e -> editSelectedItem());
        btnMarkOutOfStock.addActionListener(e -> markSelectedItemOutOfStock());
    }
    
    /**
     * Create category tabs panel
     */
    private void createCategoryTabsPanel() {
        categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        categoryPanel.setBackground(Color.WHITE);
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        categoryButtons = new ArrayList<>();
        
        for (String category : categories) {
            JButton btnCategory = new JButton(category);
            btnCategory.setFocusPainted(false);
            btnCategory.setBorderPainted(false);
            btnCategory.setPreferredSize(new Dimension(100, 35));
            
            if (category.equals(activeCategory)) {
                btnCategory.setBackground(new Color(21, 128, 61));
                btnCategory.setForeground(Color.WHITE);
            } else {
                btnCategory.setBackground(new Color(229, 231, 235));
                btnCategory.setForeground(new Color(55, 65, 81));
            }
            
            btnCategory.addActionListener(e -> {
                activeCategory = category;
                updateCategoryButtons();
                updateTableData();
            });
            
            categoryButtons.add(btnCategory);
            categoryPanel.add(btnCategory);
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
        
        txtSearch = new JTextField("Search menu items...");
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Search menu items...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Search menu items...");
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
        
        String[] sortOptions = {"Sort by: Popularity", "Sort by: Price (Low to High)", "Sort by: Price (High to Low)", "Sort by: Name (A-Z)"};
        cmbSort = new JComboBox<>(sortOptions);
        cmbSort.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        cmbSort.addActionListener(e -> updateTableData());
        
        String[] filterOptions = {"Filter: All items", "Filter: Available only", "Filter: Low Stock only", "Filter: Out of Stock only"};
        cmbFilter = new JComboBox<>(filterOptions);
        cmbFilter.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        cmbFilter.addActionListener(e -> updateTableData());
        
        rightPanel.add(cmbSort);
        rightPanel.add(cmbFilter);
        
        searchPanel.add(leftPanel, BorderLayout.CENTER);
        searchPanel.add(rightPanel, BorderLayout.EAST);
    }
    
    /**
     * Create table panel for menu items
     */
    private void createTablePanel() {
        // Create table model with non-editable cells
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only allow editing the Actions column
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        
        // Add columns
        model.addColumn("IMAGE");
        model.addColumn("ITEM NAME");
        model.addColumn("DESCRIPTION");
        model.addColumn("PRICE");
        model.addColumn("STATUS");
        model.addColumn("ACTIONS");
        
        // Create table
        menuItemsTable = new JTable(model);
        menuItemsTable.setRowHeight(60);
        menuItemsTable.setShowVerticalLines(false);
        menuItemsTable.setIntercellSpacing(new Dimension(0, 0));
        menuItemsTable.getTableHeader().setReorderingAllowed(false);
        menuItemsTable.getTableHeader().setBackground(new Color(243, 244, 246));
        menuItemsTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        
        // Set column widths
        TableColumnModel columnModel = menuItemsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(120);
        
        // Set custom renderers
        columnModel.getColumn(0).setCellRenderer(new ImageRenderer());
        columnModel.getColumn(4).setCellRenderer(new StatusRenderer());
        columnModel.getColumn(5).setCellRenderer(new ButtonRenderer());
        
        // Set cell editor for action buttons
        columnModel.getColumn(5).setCellEditor(new ButtonEditor());
        
        // Create scroll pane
        tableScrollPane = new JScrollPane(menuItemsTable);
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
     * Update the table data based on active category, search, sort and filter
     */
    private void updateTableData() {
        DefaultTableModel model = (DefaultTableModel) menuItemsTable.getModel();
        model.setRowCount(0);
        
        String searchText = txtSearch.getText();
        if (searchText.equals("Search menu items...")) {
            searchText = "";
        }
        
        String filterOption = (String) cmbFilter.getSelectedItem();
        
        for (MenuItem item : getFilteredItems(searchText, filterOption)) {
            if (item.category.equals(activeCategory)) {
                model.addRow(new Object[]{
                    item.image,
                    "<html><b>" + item.name + "</b><br><font size='-2'>Item ID: #" + item.id + "</font></html>",
                    item.description,
                    "$" + new DecimalFormat("0.00").format(item.price),
                    item.status,
                    "actions" // This is a placeholder for the button renderer
                });
            }
        }
    }
    
    /**
     * Filter and sort menu items based on search text and filter options
     */
    private ArrayList<MenuItem> getFilteredItems(String searchText, String filterOption) {
        ArrayList<MenuItem> filteredItems = new ArrayList<>();
        
        for (MenuItem item : menuItems) {
            // Apply search filter
            boolean matchesSearch = searchText.isEmpty() || 
                    item.name.toLowerCase().contains(searchText.toLowerCase()) ||
                    item.description.toLowerCase().contains(searchText.toLowerCase()) ||
                    item.id.toLowerCase().contains(searchText.toLowerCase());
            
            // Apply status filter
            boolean matchesStatusFilter = true;
            if (filterOption.equals("Filter: Available only")) {
                matchesStatusFilter = item.status.equals("Available");
            } else if (filterOption.equals("Filter: Low Stock only")) {
                matchesStatusFilter = item.status.equals("Low Stock");
            } else if (filterOption.equals("Filter: Out of Stock only")) {
                matchesStatusFilter = item.status.equals("Out of Stock");
            }
            
            if (matchesSearch && matchesStatusFilter) {
                filteredItems.add(item);
            }
        }
        
        // Apply sorting
        String sortOption = (String) cmbSort.getSelectedItem();
        if (sortOption.equals("Sort by: Price (Low to High)")) {
            filteredItems.sort(Comparator.comparingDouble(o -> o.price));
        } else if (sortOption.equals("Sort by: Price (High to Low)")) {
            filteredItems.sort((o1, o2) -> Double.compare(o2.price, o1.price));
        } else if (sortOption.equals("Sort by: Name (A-Z)")) {
            filteredItems.sort(Comparator.comparing(o -> o.name));
        }
        
        return filteredItems;
    }
    
    /**
     * Update category button styles based on active category
     */
    private void updateCategoryButtons() {
        for (int i = 0; i < categories.length; i++) {
            JButton button = categoryButtons.get(i);
            if (categories[i].equals(activeCategory)) {
                button.setBackground(new Color(21, 128, 61));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(229, 231, 235));
                button.setForeground(new Color(55, 65, 81));
            }
        }
    }
    
    /**
     * Setup table cell renderers and editors
     */
    private void setupTable() {
        // Additional setup for table if needed
    }
    
    /**
     * Show dialog to add a new menu item
     */
    private void showAddItemDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Menu Item", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtId = new JTextField(10);
        JTextField txtName = new JTextField(20);
        JTextArea txtDescription = new JTextArea(3, 20);
        txtDescription.setLineWrap(true);
        JTextField txtPrice = new JTextField(10);
        JComboBox<String> cmbCategory = new JComboBox<>(categories);
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Available", "Low Stock", "Out of Stock"});
        
        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(txtDescription));
        formPanel.add(new JLabel("Price:"));
        formPanel.add(txtPrice);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(cmbCategory);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(cmbStatus);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancel = new JButton("Cancel");
        JButton btnSave = new JButton("Save");
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        btnSave.addActionListener(e -> {
            try {
                String id = txtId.getText().trim();
                String name = txtName.getText().trim();
                String description = txtDescription.getText().trim();
                double price = Double.parseDouble(txtPrice.getText().trim());
                String category = (String) cmbCategory.getSelectedItem();
                String status = (String) cmbStatus.getSelectedItem();
                
                if (id.isEmpty() || name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Add new menu item
                menuItems.add(new MenuItem(id, name, description, price, category, status));
                
                // Update table
                updateTableData();
                
                // Close dialog
                dialog.dispose();
                
                // Show success message
                JOptionPane.showMessageDialog(this, "Menu item added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid price", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    /**
     * Remove selected menu item
     */
    private void removeSelectedItem() {
        int selectedRow = menuItemsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String itemName = ((String) menuItemsTable.getValueAt(selectedRow, 1)).split("<br>")[0].replace("<html><b>", "").replace("</b>", "");
        String itemId = ((String) menuItemsTable.getValueAt(selectedRow, 1)).split("#")[1].replace("</font></html>", "");
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete " + itemName + "?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Find and remove the item
            for (int i = 0; i < menuItems.size(); i++) {
                if (menuItems.get(i).id.equals(itemId)) {
                    menuItems.remove(i);
                    break;
                }
            }
            
            // Update table
            updateTableData();
            
            // Show success message
            JOptionPane.showMessageDialog(this, "Menu item removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Edit selected menu item
     */
    private void editSelectedItem() {
        int selectedRow = menuItemsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String itemId = ((String) menuItemsTable.getValueAt(selectedRow, 1)).split("#")[1].replace("</font></html>", "");
        
        // Find the selected item
        MenuItem selectedItem = null;
        for (MenuItem item : menuItems) {
            if (item.id.equals(itemId)) {
                selectedItem = item;
                break;
            }
        }
        
        if (selectedItem == null) return;
        
        // Create and show edit dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Menu Item", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField txtId = new JTextField(selectedItem.id, 10);
        txtId.setEditable(false);
        JTextField txtName = new JTextField(selectedItem.name, 20);
        JTextArea txtDescription = new JTextArea(selectedItem.description, 3, 20);
        txtDescription.setLineWrap(true);
        JTextField txtPrice = new JTextField(String.valueOf(selectedItem.price), 10);
        JComboBox<String> cmbCategory = new JComboBox<>(categories);
        cmbCategory.setSelectedItem(selectedItem.category);
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Available", "Low Stock", "Out of Stock"});
        cmbStatus.setSelectedItem(selectedItem.status);
        
        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(txtDescription));
        formPanel.add(new JLabel("Price:"));
        formPanel.add(txtPrice);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(cmbCategory);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(cmbStatus);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancel = new JButton("Cancel");
        JButton btnSave = new JButton("Save");
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        btnSave.addActionListener(e -> {
            try {
                String name = txtName.getText().trim();
                String description = txtDescription.getText().trim();
                double price = Double.parseDouble(txtPrice.getText().trim());
                String category = (String) cmbCategory.getSelectedItem();
                String status = (String) cmbStatus.getSelectedItem();
                
                if (name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Update menu item
                selectedItem.name = name;
                selectedItem.description = description;
                selectedItem.price = price;
                selectedItem.category = category;
                selectedItem.status = status;
                
                // Update table
                updateTableData();
                
                // Close dialog
                dialog.dispose();
                
                // Show success message
                JOptionPane.showMessageDialog(this, "Menu item updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid price", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    /**
     * Mark selected item as out of stock
     */
    private void markSelectedItemOutOfStock() {
        int selectedRow = menuItemsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String itemId = ((String) menuItemsTable.getValueAt(selectedRow, 1)).split("#")[1].replace("</font></html>", "");
        
        // Find and update the item
        for (MenuItem item : menuItems) {
            if (item.id.equals(itemId)) {
                item.status = "Out of Stock";
                break;
            }
        }
        
        // Update table
        updateTableData();
        
        // Show success message
        JOptionPane.showMessageDialog(this, "Item marked as Out of Stock", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Create a button with specified text and color
     */
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
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
        
        return button;
    }
    
    /**
     * Create a placeholder icon
     */
    private static ImageIcon createPlaceholderIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(249, 219, 187)); // Light orange background
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(234, 88, 12)); // Orange text
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("BM", width/2 - 15, height/2 + 8);
        g.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Custom renderer for image column
     */
    private class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(JLabel.CENTER);
                
                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                    label.setOpaque(true);
                } else {
                    label.setBackground(table.getBackground());
                    label.setOpaque(false);
                }
                
                return label;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    
    /**
     * Custom renderer for status column
     */
    private class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (value != null) {
                String status = value.toString();
                setHorizontalAlignment(JLabel.CENTER);
                
                if (status.equals("Available")) {
                    setBackground(new Color(209, 250, 229)); // Light green
                    setForeground(new Color(21, 128, 61)); // Dark green
                    setText("Available");
                } else if (status.equals("Low Stock")) {
                    setBackground(new Color(254, 243, 199)); // Light yellow
                    setForeground(new Color(234, 151, 12)); // Dark yellow
                    setText("Low Stock");
                } else if (status.equals("Out of Stock")) {
                    setBackground(new Color(254, 226, 226)); // Light red
                    setForeground(new Color(220, 38, 38)); // Dark red
                    setText("Out of Stock");
                }
                
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                }
            }
            
            return component;
        }
    }
    
    /**
     * Custom renderer for action buttons column
     */
    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton editButton;
        private JButton deleteButton;
        
        public ButtonRenderer() {
            setLayout(new GridLayout(1, 2, 5, 0));
            
            editButton = new JButton("Edit");
            editButton.setBackground(new Color(59, 130, 246)); // Blue
            editButton.setForeground(Color.WHITE);
            editButton.setFocusPainted(false);
            editButton.setBorderPainted(false);
            editButton.setFont(new Font("Arial", Font.BOLD, 10));
            
            deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(239, 68, 68)); // Red
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFocusPainted(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setFont(new Font("Arial", Font.BOLD, 10));
            
            add(editButton);
            add(deleteButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(table.getBackground());
            return this;
        }
    }
    
    /**
     * Custom editor for action buttons column
     */
    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        
        public ButtonEditor() {
            panel = new JPanel(new GridLayout(1, 2, 5, 0));
            
            editButton = new JButton("Edit");
            editButton.setBackground(new Color(59, 130, 246)); // Blue
            editButton.setForeground(Color.WHITE);
            editButton.setFocusPainted(false);
            editButton.setBorderPainted(false);
            editButton.setFont(new Font("Arial", Font.BOLD, 10));
            
            deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(239, 68, 68)); // Red
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFocusPainted(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setFont(new Font("Arial", Font.BOLD, 10));
            
            editButton.addActionListener(e -> {
                editSelectedItem();
                fireEditingStopped();
            });
            
            deleteButton.addActionListener(e -> {
                removeSelectedItem();
                fireEditingStopped();
            });
            
            panel.add(editButton);
            panel.add(deleteButton);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            panel.setBackground(table.getBackground());
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "actions";
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // NetBeans generated code would be here
        // We're using our own setup method instead
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Main method to run the panel for testing
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
            java.util.logging.Logger.getLogger(MenuManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Menu Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new MenuManagement());
            frame.pack();
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
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
