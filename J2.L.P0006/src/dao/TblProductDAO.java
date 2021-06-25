/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Product;
import gui.CustomerTableModelProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import utils.DBUltis;

/**
 *
 * @author By Van Lung, IDStudent: SE140193
 */
public class TblProductDAO {

    private String[] headeres = {"productID", "productName", "unit", "price", "quantity", "categoryID"};
    private int[] indexes = {0, 1, 2, 4, 3, 5};
    private CustomerTableModelProduct modelItem = new CustomerTableModelProduct(headeres, indexes);

    public CustomerTableModelProduct getModelItem() {
        return modelItem;
    }

    public TblProductDAO() {
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() throws Exception {
        modelItem.getList().clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "SELECT*FROM TblProducts";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Product pro = new Product(rs.getString("productID"), rs.getString("productName"), rs.getString("unit"),
                            rs.getFloat("price"), rs.getInt("quantity"), rs.getString("categoryID"));
                    modelItem.getList().add(pro);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public String loadCaCodeIntoItems(String Suppliers) {
        StringTokenizer stk = new StringTokenizer(Suppliers, " - ");
        return stk.nextToken();
    }

    public int Insert(Product pro) throws Exception {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "insert into TblProducts(productID,productName,unit,price,quantity,categoryID) values(?,?,?,?,?,?)";
                StringTokenizer stk = new StringTokenizer(pro.getCategoryID(), " - ");
                ps = con.prepareStatement(sql); //excute sql statement
                ps.setString(1, pro.getProductID());
                ps.setString(2, pro.getProductName());
                ps.setString(3, stk.nextToken());
                ps.setString(4, pro.getUnit());
                ps.setFloat(5, pro.getPrice());
                ps.setInt(6, pro.getQuantity());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;
    }

    public int Save(Product it) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "Update TblProducts Set productName=?, unit=?, quantity=?, price=?, categoryID=?, Where productID=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, it.getProductName());
                ps.setString(2, it.getUnit());
                ps.setFloat(3, it.getPrice());
                ps.setInt(4, it.getQuantity());
                ps.setString(5, it.getCategoryID());
                ps.setString(6, it.getProductID());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;
    }

    public int Delete(Product pro) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "DELETE FROM TblProducts WHERE productID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, pro.getProductID());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;
    }

    public boolean validDateCodeProduct(String productID) {
        try {
            for (int i = 0; i < modelItem.getList().size(); i++) {
                if (modelItem.getList().get(i).getProductID().compareToIgnoreCase(productID) == 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean validDate(String productID, String productName, String unit, String quantity, String price, Object categoryID) {

        if (!productID.toUpperCase().matches("^{3,10}$")) {
            JOptionPane.showMessageDialog(null, "Invalid Code");
            return false;
        }

        if (productName.isEmpty() || productName.length() >= 50) {
            JOptionPane.showMessageDialog(null, "Invalid Name");
            return false;
        }
        if (unit.isEmpty() || unit.length() >= 50) {
            JOptionPane.showMessageDialog(null, "Invalid Unit");
            return false;
        }
        if (!quantity.matches("^[0-9]{0,300}$")) {
            JOptionPane.showMessageDialog(null, "Invalid Quantity");
            return false;
        }
        if (!price.matches("\\d+.?\\d*")) {
            JOptionPane.showMessageDialog(null, "Invalid Price");
            return false;
        }
        if (categoryID == null) {
            JOptionPane.showMessageDialog(null, "Choose the categoryID");
            return false;
        }
        return true;
    }

    public boolean checkconstraintCaCode(String supCode) {
        for (int i = 0; i < modelItem.getList().size(); i++) {
            if (supCode.compareToIgnoreCase(modelItem.getList().get(i).getCategoryID()) == 0) {
                return false;
            }
        }
        return true;
    }
}
