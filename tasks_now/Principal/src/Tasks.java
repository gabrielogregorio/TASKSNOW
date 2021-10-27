import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class Tasks extends JFrame {
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tasks frame = new Tasks();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String formataTexto(String valor) {
		valor = valor + "                              ";
		return valor.substring(0,30);
	}
	
	public boolean update_prioridade(int ID, int prioridade) {
		String sqlAtualiza = "UPDATE TAREFAS SET PRIORIDADE=" + prioridade +  " WHERE ID = " + ID;

		if (Dados.objBD.conectaBD()) {
			try {
				Dados.objBD.atualiza(sqlAtualiza);
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Erro = " + e + ", erro classe" + Dados.objBD.mensagem());
				return false;
			}			
		} else {
			JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco => " + Dados.objBD.mensagem());
			return false;
		}
		return true;
	}
	
	public boolean alteraPrioridade(String tipoPrioridade, int ID) {
		String sqlPesquisa;
		
		if (tipoPrioridade == "+") {
			sqlPesquisa = "SELECT ID, TITULO, DESCRICAO, PRIORIDADE, STATUS FROM TAREFAS WHERE STATUS != 'FEITO' ORDER BY PRIORIDADE ASC";
		} else {
			sqlPesquisa = "SELECT ID, TITULO, DESCRICAO, PRIORIDADE, STATUS FROM TAREFAS WHERE STATUS != 'FEITO' ORDER BY PRIORIDADE DESC";
		}
	
		if (Dados.objBD.conectaBD()) {
			ResultSet objRes = Dados.objBD.consulta(sqlPesquisa);
			
				try {
					int id_anterior = 0;
					int prioridade_anterior = 0;
					int id_analise;
					int prioridade;

					while (objRes.next()) {
						
						id_analise =  Integer.parseInt(objRes.getString("ID").toString());
						prioridade =  Integer.parseInt(objRes.getString("PRIORIDADE").toString());
						
						if (id_analise == ID) {

							if (id_anterior == 0) {
								return true;
							} else {
								update_prioridade(id_anterior, prioridade);
								update_prioridade(id_analise, prioridade_anterior);
								
								Tasks janela = new Tasks();
								janela.setVisible(true);
								setVisible(false);
								
								return true;
							}
						}
						
						id_anterior = id_analise;
						prioridade_anterior = prioridade;
					}
				} catch (Exception e2 ) {
					JOptionPane.showMessageDialog(null, "Deu tudo errado mano, olha só = " + e2);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco! "  + Dados.objBD.mensagem());
			}
		return true;
	}
	
	public Tasks() {
		setTitle("Tarefas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel superior_botoes = new JPanel();
		contentPane.add(superior_botoes, BorderLayout.NORTH);
		
		JButton btnTarefas = new JButton("Tarefas");
		btnTarefas.setBackground(Color.GRAY);
		btnTarefas.setForeground(Color.BLACK);
		superior_botoes.add(btnTarefas);
		
		JButton btnConcluidos = new JButton("Concluidas");
		btnConcluidos.setBackground(new Color(255, 255, 255));
		btnConcluidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Concluidos janela = new Concluidos();
				janela.setVisible(true);
				setVisible(false);
			}
		});
		superior_botoes.add(btnConcluidos);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox_1 = Box.createVerticalBox();
		panel_1.add(verticalBox_1);
				
		String sqlPesquisa = "SELECT ID, TITULO, DESCRICAO, PRIORIDADE, STATUS FROM TAREFAS WHERE STATUS != 'FEITO' ORDER BY PRIORIDADE";
		
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
					
					JLabel lblTitulo_1 = new JLabel(formataTexto(objRes.getString("PRIORIDADE").toString() + " - " + objRes.getString("TITULO").toString()));
					lblTitulo_1.setName( objRes.getString("ID").toString() );
					lblTitulo_1.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							JLabel botao = (JLabel) (e.getSource());
							
							Editar janela = new Editar(botao.getName().toString(), "Tasks");
							janela.setVisible(true);
							setVisible(false);
							
						}
					});
					panel_post1_1.add(lblTitulo_1);
					
					JButton btnSob_1 = new JButton("+");
					btnSob_1.setName( objRes.getString("ID").toString() );
					btnSob_1.setForeground(Color.WHITE);
					btnSob_1.setBackground(new Color(255, 69, 0));
					btnSob_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JButton botao = (JButton) (arg0.getSource());
							alteraPrioridade("+", Integer.parseInt( botao.getName().toString()));
						}
					});
					panel_post1_1.add(btnSob_1);
					
					JButton btnSub_1 = new JButton("-");
					btnSub_1.setName( objRes.getString("ID").toString() );
					btnSub_1.setForeground(Color.WHITE);
					btnSub_1.setBackground(new Color(100, 149, 237));
					btnSub_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JButton botao = (JButton) (arg0.getSource());
						    alteraPrioridade("-", Integer.parseInt( botao.getName().toString()));
						}
					});
					panel_post1_1.add(btnSub_1);
					
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
							
							Tasks janela = new Tasks();
							janela.setVisible(true);
							setVisible(false);
						}
					});
					panel_post1_1.add(btnX_1);
					
					JButton btnOk_1 = new JButton("OK");
					btnOk_1.setName( objRes.getString("ID").toString() );
					btnOk_1.setForeground(Color.WHITE);
					btnOk_1.setBackground(new Color(0, 128, 0));
					btnOk_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JButton botao = (JButton) (arg0.getSource());
							String sqlAtualiza = "UPDATE TAREFAS SET STATUS='FEITO' WHERE ID = " + botao.getName().toString();
							boolean sucesso = true;

							if (Dados.objBD.conectaBD()) {
								try {
									Dados.objBD.atualiza(sqlAtualiza);
								} catch(Exception e) {
									sucesso = false;
									JOptionPane.showMessageDialog(null, "Erro '" + e + "', erro classe = " + Dados.objBD.mensagem());
									
								}			
							} else {
								sucesso = false;
								JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco = " + Dados.objBD.mensagem());
							}
							
							if (sucesso) {
								Tasks janela = new Tasks();
								janela.setVisible(true);
								setVisible(false);
							}
						}
					});
					panel_post1_1.add(btnOk_1);
					
					JPanel panel_POSTAGEM_2 = new JPanel();
					verticalBox_1.add(panel_POSTAGEM_2);
				}
			} catch (Exception e2 ) {
			JOptionPane.showMessageDialog(null, "Deu tudo errado mano, olha só = " + e2);
		}
			 
		} else {
			JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco! "  + Dados.objBD.mensagem());
		}
	
        JScrollPane scrollPane2 = new JScrollPane(panel_1);
		scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane2);

		JPanel inferior = new JPanel();
		contentPane.add(inferior, BorderLayout.SOUTH);
		
		JButton lblNovaTarefa = new JButton("+");
		lblNovaTarefa.setForeground(new Color(255, 255, 255));
		lblNovaTarefa.setBackground(new Color(34, 139, 34));
		lblNovaTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Adicionar janela = new Adicionar();
				setVisible(false);
				janela.setVisible(true);
			}
		});
		inferior.add(lblNovaTarefa);
		
		JLabel lblNovaTarefa2 = new JLabel("Adicionar nova tarefa");
		inferior.add(lblNovaTarefa2);
	}
}
