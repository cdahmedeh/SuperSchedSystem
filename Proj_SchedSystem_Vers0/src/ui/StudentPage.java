package ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import student.Student;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import constraint.Constraint;
import constraint.MustHaveCourse;
import constraint.PreferedGeneralCourseTime;

public class StudentPage extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table table;
	private Table table2;
	private ArrayList<String> courses;
	private Composite composite;
	private Button btnNewButton;
	private Button btnNewButton_1;
	private Student student;
	private Composite composite_1;
	private Button btnNewButton_2;
	private Button btnRadioButton;
	private Button btnRadioButton_1;
	private Button btnRadioButton_2;
	public ArrayList<String> getCourses() {
		return courses;
	}
	public void setCourses(ArrayList<String> courses) {
		this.courses = courses;
	}
	
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public StudentPage(Shell parent, int style, ArrayList<String> courses, Student student) {
		super(parent, style);
		setText("SWT Dialog");
		this.courses = courses;
		this.student = student;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(500, 300);
		shell.setText(getText());

		shell.setLayout(new BorderLayout());
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(BorderLayout.WEST);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table2 = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table2.setLayoutData(BorderLayout.EAST);
		table2.setHeaderVisible(true);
		table2.setLinesVisible(true);
				
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(200);
		tblclmnNewColumn.setText("Cours Disponible");
		
		TableColumn tblclmnNewColumn2 = new TableColumn(table2, SWT.NONE);
		tblclmnNewColumn2.setWidth(200);
		tblclmnNewColumn2.setText("Cours Selectionné");
		
		composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setFont(SWTResourceManager.getFont("Sans", 40, SWT.NORMAL));
		btnNewButton.setText("<");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ArrayList<Constraint> constraints = student.getConstraints();
				Constraint cons2 = null;
				for (Constraint cons: constraints){
					if (cons instanceof MustHaveCourse) {
						if (((MustHaveCourse) cons).getCourseName().equals(table2.getSelection()[0].getText())){
							cons2 = cons;
							break;
						}
					}
				}
				constraints.remove(cons2);
				fillTable();
				fillTable2();
			}
			
		});
		
		btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setFont(SWTResourceManager.getFont("Sans", 40, SWT.NORMAL));
		btnNewButton_1.setText(">");
		
		composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.SOUTH);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		btnNewButton_2 = new Button(composite_1, SWT.NONE);
		btnNewButton_2.setText("Deconnexion");
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
				
		btnRadioButton = new Button(composite_1, SWT.RADIO);
		btnRadioButton.setText("Aucune Preference");
		btnRadioButton.setSelection(true);
		btnRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Constraint cons2 = null;
				for (Constraint cons: student.getConstraints()){
					if (cons instanceof PreferedGeneralCourseTime) {
						cons = cons2;
					}
				}
				student.getConstraints().remove(cons2);
			}
		});
		
		btnRadioButton_1 = new Button(composite_1, SWT.RADIO);
		btnRadioButton_1.setText("Matin (8:30-16:00)");
		btnRadioButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Constraint cons2 = null;
				for (Constraint cons: student.getConstraints()){
					if (cons instanceof PreferedGeneralCourseTime) {
						cons = cons2;
					}
				}
				student.getConstraints().remove(cons2);
				student.addConstraint(new PreferedGeneralCourseTime(8, 30, 16, 00));
			}
		});
		
		btnRadioButton_2 = new Button(composite_1, SWT.RADIO);
		btnRadioButton_2.setText("Soir (14:30-22:00)");
		btnRadioButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Constraint cons2 = null;
				for (Constraint cons: student.getConstraints()){
					if (cons instanceof PreferedGeneralCourseTime) {
						cons = cons2;
					}
				}
				student.getConstraints().remove(cons2);
				student.addConstraint(new PreferedGeneralCourseTime(14, 30, 22, 00));
			}
		});
		
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				student.addConstraint(new MustHaveCourse(table.getSelection()[0].getText()));
				fillTable();
				fillTable2();
			}
		});
		
		fillTable();
		fillTable2();
	}

	
	public void fillTable() {
		table.removeAll();
		for (String course: courses){
			boolean stop = false;
			for (String select : student.getWantedCoursesNonOptimzed()){
				if (select.equals(course)) {
					stop = true;
					break;
				}
			}
			if (stop){
				continue;
			}
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(course);
		}
	}
	
	public void fillTable2() {
		table2.removeAll();
		for (String wantedCourses: student.getWantedCoursesNonOptimzed()){
			TableItem item = new TableItem(table2, SWT.NONE);
			item.setText(wantedCourses);
		}
	}
}
