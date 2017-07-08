package com.brilapps.etl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.brilapps.etl.source.CostTypeReferenceTableExtractor;
import com.brilapps.etl.source.ProjectDefinitionRefereceTableExtractor;
import com.brilapps.etl.target.NetworkActivityGenerator;
import com.brilapps.etl.target.NetworkHeaderGenerator;
import com.brilapps.etl.target.ProjectDefinitionGenerator;
import com.brilapps.etl.target.WBSGenerator;

public class ETLMainClass extends JFrame implements ActionListener {
	static Logger logger = Logger.getLogger(ETLMainClass.class);

	private static final long serialVersionUID = 7221908953707958694L;
	private JTextField projectDefinitionJTextField, wbsJTextField, networkHeaderJTextField, destinationFolderJTextField, referenceTableJTextField;
	private JButton projectDefinitionJButton, wbsJButton, networkHeaderJButton, destinationFolderJButton, jButton5, jButton6, referenceTableButton;
	private JFileChooser fileChooser;
	private File projectDefinitionSourceFile, wbsSourceFile, networkHeaderSourceFile, destinationFolder,
	projectDefinitionDestinationFile;
	private File projectDefinitionReferenceTableFile;
	JProgressBar progressBar;

	public static void main(final String... d) throws FileNotFoundException {
		/*String log4jConfigFile = System.getProperty("user.dir") + "/src/com/brilapps/etl/" + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		logger.debug("this is a debug log message");
		logger.info("this is a information log message");
		logger.warn("this is a warning log message");*/
		new ETLMainClass();
	}

