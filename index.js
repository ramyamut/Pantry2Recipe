// set up Express
var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// import the Person class from Person.js
var Person = require('./Person.js');

//import the Worker class from Worker.js
var Worker = require('./Worker.js');


// route for creating a new healthcare worker
app.use('/createWorker', (req, res) => {
	var newWorker = new Worker ({
		firstname: req.query.firstname,
		lastname: req.query.lastname,
		username: req.query.username,
        passwordHash: req.query.passwordHash
    });
    console.log('createWorker called');
    newWorker.save((err)=> {
        if(err) {
            res.json({'status': err});
        } else {
            res.json({'status': 'success'});
        }
    });
});

app.use('/getWorker', (req, res) => {
    var queryObj = {username: req.query.username};
    Worker.findOne(queryObj, (err, worker)=> {
        if(err) {
            res.json({'status': err});
        } else if(!worker) {
            res.json({'status': 'no worker found'})
        } else {
            res.json(worker);
        }
    });
});

app.use('/createPerson', (req, res)=> {
    var newPerson = new Person ({
        id: req.query.idNum,
        firstname: req.query.firstname,
        lastname: req.query.lastname,
        phonenumber: req.query.phonenumber,
        region: req.query.region,
        testpos: req.query.testpos
    });

    newPerson.save((err) => {
        if (err) {
            res.json({'status': err})
        } else {
            res.json({'status': 'success'});
        }
    })
});

app.use('/alert', (req, res)=> {
    var queryObj = {id: req.query.idNum};
    const accountSid = 'AC69907a0d2d76d94ca05a3d3d843ab99f';
    const authToken = '23e8fa3cad484ff9cbf0ea7ba77601a3';
    const twilioClient = require('twilio')(accountSid, authToken);
    Person.findOne(queryObj, (err, person) => {
        if(err) {
            console.log(err);
            res.json({'status': err});
        } else if(!person) {
            res.json({'status': 'no person found'});
        } else if(person.testpos) {
            res.json({'status': 'already tested positive'});
        } else {
            person.testpos = true;
            var region = person.region;
            Person.find({'region': region}, (err2, matches)=> {
                if(err2) {
                    console.log(err2);
                    res.json({'status': err2});
                } else {
                    matches.forEach((match)=> {
                        var numberToSend = '+1' + match.phonenumber;
                        twilioClient.messages.create({
                            body: 'Warning: Someone in your region has just tested positive for COVID-19!',
                            from: '+18593502238',
                            to: numberToSend
                        }).then(message => console.log(message.sid));
                    })
                }
            });
            person.save((err3) => {
                if(err3) {
                    console.log(err3);
                    res.json({'status': err3});
                } else {
                    res.json({'status': 'success'});
                }
            })
        }
    })
});

app.use('/individualAlertPos', (req, res)=> {
    var queryObj = {id: req.query.idNum};
    const accountSid = 'AC69907a0d2d76d94ca05a3d3d843ab99f';
    const authToken = '23e8fa3cad484ff9cbf0ea7ba77601a3';
    const twilioClient = require('twilio')(accountSid, authToken);
    Person.findOne(queryObj, (err, person) => {
        if(err) {
            console.log(err);
            res.json({'status': err});
        } else if(!person) {
            res.json({'status': 'no person found'});
        } else if(person.testpos) {
            res.json({'status': 'already tested positive'});
        } else {
            person.testpos = true;
            var numberToSend = '+1' + person.phonenumber;
            if(result) {
                twilioClient.messages.create({
                    body: 'You have tested positive for COVID-19! Please stay safe and seek care if needed.',
                    from: '+18593502238',
                    to: numberToSend
                }).then(message => console.log(message.sid));
            } else {
                twilioClient.messages.create({
                    body: 'You have tested negative for COVID-19!',
                    from: '+18593502238',
                    to: numberToSend
                }).then(message => console.log(message.sid));
            }
            person.save((err3) => {
                if(err3) {
                    console.log(err3);
                    res.json({'status': err3});
                } else {
                    res.json({'status': 'success'});
                }
            })
        }
    })
});

app.use('/individualAlertNeg', (req, res)=> {
    var queryObj = {id: req.query.idNum};
    const accountSid = 'AC69907a0d2d76d94ca05a3d3d843ab99f';
    const authToken = '23e8fa3cad484ff9cbf0ea7ba77601a3';
    const twilioClient = require('twilio')(accountSid, authToken);
    Person.findOne(queryObj, (err, person) => {
        if(err) {
            console.log(err);
            res.json({'status': err});
        } else if(!person) {
            res.json({'status': 'no person found'});
        } else if(person.testpos) {
            res.json({'status': 'already tested positive'});
        } else {
            person.testpos = false;
            var numberToSend = '+1' + person.phonenumber;
            if(result) {
                twilioClient.messages.create({
                    body: 'You have tested positive for COVID-19! Please stay safe and seek care if needed.',
                    from: '+18593502238',
                    to: numberToSend
                }).then(message => console.log(message.sid));
            } else {
                twilioClient.messages.create({
                    body: 'You have tested negative for COVID-19!',
                    from: '+18593502238',
                    to: numberToSend
                }).then(message => console.log(message.sid));
            }
            person.save((err3) => {
                if(err3) {
                    console.log(err3);
                    res.json({'status': err3});
                } else {
                    res.json({'status': 'success'});
                }
            })
        }
    })
});

app.use('/getAllPeople', (req, res) => {
		
    // find all the Person objects in the database
    Person.find({}, (err, people) => {
        if (err) {
            res.json({});
        }
        else {
            res.json(people);
        }
    });
});

app.use('/getAllWorkers', (req, res) => {
		
    // find all the Worker objects in the database
    Worker.find({}, (err, workers) => {
        if (err) {
            res.json({});
        }
        else {
            res.json(workers);
        }
    });
});

//clears all of the workers from the database
app.use('/clearWorkers', (req, res) => {
	Worker.remove({}, (err) => {
		if(err) {
			res.send(err);
		}
    });
    res.send('Removed workers');
});

//clears all people
app.use('/clearPeople', (req, res) => {
	Person.remove({}, (err) => {
		if(err) {
			res.send(err);
		}
	});
	res.send('Removed people');
});

app.listen(3000,  () => {
	console.log('Listening on port 3000');
})