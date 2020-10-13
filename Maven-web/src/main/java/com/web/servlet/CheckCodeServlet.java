package com.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建图片
        int width = 100;
        int height = 50;
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        //美化图片
        Graphics g = image.getGraphics();
        g.setColor(Color.pink);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.red);
        g.drawRect(0, 0, width - 1, height - 1);
        //写验证码
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 4; i++) {
            int index = r.nextInt(str.length());
            char ch = str.charAt(index);
            sb.append(ch);
            g.drawString(ch + "", width / 5 * i, 25);
        }
        String cSession=sb.toString();
        req.getSession().setAttribute("cSession",cSession);
        //输出展示
        ImageIO.write(image, "jpg", resp.getOutputStream());

    }
}