	ETLMainClass() {

		fileChooser = new JFileChooser();
		setTitle("ATL Tool");
		setLayout(null);
		setSize(1000, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel jLabel1 = new JLabel("Project Definition File");
		jLabel1.setBounds(10, 80, 300, 30);
		add(jLabel1);

		projectDefinitionJTextField = new JTextField();
		projectDefinitionJTextField.setBounds(250, 80, 400, 30);
		add(projectDefinitionJTextField);

		projectDefinitionJButton = new JButton("Browse");
		projectDefinitionJButton.setBounds(750, 80, 80, 30);
		add(projectDefinitionJButton);
		projectDefinitionJButton.addActionListener(this);

		JLabel jLabel2 = new JLabel("WBS File");
		jLabel2.setBounds(10, 160, 300, 30);
		add(jLabel2);

		wbsJTextField = new JTextField();
		wbsJTextField.setBounds(250, 160, 400, 30);
		add(wbsJTextField);

		wbsJButton = new JButton("Browse");
		wbsJButton.setBounds(750, 160, 80, 30);
		add(wbsJButton);
		wbsJButton.addActionListener(this);

		JLabel jLabel3 = new JLabel("Network Header File");
		jLabel3.setBounds(10, 240, 300, 30);
		add(jLabel3);

		networkHeaderJTextField = new JTextField();
		networkHeaderJTextField.setBounds(250, 240, 400, 30);
		add(networkHeaderJTextField);

		networkHeaderJButton = new JButton("Browse");
		networkHeaderJButton.setBounds(750, 240, 80, 30);
		add(networkHeaderJButton);
		networkHeaderJButton.addActionListener(this);

		JLabel jLabel4 = new JLabel("Destination Folder");
		jLabel4.setBounds(10, 320, 300, 30);
		add(jLabel4);

		destinationFolderJTextField = new JTextField();
		destinationFolderJTextField.setBounds(250, 320, 400, 30);
		add(destinationFolderJTextField);

		destinationFolderJButton = new JButton("Browse");
		destinationFolderJButton.setBounds(750, 320, 80, 30);
		add(destinationFolderJButton);
		destinationFolderJButton.addActionListener(this);

		JLabel jLabel5 = new JLabel("Reference Table");
		jLabel5.setBounds(10, 400, 300, 30);
		add(jLabel5);

		referenceTableJTextField = new JTextField();
		referenceTableJTextField.setBounds(250, 400, 400, 30);
		add(referenceTableJTextField);

		referenceTableButton = new JButton("Browse");
		referenceTableButton.setBounds(750, 400, 80, 30);
		add(referenceTableButton);
		referenceTableButton.addActionListener(this);

		jButton5 = new JButton("Generate Target Files");
		jButton5.setBounds(300, 480, 150, 30);
		add(jButton5);
		jButton5.addActionListener(this);

		jButton6 = new JButton("Exit");
		jButton6.setBounds(500, 480, 100, 30);
		add(jButton6);
		jButton6.addActionListener(this);

		projectDefinitionJTextField
				.setText("C:/Users/pkarrey.ORADEV/Documents/SAPOttawaProjectHeader_MOCK2_Updated_LOAD.xlsx");
		projectDefinitionSourceFile = new File(
				"C:/Users/pkarrey.ORADEV/Documents/SAPOttawaProjectHeader_MOCK2_Updated_LOAD.xlsx");

		wbsJTextField.setText("C:/Users/pkarrey.ORADEV/Documents/SAPOttawaProjectWBS_Mock2.xlsx");
		wbsSourceFile = new File("C:/Users/pkarrey.ORADEV/Documents/SAPOttawaProjectWBS_Mock2.xlsx");

		networkHeaderJTextField.setText("C:/Users/pkarrey.ORADEV/Documents/SAPOttawaProjectActualCosts_Mock2.xlsx");
		networkHeaderSourceFile = new File("C:/Users/pkarrey.ORADEV/Documents/SAPOttawaProjectActualCosts_Mock2.xlsx");

		referenceTableJTextField.setText("C:/Users/pkarrey.ORADEV/Documents/ProjectDefnitionReferenceTable.xls");
		projectDefinitionReferenceTableFile = new File(
				"C:/Users/pkarrey.ORADEV/Documents/ProjectDefnitionReferenceTable.xls");

		destinationFolderJTextField.setText("C:/Users/pkarrey.ORADEV/Documents");
		destinationFolder = new File("C:/Users/pkarrey.ORADEV/Documents");


	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		try {
			if (event.getSource() == projectDefinitionJButton) {
				FileFilter filter = new FileNameExtensionFilter("excel", "xls", "xlsx");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(filter);
				int x = fileChooser.showOpenDialog(null);
				if (x == JFileChooser.APPROVE_OPTION) {
					projectDefinitionSourceFile = fileChooser.getSelectedFile();
					projectDefinitionJTextField.setText(projectDefinitionSourceFile.getAbsolutePath());
				}
			} else if (event.getSource() == wbsJButton) {
				FileFilter filter = new FileNameExtensionFilter("excel", "xls", "xlsx");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(filter);
				int x = fileChooser.showOpenDialog(null);
				if (x == JFileChooser.APPROVE_OPTION) {
					wbsSourceFile = fileChooser.getSelectedFile();
					wbsJTextField.setText(wbsSourceFile.getAbsolutePath());
				}
			} else if (event.getSource() == networkHeaderJButton) {
				FileFilter filter = new FileNameExtensionFilter("excel", "xls", "xlsx");
				// fileChooser.setCurrentDirectory("");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(filter);
				int x = fileChooser.showOpenDialog(null);
				if (x == JFileChooser.APPROVE_OPTION) {
					networkHeaderSourceFile = fileChooser.getSelectedFile();
					networkHeaderJTextField.setText(networkHeaderSourceFile.getAbsolutePath());
				}
			} else if (event.getSource() == destinationFolderJButton) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int x = fileChooser.showOpenDialog(null);
				if (x == JFileChooser.APPROVE_OPTION) {
					destinationFolder = fileChooser.getSelectedFile();
					destinationFolderJTextField.setText(destinationFolder.getAbsolutePath());
				}
			} else if (event.getSource() == referenceTableButton) {
				FileFilter filter = new FileNameExtensionFilter("excel", "xls", "xlsx");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(filter);
				int x = fileChooser.showOpenDialog(null);
				if (x == JFileChooser.APPROVE_OPTION) {
					projectDefinitionReferenceTableFile = fileChooser.getSelectedFile();
					referenceTableJTextField.setText(projectDefinitionReferenceTableFile.getAbsolutePath());
				}
			} else if (event.getSource() == jButton5) {
				convertFiles();
			} else if (event.getSource() == jButton6) {
				System.exit(0);
			}
		} catch (Exception ex) {
			logger.error("Error : ", ex);
		}
	}

	private void convertFiles() {

		if (projectDefinitionSourceFile == null) {
			JOptionPane.showMessageDialog(null, "Please select Project Definition source file.", "Error",
					JOptionPane.ERROR_MESSAGE);
			logger.error("Error : ", new Exception("Please select Project Definition source file."));
			return;
		}
		if (wbsSourceFile == null) {
			JOptionPane.showMessageDialog(null, "Please select WBS source file.", "Error",
					JOptionPane.ERROR_MESSAGE);
			logger.error("Error : ", new Exception("Please select WBS source file."));
			return;
		}

		if (networkHeaderSourceFile == null) {
			JOptionPane.showMessageDialog(null, "Please select Network Header source file.", "Error",
					JOptionPane.ERROR_MESSAGE);
			logger.error("Error : ", new Exception("Please select Network Header source file."));
			return;
		}

		JDialog jdialog = new JDialog();
		/*progressBar = new JProgressBar();
		progressBar.setBounds(10, 30, 450, 30);
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);*/
		jdialog.setSize(200, 200);
		jdialog.setVisible(true);
		// jdialog.add(progressBar);
		updateLog4jConfiguration();
		extractProjectDefinitionRefereceTable();
		generateProjectDefinitionFile();
		generateWBSFile();
		generateNetworkHeaderFile();
		System.exit(0);
	}

