module.exports = {
    production: {
        username: 'quality-sistem',
        password: '#Gfgrupo7',
        database: 'quality-sistem',
        host: 'quality-sistem.database.windows.net',
        dialect: 'mssql',
        xuse_env_variable: 'quality-sistem',
        dialectOptions: {
            options: {
                encrypt: true
            }
        },
        pool: {
            max: 5,
            min: 1,
            acquire: 5000,
            idle: 30000,
            connectTimeout: 5000
        }
    }
};