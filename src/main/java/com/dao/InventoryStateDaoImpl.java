package com.dao;

import com.entity.InventoryState;
import com.entity.InventoryStatePK;
import com.entity.InvoiceItem;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("inventoryStateDaoImpl")
public class InventoryStateDaoImpl extends BaseDao implements InventoryStateDao {

    @Override
    public void saveInventoryState(InventoryState inventoryState) {
        getSession().save(inventoryState);
    }

    @Override
    public InventoryState getInventoryStateByPK(InventoryStatePK inventoryStatePK) {
        return getSession().get(InventoryState.class, inventoryStatePK);
    }

    @Override
    public InventoryState getInventoryStateByProductIdWhereMaxStateDate(InvoiceItem invoiceItem) {
        Integer productId = invoiceItem.getProduct().getId();
        String sql = "select * from inventory_state where product_id = :param_product_id and " +
                " state_date = (select max(state_date) from inventory_state where product_id = :param_product_id)";
        NativeQuery<InventoryState> nativeQuery = getSession().createNativeQuery(sql, InventoryState.class);
        nativeQuery.setParameter("param_product_id", productId);
        return nativeQuery.getSingleResult();
    }

    @Override
    public List<InventoryState> getInventoryStates(List<Integer> productIds) {
        String query = "SELECT  p.id as product_id, " +
                " IFNULL(inv_st1.quantity, 0) as quantity, IFNULL(inv_st1.state_date, NOW()) as state_date " +
                " FROM product p LEFT JOIN (SELECT product_id, MAX(state_date) state_date " +
                " FROM inventory_state inv_st WHERE inv_st.product_id IN (:product_ids) " +
                " GROUP BY product_id) inv_st ON inv_st.product_id = p.id LEFT JOIN " +
                " inventory_state inv_st1 ON inv_st1.product_id = p.id " +
                " AND inv_st1.state_date = inv_st.state_date WHERE " +
                " p.id IN (:product_ids) ";
        NativeQuery<InventoryState> nativeQuery = getSession().createNativeQuery(query, InventoryState.class);
        nativeQuery.setParameterList("product_ids", productIds);
        return nativeQuery.getResultList();
    }

    @Override
    public List<InventoryState> getActualInventoryStateByDate(LocalDateTime date) {
        String sql = "select max_date_product.p_id as product_id, " +
                " max_date_product.max_date as state_date, inv_st.quantity as quantity " +
                " from (select product_id p_id, max(state_date) max_date from inventory_state " +
                " where state_date <= :param_date group by p_id) max_date_product inner join " +
                " inventory_state inv_st on max_date_product.p_id = inv_st.product_id " +
                " and max_date_product.max_date = inv_st.state_date ";
        NativeQuery<InventoryState> nativeQuery = getSession().createNativeQuery(sql, InventoryState.class);
        nativeQuery.setParameter("param_date", date);
        return nativeQuery.list();
    }
}
