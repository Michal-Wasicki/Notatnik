import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import javax.print.PrintException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Okno extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JTextArea pole = new JTextArea();
	public Okno() throws PrintException, FileNotFoundException {
		setVisible(true);
		setSize(600,500);
		setTitle("Notatnik");
		setDefaultCloseOperation(3);
		setLayout(new GridBagLayout());
		JMenuBar pasek = new JMenuBar();
		JMenu plik = new JMenu("Plik");
		JMenu edycja = new JMenu("Edytuj");
		JMenu pomoc = new JMenu("Pomoc");
		plik.setMnemonic(KeyEvent.VK_P);
		edycja.setMnemonic(KeyEvent.VK_E);
		pomoc.setMnemonic(KeyEvent.VK_H);
		Image icon = new javax.swing.ImageIcon("images/notatnik.jpg").getImage();
		
		
		
		//---------------------ikonki---------------------------------
		//plik
		JMenuItem nowyPlik = new JMenuItem("nowyPlik",new ImageIcon("images/newFile.png"));
		JMenuItem zapisz = new JMenuItem("zapisz",new ImageIcon("images/save.png"));
		JMenuItem zamknij = new JMenuItem("zamknij",new ImageIcon("images/close.png"));
		JMenuItem otworz = new JMenuItem("otwórz",new ImageIcon("images/open.png"));
		JMenuItem drukuj = new JMenuItem("drukuj",new ImageIcon("images/drukuj.png"));
		//edytuj
		JMenuItem zaznacz = new JMenuItem("zaznacz wszystko",new ImageIcon("images/edit.png"));
		JMenuItem cofnij = new JMenuItem("cofnij",new ImageIcon("images/undo.png"));
		JMenuItem kopiuj = new JMenuItem("kopiuj",new ImageIcon("images/kopiuj.png"));
		JMenuItem wklej = new JMenuItem("wklej",new ImageIcon("images/wklej.png"));
		JMenuItem powtorz = new JMenuItem("powtorz",new ImageIcon("images/powtorz.png"));
		JMenuItem wytnij = new JMenuItem("wytnij",new ImageIcon("images/wytnij.png"));
		//pomoc
		JMenuItem info = new JMenuItem("info",new ImageIcon("images/info.png"));
		JMenuItem infoAutor = new JMenuItem("info o autorze",new ImageIcon("images/infoAutor.png"));
		
		//-------------Ustawienie wielkosci
//		Dimension newDim = new Dimension(50, 50);
//
//	    nowyPlik.setMinimumSize(newDim);
//	    nowyPlik.setPreferredSize(newDim);
//	    nowyPlik.setMaximumSize(newDim);
//	    nowyPlik.setSize(newDim);
//	    nowyPlik.revalidate();
		
		//---------------------dorzucenie Menu --------------------
		pasek.add(plik);
		pasek.add(edycja);
		pasek.add(pomoc);
		//-----------------wrzucenie ikonki do paska plik--------------------
		plik.add(nowyPlik);
		plik.add(zapisz);
		plik.add(zamknij);
		plik.add(otworz);
		plik.add(drukuj);
		//-----------------wrzucenie ikonki do paska edytuj--------------------
		edycja.add(zaznacz);
		edycja.add(cofnij);
		edycja.add(kopiuj);
		edycja.add(wklej);
		edycja.add(powtorz);
		edycja.add(wytnij);
		
		//-----------------wrzucenie ikonki do paska pomoc--------------------
		pomoc.add(info);
		pomoc.add(infoAutor);
		
		setIconImage(icon); // aktywacja ikony g³ównej
		setJMenuBar(pasek); // aktywacja paska
		
		setVisible(true); // widocznosc na koncu zeby rozciaganie 
		
		//------------Kontener
		Container c = getContentPane();
		final JScrollPane sp = new JScrollPane(pole);
		sp.setSize(590,350);
		c.add(sp);
		sp.setVisible(false);
		c.setBackground(new Color(45, 167, 222));
		c.setLayout(null);
		
		
		
		
		//---------------akcje
		
		nowyPlik.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pole.setText("");
				sp.setVisible(true);
			}
		});
		
		zapisz.addActionListener(new ActionListener() {
			private Component aComponent;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showDialog(aComponent, "Zapisz");
				File f = fc.getSelectedFile();
				if(f != null) {    //if zeby bledy nie wyskakiwaly
				String nazwa = f.getAbsolutePath();
				String text = pole.getText();
				
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(nazwa));
					bw.write(text, 0, text.length());
					bw.flush();
					bw.close();	
					
				} catch (Exception ek) {
					System.out.println(""+ek.getMessage());
				}
				}
			}
		});
		zamknij.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		otworz.addActionListener(new ActionListener() {
			private Component aComponent;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showDialog(aComponent, "Otwórz");
				File f = fc.getSelectedFile();
				if(f != null) {
				String nazwa = f.getAbsolutePath();
				sp.setVisible(true);
				String linia;
				
				try {
					BufferedReader br = new BufferedReader(new FileReader(nazwa));
					pole.setText("");
					while((linia=br.readLine())!=null) {
						pole.append(linia+"\n");
					}
					br.close();
					
				} catch (Exception ek) {
					System.out.println("--------------");
				}
				}
			}
		});
		
		drukuj.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					pole.print();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		zaznacz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pole.selectAll();
            }
        });
		
		kopiuj.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				pole.copy();
			}
		});
		
		wklej.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				pole.paste();
			}
		});
		
		powtorz.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		wytnij.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pole.cut();
			}
		});
			

		
		info.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showConfirmDialog(null, "Version 0.0.1","O programie",-1,1);
				
			}		
			});
		
		infoAutor.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showConfirmDialog(null, "Autor aplikacji: Micha³ W¹sicki","O autorze",-1,1);
				
			}		
			});
				
//		zapisz.addActionListener(new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			JFrame parentFrame = new JFrame();
//			 
//			JFileChooser fileChooser = new JFileChooser();
//			fileChooser.setDialogTitle("Specify a file to save");   
//			 
//			int userSelection = fileChooser.showSaveDialog(parentFrame);
//			 
//			if (userSelection == JFileChooser.APPROVE_OPTION) {
//			    File fileToSave = fileChooser.getSelectedFile();
//			    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
//			}
//			
//		}
//	});	
		
		
	}

}
