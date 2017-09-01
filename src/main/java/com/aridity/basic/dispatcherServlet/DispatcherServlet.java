package com.aridity.basic.dispatcherServlet;

import com.aridity.basic.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shanlin on 2017/9/1.
 */
@WebServlet(value = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.debug("初始化信息");
        // 初始化helper
        HelperLoad.initLoad();
        // 获取 servletContext
        ServletContext servletContext = config.getServletContext();
        // 注册出来jsp 的servlet
        ServletRegistration jspRegistration = servletContext.getServletRegistration("jsp");
        jspRegistration.addMapping(ConfigHelper.getJspPath() + "*");
        //注册静态资源 的servlet
        ServletRegistration defaultRegistration = servletContext.getServletRegistration("default");
        defaultRegistration.addMapping(ConfigHelper.getAssetPath() + "*");

    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("根据路径和方法进行分发");
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        Map<String, Object> params = new HashMap<>();
        if (null != handler) {
            Param param = null;
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                String value = req.getParameter(name);
                params.put(name, value);
            }
            String body = CodeUtils.decodeURL(StreamUtils.getString(req.getInputStream()));
            if (StringUtils.isNoneBlank(body)) {
                String[] arrays = body.split("&");
                if (null != arrays) {
                    for (String array : arrays) {
                        String[] values = array.split("=");
                        if (null != values && values.length == 2) {
                            params.put(values[0], values[1]);
                        }
                    }
                }
            }
            Method method = handler.getMethod();
            Class<?> cls = handler.getController();
            Object obj = BeanUtils.getBean(cls);
            Object result = null;
            if (!params.isEmpty()) {
                LOG.debug("参数 {}", param);
                param = new Param(params);
                result = ReflectUtils.invokeMethod(obj, method, param.getMap().values());
            } else {
                result = ReflectUtils.invokeMethod(obj, method);
            }
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNoneBlank(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> objectEntry : model.entrySet()) {
                            req.setAttribute(objectEntry.getKey(), objectEntry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getJspPath() + path).forward(req, resp);
                    }

                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter printWriter = resp.getWriter();
                String json = JsonUtils.toJson(data);
                printWriter.write(json);
                printWriter.flush();
                printWriter.close();
            }

        }


    }


}
