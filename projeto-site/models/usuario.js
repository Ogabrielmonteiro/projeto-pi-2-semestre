	'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let Usuario = sequelize.define('Usuario',{
		id: {
			field: 'idColaborador',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nome: {
			field: 'nomeColaborador',
			type: DataTypes.STRING,
			allowNull: false
		},
		cpf: {
			field: 'cpfColaborador',
			type: DataTypes.INTEGER,
			allowNull: false
		},
		login: {
			field: 'loginColaborador',
			type: DataTypes.STRING,
			allowNull: false
		},
		senha: {
			field: 'senhaColaborador',
			type: DataTypes.STRING,
			allowNull: false
		},
		idCargo: {
			field: 'idCargo',
			type: DataTypes.INTEGER,
			allowNull: false
		},
		idEmpresa: {
			field: 'idEmpresa',
			type: DataTypes.INTEGER,
			allowNull: false
		}
	}, 
	{
		tableName: 'colaborador', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Usuario;
};
