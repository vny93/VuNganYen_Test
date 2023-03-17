var mysql = require('mysql')
var connection = mysql.createConnection({
    host : 'localhost',
    user:'admin',
    password: 'Nganyen1234@',
    database: 'mobilejokes'
})

connection.connect(function (err){
    if(err)
    {
        throw err.stack;
    }
    else
    console.log("connect success");
 })

module.exports = connection