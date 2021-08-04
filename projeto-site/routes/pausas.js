var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var pausa = require('../models').pausa;

router.get('/colaboradores', (req, res, next) => {
    console.log('recuperando colaboradores');

    let instrucaoSql = 'select * from colaborador';

    sequelize.query(instrucaoSql, {
		model: pausa,
		mapToModel: true
	  }).then(resultado => {
			res.json(resultado);
	  }).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
	  });
})

router.get('/colaboradores/:idColaborador/:dataPausa', (req, res, next) => {
    console.log('recuperando colaboradores');
	const idColaborador = req.params.idColaborador;
	const dataPausa = req.params.dataPausa;
	console.log(idColaborador);

    let instrucaoSql = `select marcaPausa.fkColaborador, marcaPausa.descricao, marcaPausa.horaPausa,
							colaborador.nomeColaborador from marcaPausa, colaborador 
							where marcaPausa.fkColaborador = colaborador.idColaborador and 
							marcaPausa.dataPausa = '${dataPausa}' and
							colaborador.idColaborador = '${idColaborador}'`;
    sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
	.then(resultado => {
		  res.json(resultado);
	  }).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
	  });
})

module.exports = router;