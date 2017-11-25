 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leoed
 */
public class ApproveMemberships extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        DBBean bean = (DBBean)session.getAttribute("bean");
        ArrayList<Member> unapprovedMembers = Members.getAppliedMembers(bean);
        boolean[] feesPaid = new boolean[unapprovedMembers.size()];
        for(int x = 0; x < unapprovedMembers.size(); x++)
            feesPaid[x] = Fees.initialFeePaid(unapprovedMembers.get(x), bean);
        request.setAttribute("members", unapprovedMembers);
        request.setAttribute("fees", feesPaid);
        RequestDispatcher view = request.getRequestDispatcher("ApproveMembers.jsp");
        view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ApproveMemberships.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] checkedMembers = request.getParameterValues("checkedMembers");
        if(checkedMembers != null){
            ArrayList<Member> membersToApprove = new ArrayList<>();
            HttpSession session = request.getSession();
            ArrayList<Member> unapprovedMembers = new ArrayList<>();
            DBBean bean = (DBBean)session.getAttribute("bean");
            try {
                unapprovedMembers = Members.getAppliedMembers(bean);
            } catch (SQLException ex) {
                Logger.getLogger(ApproveMemberships.class.getName()).log(Level.SEVERE, null, ex);
            }

            for(String id : checkedMembers){
                for(Member m : unapprovedMembers)
                    if(m.id.equals(id))
                        membersToApprove.add(m);
            }

            for(Member m : membersToApprove)
                Members.approveMember(m, bean);
        }

        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ApproveMemberships.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}