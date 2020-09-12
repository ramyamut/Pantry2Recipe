var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
//mongoose.connect('mongodb://mongodb-master:27017,mongodb-slave1:27017,mongodb-slave2:27017/wehpu?replicaSet=wehpu');
mongoose.connect('mongodb+srv://admin:ramya123@clusterapoc-kkcta.mongodb.net/test?retryWrites=true&w=majority', { useNewUrlParser: true, useUnifiedTopology: true});

var Schema = mongoose.Schema;

var workerSchema = new Schema({
firstname: {type: String, required: true},
lastname: {type: String, required: true},
username: {type: String, required: true, unique: true},
passwordHash: {type: Number, required: true},
    });

// export personSchema as a class called Person
module.exports = mongoose.model('Worker', workerSchema);