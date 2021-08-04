package telas;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.util.Conversor;
import conecaoBanco.Conexao;
import funcionalidades.MensagemPausa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import oshi.util.FormatUtil;
import funcionalidades.UsuarioFuncionalidade;
import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import log.Log;
import org.json.JSONObject;
import slack.integracao.Slack;

public class Computador extends javax.swing.JFrame {

    //Aqui Criamos nossos Objetos de cada classe
    UsuarioFuncionalidade usuario = new UsuarioFuncionalidade();
    Login login = new Login();
    Looca api = new Looca();
    Timer timer = new Timer();
    Conversor conversor = new Conversor();
    MensagemPausa msg = new MensagemPausa();
    Log log = new Log();

    
    
    private Connection conectoryFactory;
    private Connection conectoryFactoryDocker;
    private String hora;
    private LocalDate data;
    
    Integer min=0,seg=0,hra=0;

    //Quantos Segundos a RAM vai atualizar(2s)
    final long SEGUNDOSRAM = (1000 * 2);
    //Quantos Segundos os PROCESSOS vão atualizar(10s)
    final long SEGUNDOSPROCESS = (1000 * 10);
    //Quantos Segundos os DISCOS vão atualizar(2s)
    final long SEGUNDOSDISCOS = (1000 * 2);
    //Quantos Segundos a CPU vai atualizar (3s)
    final long SEGUNDOSCPU = (1000 * 3);
    final long SEGUNDOSMENSAGEM = (1000 * 120);

