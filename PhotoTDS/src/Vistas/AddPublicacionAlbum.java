package Vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controlador.Controlador;
import Modelo.Album;

public class AddPublicacionAlbum {

	private JFrame frmAddFotoAlbum;
	private Album album;
	private List<File> fotos;

	void muestra(Album album) {
		this.album = album;
		initialize();
		frmAddFotoAlbum.setVisible(true);
		frmAddFotoAlbum.setLocationRelativeTo(null);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPublicacionAlbum window = new AddPublicacionAlbum(null);
					window.frmAddFotoAlbum.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddPublicacionAlbum(Album album) {
		fotos = new LinkedList<File>();
		this.album = album;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddFotoAlbum = new JFrame();
		frmAddFotoAlbum.setUndecorated(true);
		frmAddFotoAlbum.setIconImage(Toolkit.getDefaultToolkit().getImage(AddPublicacionAlbum.class.getResource("/Imagenes/instagramx16.png")));
		frmAddFotoAlbum.setTitle("A\u00F1ada una foto");
		frmAddFotoAlbum.setBounds(100, 100, 616, 404);
		frmAddFotoAlbum.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmAddFotoAlbum.getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0};
		gbl_panel.rowHeights = new int[]{10, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Gestor de archivos - Imagen");
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PNG", "png");
		fc.setFileFilter(filtro);
		
		/**
		 * Boton de Imagen del buscador de archivos
		 */
		JButton btnNewButton = new JButton("Agregar Imagen");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int resultado = fc.showOpenDialog(fc);
				if(resultado == JFileChooser.APPROVE_OPTION) {
					File fotoSeleccionada = fc.getSelectedFile();
					fotos.add(fotoSeleccionada);
				}
				
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 20;
		gbc_btnNewButton.gridy = 1;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		JPanel panel_1 = new JPanel();
		frmAddFotoAlbum.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton_1 = new JButton("Siguiente ->");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (album.getFotos().size() + fotos.size() > 16) {
					System.out.println("Supera el limite de fotos en un album");
				}
				else if(fotos.size() >= 1) {
					frmAddFotoAlbum.setVisible(false);
					try {
						Controlador.getUnicaInstancia().addFotoAlbum(fotos, album);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
				}
				else {
					System.out.println("No hay foto");
				}
				
				
			}
		});
		
		JButton btnNewButton_2 = new JButton("Cancelar");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmAddFotoAlbum.setVisible(false);
			}
		});
		panel_1.add(btnNewButton_2);
		panel_1.add(btnNewButton_1);
		
		JEditorPane dtrpnagregarFotoanmateA = new JEditorPane();
		frmAddFotoAlbum.getContentPane().add(dtrpnagregarFotoanmateA, BorderLayout.CENTER);
		dtrpnagregarFotoanmateA.setContentType("text/html");
		dtrpnagregarFotoanmateA.setText(
				"<h1>Agregar Foto</h1><p>Añade fotos a tu álbum.</p>");
		dtrpnagregarFotoanmateA.setEditable(false);
		dtrpnagregarFotoanmateA.setDropTarget(new DropTarget() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						if(file != null) {
							fotos.add(file);
						}
						
						
						System.out.println(file.getPath());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	//metodos privados

	public List<File> getFotos() {
		return fotos;
	}

	public void setFotos(List<File> fotos) {
		this.fotos = fotos;
	}
	
	
	
}
