'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let registro = sequelize.define('registro',{	
		idRegistro: {
			field: 'id',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},	
		cpu: {
			field: 'cpu',
			type: DataTypes.STRING,
			allowNull: false
		},
		disco: {
			field: 'disco',
			type: DataTypes.INTEGER, // NÃO existe DATETIME. O tipo DATE aqui já tem data e hora
			allowNull: false
		},
		memoria: {
			field: 'RAM',
			type: DataTypes.INTEGER, // campo 'falso' (não existe na tabela). Deverá ser preenchido 'manualmente' no select
			allowNull: true
		},
		dataHora: {
			field: 'data/hora',
			type: DataTypes.DATE, // campo 'falso' (não existe na tabela). Deverá ser preenchido 'manualmente' no select
			allowNull: true
		}
	},

	{
		tableName: 'registro', 
		freezeTableName: true,
		underscored: true,
		timestamps: false,
	});

    return registro;
};
