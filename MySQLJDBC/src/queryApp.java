import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSeparator;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class queryApp extends JFrame {

	private JPanel contentPane;
	
	private static MySQLJDBCUtil jdbcUtil;
	private JTable table_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jdbcUtil = new MySQLJDBCUtil();
					queryApp frame = new queryApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public queryApp() {
		setTitle("Project 2 App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{503, 0};
		gbl_contentPane.rowHeights = new int[]{33, 336, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		
		JButton btnQuery1 = new JButton("Query 1");
		panel.add(btnQuery1);
		
		JButton btnQuery2 = new JButton("Query 2");
		panel.add(btnQuery2);
		
		JButton btnQuery4 = new JButton("Query 4");
		btnQuery4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnQuery3 = new JButton("Query 3");
		btnQuery3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Run pre-defined Query
				// Call DAO and get qualified employees
				// Print out employees
				String sql = "SELECT *  from (employees e NATURAL JOIN salaries s)" +
						"where gender = 'F' AND to_date = '9999-01-01' AND salary >= 80000 AND emp_no IN ( SELECT emp_no from dept_manager)";
	        ArrayList<Employee> list = new ArrayList<Employee>();
	        try (Connection conn = MySQLJDBCUtil.getConnection();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)) {
	           
	            // loop through the result set
	            while (rs.next()) {
	            	Employee employee = new Employee(rs.getInt("emp_no"), rs.getString("first_name"), rs.getString("last_name"),
	            			rs.getString("birth_date"), rs.getString("gender"), rs.getString("hire_date"));
	            	list.add(employee);
	                System.out.println(rs.getString("first_name") + "\t" + 
	                                   rs.getString("last_name")  + "\t");
	                    
	            }
	            JTable table = new JTable(MySQLJDBCUtil.buildTableModel(rs));
	            JScrollPane scrollPane = new JScrollPane();
	    		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
	    		gbc_scrollPane.fill = GridBagConstraints.BOTH;
	    		gbc_scrollPane.gridx = 0;
	    		gbc_scrollPane.gridy = 1;
	    		contentPane.add(scrollPane, gbc_scrollPane);
	    		
	    		table = new JTable();
	    		scrollPane.setViewportView(table);
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
			}
		});
		panel.add(btnQuery3);
		panel.add(btnQuery4);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
	}

}
