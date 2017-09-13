package com.aridity.basic.service;

import com.aridity.basic.model.Customer;
import com.aridity.basic.utils.DatabaseHelper;
import com.aridity.basic.utils.Service;
import com.aridity.basic.utils.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供客户数据服务
 * Created by shanlin on 2017/8/29.
 */
@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    //TODO 实现

    /**
     * 获取客户列表
     *
     * @return
     */
    public List<Customer> getCustomers() {
        String sql = "select * from customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    /**
     * 获取客户
     *
     * @return
     */
    public Customer getCustomer(Object id) {
        String sql = "select * from customer where id = ? ";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    @Transaction
    public boolean createCustomer(Map<String, Object> map) {
        return DatabaseHelper.insertEntity(Customer.class, map);
    }
    @Transaction
    public boolean updateCustomer(Object id, Map<String, Object> map) {
        return DatabaseHelper.updateEntity(Customer.class, id, map);
    }
    @Transaction
    public boolean deleteCustomer(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return DatabaseHelper.deleteEntity(Customer.class, map);
    }


}
