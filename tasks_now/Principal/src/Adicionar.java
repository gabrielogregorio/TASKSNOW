import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import javax.swing.DropMode;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Adicionar extends JFrame {

	private JPanel contentPane;
	private JTextField textTitulo;
	private JTextField textDescricao;
	private JTextField textNumeroPrioridade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Adicionar frame = new Adicionar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Adicionar() {
		setTitle("Adicionar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 238, 238));
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox_2 = Box.createVerticalBox();
		verticalBox_2.setBackground(new Color(238, 238, 238));
		panel.add(verticalBox_2);
		
		JPanel panel_1_1_1_1 = new JPanel();
		panel_1_1_1_1.setMinimumSize(new Dimension(10, 200));
		verticalBox_2.add(panel_1_1_1_1);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		horizontalBox_2.setBackground(new Color(238, 238, 238));
		horizontalBox_2.setFont(new Font("Dialog", Font.PLAIN, 25));
		verticalBox_2.add(horizontalBox_2);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBackground(new Color(238, 238, 238));
		horizontalBox_2.add(verticalBox);
		
		JLabel lbltitulo = new JLabel("Titulo");
		lbltitulo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lbltitulo.setFont(new Font("Dialog", Font.PLAIN, 17));
		verticalBox.add(lbltitulo);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setMinimumSize(new Dimension(10, 30));
		verticalBox.add(panel_1_2);
		
		JLabel lbldescricao = new JLabel("Descricao");
		lbldescricao.setFont(new Font("Dialog", Font.PLAIN, 17));
		verticalBox.add(lbldescricao);
		
		JPanel panel_1_2_1 = new JPanel();
		panel_1_2_1.setMinimumSize(new Dimension(10, 30));
		verticalBox.add(panel_1_2_1);
		
		JLabel lblPrioridade = new JLabel("Prioridade    ");
		lblPrioridade.setFont(new Font("Dialog", Font.PLAIN, 17));
		verticalBox.add(lblPrioridade);
		
		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setBackground(new Color(238, 238, 238));
		horizontalBox_2.add(verticalBox_1);
		
		textTitulo = new JTextField();
		textTitulo.setFont(new Font("Dialog", Font.PLAIN, 17));
		verticalBox_1.add(textTitulo);
		textTitulo.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(10, 30));
		verticalBox_1.add(panel_1);
		
		textDescricao = new JTextField();
		textDescricao.setFont(new Font("Dialog", Font.PLAIN, 17));
		verticalBox_1.add(textDescricao);
		textDescricao.setColumns(10);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setMinimumSize(new Dimension(10, 30));
		verticalBox_1.add(panel_1_1);
		
		textNumeroPrioridade = new JTextField();
		textNumeroPrioridade.setFont(new Font("Dialog", Font.PLAIN, 17));
		verticalBox_1.add(textNumeroPrioridade);
		textNumeroPrioridade.setColumns(10);
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setMinimumSize(new Dimension(10, 50));
		verticalBox_2.add(panel_1_1_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(238, 238, 238));
		verticalBox_2.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setForeground(new Color(238, 238, 238));
		btnSalvar.setAutoscrolls(true);
		btnSalvar.setBackground(new Color(51, 204, 51));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sqlInsert = "INSERT INTO TAREFAS"
						+ "(TITULO, DESCRICAO, PRIORIDADE, STATUS)"
						+ "VALUES"
						+ "('"
						+ textTitulo.getText().toString()
						+ "', '"
						+ textDescricao.getText().toString()
						+ "',"
						+ textNumeroPrioridade.getText()
						+ ", 'A FAZER')";

				if (Dados.objBD.conectaBD()) {
					try {
						Dados.objBD.atualiza(sqlInsert);
						System.out.println("Inserindo o valor");
					} catch(Exception e) {
						JOptionPane.showMessageDialog(null, "Erro => " + e + ", erro classe" + Dados.objBD.mensagem());
					}			
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco => " + Dados.objBD.mensagem());
				}

				Tasks janela = new Tasks();
				janela.setVisible(true);
				setVisible(false);
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(new Color(238, 238, 238));
		btnCancelar.setBackground(new Color(255, 51, 51));
		btnCancelar.setAutoscrolls(true);
		panel_2.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tasks janela = new Tasks();
				janela.setVisible(true);
				setVisible(false);
			}
		});
		panel_2.add(btnSalvar);
	}

}
