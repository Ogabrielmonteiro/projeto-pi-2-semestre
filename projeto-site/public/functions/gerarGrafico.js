let proximaAtualizacao;

// window.onload = obterDadosGraficoPrimeiraVez(1);


// function chamargraficos(idRegistro) {

//     obterDadosGraficoPrimeiraVez(idRegistro)
//     atualizarGrafico(idRegistro)

// }

// verificar_autenticacao();

// neste JSON tem que ser 'labels', 'datasets' etc, 
// porque é o padrão do Chart.js

// altere aqui como os dados serão exibidos
// e como são recuperados do BackEnd
function obterDadosGraficoPrimeiraVez(idRegistro) {
    document.getElementById('dash').classList.remove('display-none');
    if (proximaAtualizacao != undefined) {
        clearTimeout(proximaAtualizacao);
    }

    fetch(`/leituras/ultimas/${idRegistro}`, {
        cache: 'no-store'
    }).then(function (response) {
        if (response.ok) {
            response.json().then(function (resposta) {
                resposta.reverse();

                // esse metodo irá receber a resposta retornada da requisição
                plotarGrafico(resposta, idRegistro);

                setTimeout(() => atualizarGrafico(idRegistro, resposta), 3000);
            });
        } else {
            console.error('Nenhum dado encontrado ou erro na API');
        }
    })
        .catch(function (error) {
            console.error(`Erro na obtenção dos dados p/ gráfico: ${error.message}`);
        });
}



// só mexer se quiser alterar o tempo de atualização
// ou se souber o que está fazendo!
function atualizarGrafico(idRegistro, dados) {

    fetch(`/leituras/tempo-real`, {
        cache: 'no-store'
    }).then(function (response) {
        if (response.ok) {
            response.json().then(function (novoRegistro) {


                // tirando e colocando valores no gráfico
                // dados.shift(); // apagar o primeiro
                // dados.push(novoRegistro.momento); // incluir um novo momento
                dados.shift(); // apagar o primeiro de temperatura
                // dados.datasets[1].data.shift();  // apagar o primeiro de umidade
                dados.push(novoRegistro); // incluir uma nova leitura de temperatura
                // dados.datasets[1].data.push(novoRegistro.umidade); // incluir uma nova leitura de umidade
                
                plotarGrafico(dados, 1);
                
                setTimeout(() => atualizarGrafico(idRegistro, dados), 3000);
            });
        } else {
            console.error('Nenhum dado encontrado ou erro na API');
            proximaAtualizacao = setTimeout(() => atualizarGrafico(idRegistro, dados), 3000);
        }
    })
        .catch(function (error) {
            console.error(`Erro na obtenção dos dados p/ gráfico: ${error.message}`);
        });
}

// só altere aqui se souber o que está fazendo!
function plotarGrafico(dados, idRegistro) {

    // basicamente aqui ele irá pegar o contexto de todos os graficos para mais tarde implementar aparencia e informações nele
    let ctxCpu = document.getElementById('canvas_grafico_cpu')
    let ctxRam = document.getElementById('canvas_grafico_ram')
    let ctxDisco = document.getElementById('canvas_grafico_disco')

    // aqui serão os vetores que irão guardar as informações especificas de cada componente
    let dadosCpu = []
    let dadosRam = []
    let dadosDisco = []
    let dadosMomento = []

    console.log(dados);

    for (i = 0; i < dados.length; i++) {
        var registro = dados[i];

        dadosCpu.push(parseFloat(registro.cpu1))
        dadosRam.push((parseFloat(registro.memoria) / 100000000).toFixed(1))
        dadosDisco.push((parseFloat(registro.disco) / 1000000000).toFixed(1))
        dadosMomento.push(registro.momento_grafico)
    }


    criarGrafico(ctxCpu, 'CPU', dadosCpu, dadosMomento);
    criarGrafico(ctxDisco, 'DISCO', dadosDisco, dadosMomento);
    criarGrafico(ctxRam, 'RAM', dadosRam, dadosMomento);
}

// esse metodo será o que irá "criar" os graficos
// contexto: será o canvas pega utilizando o id do grafico especifico
// nome: será o nome do grafico
// dados: serão os dados especificos de cada grafico
// momento: será os dados do momento
function criarGrafico(contexto, nome, dados, momento) {
    let grafico = new Chart(contexto, {
        type: 'line',
        data: {
            labels: momento,
            datasets: [{
                label: nome,
                borderColor: 'rgb(118, 0, 113)',
                backgroundColor: 'rgb(255, 99, 132)',
                fill: false,
                data: dados
            },]
        }
    })
}