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

    db.query("SELECT UserId, Password, Salt, RoleId FROM Users WHERE UserName = ? AND Active = 1", username, function(err, rows, fields) {

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
			        				//console.log("Session created: " + key);
			        				response.send(200, {key: key, type: parseInt(rows[0].RoleId, 10)});
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
 * HTTP GET /logout
 * Returns: A message if successful
 */
app.get('/logout', function (request, response) {

	verifySessionKey(request, function (valid, key) {

		if(valid) {

			db.query("DELETE FROM UsersSessions WHERE SessionKey = ? LIMIT 1", key, function(err, result) {

				if(err) {
					console.log(err);
					//response.send(500, {error: "An error has occured."});
				}

				response.send(200, {message: "You have been logged out."});

			});

		} else
			response.send(403, {error: "You are not logged in."});

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

			db.query('SELECT ParkingLots.*, Rows*Columns - COUNT(ParkingSpaces.SpaceId) AS Available, '
				+ 'concat("[", group_concat(concat("{""lat"":", Latitude, ",""lng"":", Longitude, "}")), "]") AS points FROM ParkingLots '
				+ 'LEFT JOIN ParkingCoordinates ON ParkingLots.LotId = ParkingCoordinates.LotId '
				+ 'LEFT JOIN ParkingSpaces ON ParkingSpaces.LotId = ParkingLots.LotId AND ParkingSpaces.StatusId > 1 GROUP BY ParkingLots.LotId', function(err, rows, fields) {

				if(err) {
					
					console.log(err);
					response.send(500, {error: "An error has occured."});

				} else {

					var lots = [];

					for(var i = 0; i < rows.length; i++) {
						lots.push( { id: rows[i].LotId, lotNumber: rows[i].LotNumber, 
							type: rows[i].TypeId, active: rows[i].Active[0], rows: rows[i].Rows,
							columns: rows[i].Columns, available: rows[i].Available, points: JSON.parse(rows[i].points) } );
					}

					response.json(lots);
				}

			});

		}

	});


});


/**
 * HTTP GET /lots
 * Returns: Information about a particular lot, such as all the spaces.
 */
app.get('/lots/:id', function (request, response) {

	var key = request.headers['authorization'];

	//to-do: Verify the session key is valid, and if so, return lot info. Otherwise, 403.

    response.send(200, {lot:{}});
});


/**
 * HTTP POST /lots
 * Returns: A message if successful
 */
app.post('/lots', function (request, response) {

	
	verifySessionKey(request, function(valid) {

		if(!valid) {

			response.send(403, {error: "You are not authorized to complete this request."});

		} else {

			var num = request.body.LotNumber;
			var type = request.body.TypeId;
			var rows = request.body.Rows;
			var columns = request.body.Columns;
			var active = request.body.Active;

			var lot = {LotNumber: num, TypeId: type, Rows: rows, Columns: columns, Active: active};

			db.query('INSERT INTO ParkingLots SET ?', lot, function(err, result) {

				if(err) {
					
					console.log(err);
					response.send(500, {error: "An error has occured."});

				} else {

					var id = result.insertId;
					var points = request.body.Points;

					for(var i = 0; i < points.length; i++)
						points[i].push(id);


					db.query('INSERT INTO ParkingCoordinates (Latitude, Longitude, LotId) VALUES ?', [points], function(err) { 

						if(err) {
							console.log(err);
							response.send(500, {error: "An error has occured."});
						} else
							response.send(200, {message: "Parking lot created!"});

					});

				}

			});

		}

	});


});

/**
 * HTTP DELETE /lots/ID
 * Returns: A message if successful.
 */
app.delete('/lots/:id', function (request, response) {

	verifyAdminSession(request, function (valid) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var id = request.params.id;

			db.query("DELETE FROM ParkingCoordinates WHERE LotId = ?", id, function(err) {

				if(err) {
					console.log(err);
					response.send(500, {error: "An error has occured."});
				} else {

					db.query("DELETE FROM ParkingLots WHERE LotId = ? LIMIT 1", id, function(err) {

						if(err) {
							console.log(err);
							response.send(500, {error: "An error has occured."});
						}
						else
							response.send(200, {message: "The lot has been deleted."});
					});

				}		

			});


		}

	});

});


