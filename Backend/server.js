var port = 8080;
var express = require('express');
var mysql = require('mysql');
var bcrypt = require('bcryptjs');
var crypto = require('crypto');
var https = require("https");
var qs = require('querystring');
var app = express();

var db = mysql.createPool({
  host     : 'ec2-54-200-98-161.us-west-2.compute.amazonaws.com',
  database : 'app',
  user     : 'app',
  password : 'u5zwwmGh8MVzanp!'
});

app.configure(function() {
	//app.use(express.bodyParser())
    app.use(express.json());
    app.use(express.urlencoded());
});

process.on('uncaughtException', function(err) {
    console.log(err);
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

    db.query("SELECT UserId, Password, Salt FROM Users WHERE UserName = ? AND Active = 1", username, function(err, rows, fields) {

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
 * HTTP POST /login-nku
 * Returns: A session key if successful, or an error message.
 */
app.post('/login-nku', function (request, response) {

    var username = request.body.username;
    var password = request.body.password;

    //var headers = {'Host': 'https://mobilefeeds.nku.edu', 'Connection': 'close', 'Content-Type': 'application/x-www-form-urlencoded'};


	var postData = qs.stringify({
	  'user': username,
	  'pass': password,
	  'enc': 'JSON',
	  'action': 'authenticate',
	  'tokenDuration': '600'
	});

    var options = {
		hostname: 'mobilefeeds.nku.edu',
		port: 443,
		path: '/request_auth_hash.php',
		method: 'POST',
		headers: {
			'Connection': 'close', 
			'Content-Type': 'application/x-www-form-urlencoded',
			'Content-Length': postData.length
		}
	};

    var body = "";
    var req = https.request(options, function(resp) {


	    resp.on('data', function (chunk) {
	        body += chunk;
	    });

	    resp.on('end',function() {
	        //Do Something with the data

	        try {

	        	body = JSON.parse(body);
		        if(typeof body.authData != 'undefined' && body.authData != null) {
		        	response.send(200, {key: '000'});
		        } else {
		        	response.send(500, {error: "Invalid username or password."});
		        }
	    	} catch(err) {
	    		response.send(500, {error: "Invalid username or password."});
	    	}



	    });

	});

	req.on('error', function(e) {
	  console.log('Request error: ' + e.message);
	  console.log(postData);
	});

	req.write(postData);
	req.end();

});



/**
 * HTTP GET /lots
 * Returns: A list of all the lots
 */
app.get('/lots', function (request, response) {

	
	verifySessionKey(request, function(valid) {

		if(!valid) {

			response.send(403, {error: "Your session has expired. Please login."});

		} else {

			db.query('SELECT ParkingLots.*, concat("[", group_concat(concat("{""lat"":", Latitude, ",""lng"":", Longitude, "}")), "]") AS points FROM ParkingLots' 
				+ ' LEFT JOIN ParkingCoordinates ON ParkingLots.LotId = ParkingCoordinates.LotId GROUP BY ParkingLots.LotId;', function(err, rows, fields) {

				if(err) {
					
					console.log(err);
					response.send(500, {error: "An error has occured."});

				} else {

					var lots = [];

					for(var i = 0; i < rows.length; i++) {
						lots.push( { lotNumber: rows[i].LotNumber, type: rows[i].TypeId, active: rows[i].Active[0], points: JSON.parse(rows[i].points) } );
					}

					response.json(lots);
				}

			});

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
 * HTTP POST /space
 * Returns: Space status and possibly an error if space it not available.
 */
app.post('/space', function (request, response) {

	var key = request.headers['authorization'];
	var id = request.body.spaceId;

	//to-do: Verify the session key is valid, and if so, update space. Otherwise, 403.

    response.send(200, {space:{}});
});

/**
 * HTTP GET /verify
 * Returns: A boolean that tells whether or not a session key is valid.
 */
app.get('/verify', function (request, response) {

	verifySessionKey(request, function(valid) {
		response.send(200, {valid:valid});
	});

});


/**
 * HTTP POST /register
 * Returns: A session key if successful.
 */
app.post('/register', function (request, response) {

    var username = request.body.username;
    var password = request.body.password;

    //To-do: Validate username and password.

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


/**
 * HTTP DELETE /users/ID
 * Returns: A message if successful.
 */
app.delete('/users/:id', function (request, response) {

	verifyAdminSession(request, function (valid) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var id = request.params.id;

			db.query("UPDATE Users SET Active = 0 WHERE UserId = ? LIMIT 1", id, function(err) {

				if(err) {
					console.log(err);
					response.send(500, {error: "An error has occured."});
				} else {
					response.send(200, {message: "User has been deleted."});
				}		

			});


		}

	});

});


/**
 * HTTP POST /users/ID
 * Returns: A message if successful.
 */
app.post('/users/:id', function (request, response) {

	verifyAdminSession(request, function (valid) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var id = request.params.id;
			var json = request.body;

			delete json.UserId; //Make sure the User ID doesn't get updated.

			if(typeof json.Password !== 'undefined' && json.Password !== null && json.Password.length > 0) {

				bcrypt.genSalt(10, function(err, salt) {

		   			bcrypt.hash(json.Password, salt, function(err2, hash) {	

		   				if(err || err2) {
		   					console.log(err);
		   					console.log(err2);

		   					response.send(500, {error: "An error has occured."});

		   				} else {

			   				json.Password = hash;
			   				json.Salt = salt;

							db.query("UPDATE Users SET ? WHERE UserId = ? LIMIT 1", [json, id], function(err) {

								if(err) {
									console.log(err);
									response.send(500, {error: "An error has occured."});
								} else {
									response.send(200, {message: "User has been updated."});
								}		

							});

						}

		   			}); //hash

	   			});	//genSalt	

			} else {

				delete json.Password; //Make sure the password doesn't get set.
				delete json.Salt; //Make sure the salt doesn't get set.

				db.query("UPDATE Users SET ? WHERE UserId = ? LIMIT 1", [json, id], function(err) {

					if(err) {
						console.log(err);
						response.send(500, {error: "An error has occured."});
					} else {
						response.send(200, {message: "User has been updated."});
					}		

				});

			}


		}

	});

});


/**
 * HTTP POST /users
 * Returns: A message if successful.
 */
app.post('/users', function (request, response) {

	verifyAdminSession(request, function (valid) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var id = request.params.id;
			var json = request.body;

			delete json.UserId; //Make sure the User ID doesn't get set manually.

			if(typeof json.RoleId === "undefined")
				json.RoleId = 4;

			if(typeof json.Active === "undefined")
				json.Active = 1;

			if(typeof json.Password !== 'undefined' && json.Password !== null && json.Password.length > 0
				&& typeof json.UserName !== 'undefined' && json.UserName !== null && json.UserName.length > 0) {

				bcrypt.genSalt(10, function(err, salt) {

		   			bcrypt.hash(json.Password, salt, function(err2, hash) {	

		   				if(err || err2) {
		   					console.log(err);
		   					console.log(err2);

		   					response.send(500, {error: "An error has occured."});

		   				} else {

			   				json.Password = hash;
			   				json.Salt = salt;

							db.query("INSERT INTO Users SET ?", json, function(err) {

								if(err) {
									console.log(err);
									response.send(500, {error: "An error has occured."});
								} else {
									response.send(200, {message: "The user, " + json.UserName + ", has been added."});
								}		

							});

						}

		   			}); //hash

	   			});	//genSalt	

			} else {

				response.send(500, {error: "Please provide a username and password."});

			}


		}

	});

});


/**
 * HTTP GET /users
 * Returns: A list of all the users.
 */
app.get('/users', function (request, response) {

	verifyAdminSession(request, function (valid) {

		if(valid) {

			db.query("SELECT * FROM v_users", function(err, rows, fields) {

				if(err) {
					
					console.log(err);
					response.send(500, {error: "An error has occured."});

				} else {

					var users = [];

					for(var i = 0; i < rows.length; i++) {
						//users.push( { id: rows[i].UserId, username: rows[i].UserName, role: rows[i].RoleId, admin: rows[i].IsAdmin[0] } );
						users.push(rows[i]);
					}

					response.json(users);
				}

			});

		} else
			response.send(403, {error: "Access denied."});

	});

});


app.listen(port, function() {
    console.log("Server listening on port " + port);
});


function generateSessionKey(username) {

	var sha = crypto.createHash('sha1');

	sha.update(username + Date.now() + Math.random());
	return sha.digest('hex');
}

function verifySessionKey(request, callback) {

	var key = request.headers['authorization'];

	if(typeof key != 'undefined' && key != null && key.length > 0) {
		db.query("SELECT SessionId FROM UsersSessions JOIN Users ON UsersSessions.UserId = Users.UserId AND Active = 1 WHERE SessionKey = ?", key, function(err, rows, fields) {

			if(err || rows.length === 0) {
				callback(false); 
			} else {
				callback(true);
			}

		});

	} else
		callback(false);

}

function verifyAdminSession(request, callback) {

	var key = request.headers['authorization'];

	if(typeof key != 'undefined' && key != null && key.length > 0) {
		db.query("SELECT IsAdmin FROM UsersSessions JOIN Users ON UsersSessions.UserId = Users.UserId AND IsAdmin = 1 AND Active = 1 WHERE SessionKey = ?", key, function(err, rows, fields) {

			if(err || rows.length === 0) {
				callback(false); 
			} else {
				callback(true);
			}

		});

	} else
		callback(false);

}

