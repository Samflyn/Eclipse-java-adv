package getset;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SAM")
public class Employee {
	@Id
	private int empno;
	private NameClass empname;
	private int deptno;
	private long salary;

	@Override
	public String toString() {
		return "Employee [empno=" + empno + ", empname=" + empname + ", deptno=" + deptno + ", salary=" + salary + "]";
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public NameClass getEmpname() {
		return empname;
	}

	public void setEmpname(NameClass empname) {
		this.empname = empname;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}
}
