package ui;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import runs.Run09Fast;
import runs.Run10FastExternalizer;
import student.Student;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class SuperAdminPage extends Dialog {

	protected Object result;
	protected Shell shell;
	private ArrayList<Student> students;
	private ArrayList<String> courses;
	private StringBuffer buffer;
	private Text progressBar;
	private DoubleWrapper initFit = new DoubleWrapper();
	private ProgressBar progressBar_1;
	private Thread thread;
	
	private String solutionProcessorStartResult;

	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SuperAdminPage(Shell parent, int style, ArrayList<Student> students, ArrayList<String> courses) {
		super(parent, style);
		setText("SWT Dialog");
		
		this.students = students;
		this.courses = courses;
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
				if (progressBar != null && buffer != null) {
					String string = buffer.toString();
					progressBar.setText(string);
					if (!string.isEmpty()){
					progressBar_1.setMaximum(new Double(Math.pow(initFit.getDbl().intValue(), 1)).intValue());
					progressBar_1.setSelection(new Double(Math.pow(initFit.getDbl().intValue() - Double.valueOf(string).intValue(), 1)).intValue());
					}

					if (!string.isEmpty()){
					if (thread != null && Double.valueOf(string).intValue() == 0){
						new SolutionDemonstrator(shell, SWT.NONE, solutionProcessorStartResult).open();
						thread = null;
						solutionProcessorStartResult = "";
					}
					}
				}
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
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new GridLayout(2, false));
		
		progressBar_1 = new ProgressBar(shell, SWT.NONE);
		GridData gd_progressBar_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_progressBar_1.heightHint = 46;
		progressBar_1.setLayoutData(gd_progressBar_1);
		
		progressBar = new Text(shell, SWT.V_SCROLL);
		progressBar.setText("1\n2\n3");
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setText("Deconnexion");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				thread.stop();
				thread = null;
				shell.close();
			}
		});
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnNewButton_1.setText("Demarrer Resolvation d'Horaires");

		buffer = new StringBuffer();
		
		btnNewButton_1.addSelectionListener(new SelectionListener() {



			@Override
			public void widgetSelected(SelectionEvent arg0) {
				thread = new Thread(new Runnable() {
					

					@Override
					public void run() {
						HashMap<Integer, String> classList = new HashMap<>();
						int i = 0; for (String course: courses){
							classList.put(i++, course);
						}
						solutionProcessorStartResult = Run10FastExternalizer.solutionProcessorStart(classList, students, buffer, initFit);
					}
				});
				thread.start();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