    TimerTask envioApi = new TimerTask() {
        @Override
        public void run() {
            PreparedStatement stn = null;
            String insertQuery = String.format("INSERT INTO registro (cpu1, disco, memoria, dataHora, idMaquina) values ('%s', %d, %d, getdate(), 1)", api.getProcessador().getUso().toString().replaceAll(",", "."), api.getGrupoDeDiscos().getTamanhoTotal(), api.getMemoria().getEmUso());
            try {
                stn = conectoryFactory.prepareStatement(insertQuery);
                stn.executeUpdate();
            } catch (SQLException ex) {
                try {
                    log.logar(ex.getMessage());
                } catch (IOException ex1) {
                    Logger.getLogger(Computador.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    };

    TimerTask envioApiDocker = new TimerTask() {
        @Override
        public void run() {
            PreparedStatement stn = null;
            String insertQuery = String.format("INSERT INTO registro (cpu1, disco, memoria, dataHora, idMaquina) values ('%s', %d, %d, SYSDATE(), 1)", api.getProcessador().getUso().toString().replaceAll(",", "."), api.getGrupoDeDiscos().getTamanhoTotal(), api.getMemoria().getEmUso());
            try {
                stn = conectoryFactoryDocker.prepareStatement(insertQuery);
                stn.executeUpdate();
            } catch (SQLException ex) {
                try {
                    log.logar(ex.getMessage());
                } catch (IOException ex1) {
                    Logger.getLogger(Computador.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    };

     Timer cronoTemp = new Timer();
    long tempo = (1000 * 1); // chama o método a cada 3 segundos 
    
     TimerTask crono = new TimerTask() {
        @Override
        public void run() {
            try {
                    seg++;
                    if (seg == 60) {
                        seg = 00;
                        min += 01;
                    }
                    if (min == 60) {
                        min = 00;
                        hra += 01;
                    }
                    cronometro.setText(hra + ":" + min + ":" + seg);
                }
        catch (Exception e
                
              ) 
                {
                    System.out.println(e.getStackTrace());
                }
       }
        
    };
    
    
    //AQUI RODAMOS AS INFORMAÇÕES DA RAM
    TimerTask ram = new TimerTask() {
        @Override
        public void run() {
            var memoriaEmUso = (FormatUtil.formatBytes(api.getMemoria().getEmUso()));
            var memoriaTotal = (FormatUtil.formatBytes(api.getMemoria().getTotal()));
            var memoriaDisponivel = (FormatUtil.formatBytes(api.getMemoria().getDisponivel()));

            double memoriaEmUsoDouble = (double) api.getMemoria().getEmUso();
            double memoriaTotalDezPorcento = api.getMemoria().getTotal() - (api.getMemoria().getTotal() * 0.1); //90% do total
            double memoriaTotalVintePorcento = api.getMemoria().getTotal() - (api.getMemoria().getTotal() * 0.2); //80% do total

            if (memoriaEmUsoDouble > memoriaTotalDezPorcento) { //90% do total
                PanelRam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0))); //vermelho
                fraseRam.setText("Cuidado!");
                statusRam.setBackground(Color.red);
            } else if (memoriaEmUsoDouble > memoriaTotalVintePorcento) { //80% do total
                PanelRam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 215, 0)));  //Laranja
                fraseRam.setText("Atenção!");
                statusRam.setBackground(Color.YELLOW);
            } else {
                PanelRam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 128, 0))); //verde
                fraseRam.setText("Bom Estado!");
                statusRam.setBackground(Color.GREEN);
            }

            lblRamUso.setText(memoriaEmUso);
            lblTotalRam.setText(memoriaTotal);
            lblRamDisponivel.setText(memoriaDisponivel);

        }
    };

    //AQUI RODAMOS AS INFORMAÇÕES DOS PROCESSOS
    TimerTask processos = new TimerTask() {
        @Override
        public void run() {
            listProcesso.setText("");
            var teste = api.getGrupoDeProcessos().getProcessos();
            PanelProcessos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 128, 0)));
            for (int x = 0; x < teste.size(); x++) {
                listProcesso.setText(listProcesso.getText() + teste.get(x).getNome() + "\n");
            }
        }
    };

    //AQUI RODAMOS AS INFORMAÇÕES DA DISCO
    TimerTask disco = new TimerTask() {
        @Override
        public void run() {
          
              DiscosGroup grupoDeDiscos = api.getGrupoDeDiscos();
       List<Disco> listDisks = grupoDeDiscos.getDiscos();
        List<Volume> volumes = grupoDeDiscos.getVolumes();

        Double memoriaUsada = 0.0;
        Double totalCapacity = 0.0;

        //Retornar apenas um disco do usuario
        String capacityDiskRefactored = conversor.formatarBytes(listDisks.get(0).getTamanho()).replace(" GiB", "");
        String capacity = capacityDiskRefactored.replace(",", ".");
        totalCapacity = Double.parseDouble(capacity);

        String memoryFree = conversor.formatarBytes(volumes.get(0).getDisponivel());
        String memoryRefactored = memoryFree.replace(" GiB", "").replace(",", ".");
        Double memory = Double.parseDouble(memoryRefactored); 

        memoriaUsada = memory;
        Double discoDispo = totalCapacity - memoriaUsada ;
         
            fraseDisco.setText("Bom estado!");
            statusDisco.setBackground(Color.GREEN);
            PanelDisco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 128, 0)));
            for (int x = 0; x < discoDispo; x++) {
                lblDiscoDisponivel.setText(memoryFree);
               // lblDiscoDisponivel.setText(String.format("%.1f GiB", new Double(lblDiscoDisponivel.getText() + discoDisponivel.get(x).getDisponivel()) / 1000000000));
            }

            lblTotalDisco.setText(capacityDiskRefactored);
            lblDiscoUso.setText(""+discoDispo);
        }
    };
    //AQUI RODAMOS AS INFORMAÇÕES DA CPU
    TimerTask cpu = new TimerTask() {
        @Override
        public void run() {
            var cpuUso = api.getProcessador().getUso();
            var cpuFrequencia = (FormatUtil.formatHertz(api.getProcessador().getFrequencia()));
            var cpuTempo = (FormatUtil.formatElapsedSecs(api.getSistema().getTempoDeAtividade()));

            if (cpuUso > 90) {
                fraseCpu.setText("Cuidado!");
                statusCpu.setBackground(Color.red);
                PanelCpu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0))); //vermelho   
            } else if (cpuUso >= 80) {
                fraseCpu.setText("Atenção!");
                statusCpu.setBackground(Color.YELLOW);
                PanelCpu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 215, 0)));  //Laranja  
            } else {
                fraseCpu.setText("Bom estado!");
                statusCpu.setBackground(Color.GREEN);
                PanelCpu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 128, 0))); //verde
            }

            lblUso.setText(String.format("%.2f", cpuUso));
            lblFrequencia.setText(cpuFrequencia);
            lblTempo.setText(cpuTempo);
        }
    };

    TimerTask msgNR17 = new TimerTask() {
        @Override
        public void run() {
            Object[] opcoes = {"Fazer pausa", "Mais tarde"};
            JLabel label = new JLabel(msg.gerarMensagem());
            label.setHorizontalAlignment(SwingConstants.CENTER);
            int clicou = JOptionPane.showOptionDialog(null, label, "Mensagem de pausa - NR17",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    opcoes, "Fazer pausa");

            if (clicou == 0) { //fazer pausa
                data = LocalDate.now();
                hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                PreparedStatement stn = null;
                String insertQuery = String.format("INSERT INTO marcaPausa (fkColaborador, dataPausa, horaPausa, descricao) values (%d, '%s', '%s', '%s')", 2, data, hora, msg.gerarMensagem());
                try {
                    stn = conectoryFactory.prepareStatement(insertQuery);
                    stn.executeUpdate();
                } catch (SQLException ex) {
                    try {
                        log.logar("Erro ao enviar pausas para o azure: " + ex.getMessage());
                    } catch (IOException ex1) {
                        Logger.getLogger(Computador.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(Computador.class.getName()).log(Level.SEVERE, null, ex);
                }
                msg.setIdPausa(msg.getIdPausa() + 1);
                msg.setQtdPausasFeitas(msg.getQtdPausasFeitas() + 1);
            } else { // adiar pausa
                msg.setQtdPausasAdiadas(msg.getQtdPausasAdiadas() + 1);
            }

        }
    };
    //Variavel Global para Reconhecer Usuario no parametro do construtor.
    static String colaborador;

    public Computador(String usuario) throws IOException {

        initComponents();
        //Aqui Mandamos o usuario que foi digitado na tela de Login
        this.colaborador = usuario;
        lblColaborador.setText("Olá, " + usuario);

        Conexao connection = new Conexao();

        try {
            this.conectoryFactory = connection.getConnection();
        } catch (SQLException e) {
            log.logar(e.getMessage());
        }

        //DOCKER
        try {
            this.conectoryFactoryDocker = connection.getConnectionDocker();
        } catch (Exception e) {
            try {
                log.logar(e.getMessage());

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        //AQUI O CRONOMETRO
        cronoTemp.scheduleAtFixedRate(crono, tempo, tempo);
        //AQUI CHAMAMOS AS INFORMAÇÕES DA RAM DE 2 EM 2 SEGUNDOS
        timer.scheduleAtFixedRate(msgNR17, 1000 * 20, SEGUNDOSMENSAGEM);
        //AQUI CHAMAMOS AS INFORMAÇÕES DA RAM DE 2 EM 2 SEGUNDOS
        timer.scheduleAtFixedRate(ram, 0, SEGUNDOSRAM);
        //AQUI CHAMAMOS AS INFORMAÇÕES DOS PROCESSOS DE 10 EM 10 SEGUNDOS
        timer.scheduleAtFixedRate(processos, 0, SEGUNDOSPROCESS);
        //AQUI CHAMAMOS AS INFORMAÇÕES DO DISCO DE 2 EM 2 SEGUNDOS
        timer.scheduleAtFixedRate(disco, 0, SEGUNDOSDISCOS);
        //AQUI CHAMAMOS AS INFORMAÇÕES DA CPU DE 3 EM 3 SEGUNDOS
        timer.scheduleAtFixedRate(cpu, 0, SEGUNDOSCPU);
        //AQUI CHAMAMOS AS INFORMAÇÕES DA CPU DE 0 EM 10 SEGUNDOS
        timer.scheduleAtFixedRate(envioApi, 0, 10000);

        timer.scheduleAtFixedRate(envioApiDocker, 0, 10000);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        lblColaborador = new javax.swing.JLabel();
        PanelCpu = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTempo = new javax.swing.JLabel();
        lblUso = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblFrequencia = new javax.swing.JLabel();
        statusCpu = new javax.swing.JPanel();
        fraseCpu = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        PanelRam = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblRamUso = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblTotalRam = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblRamDisponivel = new javax.swing.JLabel();
        statusRam = new javax.swing.JPanel();
        fraseRam = new javax.swing.JLabel();
        PanelProcessos = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listProcesso = new javax.swing.JTextArea();
        PanelDisco = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblDiscoUso = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblTotalDisco = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblDiscoDisponivel = new javax.swing.JLabel();
        statusDisco = new javax.swing.JPanel();
        fraseDisco = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnAjuda = new javax.swing.JButton();
        cronometro = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblColaborador.setBackground(new java.awt.Color(121, 63, 255));
        lblColaborador.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblColaborador.setForeground(new java.awt.Color(121, 63, 255));

        PanelCpu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(121, 63, 255)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("CPU: ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Em uso:");

        lbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbl.setText("Velocidade Base");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Tempo atividade");

        lblTempo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTempo.setText("00:00:00");

        lblUso.setText("0,0");

        jLabel17.setText("%");

        lblFrequencia.setText("0.0");

        statusCpu.setBackground(new java.awt.Color(153, 255, 102));

        javax.swing.GroupLayout statusCpuLayout = new javax.swing.GroupLayout(statusCpu);
        statusCpu.setLayout(statusCpuLayout);
        statusCpuLayout.setHorizontalGroup(
            statusCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        statusCpuLayout.setVerticalGroup(
            statusCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        fraseCpu.setText("Bom estado!");

        jLabel19.setText("%");

        javax.swing.GroupLayout PanelCpuLayout = new javax.swing.GroupLayout(PanelCpu);
        PanelCpu.setLayout(PanelCpuLayout);
        PanelCpuLayout.setHorizontalGroup(
            PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCpuLayout.createSequentialGroup()
                .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCpuLayout.createSequentialGroup()
                        .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelCpuLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCpuLayout.createSequentialGroup()
                                        .addComponent(lblUso)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel17))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCpuLayout.createSequentialGroup()
                                        .addComponent(lblFrequencia, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel19)))
                                .addGap(8, 8, 8))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelCpuLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelCpuLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(lblTempo))
                                    .addComponent(jLabel9))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelCpuLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fraseCpu)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(PanelCpuLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)))
                .addComponent(statusCpu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelCpuLayout.setVerticalGroup(
            PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCpuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fraseCpu))
                    .addComponent(statusCpu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblUso)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCpuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFrequencia)
                    .addComponent(lbl)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTempo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelRam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(121, 63, 255)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("RAM: ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Em uso: ");

        lblRamUso.setText("0,0");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Total:");

        lblTotalRam.setText("0,0");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Disponível:");

        lblRamDisponivel.setText("0,0");

        statusRam.setBackground(new java.awt.Color(153, 255, 102));

        javax.swing.GroupLayout statusRamLayout = new javax.swing.GroupLayout(statusRam);
        statusRam.setLayout(statusRamLayout);
        statusRamLayout.setHorizontalGroup(
            statusRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        statusRamLayout.setVerticalGroup(
            statusRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        fraseRam.setText("Bom estado!");

        javax.swing.GroupLayout PanelRamLayout = new javax.swing.GroupLayout(PanelRam);
        PanelRam.setLayout(PanelRamLayout);
        PanelRamLayout.setHorizontalGroup(
            PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelRamLayout.createSequentialGroup()
                        .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelRamLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fraseRam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statusRam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelRamLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelRamLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelRamLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRamUso))
                            .addGroup(PanelRamLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRamDisponivel)
                                    .addComponent(lblTotalRam))))
                        .addGap(99, 99, 99))))
        );
        PanelRamLayout.setVerticalGroup(
            PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusRam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fraseRam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(28, 28, 28)
                .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRamUso)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalRam))
                .addGap(27, 27, 27)
                .addGroup(PanelRamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRamDisponivel))
                .addContainerGap())
        );

        PanelProcessos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(121, 63, 255)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Processos:");

        listProcesso.setColumns(20);
        listProcesso.setRows(5);
        jScrollPane1.setViewportView(listProcesso);

        javax.swing.GroupLayout PanelProcessosLayout = new javax.swing.GroupLayout(PanelProcessos);
        PanelProcessos.setLayout(PanelProcessosLayout);
        PanelProcessosLayout.setHorizontalGroup(
            PanelProcessosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProcessosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelProcessosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelProcessosLayout.setVerticalGroup(
            PanelProcessosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProcessosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        PanelDisco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(121, 63, 255)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Disco:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Em uso: ");
        jLabel11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblDiscoUso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDiscoUso.setText("0,0");
        lblDiscoUso.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Total:");
        jLabel18.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblTotalDisco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalDisco.setText("0,0");
        lblTotalDisco.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("Disponível:");
        jLabel21.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblDiscoDisponivel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDiscoDisponivel.setText("0,0");
        lblDiscoDisponivel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        statusDisco.setBackground(new java.awt.Color(153, 255, 102));

        javax.swing.GroupLayout statusDiscoLayout = new javax.swing.GroupLayout(statusDisco);
        statusDisco.setLayout(statusDiscoLayout);
        statusDiscoLayout.setHorizontalGroup(
            statusDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        statusDiscoLayout.setVerticalGroup(
            statusDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        fraseDisco.setText("Bom estado!");

        jLabel8.setText("GiB");

        jLabel10.setText("GiB");

        javax.swing.GroupLayout PanelDiscoLayout = new javax.swing.GroupLayout(PanelDisco);
        PanelDisco.setLayout(PanelDiscoLayout);
        PanelDiscoLayout.setHorizontalGroup(
            PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDiscoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDiscoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fraseDisco))
                    .addGroup(PanelDiscoLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(6, 6, 6)
                        .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDiscoDisponivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(PanelDiscoLayout.createSequentialGroup()
                                .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel11))
                                .addGap(6, 6, 6)
                                .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblTotalDisco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDiscoUso, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10))
                                .addGap(0, 4, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusDisco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelDiscoLayout.setVerticalGroup(
            PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDiscoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDiscoLayout.createSequentialGroup()
                        .addComponent(statusDisco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelDiscoLayout.createSequentialGroup()
                        .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fraseDisco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)))
                .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelDiscoLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDiscoUso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(lblTotalDisco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(PanelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiscoDisponivel))
                .addContainerGap())
        );

        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnAjuda.setText("Precisa de ajuda?");
        btnAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjudaActionPerformed(evt);
            }
        });

        cronometro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cronometro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cronometro.setText("0.0.0");

        jLabel1.setText("Tempo de Sessão:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PanelCpu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PanelProcessos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PanelRam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PanelDisco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnAjuda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cronometro)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelCpu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelRam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelDisco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelProcessos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAjuda)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudaActionPerformed
        JSONObject json = new JSONObject();
        Slack slack = new Slack();

        try {
            json.put("text", "@channel :robot_face: " + colaborador + " precisa da sua ajuda! \n "
                    + log.pegarLog());
        } catch (IOException ex) {
            Logger.getLogger(Computador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            slack.enviarMensagem(json);
        } catch (IOException ex) {
            try {
                log.logar("Erro ao enviar solicitação pro Slack: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(Computador.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (InterruptedException ex) {
            try {
                log.logar("Erro ao enviar solicitação pro Slack: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(Computador.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }//GEN-LAST:event_btnAjudaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        // Aqui Damos o LogOff da telaPrincipal e ativamos o Login
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Computador(colaborador).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Computador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCpu;
    private javax.swing.JPanel PanelDisco;
    private javax.swing.JPanel PanelProcessos;
    private javax.swing.JPanel PanelRam;
    private javax.swing.JButton btnAjuda;
    private javax.swing.JLabel cronometro;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel fraseCpu;
    private javax.swing.JLabel fraseDisco;
    private javax.swing.JLabel fraseRam;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JLabel lbl;
    public javax.swing.JLabel lblColaborador;
    private javax.swing.JLabel lblDiscoDisponivel;
    private javax.swing.JLabel lblDiscoUso;
    private javax.swing.JLabel lblFrequencia;
    private javax.swing.JLabel lblRamDisponivel;
    private javax.swing.JLabel lblRamUso;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JLabel lblTotalDisco;
    private javax.swing.JLabel lblTotalRam;
    private javax.swing.JLabel lblUso;
    private javax.swing.JTextArea listProcesso;
    private javax.swing.JPanel statusCpu;
    private javax.swing.JPanel statusDisco;
    private javax.swing.JPanel statusRam;
    // End of variables declaration//GEN-END:variables

}
