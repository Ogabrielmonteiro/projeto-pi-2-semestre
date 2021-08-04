const contentSolucoes = document.querySelector(".content-sobre");
var solucoesIntegrante;
var asideIntegrantes = document.querySelectorAll(".aside-item")
const headerSobre = document.querySelector("#header-sobre");
const headerIntegrantes = document.querySelector("#header-integrantes");

const setIntegranteImageAndDescription = (id) => {
    solucoesIntegrante.innerHTML = `
        <img src="${integrantesList[id].path}" alt="">
        <div>
            <h1 id="h1-integrantes">${integrantesList[id].name}</h1>
            <h2 id="h2-integrantes">Empresa: ${integrantesList[id].empresa}</h2>
        </div>
    `;

    changeAsideItemCurrent(id);
}

const integrantesList = [
    {
        id: 0,
        name: "Carlos Gomes da Silva",
        empresa: "Linx",
        path: "img/carlos.jpg"
    },
    {
        id: 1,
        name: "Felipe Olegario",
        empresa: "Linx",
        path: "img/felipe.jpg"
    },
    {
        id: 2,
        name: "Gabriel Nicodemos",
        empresa: "Stefanini",
        path: "img/gabriel.jpg"
    },
    {
        id: 3,
        name: "Gabriel Monteiro",
        empresa: "Stefanini",
        path: "img/monteiro.jpeg"
    },
    {
        id: 4,
        name: "Ítalo de Souza",
        empresa: "Stefanini",
        path: "img/italo.jpeg"
    },
    {
        id: 5,
        name: "Nicolas Ruiz",
        empresa: "Stefanini",
        path: "img/nicolas.jpg"
    },
]

const setSobreOnContent = () => {
    contentSolucoes.innerHTML = `<p>
    Nossa Empresa busca uma inovação nos métodos de trabalho HomeOffice. Devido a essa
    nova realidade que estamos vivenciando, muitas empresas se adaptaram para o modelo de trabalho em casa, e com isso  acabam se esquecendo da qualidade de vida do seu colaborador, pois o pensamento de que o fato de não estar presencial e 
    não haver locomoção diária, a produtividade aumenta, porém não é exatamente assim.
    <br><br>
    A Quality System vem com um sistema implantado ao HomeOffice, onde monitora o hardware do computador do usuário, fornecendo maior facilidade em seus problemas diários, melhorando sua qualidade de vida e aumento a produtividade do seu funcionário.
        </p>`;

    headerSobre.classList.value = "current-sobre";
    headerIntegrantes.classList.value = "";
}

const setIntegrantesOnContent = () => {
    contentSolucoes.innerHTML = `<aside>
                    <ul>
                        <li class="aside-item" onClick="setIntegranteImageAndDescription(0)">
                            Carlos Gomes
                        </li>
                        <li class="aside-item" onClick="setIntegranteImageAndDescription(1)">
                            Felipe Olegario
                        </li>
                        <li class="aside-item" onClick="setIntegranteImageAndDescription(2)">
                            Gabriel Nicodemos
                        </li>
                        <li class="aside-item" onClick="setIntegranteImageAndDescription(3)">
                            Gabriel Monteiro
                        </li>
                        <li class="aside-item" onClick="setIntegranteImageAndDescription(4)">
                            Ítalo de Souza
                        </li>
                        <li class="aside-item" onClick="setIntegranteImageAndDescription(5)">
                            Nicolas Ruiz
                        </li>
                    </ul>
                </aside>

                <div class="sobre-integrante">
                    <img src="img/carlos.jpg" alt="">
                    <div>
                        <h1 id="h1-integrantes">Carlos Gomes da Silva</h1>
                        <h2 id="h2-integrantes">Empresa: Linx</h2>
                    </div>
                </div>`

        headerIntegrantes.classList.value = "current-sobre";
        headerSobre.classList.value = "";
        solucoesIntegrante = document.querySelector(".sobre-integrante");
        asideIntegrantes = document.querySelectorAll(".aside-item")
        asideIntegrantes[0].classList.value = "aside-item active";
}

setSobreOnContent();

const changeAsideItemCurrent = (id) => {

    for(var i = 0; i < 6; i++) {
        if (i == id) {
            asideIntegrantes[i].classList.value = "aside-item active";
        } else {
            asideIntegrantes[i].classList.value = "aside-item";
        }
    }

}

const changeHeader = (id) => {
    if (id == 1) {
        setSobreOnContent();
    } else {
        setIntegrantesOnContent();
    }
}

