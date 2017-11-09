package guide2.rmi.rmiParkingClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import guide2.rmi.rmiParkingInterface.IParkingManager;

public class ParkingApplicationClient extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField carId;

	private JLabel statusLine;
	private IParkingManager manager = null;
	/**
	 * Launch the application.
	 * Não esquecer de correr o rmiregistry na pasta build
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParkingApplicationClient frame = new ParkingApplicationClient("localhost");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ParkingApplicationClient(String server) {
		// manager = new ClientStub ( "localhost", 5000);
		try { // Now we lookup the server and create the server proxy object
			String name = "Parking";
			Registry registry = LocateRegistry.getRegistry(server);
			manager = (IParkingManager) registry.lookup(name); // << ==== get proxy
			System.out.println("Manager clientÂ â€?>" + manager);
			// Create GUI
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 160);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);

			JButton btnNewButton = new JButton("Enter Park");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean res = false;
					try {
						res = manager.enterPark(carId.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (res)
						statusLine.setText("Ok Enterpark -" + carId.getText());
					else
						statusLine.setText("Error Enterpark - " + carId.getText());
				}
			});
			contentPane.add(btnNewButton, BorderLayout.WEST);

			JButton btnNewButton_1 = new JButton("Leave Park");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean res = false;
					try {
						res = manager.leavePark(carId.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (res)
						statusLine.setText("Ok Leavepark -" + carId.getText());
					else
						statusLine.setText("Error Leavepark - " + carId.getText());
				}
			});
			contentPane.add(btnNewButton_1, BorderLayout.CENTER);

			JButton btnNewButton_2 = new JButton("Is In Park?");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean res = false;
					try {
						res = manager.isInPark(carId.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (res)
						statusLine.setText(carId.getText() + " is in Park");
					else
						statusLine.setText(carId.getText() + " is NOT in Park");
				}
			});

			contentPane.add(btnNewButton_2, BorderLayout.EAST);

			JButton btnNewButton_3 = new JButton("Stop Server");
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean res = false;
					try {
						res = manager.stop();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (res)
						statusLine.setText("Server stopping");
					else
						statusLine.setText("Error while stopping server");
				}
			});
			JPanel subPanel = new JPanel();
			contentPane.add(subPanel, BorderLayout.SOUTH);
			subPanel.setLayout(new BorderLayout());
			subPanel.add(btnNewButton_3, BorderLayout.CENTER);
			// subPanel.add(btnNewButton_3);
			// contentPane.add(btnNewButton_3,BorderLayout.PAGE_END);
			// contentPane.add(btnNewButton_3, BorderLayout.EAST);

			JPanel panel_1 = new JPanel();
			contentPane.add(panel_1, BorderLayout.NORTH);

			JLabel lblNewLabel = new JLabel("Car Id (text)");
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(lblNewLabel);

			carId = new JTextField();
			carId.setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(carId);
			carId.setColumns(20);

			JPanel panel_2 = new JPanel();
			// contentPane.add(panel_2, BorderLayout.SOUTH);
			subPanel.add(panel_2, BorderLayout.SOUTH);
			statusLine = new JLabel("       ");
			statusLine.setVerticalAlignment(SwingConstants.TOP);
			panel_2.add(statusLine);

		} catch (Exception e) {
			System.err.println("Park Manager creation exception:");
			e.printStackTrace();
			System.exit(1); // Terminate process, we can't go further! :â€?(
		}
	}


}
