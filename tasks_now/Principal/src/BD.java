import java.sql.*;


public class BD{
	   
    private Connection con;
	private Statement stmt;
	private boolean erro;
	private String msg;
	private String banco, usuario, senha;
	private Class<?> forName;
   
      public BD(String b, String u, String s){
		 this.banco = "jdbc:mysql://localhost/" + b;
		 this.usuario = u;
		 this.senha = s;
		 erro = false;
		 msg = "";
	 }
	 public boolean conectaBD(){
		 this.erro=false;
	       try {
		 	
		      Class.forName("com.mysql.jdbc.Driver");
		      
		      con = DriverManager.getConnection(this.banco, this.usuario, this.senha);
		      stmt=con.createStatement();
		     }catch (SQLException e){this.erro=true;
		      	System.out.println(e);
			  this.msg="Falha na conexao com o banco de dados!"; 
			}
		      catch (java.lang.Exception e){this.erro=true;
		      	System.out.println(e);
			     this.msg="Erro no driver de conexao!"; 
			}
		return !erro;      
	}
	public ResultSet consulta (String c){
		ResultSet res=null;
		this.erro=false;
		this.msg="Sucesso na execucao da consulta!";
		try{
			res=stmt.executeQuery(c);
		}catch (SQLException e){this.erro=true;
		      this.msg="Falha na execucao da consulta!";
		 }
		return res;
	}
	public boolean atualiza(String c){
		int i=-1;
		this.erro=false;
		this.msg="Operacao realizada com sucesso!";
		System.out.println("....");

		try
		{
			i = stmt.executeUpdate(c);
			System.out.println(i);
			
		}
		catch (SQLException e)
		{
			System.out.println("Erro ao atualizar registro => " + e.getMessage());
			this.erro=true; 
			this.msg="Falha na operaao!";
        }

		return !erro;
	}
	public boolean desconecta(){
		boolean sucesso=true;
		try{
			stmt.close();
			con.close();
		}catch(SQLException e){sucesso=false;}
		return sucesso;
	}
	public boolean ocorreuErro(){
		return this.erro;
	}

	public String mensagem(){
		return this.msg;
	}
}
