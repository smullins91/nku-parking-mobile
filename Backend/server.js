var port = 8080;
var express = require('express')
var mysql = require('mysql')
var app = express();

var db = mysql.createConnection({
  host     : 'localhost',
  database : 'app',
  user     : 'app',
  password : 'u5zwwmGh8MVzanp!'
});

db.connect();

app.configure(function() {
    app.use(express.json());
    app.use(express.urlencoded());
});

/**
 * HTTP GET /
 * Returns:
 */
app.get('/', function (request, response) {
    //response.json({test:'test'});
    response.send("Hello world!");
});

/**
 * HTTP POST /login
 * Returns: A session key if successful, or an error message.
 */
app.post('/login', function (request, response) {
	//request.headers['authorization']
    //response.json({test:'test'});

    var user = request.body.user;
    var password = request.body.password;

    //To-do: hash password and check database

    response.send(200, {key:'0'});
});

/**
 * HTTP GET /lots
 * Returns: A list of all the lots
 */
app.get('/lots', function (request, response) {

	var key = request.headers['authorization'];

	//to-do: Verify the session key is valid, and if so, return a list of all lots. Otherwise, 403.


	db.query("SELECT * FROM ParkingLots", function(err, rows, fields) {

		if(err) {

			console.log(err);
			response.send(500, {error: "An error has occured."});

		} else {

			var lots = [];

			for(var i = 0; i < rows.length; i++) {
				lots.push( { lotNumber: rows[i].LotNumber } );
			}

			response.send(200, {lots: lots});
		}

	});

});


/**
 * HTTP GET /lot
 * Returns: Information about a particular lot, such as all the spaces.
 */
app.get('/lot/:id', function (request, response) {

	var key = request.headers['authorization'];

	//to-do: Verify the session key is valid, and if so, return lot info. Otherwise, 403.

    response.send(200, {lot:{}});
});


/**
 * HTTP GET /space
 * Returns: Information about a particular space
 */
app.get('/space/:id', function (request, response) {

	var key = request.headers['authorization'];

	//to-do: Verify the session key is valid, and if so, return space info. Otherwise, 403.

    response.send(200, {space:{}});
});


/**
 * HTTP GET /space
 * Returns: Information about a particular space
 */
app.get('/space/:id', function (request, response) {

	var key = request.headers['authorization'];

	//to-do: Verify the session key is valid, and if so, return space info. Otherwise, 403.

    response.send(200, {space:{}});
});

/**
 * HTTP POST /space
 * Returns: Space status and possibly an error if space it not available.
 */
app.post('/space', function (request, response) {

	var key = request.headers['authorization'];
	var id = request.body.spaceId;

	//to-do: Verify the session key is valid, and if so, update space. Otherwise, 403.

    response.send(200, {space:{}});
});


app.listen(port, function() {
    console.log("Server listening on port " + port);
});
