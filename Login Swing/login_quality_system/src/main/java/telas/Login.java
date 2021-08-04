
package telas;


import funcionalidades.UsuarioFuncionalidade;
import java.io.IOException;
import java.util.logging.Level;
import log.Log;
import java.util.logging.Logger;

public class Login extends javax.swing.JFrame {

    
    //Criando ojeto da classe UsuarioFuncionalidade
    UsuarioFuncionalidade usuarioFunc = new UsuarioFuncionalidade();
    Log log = new Log();

    //Construtor colando os componentes da pagina
    public Login() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        input_senha = new javax.swing.JPasswordField();
        txt_password = new javax.swing.JLabel();
        input_user = new javax.swing.JTextField();
        btn_entrar = new javax.swing.JButton();
        txt_esqueceuSenha = new javax.swing.JLabel();
        txt_user = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        imagemLogo = new javax.swing.JLabel();
        txt_contatos1 = new javax.swing.JLabel();
        txt_email = new javax.swing.JLabel();
        txt_telefone = new javax.swing.JLabel();
        txt_sistema = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(43, 43, 47));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel5.setBackground(new java.awt.Color(121, 63, 255));

        input_senha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_senhaActionPerformed(evt);
            }
        });

        txt_password.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        txt_password.setForeground(new java.awt.Color(255, 255, 255));
        txt_password.setText("Senha");

        input_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_userActionPerformed(evt);
            }
        });

        btn_entrar.setBackground(new java.awt.Color(153, 153, 255));
        btn_entrar.setForeground(new java.awt.Color(255, 255, 255));
        btn_entrar.setText("Entrar");
        btn_entrar.setBorder(null);
        btn_entrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_entrarMouseEntered(evt);
            }
        });
        btn_entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_entrarActionPerformed(evt);
            }
        });

        txt_esqueceuSenha.setForeground(new java.awt.Color(255, 255, 255));
        txt_esqueceuSenha.setText("Esqueceu a senha?");

        txt_user.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        txt_user.setForeground(new java.awt.Color(255, 255, 255));
        txt_user.setText("Usuário");

        imagemLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/QualitySystemLogoSimples.png"))); // NOI18N
        imagemLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        imagemLogo.setMaximumSize(new java.awt.Dimension(10, 10));
        imagemLogo.setMinimumSize(new java.awt.Dimension(10, 10));
        imagemLogo.setPreferredSize(new java.awt.Dimension(10, 10));

        txt_contatos1.setBackground(new java.awt.Color(121, 63, 255));
        txt_contatos1.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        txt_contatos1.setForeground(new java.awt.Color(121, 63, 255));
        txt_contatos1.setText("Contato");

        txt_email.setBackground(new java.awt.Color(121, 63, 255));
        txt_email.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        txt_email.setForeground(new java.awt.Color(121, 63, 255));
        txt_email.setText("suporte@qualitysystem.com");

        txt_telefone.setBackground(new java.awt.Color(121, 63, 255));
        txt_telefone.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        txt_telefone.setForeground(new java.awt.Color(121, 63, 255));
        txt_telefone.setText("0800 222 444 5555");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(txt_email))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(txt_telefone))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(txt_contatos1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(imagemLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imagemLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(txt_contatos1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        txt_sistema.setBackground(new java.awt.Color(255, 255, 255));
        txt_sistema.setFont(new java.awt.Font("Malgun Gothic", 0, 18)); // NOI18N
        txt_sistema.setForeground(new java.awt.Color(255, 255, 255));
        txt_sistema.setText("Acesso ao sistema");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_esqueceuSenha)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_password)
                                    .addComponent(input_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(33, 33, 33))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_user)
                                    .addComponent(input_user, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(txt_sistema)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(txt_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_user, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_esqueceuSenha)
                .addContainerGap(48, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void btn_entrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_entrarActionPerformed
   
        //Botão de login fazendo select no banco
        String login = input_user.getText();
        String senha = input_senha.getText();
        //Verificando se usuario existe no banco de dados
        Boolean usuarioLogado = usuarioFunc.login(login, senha);

        if (usuarioLogado) {
            Computador telaPrincipal = null;
            try {
                log.logar("Sessão iniciada! Usuário: " + input_user.getText());
                telaPrincipal = new Computador(input_user.getText());
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            telaPrincipal.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btn_entrarActionPerformed
    
    private void input_senhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_senhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_senhaActionPerformed

    private void input_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_userActionPerformed
        // TODO add your handling code here:
           
    }//GEN-LAST:event_input_userActionPerformed

    private void btn_entrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_entrarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_entrarMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Computador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Computador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Computador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Computador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
                 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_entrar;
    private javax.swing.JLabel imagemLogo;
    private javax.swing.JPasswordField input_senha;
    private javax.swing.JTextField input_user;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel txt_contatos1;
    private javax.swing.JLabel txt_email;
    private javax.swing.JLabel txt_esqueceuSenha;
    private javax.swing.JLabel txt_password;
    private javax.swing.JLabel txt_sistema;
    private javax.swing.JLabel txt_telefone;
    private javax.swing.JLabel txt_user;
    // End of variables declaration//GEN-END:variables

}