/**
 * HTTP POST /lots/id
 * Returns: A message if successful
 */
app.post('/lots/:id', function (request, response) {

	verifyAdminSession(request, function (valid) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var id = request.params.id;
			var json = request.body;

			delete json.LotId; //Make sure the Lot ID doesn't get updated.

			db.query("UPDATE ParkingLots SET ? WHERE LotId = ? LIMIT 1", [json, id], function(err) {

				if(err) {
					console.log(err);
					response.send(500, {error: "An error has occured."});
				} else {
					response.send(200, {message: "The lot has been updated."});
				}		

			});

		}

	});

});


/**
 * HTTP GET /reservations
 * Returns: Information about all the reservations.
 */
app.get('/reservations', function (request, response) {

	verifyAdminSession(request, function (valid, key) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var lot = request.params.lot;

			db.query("SELECT * FROM v_user_parking", lot, function(err, rows, fields) {

				if(err) {
					console.log(err);
					response.send(500, {error: "An error has occured."});
				} else
					response.json(rows);

			});

		}

	});

});


/**
 * HTTP GET /status
 * Returns: Information about the logged-in user.
 */
app.get('/status', function (request, response) {

	verifySessionKey(request, function (valid, key, user) {

		if(!valid) {
			response.send(403, {error: "Your session has expired."});
		} else {

			db.query("SELECT RoleId,LotNumber,SpaceId,unix_timestamp(TimeOut) AS TimeOut FROM Users u LEFT JOIN Reservations r ON r.UserId = u.UserId AND TimeOut > NOW() LEFT JOIN ParkingLots p ON r.LotId = p.LotId WHERE u.UserId = ? LIMIT 1", 
				user, function(err, rows, fields) {

					if(err || rows.length === 0) {
						console.log(err);
						response.send(500, {error: "An error has occured."});
					} else {

						response.json(rows[0]);

					}

			});

		}

	});

});


/**
 * HTTP GET /spaces/LOT_ID
 * Returns: Information about all the spaces of a certian lot.
 */
app.get('/spaces/:lot', function (request, response) {


	verifySessionKey(request, function (valid, key) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var lot = request.params.lot;

			db.query("SELECT * FROM Reservations WHERE LotId = ? AND TimeOut > NOW()", lot, function(err, rows, fields) {


				var spaces = [];

				for(var i = 0; i < rows.length; i++) {
					delete rows[i].Active;
					spaces.push(rows[i]);
				}

				response.json(spaces);

			});

		}

	});

});


/**
 * HTTP POST /spaces/LOT_ID
 * Returns: A message if successful.
 */
app.post('/spaces/:lot', function (request, response) {


	verifySessionKey(request, function (valid, key, user, isAdmin) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var lot = request.params.lot;
			var space = request.body.space;
			var time = request.body.time;
			var userId;

			if(isAdmin && typeof request.body.user !== 'undefined' && request.body.user !== null)
				userId = request.body.user;
			else
				userId = user;


			switch(time) {
				case 0: time = 2; break;
				case 1: time = 4; break;
				case 2: time = 6; break;
				case 3: time = 12; break;
				case 4: time = 24; break;
				default: time = 2;
			}


			db.query("SELECT * FROM Reservations WHERE LotId = ? AND SpaceId = ? AND TimeOut > NOW()", [lot, space], function(err, rows, fields) {


				if(err || rows.length > 0) {
					response.send(500, {error: "Space already reserved. Please choose another."});
				} else {


					db.query("UPDATE Reservations SET TimeOut = NOW() WHERE TimeOut > NOW() AND UserId = ?", userId, function(err, result) {

						if(err) {
							console.log(err);
							response.send(500, {error: "Error reserving space."});
						} else {

							db.query("INSERT INTO Reservations (SpaceId, UserId, TimeIn, TimeOut, LotId) VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL ? HOUR), ?)", 
								[space, userId, time, lot], function(err) {

									if(err) {
										response.send(500, {error: "Error reserving space."});
									} else {
										response.send(200, {message: "Space reserved!"});
									}

							});
						}


					});


				}

			});

		}

	});

});


/**
 * HTTP DELETE /spaces/LOT_ID/SPACE_ID
 * Returns: A message if successful.
 */
