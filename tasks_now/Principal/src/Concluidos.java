import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Color;

public class Concluidos extends JFrame {

	private JPanel contentPane;
	public String formataTexto(String valor) {
		valor = valor + "                              ";
		return valor.substring(0,30);
	}
	
	/**
	 * Create the frame.
	 */
	public Concluidos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel superior_botoes = new JPanel();
		contentPane.add(superior_botoes);
		superior_botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("Tarefas");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tasks janela = new Tasks();
				janela.setVisible(true);
				setVisible(false);
			}
		});		superior_botoes.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Concluidas");
		btnNewButton_1.setBackground(Color.GRAY);
		superior_botoes.add(btnNewButton_1);

		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox_1 = Box.createVerticalBox();
		panel_1.add(verticalBox_1);
				
		String sqlPesquisa = "SELECT ID, TITULO, DESCRICAO, PRIORIDADE, STATUS FROM TAREFAS WHERE STATUS = 'FEITO' ORDER BY PRIORIDADE";
		
		if (Dados.objBD.conectaBD()) {
			System.out.println("Conectou ao banco de dados!");
			ResultSet objRes = Dados.objBD.consulta(sqlPesquisa);
			
			try {
				while (objRes.next()) {
		
					JPanel panel_POSTAGEM = new JPanel();
					verticalBox_1.add(panel_POSTAGEM);
					
					Box horizontalConteudo_1_1 = Box.createHorizontalBox();
					horizontalConteudo_1_1.setBackground(Color.WHITE);
					panel_POSTAGEM.add(horizontalConteudo_1_1);
					
					JPanel panel_post1_1 = new JPanel();
					panel_post1_1.setBackground(Color.WHITE);
					horizontalConteudo_1_1.add(panel_post1_1);
					
					JLabel lblTitulo_1 = new JLabel(formataTexto(formataTexto(objRes.getString("PRIORIDADE").toString() + " - " + objRes.getString("TITULO").toString())));
					lblTitulo_1.setName( objRes.getString("ID").toString() );
					lblTitulo_1.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							JLabel botao = (JLabel) (e.getSource());
							
							Editar janela = new Editar(botao.getName().toString(), "Concluidos");
							janela.setVisible(true);
							setVisible(false);
							
						}
					});
					panel_post1_1.add(lblTitulo_1);
					
					JButton btnSob_1 = new JButton("<");
					btnSob_1.setName( objRes.getString("ID").toString() );
					btnSob_1.setForeground(Color.WHITE);
					btnSob_1.setBackground(new Color(200, 140, 0));
					btnSob_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JButton botao = (JButton) (arg0.getSource());
							
							String sqlAtualiza = "UPDATE TAREFAS SET STATUS='A FAZER' WHERE ID = " + botao.getName().toString();
							boolean sucesso = true;

							if (Dados.objBD.conectaBD()) {
								try {
									Dados.objBD.atualiza(sqlAtualiza);
									System.out.println("Atualizado o valor");
								} catch(Exception e) {
									sucesso = false;
									JOptionPane.showMessageDialog(null, "Erro => " + e + ", erro classe" + Dados.objBD.mensagem());
								}			
							} else {
								sucesso = false;
								JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco => " + Dados.objBD.mensagem());
							}
							
							if (sucesso) {
								Tasks janela = new Tasks();
								janela.setVisible(true);
								setVisible(false);
							}
							
						}
					});
					panel_post1_1.add(btnSob_1);
					
					JButton btnX_1 = new JButton("X");
					btnX_1.setName( objRes.getString("ID").toString() );
					btnX_1.setForeground(Color.WHITE);
					btnX_1.setBackground(Color.RED);
					btnX_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JButton botao = (JButton) (arg0.getSource());

							String sqlExcluir = "DELETE FROM TAREFAS WHERE ID= " + botao.getName().toString();
							
							if (Dados.objBD.conectaBD()) {
								try {
									Dados.objBD.atualiza(sqlExcluir);
									System.out.println("Inserindo o valor");
								} catch(Exception e) {
									JOptionPane.showMessageDialog(null, "Erro => " + e + ", erro classe" + Dados.objBD.mensagem());
								}			
							} else {
								JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco => " + Dados.objBD.mensagem());
							}
							
							Concluidos janela = new Concluidos();
							janela.setVisible(true);
							setVisible(false);
						}
					});
					panel_post1_1.add(btnX_1);
										
					JPanel panel_POSTAGEM_2 = new JPanel();
					verticalBox_1.add(panel_POSTAGEM_2);

				}
			} catch (Exception e2 ) {
			System.out.println("Deu tudo errado mano, olha só = " + e2);
		}
			 
		} else {
			System.out.println("Erro ao conectar ao banco! "  + Dados.objBD.mensagem());
		}

        JScrollPane scrollPane2 = new JScrollPane(panel_1);
		scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane2);
	}
}
