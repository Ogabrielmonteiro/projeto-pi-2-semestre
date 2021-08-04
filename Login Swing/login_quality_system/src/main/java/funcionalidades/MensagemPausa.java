package funcionalidades;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class MensagemPausa {

    private String descricao = "";
    private Integer idPausa = 1;
    private Integer qtdPausasFeitas = 0, qtdPausasAdiadas = 0;


    public String gerarMensagem() {
        if (idPausa > 4) {
            idPausa = 1;
        }
        if (idPausa == 1) {
            descricao = "Hidratar faz bem! Beba água";
        } else if (idPausa == 2) {
            descricao = "Alivie a tensão muscular! Alongue-se";
        } else if (idPausa == 3) {
            descricao = "Que tal uma pausa para o café ou chá?";
        } else if (idPausa == 4) {
            descricao = "Respire um ar fresco! Faça uma caminhada curta";
        }
        return descricao;
    }

    public Integer getIdPausa() {
        return idPausa;
    }

    public void setIdPausa(Integer idPausa) {
        this.idPausa = idPausa;
    }

    public Integer getQtdPausasFeitas() {
        return qtdPausasFeitas;
    }

    public void setQtdPausasFeitas(Integer qtdPausasFeitas) {
        this.qtdPausasFeitas = qtdPausasFeitas;
    }

    public Integer getQtdPausasAdiadas() {
        return qtdPausasAdiadas;
    }

    public void setQtdPausasAdiadas(Integer qtdPausasAdiadas) {
        this.qtdPausasAdiadas = qtdPausasAdiadas;
    }
}
