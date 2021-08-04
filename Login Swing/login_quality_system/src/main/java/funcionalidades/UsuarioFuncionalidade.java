package funcionalidades;

import conecaoBanco.Conexao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import log.Log;

public class UsuarioFuncionalidade {
    Log log = new Log();

    private Connection conectoryFactory;
    private String login1;

    public UsuarioFuncionalidade() {
        Conexao connection = new Conexao();
        try {
            this.conectoryFactory = connection.getConnection();
        } catch (Exception e) {
            try {
                log.logar("Erro ao conectar na URL do banco de dados Azure: " + e.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(UsuarioFuncionalidade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getLogin1() {
        return login1;
    }
    
    
   public Boolean login(String loginColaborador, String senha) {
       
       
        login1 = loginColaborador;
       
        PreparedStatement stn = null;
        Boolean userLoged = false;
      
        try {
            String sql = "select * from colaborador where loginColaborador = ? and senhaColaborador = ?;";
            stn = conectoryFactory.prepareStatement(sql);
            stn.setString(1, loginColaborador);
            stn.setString(2, senha);
            
            boolean userExists = stn.executeQuery().next();

            if(userExists){
                userLoged = true;
                try {
                    log.logar("Sessão iniciada com sucesso!");
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioFuncionalidade.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "Login feito com sucesso");
                conectoryFactory.close();
            }else{
                userLoged = false;
                JOptionPane.showMessageDialog(null, "Usuario ou senha incorreto");
                conectoryFactory.close();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                log.logar("Erro ao tentar fazer login: " + e.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(UsuarioFuncionalidade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userLoged;
    }
    
}
