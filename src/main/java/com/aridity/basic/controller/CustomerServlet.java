package com.aridity.basic.controller;

import com.aridity.basic.model.Customer;
import com.aridity.basic.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 创建客户
 * Created by shanlin on 2017/8/29.
 */
@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServlet.class);
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("获取所有列表");
        List<Customer> customers = customerService.getCustomers();
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("/WEB-INF/view/customers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
