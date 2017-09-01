package com.aridity.basic.controller;

import com.aridity.basic.model.Customer;
import com.aridity.basic.service.CustomerService;
import com.aridity.basic.utils.Autowired;
import com.aridity.basic.utils.Controller;
import com.aridity.basic.utils.RequestMapping;
import com.aridity.basic.utils.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

        return new View("customers.jsp").addModel("customers",customers);

    }

}
