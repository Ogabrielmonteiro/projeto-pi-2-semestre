
    function change_integrantes() {
        div_sobre.style.display = 'none';
        div_integrantes.style.display = 'block';
        document.getElementById("li_integrantes").className = "current";
        document.getElementById("li_sobre").className = 'link_underscore';
    }
    function change_sobre() {
        div_sobre.style.display = 'block';
        div_integrantes.style.display = 'none';
        document.getElementById("li_integrantes").className = "link_underscore";
        document.getElementById("li_sobre").className = 'current';
    }
    function change_automatizacao() {
        //MUDAR O CSS
        document.getElementById("li_automatizacao").className = "current";
        document.getElementById("li_suporte").className = "link_underscore";
        document.getElementById("li_qualidade").className = 'link_underscore';
        // MUDAR O TEXTO DA TAG
        text_automatizacao.innerHTML = 'Nosso sistema tem a implatação da automatização de batida de ponto para facilitar a vida do seu colaborador com maior comodidade e menor preocupação com seus prontuários.';
        // MUDAR O ICONE
        const icone = document.querySelectorAll('.icon');
        icone.forEach(icon => {
            icon.src = './img/icon/icon1.png'
            console.log('teste')
        })
        //MUDAR O TEXTO DO ICONE
        text_icon1.innerHTML = 'Entrada e saída de expediente';
        text_icon2.innerHTML = 'Calculo de horas extras';
        text_icon3.innerHTML = 'Relatório mensal';

    }
    function change_suporte() {
        document.getElementById("li_automatizacao").className = "link_underscore";
        document.getElementById("li_suporte").className = "current";
        document.getElementById("li_qualidade").className = 'link_underscore';

        text_automatizacao.innerHTML = 'A Quality System oferece uma solução de suporte remoto para seus colaborades afim de resolver eventuais problemas diários.';



        const icone = document.querySelectorAll('.icon');
        icone.forEach(icon => {
            icon.src = './img/icon/icon2.png'
            console.log('teste')
        })
        //MUDAR O TEXTO DO ICONE
        text_icon1.innerHTML = 'Suporte remoto';
        text_icon2.innerHTML = 'Prevenção';
        text_icon3.innerHTML = 'Monitoramento';
    }
    function change_qualidade() {
        document.getElementById("li_suporte").className = "link_underscore";
        document.getElementById("li_qualidade").className = 'current';

        text_automatizacao.innerHTML = 'Com o sistema Quality Sytem seu colaborador poderá realizar seus serviços e tarefas sem deixar a saúde e o bem estar de lado, podendo assim aumentar sua produtividade.';

        const icone = document.querySelectorAll('.icon');
        icone.forEach(icon => {
            icon.src = './img/icon/icon3.png'
            console.log('teste')
        })
        //MUDAR O TEXTO DO ICONE
        text_icon1.innerHTML = 'Alertas de pausas em suas tarefas';
        text_icon2.innerHTML = 'Aviso para realizar exercicios e alongamentos';
        text_icon3.innerHTML = 'Relatorio de uso diario de aplicativos';
    }
