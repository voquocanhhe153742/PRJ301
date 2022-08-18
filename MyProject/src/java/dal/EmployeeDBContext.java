/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import helper.DateTimeHelper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.TimeSheet;

/**
 *
 * @author admin
 */
public class EmployeeDBContext extends DBContext {

    public ArrayList<Employee> getEmps(Date from, Date to) {
        ArrayList<Employee> emps = new ArrayList<>();
        try {
            String sql = "SELECT e.eid,e.ename,ISNULL(t.tid,-1) tid,\n"
                    + "t.checkin,t.checkout\n"
                    + "FROM Employee e LEFT JOIN \n"
                    + "(SELECT * FROM TimeSheet WHERE checkin >= ?\n"
                    + "AND checkin <= ?) t\n"
                    + "ON e.eid = t.eid";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, DateTimeHelper.getTimeStamp(from));
            stm.setTimestamp(2, DateTimeHelper.getTimeStamp(to));
            ResultSet rs = stm.executeQuery();
            Employee currentEmp = new Employee();
            currentEmp.setId(-1);
            while (rs.next()) 
            {
                int eid = rs.getInt("eid");
                if (eid != currentEmp.getId()) 
                {
                    currentEmp = new Employee();
                    currentEmp.setId(eid);
                    currentEmp.setName(rs.getString("ename"));
                    emps.add(currentEmp);
                }
                int tid = rs.getInt("tid");
                if (tid != -1) 
                {
                    TimeSheet t = new TimeSheet();
                    t.setTid(tid);
                    t.setCheckin(rs.getTimestamp("checkin"));
                    t.setCheckout(rs.getTimestamp("checkout"));
                    t.setEmployee(currentEmp);
                    currentEmp.getTimesheets().add(t);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emps;
    }
}
