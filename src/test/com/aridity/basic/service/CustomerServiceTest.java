package com.aridity.basic.service;

import com.aridity.basic.model.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CustomerService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 29, 2017</pre>
 */
public class CustomerServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceTest.class);
    private final CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getCustomers(String keyword)
     */
    @Test
    public void testGetCustomers() throws Exception {
        List<Customer> customers = customerService.getCustomers();
        for (Customer customer : customers) {
            LOGGER.info("打印对象 {}", customer);
        }
    }

    /**
     * Method: getCustomer(Long id)
     */
    @Test
    public void testGetCustomer() throws Exception {
        Customer customer = customerService.getCustomer(5l);
        LOGGER.info("打印对象 {}", customer);
    }

    /**
     * Method: createCustomer(Map<String, Object> map)
     */
    @Test
    public void testCreateCustomer() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jack");
        map.put("contact", "jack@");
        map.put("telephone", "15164521358");
        map.put("email", "15164521358@163.com");
        map.put("remark", "15164521358@163.com test");
        boolean row = customerService.createCustomer(map);
        LOGGER.info("打印日志 ,插入是否成功 ：{}", row);
    }

    /**
     * Method: updateCustomer(Long id, Map<String, Object> map)
     */
    @Test
    public void testUpdateCustomer() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "123123");
        boolean row = customerService.updateCustomer(2l, map);
        LOGGER.info("打印日志 ,修改是否成功 ：{}", row);
    }

    /**
     * Method: deleteCustomer(Long id)
     */
    @Test
    public void testDeleteCustomer() throws Exception {
        boolean row = customerService.deleteCustomer(4l);
        LOGGER.info("打印日志 ,删除是否成功 ：{}", row);
    }


} 
