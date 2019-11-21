package com.testing.jersey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestRepository {
	List<Employee> emp = new ArrayList<Employee>();
	Employee e = new Employee();

	public void ConnectionDB() throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			Statement st = con.createStatement();
			String sqlQuery = "select * from sam";
			ResultSet rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				e.setEmpnum(rs.getInt(1));
				e.setEname(rs.getString(2));
				e.setSalary(rs.getInt(3));
				e.setDept(rs.getInt(4));
				emp.add(e);
				e = new Employee();
			}
			con.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public List<Employee> getEmployees() throws SQLException {
		ConnectionDB();
		return emp;
	}

	public Employee getEmployee(int no) throws SQLException {
		ConnectionDB();
		for (Employee e1 : emp) {
			if (e1.getEmpnum() == no) {
				return e1;
			}
		}
		return e;
	}

	public int create(Employee e1) throws SQLException {
		Connection con = null;
		ConnectionDB();
		int i = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			if (!emp.contains(e1)) {
				String query = "insert into sam values(?,?,?,?)";
				PreparedStatement pst = con.prepareStatement(query);
				pst.setInt(1, e1.getEmpnum());
				pst.setString(2, e1.getEname());
				pst.setInt(3, e1.getSalary());
				pst.setInt(4, e1.getDept());
				i = pst.executeUpdate();
			}
			con.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return i;
	}

	public int update(Employee e1) throws SQLException {
		Connection con = null;
		ConnectionDB();
		int i = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			for (Employee ex : emp) {
				if (ex.getEmpnum() == e1.getEmpnum()) {
					String query = "update sam set ename=?, salary=?, dept=? where empnum=" + e1.getEmpnum();
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, e1.getEname());
					pst.setInt(2, e1.getSalary());
					pst.setInt(3, e1.getDept());
					i = pst.executeUpdate();
				}
			}
			con.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return i;

	}

	public int delete(int value) throws SQLException {
		Connection con = null;
		int i = 0;
		ConnectionDB();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			for (Employee ex : emp) {
				if (ex.getEmpnum() == value) {
					String query = "delete from sam where empnum=" + value;
					PreparedStatement pst = con.prepareStatement(query);
					i = pst.executeUpdate();
				}
			}
			con.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return i;
	}
}