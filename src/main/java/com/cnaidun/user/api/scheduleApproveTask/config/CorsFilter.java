package com.cnaidun.user.api.scheduleApproveTask.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 项目名称：PoliceCloud
 * 类名称：CorsFilter
 * 类描述：
 * 创建人：JackJun
 * 创建时间：2018/7/25
 * 修改人：JackJun
 * 修改时间：2018/7/25
 * 修改备注：
 * 版权所有权：江苏艾盾网络科技有限公司
 *
 * @version V1.0
 */
@Component
public class CorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");

        response.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");
        System.out.println("*********************************过滤器被使用**************************");
        JSONObject data = null;
        try {
            InputStream is = req.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String line = null;
            StringBuffer content = new StringBuffer();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                content.append(line);
                System.out.println(content);
            }
            String reqStr = content.toString().trim();

            if (reqStr.contains("=")) {
                reqStr = reqStr.replaceAll("=", ":");
            }
            if (!reqStr.startsWith("{")) {
                reqStr = "{" + reqStr;
            }
            if (!reqStr.endsWith("}")) {
                reqStr = reqStr + "}";
            }
            data = JSONObject.parseObject(reqStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}

