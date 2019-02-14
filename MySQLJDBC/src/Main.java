import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

 
/**
 *
 * @author mysqltutorial.org
 */
public class Main {
	
	public static void query1() {
		String sql = "with\r\n" + 
				"males_sal as (SELECT dept_no, avg(salary) as Msal from employees natural join salaries, dept_emp where gender ='M' AND dept_emp.emp_no = employees.emp_no group by dept_no )," + 
				"fem_sal as (SELECT dept_no, avg(salary) as Fsal from employees natural join salaries, dept_emp where gender ='F' AND dept_emp.emp_no = employees.emp_no group by dept_no)" + 
				"select dept_name, (fem_sal.Fsal/males_sal.Msal) as ratio from (males_sal natural join fem_sal) natural join departments\r\n" + 
				"where (fem_sal.Fsal/males_sal.Msal) = (select max((fem_sal.Fsal/males_sal.Msal)) from (males_sal natural join fem_sal) natural join departments)";
		
				
		try (Connection conn = MySQLJDBCUtil.getConnection();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)) {
	           System.out.println("dept_name\tsalary_ratio");
	            // loop through the result set
	            while (rs.next()) {
	                System.out.println(rs.getString("dept_name") + "\t" + rs.getString("ratio"));
	                    
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	}
	
	public static void query2() {
		String sql = "SELECT first_name, last_name, DATEDIFF(to_date, from_date) as diff "
				+ "FROM employees "
				+ "INNER JOIN dept_manager "
				+ "ON employees.emp_no = dept_manager.emp_no "
				+ "ORDER BY diff DESC "
				+ "LIMIT 1;";
				
		try (Connection conn = MySQLJDBCUtil.getConnection();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)) {
	           System.out.println("first_name\tlast_name\tdiff");
	            // loop through the result set
	            while (rs.next()) {
	                System.out.println(rs.getString("first_name") +  "\t\t" + rs.getString("last_name") + "\t" + rs.getString("diff"));
	                                   
	                    
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	}
	
	public static void query3() {
		String sql = "WITH fifties AS (SELECT dept_no, Count(*) AS fifties_employees FROM employees NATURAL JOIN dept_emp "
				+ "WHERE birth_date BETWEEN '1949-12-31' AND '1960-01-01' GROUP BY dept_no),"
				+ "sixties AS (SELECT dept_no,Count(*) AS sixties_employees FROM employees NATURAL JOIN dept_emp "
				+ "WHERE birth_date BETWEEN '1959-12-31' AND '1970-01-01' GROUP BY dept_no),"
				+ "sals AS (SELECT dept_no, avg(salary) AS avg_salaries from employees NATURAL JOIN dept_emp de, salaries "
				+ "WHERE salaries.emp_no = employees.emp_no group by dept_no)"
				+ "SELECT * FROM (fifties natural left JOIN sixties natural left join sals)";
		
		try (Connection conn = MySQLJDBCUtil.getConnection();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)) {
	           System.out.println("dept_no\tfifties_employees\tsixties_employees\tAvg_salaries");
	            // loop through the result set
	            while (rs.next()) {
	                System.out.println(rs.getString("dept_no") +  "\t" + rs.getString("fifties_employees") + "\t\t\t" + rs.getString("sixties_employees") + "\t\t\t" + 
	                                   rs.getString("avg_salaries"));
	                                   
	                    
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	}
	
	public static void query4() {
		String sql = "SELECT *  from (employees e NATURAL JOIN salaries s)" +
				"where gender = 'F' AND timediff(birth_date,'1990-01-01') < 0 AND salary >= 80000 AND emp_no IN ( SELECT emp_no from dept_manager)";
		
		try (Connection conn = MySQLJDBCUtil.getConnection();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)) {
	           System.out.println("emp_no\tbirthdate\tfirstname\tlastname\tgender\thiredate\tsalary\tfromdate\ttodate");
	            // loop through the result set
	            while (rs.next()) {
	                System.out.println(rs.getString("emp_no") +  "\t" + rs.getString("birth_date") + "\t" + rs.getString("first_name") + "   \t" + 
	                                   rs.getString("last_name") + "   \t" + rs.getString("gender") + "\t" + rs.getString("hire_date") 
	                                   +  "\t" + rs.getString("salary") +  "\t" + rs.getString("from_date") +  "\t" + rs.getString("to_date") );
	                                   
	                    
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	}
	
	public static void query5(String emp1, String emp2) {
			String sql = "SELECT E1.emp_no as employee1, E1.dept_no, E1.from_date as e1from,E1.to_date as e1to,E2.emp_no as employee2, "
					+ "E2.dept_no,E2.from_date as e2from,E2.to_date as e2to\r\n" + 
				"FROM dept_emp E1   \r\n" + 
				"JOIN dept_emp E2 ON E2.dept_no = E1.dept_no  \r\n" + 
				"WHERE E1.emp_no = "+emp1+"  AND  E2.emp_no ="+emp2+"  AND ((E2.from_date BETWEEN E1.from_date AND E1.to_date) "
						+ "AND (E2.to_date BETWEEN E1.from_date AND E1.to_date))";
		try (Connection conn = MySQLJDBCUtil.getConnection();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)) {
			int size = 0;  
			if (rs != null)   
			{  
				rs.beforeFirst();  
				 rs.last();  
				size = rs.getRow();
			}
					if(size > 0) {
						rs.beforeFirst();  
			           System.out.println("emp_no\tdept_no\tfrom_date\tto_date\t\temp_no\tdept_no\tfromdate\ttodate");
			            // loop through the result set
			            while (rs.next()) {
			                System.out.println(rs.getString("employee1") +  "\t" + rs.getString("dept_no") + "\t" + rs.getString("e1from") + "   \t" + 
			                                   rs.getString("e1to") + "   \t" + rs.getString("employee2") + "\t" + rs.getString("dept_no") 
			                                   +  "\t" + rs.getString("e2from") +  "\t" + rs.getString("e2to"));
			            }
					}
					else {
						query6(emp1,emp2);
					}
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	}
	public static void query6(String emp1, String emp2){
		String sql = "SELECT * FROM dept_emp E1 JOIN dept_emp E3 ON E3.dept_no = E1.dept_noWHERE (( E1.emp_no =" + emp1+ ") "
				+ "AND (E3.from_date BETWEEN E1.from_date AND E1.to_date) AND (E3.to_date BETWEEN E1.from_date AND E1.to_date)) "
				+ "JOIN dept_emp E2 ON E3.dept_no = E1.dept_no "
				+ "WHERE (((E2.emp_no =" + emp2 + ") AND (E2.from_date BETWEEN E3.from_date AND E3.to_date) AND (E2.to_date BETWEEN E3.from_date AND E3.to_date))";

		try (Connection conn = MySQLJDBCUtil.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql)) {
			System.out.println("emp_no\tdept_no\tfromdate\ttodate");
			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getString("emp_no") + "\t" + rs.getString("from_date") + "\t" + rs.getString("to_date") );
			}
		} catch (SQLException ex) {
		System.out.println(ex.getMessage());
		}
	}
    public static void main(String[] args) { 
        // 
        // String sql = "SELECT first_name, last_name, email " +
        //             "FROM candidates";
    	System.out.println("Commands: Quit = 0\t Query 1 = 1\t Query 2 = 2 \t Query 3 = 3 \t Query 4 = 4 \tQuery 5 = 5");
    	System.out.println("Please enter an input");
    	boolean run = true;
    	
        Scanner scan = new Scanner(System.in);
        
        while(run) {
        	int input = scan.nextInt();
        	if(input == 0 ) {
        		break;
        	}
			else if(input == 1) {
				query1();
			}
			else if(input == 2) {
				query2();
			}
        	else if(input == 3 ) {
        		query3();
        	}
        	else if(input == 4 ) {
        		query4();
        	}
        	else if(input == 5 ) {
        		System.out.println("Enter employee1 ID");
        		scan.nextLine();
        		String emp1_id = scan.next();
        		System.out.println("Enter employee2 ID");
        		String emp2_id = scan.next();
        		query5(emp1_id,emp2_id);
        	}
        	
        }
        
    	
        
    }
}