	private void extractProjectDefinitionRefereceTable() {
		try {
			new ProjectDefinitionRefereceTableExtractor()
			.extractProjectDefinitionRefereceTable(projectDefinitionReferenceTableFile);
			new CostTypeReferenceTableExtractor().extractCostTypeRefereceTable(projectDefinitionReferenceTableFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("Error : ", e);
		}
	}

	private void generateProjectDefinitionFile() {
		try {
			projectDefinitionDestinationFile = new File(
					destinationFolder.getAbsolutePath() + "/ProjectDefinitionTarget.xls");
			File wBSElementUserField1DestinationFile = new File(
					destinationFolder.getAbsolutePath() + "/WBSElementUserField1Target.xls");
			File wBSElementUserField2DestinationFile = new File(
					destinationFolder.getAbsolutePath() + "/WBSElementUserField2Target.xls");
			new ProjectDefinitionGenerator().generateProjectDefinitionTargetWorkbook(projectDefinitionSourceFile,
					projectDefinitionDestinationFile, wBSElementUserField1DestinationFile,
					wBSElementUserField2DestinationFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("Error : ", e);
		}
	}

	private void generateWBSFile() {
		try {
			new WBSGenerator().generateWBStargetFile(wbsSourceFile,
					new File(destinationFolder.getAbsolutePath() + "/WBSNoDup.xls"),
					new File(destinationFolder.getAbsolutePath() + "/WBSTarget.xls"),
					projectDefinitionDestinationFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("Error : ", e);
		}
	}

	private void generateNetworkHeaderFile() {
		try {
			new NetworkHeaderGenerator().generateNetworkHeaderTargetFile(projectDefinitionDestinationFile,
					networkHeaderSourceFile,
					new File(destinationFolder.getAbsolutePath() + "/NetworkHeaderNoDup.xls"),
					new File(destinationFolder.getAbsolutePath() + "/NetworkHeaderTarget.xls"));
			new NetworkActivityGenerator().generateNetworkActivityTargetFile(projectDefinitionDestinationFile,
					networkHeaderSourceFile,
					new File(destinationFolder.getAbsolutePath() + "/NetworkActivityNoDup.xls"),
					new File(destinationFolder.getAbsolutePath() + "/NetworkActivityTarget.xls"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("Error : ", e);
		}
	}

	private void updateLog4jConfiguration() {
		Properties props = new Properties();
		props.setProperty("log4j.rootLogger", "DEBUG, Appender1,Appender2,Appender3");
		props.setProperty("log4j.appender.Appender1", "org.apache.log4j.ConsoleAppender");
		props.setProperty("log4j.appender.Appender1.layout", "org.apache.log4j.PatternLayout");
		props.setProperty("log4j.appender.Appender1.layout.ConversionPattern", "%-7p %d [%t] %c %x - %m%n");
		props.setProperty("log4j.appender.Appender1.Threshold", "DEBUG");

		props.setProperty("log4j.appender.Appender2", "org.apache.log4j.FileAppender");
		props.setProperty("log4j.appender.Appender2.layout", "org.apache.log4j.PatternLayout");
		props.setProperty("log4j.appender.Appender2.layout.ConversionPattern", "%-7p %d [%t] %c %x - %m%n");
		props.setProperty("log4j.appender.Appender2.file",
				destinationFolder.getAbsolutePath() + "/applogger.log");
		props.setProperty("log4j.appender.Appender2.Threshold", "ERROR");
		props.setProperty("log4j.appender.Appender2.Append", "false");

		props.setProperty("log4j.appender.Appender3", "org.apache.log4j.FileAppender");
		props.setProperty("log4j.appender.Appender3.layout", "org.apache.log4j.PatternLayout");
		props.setProperty("log4j.appender.Appender3.layout.ConversionPattern", "%-7p %d [%t] %c %x - %m%n");
		props.setProperty("log4j.appender.Appender3.file",
				destinationFolder.getAbsolutePath() + "/applogger_debug.log");
		props.setProperty("log4j.appender.Appender3.Threshold", "DEBUG");
		props.setProperty("log4j.appender.Appender3.Append", "false");

		LogManager.resetConfiguration();
		PropertyConfigurator.configure(props);
	}

}