app.delete('/spaces/:lot/:space', function (request, response) {


	verifyAdminSession(request, function (valid, key, user) {

		if(!valid) {
			response.send(403, {error: "You are not authorized to complete this request."});
		} else {

			var lot = request.params.lot;
			var space = request.params.space;

			db.query("UPDATE Reservations SET TimeOut = NOW() WHERE LotId = ? AND SpaceId = ? AND TimeOut > NOW() LIMIT 1", [lot, space], function(err, result) {

				if(err) {
					console.log(err);
					response.send(500, {error: "An error has occured."});
				} else {

					if(result.affectedRows > 0)
						response.send(200, {message: "Reservation has been deleted."});
					else
						response.send(200, {message: "No reservations found."});

				}

			});


		}

	});

});



/**
 * HTTP GET /roles
 * Returns: A list of the available roles.
 */
app.get('/roles', function (request, response) {

	verifyAdminSession(request, function (valid) {

		if(valid) {

			db.query("SELECT * FROM Roles", function(err, rows, fields) {

				if(err) {
					
					console.log(err);
					response.send(500, {error: "An error has occured."});

				} else {

					var roles = [];

					for(var i = 0; i < rows.length; i++) {
						rows[i].Active = rows[i].Active[0];
						roles.push(rows[i]);
					}

					response.json(roles);
				}

			});

		} else
			response.send(403, {error: "Access denied."});

	});

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

    var username = request.body.username.trim();
    var password = request.body.password;
    var firstname = request.body.firstname.trim();
    var lastname = request.body.lastname.trim();
    var role = parseInt(request.body.role, 10);

    //Role must match the ones in the database
    switch(role) {
    	case 0: role = 4; break;
    	case 1: role = 2; break;
    	case 2: role = 3; break;
    	case 3: role = 5; break;
    	default: role = 5; 
    }
    

    //To-do: Validate username and password.
    if(validateUserDetails(username, firstname, lastname, password))
	bcrypt.genSalt(10, function(err, salt) {

	    bcrypt.hash(password, salt, function(err2, hash) {
	        // Store user/hash/salt
	        //console.log(password + " -> " + hash + " : " + salt);
	        var user = {UserName: username, Password: hash, Salt: salt, RoleId: role, Email: '', FirstName: firstname, LastName: lastname};

	        if(err || err2) {
				console.log(err);
				console.log(err2);
	        	response.send(500, {error:'An error has occured.'});

	        } else {

	        	db.query("INSERT INTO Users SET ?", user, function(err, result) {

		        	if(err) {
		        		console.log(err);
		        		response.send(500, {error:'Username is unavailable.'});
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
			   				json.UserName = json.UserName.trim();

							db.query("INSERT INTO Users SET ?", json, function(err) {

								if(err) {
									console.log(err);
									response.send(500, {error: "Username is unavailable."});
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
		db.query("SELECT SessionId, Users.UserId, IsAdmin FROM UsersSessions JOIN Users ON UsersSessions.UserId = Users.UserId AND Active = 1 WHERE SessionKey = ?", key, function(err, rows, fields) {

			if(err || rows.length === 0) {
				callback(false); 
			} else {
				callback(true, key, rows[0].UserId, rows[0].IsAdmin[0] == 1);
			}

		});

	} else
		callback(false);

}

function verifyAdminSession(request, callback) {

	var key = request.headers['authorization'];

	if(typeof key != 'undefined' && key != null && key.length > 0) {
		db.query("SELECT IsAdmin, Users.UserId FROM UsersSessions JOIN Users ON UsersSessions.UserId = Users.UserId AND IsAdmin = 1 AND Active = 1 WHERE SessionKey = ?", key, function(err, rows, fields) {

			if(err || rows.length === 0) {
				callback(false); 
			} else {
				callback(true, key, rows[0].UserId);
			}

		});

	} else
		callback(false);

}


function validateUserDetails(username, firstname, lastname, password) {

	username = username.replace(/\s/g,'');
	firstname = firstname.replace(/\s/g,'');
	lastname = lastname.replace(/\s/g,'');

	if(username.length > 0 && username.length <= 16)
	if(firstname.length > 0 && firstname.length <= 50)
	if(lastname.length > 0 && lastname.length <= 50)
		return true;

	return false;
}