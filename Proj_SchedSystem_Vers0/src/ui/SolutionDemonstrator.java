package ui;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

public class SolutionDemonstrator extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private String results;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SolutionDemonstrator(Shell parent, int style, String results) {
		super(parent, style);
		this.results = results;
		setText("SWT Dialog");
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
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new BorderLayout(0, 0));
		
		text = new Text(shell, SWT.BORDER | SWT.V_SCROLL);
		text.setLayoutData(BorderLayout.CENTER);
		text.setText(results);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setLayoutData(BorderLayout.SOUTH);
		btnNewButton.setText("OK");
		btnNewButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
