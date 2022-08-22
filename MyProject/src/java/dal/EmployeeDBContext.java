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
            String sql = "SELECT e.eid,e.ename,ISNULL(t.tid,-1) tid,                                                                                             "
                    + "t.checkin,t.checkout,k.totalKL,k.totalCL,k.totalNL,m.sumsalary                                                                       "
                    + "FROM Employee e LEFT JOIN                                                                                                            "
                    + "(SELECT * FROM TimeSheet WHERE checkin >= ?                                                                  "
                    + "AND checkin <= ?) t                                                                                          "
                    + "ON e.eid = t.eid left join Salary s on s.eid = e.eid                                                                                 "
                    + "left join (select eid,(case when osid = 1 then DATEDIFF(day,fromDate,toDate)+1 end) as totalKL,                                      "
                    + "isnull((case when osid = 2 then DATEDIFF(day,fromDate,toDate)+1 end),0) as totalCL,                                                  "
                    + "isnull((case when osid = 3 then DATEDIFF(day,fromDate,toDate)+1 end),0) as totalNL                                                   "
                    + "from Offtime ) k on e.eid = k.eid                                                                                                    "
                    + "left join (select t.eid,(count(CASE WHEN coef = 1 THEN t.tid END)*1*s.salary + count(CASE WHEN coef = 2 THEN t.tid END)*2*s.salary   "
                    + " + count(CASE WHEN coef = 3 THEN t.tid END)*3*s.salary) as sumsalary  from TimeSheet t join                                          "
                    + "Salary s on s.eid = t.eid     group by t.eid,s.salary) m on m.eid = e.eid         ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, DateTimeHelper.getTimeStamp(from));
            stm.setTimestamp(2, DateTimeHelper.getTimeStamp(to));
            ResultSet rs = stm.executeQuery();
            Employee currentEmp = new Employee();
            currentEmp.setId(-1);
            while (rs.next()) {
                int eid = rs.getInt("eid");
                if (eid != currentEmp.getId()) {
                    currentEmp = new Employee();
                    currentEmp.setId(eid);
                    currentEmp.setName(rs.getString("ename"));
                    currentEmp.setPaidleave(rs.getInt("totalCL"));
                    currentEmp.setUnpaidleave(rs.getInt("totalKL"));
                    currentEmp.setHoliday(rs.getInt("totalNL"));
                    currentEmp.setSalary(rs.getFloat("sumsalary"));
                    emps.add(currentEmp);
                }
                int tid = rs.getInt("tid");
                if (tid != -1) {
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
      public ArrayList<Employee> getSearchEmps(Date from, Date to,String name) {
        ArrayList<Employee> emps = new ArrayList<>();
        try {
            String sql = "SELECT e.eid,e.ename,ISNULL(t.tid,-1) tid,                                                                                             "
                    + "t.checkin,t.checkout,k.totalKL,k.totalCL,k.totalNL,m.sumsalary                                                                       "
                    + "FROM Employee e LEFT JOIN                                                                                                            "
                    + "(SELECT * FROM TimeSheet WHERE checkin >= ?                                                                  "
                    + "AND checkin <= ?) t                                                                                          "
                    + "ON e.eid = t.eid left join Salary s on s.eid = e.eid                                                                                 "
                    + "left join (select eid,(case when osid = 1 then DATEDIFF(day,fromDate,toDate)+1 end) as totalKL,                                      "
                    + "isnull((case when osid = 2 then DATEDIFF(day,fromDate,toDate)+1 end),0) as totalCL,                                                  "
                    + "isnull((case when osid = 3 then DATEDIFF(day,fromDate,toDate)+1 end),0) as totalNL                                                   "
                    + "from Offtime ) k on e.eid = k.eid                                                                                                    "
                    + "left join (select t.eid,(count(CASE WHEN coef = 1 THEN t.tid END)*1*s.salary + count(CASE WHEN coef = 2 THEN t.tid END)*2*s.salary   "
                    + " + count(CASE WHEN coef = 3 THEN t.tid END)*3*s.salary) as sumsalary  from TimeSheet t join                                          "
                    + "Salary s on s.eid = t.eid     group by t.eid,s.salary) m on m.eid = e.eid  where e.ename like '%" + name + "%' ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, DateTimeHelper.getTimeStamp(from));
            stm.setTimestamp(2, DateTimeHelper.getTimeStamp(to));
            ResultSet rs = stm.executeQuery();
            Employee currentEmp = new Employee();
            currentEmp.setId(-1);
            while (rs.next()) {
                int eid = rs.getInt("eid");
                if (eid != currentEmp.getId()) {
                    currentEmp = new Employee();
                    currentEmp.setId(eid);
                    currentEmp.setName(rs.getString("ename"));
                    currentEmp.setPaidleave(rs.getInt("totalCL"));
                    currentEmp.setUnpaidleave(rs.getInt("totalKL"));
                    currentEmp.setHoliday(rs.getInt("totalNL"));
                    currentEmp.setSalary(rs.getFloat("sumsalary"));
                    emps.add(currentEmp);
                }
                int tid = rs.getInt("tid");
                if (tid != -1) {
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