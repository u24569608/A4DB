/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

package com;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author connorbell
 */
public class ProductAddDialog extends javax.swing.JDialog {
    
    public Connection connection;
    public JTable productsTable;

    /** Creates new form ProductAddDialog */
    public ProductAddDialog(java.awt.Frame parent, Connection connection, JTable productsTable) {
        super(parent, true); // Make it modal
        this.connection = connection;
        this.productsTable = productsTable;
        initComponents();
        setupListeners();
        setLocationRelativeTo(null);
    }
    
    
    private void setupListeners() {
        btnAddProductData.addActionListener(e -> saveProduct());
        btnCancel.addActionListener(e -> dispose());
    }
    
    private void saveProduct() {
        try {
        // Validate required fields
        if (edtProductName.getText().trim().isEmpty() ||
            edtProductCode.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product Name and Code are required", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get selected category and supplier
        String selectedCategory = cmbCategory.getSelectedItem() != null ? 
            cmbCategory.getSelectedItem().toString() : "";
        String selectedSupplier = cmbSupplier.getSelectedItem() != null ? 
            cmbSupplier.getSelectedItem().toString() : "";

        // Prepare the SQL statement
        String sql = "INSERT INTO products (product_code, product_name, description, " +
                     "standard_cost, list_price, quantity_per_unit, target_level, " +
                     "reorder_level, minimum_reorder_quantity, category, supplier_ids) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, edtProductCode.getText().trim());
        pstmt.setString(2, edtProductName.getText().trim());
        pstmt.setString(3, edtDescription.getText().trim());
        pstmt.setDouble(4, Double.parseDouble(edtStdCost.getText().trim()));
        pstmt.setDouble(5, Double.parseDouble(edtListPrice.getText().trim()));
        pstmt.setString(6, edtQtyPerUnit.getText().trim());
        pstmt.setInt(7, (Integer)spnTargetLevel.getValue());
        pstmt.setInt(8, (Integer)spnReorderLevel.getValue());
        pstmt.setInt(9, (Integer)spnMinReorderQty.getValue());
        pstmt.setString(10, selectedCategory);
        pstmt.setString(11, selectedSupplier);
        
        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Product added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshProductsTable();
            this.dispose();
        }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for cost and price", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving product: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshProductsTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
            model.setRowCount(0); // Clear existing data
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            ResultSetMetaData metaData = rs.getMetaData();
            
            // Set column names if empty
            if (model.getColumnCount() == 0) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    model.addColumn(metaData.getColumnName(i));
                }
            }
            
            // Add data rows
            while (rs.next()) {
                Object[] row = new Object[metaData.getColumnCount()];
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error refreshing products: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadCategories() {
        try {
            cmbCategory.removeAllItems();
            String sql = "SELECT DISTINCT category FROM products ORDER BY category";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                cmbCategory.addItem(rs.getString("category"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading categories: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }   
    
    private void loadSuppliers() {
        try {
            cmbSupplier.removeAllItems();
            String sql = "SELECT id FROM suppliers ORDER BY id";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                cmbSupplier.addItem(rs.getString("id"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAddProductData = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        edtProductCode = new javax.swing.JTextField();
        edtProductName = new javax.swing.JTextField();
        edtDescription = new javax.swing.JTextField();
        edtStdCost = new javax.swing.JTextField();
        spnTargetLevel = new javax.swing.JSpinner();
        spnReorderLevel = new javax.swing.JSpinner();
        edtQtyPerUnit = new javax.swing.JTextField();
        spnMinReorderQty = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        edtListPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnLoadCategorySuppliers = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Product Data");

        jLabel1.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 18)); // NOI18N
        jLabel1.setText("Add New Product Data");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAddProductData.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        btnAddProductData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-bolt-16.png"))); // NOI18N
        btnAddProductData.setText("Add");
        btnAddProductData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddProductData.setName("btnAddProductData"); // NOI18N
        btnAddProductData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductDataActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddProductData, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProductData)
                    .addComponent(btnCancel))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        edtProductCode.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        edtProductName.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        edtDescription.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        edtStdCost.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        spnTargetLevel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        spnReorderLevel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        edtQtyPerUnit.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        spnMinReorderQty.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        jLabel2.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel2.setText("Product Code");

        jLabel3.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel3.setText("Product Name");

        jLabel4.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel4.setText("Quantity Per Unit");

        jLabel5.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel5.setText("Description");

        edtListPrice.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N

        jLabel6.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel6.setText("Target Level");

        jLabel7.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel7.setText("List Price");

        jLabel8.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel8.setText("Re-order Level");

        jLabel9.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel9.setText("Standard Cost");

        jLabel10.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel10.setText("Minimum Re-order Quantity");

        jLabel11.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel11.setText("Category");

        btnLoadCategorySuppliers.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        btnLoadCategorySuppliers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-bolt-16.png"))); // NOI18N
        btnLoadCategorySuppliers.setText("Load");
        btnLoadCategorySuppliers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLoadCategorySuppliers.setName("btnAddProductData"); // NOI18N
        btnLoadCategorySuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadCategorySuppliersActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel13.setText("Load Supplier & Category Information");

        cmbCategory.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        cmbCategory.setEnabled(false);

        jLabel12.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        jLabel12.setText("Supplier_id");

        cmbSupplier.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 13)); // NOI18N
        cmbSupplier.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(edtStdCost)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(edtListPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(spnReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(spnTargetLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtProductCode, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtProductName)))
                            .addComponent(edtQtyPerUnit)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(164, 164, 164))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(spnMinReorderQty, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(12, 12, 12)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12)
                                    .addComponent(btnLoadCategorySuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(edtDescription, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtProductCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtStdCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtListPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnTargetLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtQtyPerUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnMinReorderQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoadCategorySuppliers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadCategorySuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadCategorySuppliersActionPerformed
         loadCategories();
         loadSuppliers();
         cmbCategory.enable();
         cmbSupplier.enable(); 
         //JOptionPane.showMessageDialog(this, "Categories and suppliers loaded successfully!", 
        //"Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLoadCategorySuppliersActionPerformed

    private void btnAddProductDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddProductDataActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProductData;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLoadCategorySuppliers;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JComboBox<String> cmbSupplier;
    private javax.swing.JTextField edtDescription;
    private javax.swing.JTextField edtListPrice;
    private javax.swing.JTextField edtProductCode;
    private javax.swing.JTextField edtProductName;
    private javax.swing.JTextField edtQtyPerUnit;
    private javax.swing.JTextField edtStdCost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner spnMinReorderQty;
    private javax.swing.JSpinner spnReorderLevel;
    private javax.swing.JSpinner spnTargetLevel;
    // End of variables declaration//GEN-END:variables

}
