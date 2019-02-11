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
    
    public static void main(String[] args) { 
        // 
        // String sql = "SELECT first_name, last_name, email " +
        //             "FROM candidates";
    	boolean run = true;
    	
        Scanner scan = new Scanner(System.in);
        
        while(run) {
        	int input = scan.nextInt();
        	if(input == 0 ) {
        		break;
        	}
        	else if(input == 3 ) {
        		query3();
        	}
        	else if(input == 4 ) {
        		query4();
        	}
        	
        }
        
    	
        
    }
}