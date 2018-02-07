package md5generator;

import java.awt.EventQueue;

/**
 * Main
 * 
 * @author pedro.toledano
 */
public class Main
{
	public static void main(String args[])
	{
		EventQueue.invokeLater(() -> {
			MainWindow appWindow = new MainWindow();
		});
	}
}
