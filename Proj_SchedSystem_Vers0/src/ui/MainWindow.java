package ui;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;

import runs.Run09Generator;
import student.Student;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import database.DatabaseHandler;

public class MainWindow {

	protected Shell shell;
	private Text textUsername;
	private Text textPassword;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	ArrayList<String> courses = new ArrayList<>();
	ArrayList<Student> students = new ArrayList<>();
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new BorderLayout(0, 0));
		
		Button btnLogin = new Button(shell, SWT.NONE);
		btnLogin.setLayoutData(BorderLayout.SOUTH);
		btnLogin.setText("Connexion");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Nom d'usager:");
		
		textUsername = new Text(composite, SWT.BORDER);
		textUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Mot de passe:");
		
		textPassword = new Text(composite, SWT.BORDER);
		textPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		HashMap<Integer, String> classList = new HashMap<>();
		courses = DatabaseHandler.databaseloadCourses();
		
		int i = 0; for (String course: courses){
			classList.put(i++, course);
		}
		
		students = Run09Generator.generateStudents(20, classList);						
		
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (textUsername.getText().equals("admin")) {
					AdminPage adminPage = new AdminPage(shell, SWT.NONE, courses);
					adminPage.open();
					textUsername.setText("");
					textPassword.setText("");
				} else if (textUsername.getText().equals("superadmin")) {
					SuperAdminPage sap = new SuperAdminPage(shell, SWT.NONE, students, courses);
					sap.open();
				} else {
					Student studentFound = null;
					for (Student student : students){
						if (student.getUsername().equals(textUsername.getText())){
							studentFound = student;
						}
					}
					if (studentFound !=null){
						StudentPage sp = new StudentPage(shell, SWT.NONE, courses, studentFound);
						sp.open();
					} else {
						Student student = new Student(textUsername.getText());
						StudentPage sp = new StudentPage(shell, SWT.NONE, courses, student);
						sp.open();	
						students.add(student);
					}
				}
			}
		});
	}

}
