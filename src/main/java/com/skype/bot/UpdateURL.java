package com.skype.bot;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skype.util.MessageFormatter;

@WebServlet("/updateURL")
public class UpdateURL extends HttpServlet {
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    		String appURL = request.getParameter("url");
    		MessageFormatter.setAppURL(appURL);
        // code to process the form...
    		// build HTML code
    		PrintWriter writer = response.getWriter();
    		String htmlRespone = "<html>";
            htmlRespone += "<h2>Your application's new URL is " + appURL + " <br/>";    
            htmlRespone += "<a href="+appURL+"/SkypeBot>Home</a>";    
            htmlRespone += "</html>";
            writer.println(htmlRespone);
    }
 
}
