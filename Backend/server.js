var port = 8080;
var express = require('express');
var mysql = require('mysql');
var bcrypt = require('bcryptjs');
var crypto = require('crypto');
var app = express();

//Possibly use a connection pool instead.
var db = mysql.createConnection({
  host     : 'ec2-54-200-98-161.us-west-2.compute.amazonaws.com',
  database : 'app',
  user     : 'app',
  password : 'u5zwwmGh8MVzanp!'
});

db.connect();

app.configure(function() {
	//app.use(express.bodyParser())
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

    var username = request.body.username;
    var password = request.body.password;

    db.query("SELECT UserId, Password, Salt FROM Users WHERE UserName = ?", username, function(err, rows, fields) {

    	if(err) {

    		console.log(err);
    		response.send(500, {error: "An error has occured."});

    	} else {

    		if(rows.length === 0) {
    			response.send(500, {error: "Invalid username or password."});
    		} else {
    			bcrypt.hash(password, rows[0].Salt, function(err, hash) {

    				if(err) {
    					console.log(err);
    					response.send(500, {error: "An error has occured."});
    				} else {
    					if(hash != rows[0].Password) {
							response.send(500, {error: "Invalid username or password."});
    					} else {
			        		var key = generateSessionKey(username);
			        		var session = {SessionKey: key, UserId: rows[0].UserId, DateTimeIn: Date.now()};

			        		db.query("INSERT INTO UsersSessions SET ?", session, function(err, result) {

			        			if(err) {
			        				console.log(err);
			        				response.send(500, {error:'An error has occured.'});
			        			} else {
			        				console.log("Session created: " + key);
			        				response.send(200, {key: key});
			        			}

			        		});
    					}
    				}

    			});
    		}

    	}


    });

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

app.get('/verify', function (request, response) {

	var key = request.headers['authorization'];

	if(typeof key != 'undefined' && key != null)
		verifySessionKey(key, function(valid) {
			response.send(200, {valid:valid});
		});

});

app.post('/register', function (request, response) {

    var username = request.body.username;
    var password = request.body.password;

	bcrypt.genSalt(10, function(err, salt) {

	    bcrypt.hash(password, salt, function(err2, hash) {
	        // Store user/hash/salt
	        //console.log(password + " -> " + hash + " : " + salt);
	        var user = {UserName: username, Password: hash, Salt: salt, RoleId: 4, Email:'', FirstName: '', LastName: ''};

	        if(err || err2) {
				console.log(err);
				console.log(err2);
	        	response.send(500, {error:'An error has occured.'});

	        } else {

	        	db.query("INSERT INTO Users SET ?", user, function(err, result) {

		        	if(err) {
		        		console.log(err);
		        		response.send(500, {error:'An error has occured.'});
		        	} else {
		        		//Generate the session key
		        		var key = generateSessionKey(username);
		        		var session = {SessionKey: key, UserId: result.insertId, DateTimeIn: Date.now()};

		        		db.query("INSERT INTO UsersSessions SET ?", session, function(err, result) {

		        			if(err) {
		        				console.log(err);
		        				response.send(500, {error:'An error has occured.'});
		        			} else {
		        				console.log("Session created: " + key);
		        				response.send(200, {key: key});
		        			}

		        		});

		        	}

		        }); // query insert user

	        }


	    }); //hash


	}); //genSalt


}); //app.post


app.listen(port, function() {
    console.log("Server listening on port " + port);
});


function generateSessionKey(username) {

	var sha = crypto.createHash('sha1');

	sha.update(username + Date.now() + Math.random());
	return sha.digest('hex');
}

function verifySessionKey(key, callback) {

	db.query("SELECT SessionId FROM UsersSessions WHERE SessionKey = ?", key, function(err, rows, fields) {

		if(err || rows.length === 0) {
			console.log(err);
			callback(false); 
		} else {
			callback(true);
		}

	});

}
