// DisplayAnswersServlet.java
package com.servlet.controller;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DisplayAnswersServlet")
public class DisplayAnswersServletcpp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/first";
        String username = "root";
        String password = "renuka";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            java.sql.Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cppquiz_questions");

            response.setContentType("text/html");
            response.getWriter().println("<html>");
            response.getWriter().println("<head>");
            response.getWriter().println("<title>Correct and Chosen Answers</title>");
            response.getWriter().println("<style>");
            response.getWriter().println("body {");
            response.getWriter().println("    background: radial-gradient(circle at 10% 20%, rgba(0, 149, 218, 0.85) 9.9%, rgb(56, 80, 114) 100.3%) no-repeat;");
            response.getWriter().println("    color: #fff;");
            response.getWriter().println("    text-align: left;");
            response.getWriter().println("    padding: 15px;");
            response.getWriter().println("    width: 100%;");
            response.getWriter().println("    margin-bottom: 20px;");
            response.getWriter().println("    font-size: 18px;");
            response.getWriter().println("}");
            response.getWriter().println("</style>");
            response.getWriter().println("</head>");
            response.getWriter().println("<body>");

            response.getWriter().println("<h1>Correct and Chosen Answers</h1>");

            while (resultSet.next()) {
                String correctOption = resultSet.getString("option_correct");
                String[] userAnswer = request.getParameterValues("answer_" + resultSet.getInt("id"));

                response.getWriter().println("<p>Question: " + resultSet.getString("question") + "</p>");

                response.getWriter().println("<p>Options:</p>");
                response.getWriter().println("<ul>");
                response.getWriter().println("<li>A. " + resultSet.getString("option_a") + "</li>");
                response.getWriter().println("<li>B. " + resultSet.getString("option_b") + "</li>");
                response.getWriter().println("<li>C. " + resultSet.getString("option_c") + "</li>");
                response.getWriter().println("<li>D. " + resultSet.getString("option_d") + "</li>");
                response.getWriter().println("</ul>");

                response.getWriter().println("<p>Your Answer: " + (userAnswer != null ? userAnswer[0] : "Not answered") + "</p>");
                response.getWriter().println("<p>Correct Answer: " + correctOption + "</p><br>");
            }

            resultSet.close();
            statement.close();

            // Home button at the end of the page
            response.getWriter().println("<button style=\"background-color: #ffffff; border: 1px solid #007BFF; padding: 5px 10px; font: inherit; cursor: pointer; text-decoration: none; color: #007BFF;\">"
            	    + "<a href='http://localhost:8080/bytebuddy/home.jsp' style=\"text-decoration: none; color: #007BFF;\">Home</a>"
            	    + "</button>");



            response.getWriter().println("</body>");
            response.getWriter().println("</html>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing the request");
        }
    }
    
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Call doGet method to reuse the code for both GET and POST
        doGet(request, response);
        System.out.println("DisplayAnswersServletcpp is called!"); 
    }
}
