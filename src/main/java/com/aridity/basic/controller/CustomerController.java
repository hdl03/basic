package com.aridity.basic.controller;

import com.aridity.basic.model.Customer;
import com.aridity.basic.service.CustomerService;
import com.aridity.basic.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by shanlin on 2017/8/31.
 */
@Controller
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    @RequestMapping(name = "get:/customers")
    public View getCustomers() {
        LOGGER.info("初始化界面");
        List<Customer> customers = customerService.getCustomers();
        return new View("customers.jsp").addModel("customers", customers);
    }

    @RequestMapping(name = "get:/customer")
    public View getCustomer(Param param) {
        LOGGER.info("查询详情");
        Customer customer = customerService.getCustomer((Long)param.getMap().get("id"));
        return new View("show_customer.jsp").addModel("customer", customer);
    }

    @RequestMapping(name = "put:/customer")
    public void editCustomer(Long id, Map<String, Object> map) {
        LOGGER.info("修改客户信息");
        boolean flag = customerService.updateCustomer(id, map);
        //return new Data("");
    }

    @RequestMapping(name = "post:/customer")
    public void createCustomers(Map<String, Object> map) {
        LOGGER.info("创建客户信息");
        boolean flag = customerService.createCustomer(map);
        // return new View("customers.jsp").addModel("customers", customers);
    }

    @RequestMapping(name = "delete:/customer")
    public void getCustomers(Long id) {
        LOGGER.info("删除客户信息");
        boolean flags = customerService.deleteCustomer(id);
        //return new View("customers.jsp").addModel("customers", customers);
    }


}
