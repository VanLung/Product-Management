/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


 
import dto.Category;
import gui.CustomerTableModelCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import utils.DBUltis;
 

/**
 *
 * @author By Van Lung, IDStudent: SE140193
 */
public class TblCategoryDAO {

    private String[] headers = {"categoryID", "categoryName", "description"};
    private int[] indexes = {0, 1, 2};
    private CustomerTableModelCategory modelCa = new CustomerTableModelCategory(headers, indexes);
    private Vector<String> listCategoryComboBox = new Vector();

    public TblCategoryDAO() {
        try {
            loadData();
            loadCategoryComboBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws Exception {
        modelCa.getList().clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "select * from TblCategories";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Category ca = new Category(rs.getString("categoryID"), rs.getString("categoryName"),
                            rs.getString("description"));
                    modelCa.getList().add(ca);
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

    public CustomerTableModelCategory getModelCa() {
        return modelCa;
    }
    

    public Vector<String> loadCategoryComboBox() throws Exception {
        listCategoryComboBox.clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                con = DBUltis.openConnection();
                if (con != null) {
                    String sql = "SELECT categoryID, categoryName FROM TblCategories";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        listCategoryComboBox.add(rs.getString("categoryID") + "-" + rs.getString("categoryName"));
                    }
                    return listCategoryComboBox;
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
        return null;
    }

    public Vector<String> getListCategoryComboBox() {
        return listCategoryComboBox;
    }

    public String getCaNameComboBox(String supCode) {
        for (Category sup : modelCa.getList()) {
            if (sup.getCategoryID().compareToIgnoreCase(supCode) == 0) {
                return sup.getCategoryName();
            }
        }
        return "";
    }

    public int Insert(Category ca) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "Insert TblCategories Values(?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, ca.getCategoryID());
                ps.setString(2, ca.getCategoryName());
                ps.setString(3, ca.getDescription());
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

    public int Save(Category ca) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "UPDATE TblCategories SET categoryName =?, description=? WHERE categoryID=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, ca.getCategoryName());
                ps.setString(2, ca.getDescription());
                ps.setString(4, ca.getCategoryID());
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

    public int Delete(Category ca) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "DELETE FROM TblCategories WHERE categoryID =?";
                ps = con.prepareStatement(sql);
                ps.setString(1, ca.getCategoryID());
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

    public boolean validDateCodeCategory(String categoryID) {
        try {
            for (int i = 0; i < modelCa.getList().size(); i++) {
                if (modelCa.getList().get(i).getCategoryID().compareToIgnoreCase(categoryID) == 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean validDate(String categoryID, String categoryName, String description) {
        if (categoryID.isEmpty() || categoryID.length() > 10) {
            JOptionPane.showMessageDialog(null, "Invalid Code Suppliers");
            return false;
        }
        if (categoryName.isEmpty() || categoryName.length() > 50) {
            JOptionPane.showMessageDialog(null, "Invalid supName");
            return false;
        }
        if (description.isEmpty() || description.length() > 50) {
            JOptionPane.showMessageDialog(null, "Invalid Address");
            return false;
        }
        return true;
    }

}
