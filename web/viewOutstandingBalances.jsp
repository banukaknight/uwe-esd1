<%-- 
    Document   : viewOutstandingBalances
    Created on : 27-Nov-2017, 15:59:38
    Author     : leoed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Outstanding Balances</title>
    </head>
    <body>
        <div>
            <%@ page import="java.util.ArrayList"%>
            <%@ page import="com.Member"%>
            <%
                ArrayList<Member> members = (ArrayList<Member>)request.getAttribute("members");
            %>
            <%  if(members.size() > 0) {    %>
                    <TABLE id="memberTable" BORDER=1 CELLPADDING=2 CELLSPACING=0>
                        <td><b>ID</b></td>
                        <td><b>Name</b></td>
                        <td><b>Address</b></td>
                        <td><b>Date of Birth</b></td>
                        <td><b>Date of Registration</b></td>
                        <td><b>Balance</b></td>
                        <%  for(int x = 0; x < members.size(); x++){
                            Member m = members.get(x);  %>
                            <TR>
                                <td><%out.print(m.id);%></td>
                                <td><%out.print(m.name);%></td>
                                <td><%out.print(m.address);%></td>
                                <td><%out.print(m.dob);%></td>
                                <td><%out.print(m.dor);%></td>
                                <td><%out.print(m.balance);%></td>
                            </TR>
                        <%}%>
                    </TABLE>
                </form>
            <%
                }
                else{
            %>
            <p>There are currently no members with outstanding balances</p>
            <%}%>
          </div>
    </body>
</html>
