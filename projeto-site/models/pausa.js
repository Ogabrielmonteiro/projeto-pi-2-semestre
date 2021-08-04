'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let pausa = sequelize.define('pausa', {
        idPausas: {
            field: 'idPausas',
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true
        },
        pausa1: {
            field: 'pausa1',
            type: DataTypes.DATE,
            allowNull: false
        },
        pausa2: {
            field: 'pausa2',
            type: DataTypes.DATE,
            allowNull: false
        },
        pausa3: {
            field: 'pausa3',
            type: DataTypes.DATE,
            allowNull: false
        },
        pausa4: {
            field: 'pausa4',
            type: DataTypes.DATE,
            allowNull: false
        }
    },
        {
            tableName: 'pausas',
            freezeTableName: true,
            underscored: true,
            timestamps: false,
        });

    return pausa;
};
