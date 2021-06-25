/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.TblCategoryDAO;
import dto.Product;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author By Van Lung, IDStudent: SE140193
 */
public class CustomerTableModelProduct extends AbstractTableModel {

    private String[] headeres;
    private int[] indexes;
    private Vector<Product> data;
    TblCategoryDAO dao=new TblCategoryDAO();


    public void loadCateprogyComboBox(){
        try{
            dao.loadCategoryComboBox();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String getCategory(String caCode){
        for (String ca : dao.getListCategoryComboBox()) {
            if(ca.contains(caCode)){
                return ca;
            }
        }
        return "";
    }
    public Vector<Product> getList(){
        return data;
    }
    public CustomerTableModelProduct(String[] headeres,int[] indexes){
        this.headeres=new String[headeres.length];
        for (int i = 0; i < headeres.length; i++) {
            this.headeres[i]=headeres[i];
        }
        this.indexes=new int[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            this.indexes[i]=indexes[i];
        }
        data=new Vector<>();
    }
    @Override
    public String getColumnName(int column){
        if(column>=0||column<headeres.length){
            return headeres[column];
        }else return "";
    }
    @Override
    public int getRowCount(){
        return data.size();
    }
    @Override
    public int getColumnCount(){
        return headeres.length;
    }
    @Override
    public Object getValueAt(int rowIndex,int columnIndex){
        if(rowIndex<0||rowIndex>=data.size()||columnIndex<0||columnIndex>=headeres.length){
            return null;
        }
        Product it=data.get(rowIndex);
        switch(indexes[columnIndex]){
            case 0: return it.getProductID();
            case 1: return it.getProductName();
            case 2: return it.getUnit();
            case 3: return it.getQuantity();
            case 4: return it.getPrice();
            case 5: return getCategory(it.getCategoryID());
        }
        return null;
    }

}
