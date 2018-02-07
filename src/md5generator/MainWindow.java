package md5generator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


public class MainWindow
{
	private final int width = 700;
	private final int height = 200;
	private JFrame windowFrame;
	private JPanel pn_mainPanel;
	private JPanel pn_fileSelection;
	private JPanel pn_fileCreation;
	private JButton bt_loadFiles;
	private JButton bt_createMD5files;
	private JTextField tf_loadFiles;
	private JFileChooser fileChooser;
	private File[] files;
	
	GridLayout layout;
	
	/**
	 * Constructor
	 */
	public MainWindow()
	{
		// Configures the window frame
		initGUI();
		// Adds the layout design to the window frame
		addLayout();
		// Shows the window frame
		windowFrame.setVisible(true);
	}
	
	private void initGUI()
	{
		// Creates the window frame
		windowFrame = new JFrame();
		// Sets the window size
		windowFrame.setSize(width, height);
		// Sets the window title
		windowFrame.setTitle("Generador MD5 desde archivo");
		// Closes window when x button is clicked
		windowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// Enables the change of size
		windowFrame.setResizable(false);
		// Centers the window
		windowFrame.setLocationRelativeTo(null);
		// Shows the window frame
		windowFrame.setVisible(true);
	}
	
	private void addLayout()
	{
		//****************************************************//
		// MAIN PANEL
		//****************************************************//		
		pn_mainPanel = new JPanel( new GridBagLayout() );
		
		GridBagConstraints gridConf = new GridBagConstraints();
			
			//****************************************************//
			// FILE SELECTION SUBPANEL
			//****************************************************//
			pn_fileSelection = new JPanel( new GridBagLayout() );
			
			bt_loadFiles = new JButton();
			bt_loadFiles.setText("Buscar...");
			bt_loadFiles.addMouseListener(new fileChooserWindow());
			gridConf.gridx = 1;
			gridConf.gridy = 0;
			gridConf.gridwidth = 1;
			gridConf.fill = GridBagConstraints.HORIZONTAL;
			pn_fileSelection.add(bt_loadFiles, gridConf);
			
			tf_loadFiles = new JTextField(20);
			tf_loadFiles.setEditable(false);
			gridConf.gridx = 2;
			gridConf.gridy = 0;
			gridConf.gridwidth = 1;
			gridConf.fill = GridBagConstraints.VERTICAL;
			pn_fileSelection.add(tf_loadFiles, gridConf);
			
			
			// Panel border configuration
			//// Border 1: title
			TitledBorder br_title = new TitledBorder("Seleccionar ficheros");
			br_title.setTitleJustification(TitledBorder.LEFT);
			br_title.setTitlePosition(TitledBorder.TOP);
			
			//// Border 2: margin
			Border br_margin = new EmptyBorder(10, 10, 10, 10);
			
			//// Borders added to panel
			pn_fileSelection.setBorder(new CompoundBorder(br_title, br_margin));
			
			
			//****************************************************//
			// FILE CREATION SUBPANEL
			//****************************************************//
			pn_fileCreation = new JPanel( new GridBagLayout() );
			
			bt_createMD5files = new JButton();
			bt_createMD5files.setText("Crear ficheros MD5");
			bt_createMD5files.addMouseListener(new createMD5file());
			//bt_createMD5files.setEnabled(false);
			gridConf.gridx = 0;
			gridConf.gridy = 0;
			gridConf.gridwidth = 2;
			gridConf.fill = GridBagConstraints.HORIZONTAL;
			pn_fileCreation.add(bt_createMD5files, gridConf);
			
			
		// File selection subpanel added to main panel
		gridConf.gridx = 0;
		gridConf.gridy = 0;
		gridConf.gridwidth = 2;
		gridConf.fill = GridBagConstraints.HORIZONTAL;
		pn_mainPanel.add(pn_fileSelection, gridConf);
		
		// Selection subpanel added to main panel
		gridConf.gridx = 0;
		gridConf.gridy = 1;
		gridConf.gridwidth = 2;
		gridConf.fill = GridBagConstraints.HORIZONTAL;
		pn_mainPanel.add(pn_fileCreation, gridConf);
		
		// Main panel added to the window
		windowFrame.getContentPane().add(pn_mainPanel);
	}
	

	// LISTENERS
	private class fileChooserWindow extends MouseAdapter
	{
		public void mouseReleased(MouseEvent event)
	    {
			// Creates the invoice chooser
			fileChooser = new JFileChooser();
			// Sets the initial directory
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			fileChooser.setMultiSelectionEnabled(true);
			//fileChooser.showOpenDialog(mainPanel);
			fileChooser.showDialog(pn_mainPanel, "Cargar");
			
			files = fileChooser.getSelectedFiles();
			
			// If any file has been selected, the "search" button will be enabled
			if( files.length > 0 )
			{
				bt_createMD5files.setEnabled(true);
				
				for(int i=0; i<files.length; i++)
				{
					if(i == 0)
					{
						tf_loadFiles.setText( files[i].getName() );
					}
					else
					{
						tf_loadFiles.setText( tf_loadFiles.getText() + ", " + files[i].getName() );
					}
				}
			}
	    }
	}
	
	private class createMD5file extends MouseAdapter
	{
		public void mouseReleased(MouseEvent event)
	    {
			// If any file has been selected
			if( files != null )
			{
				for(int i=0; i<files.length; i++)
				{
					if( files[i].exists() )
					{
						//System.out.println( getMd5FilePath(files[i]) );
						writeMd5File( files[i].getPath(), getMd5FilePath(files[i]) );
					}
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero.", "Error cargando ficheros", JOptionPane.WARNING_MESSAGE);
			}
	    }
		private String getMd5FilePath(File file)
		{
			String fileName = file.getName().substring( 0, file.getName().lastIndexOf('.') );
			fileName = fileName + ".md5";
			
			//System.out.println("En " + file.getParent() + '/' + fileName);
			return ( file.getParent() + '/' + fileName );
		}
		private void writeMd5File(String filePath, String md5FilePath) /*DOS FUNCIONES CON EL MISMO NOMBRE*/
		{
			BufferedWriter bw;
			try
			{
				bw = new BufferedWriter(new FileWriter(md5FilePath));
				
				Md5Utils checksum = new Md5Utils(filePath);
				bw.write( checksum.md5FromFile() );
				
				//System.out.println("Con MD5: " + checksum.md5FromFile() + "de: " + md5FilePath);
				
				bw.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
