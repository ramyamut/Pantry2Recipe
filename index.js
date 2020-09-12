// set up Express
var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

var fs = require('fs');
let rawdata = fs.readFileSync('recipe_data.json');
let recipes = JSON.parse(rawdata);
var indian = recipes.indian;
var mexican = recipes.mexican;
var asian = recipes.asian;
var italian = recipes.italian;

// import the Person class from Person.js
var Person = require('./Person.js');
var Recipe = require('./Recipe.js');

indian.forEach((recipe) => {
    var newRecipe = new Recipe ({
		instructions: recipe.instructions,
		ingredients: recipe.ingredients,
		title: recipe.title,
        cuisine: 'indian'
    });
    newRecipe.save((err)=> {
        if(err) {
            console.log('error loading indian recipes');
        }
    });
});

mexican.forEach((recipe) => {
    var newRecipe = new Recipe ({
		instructions: recipe.instructions,
		ingredients: recipe.ingredients,
		title: recipe.title,
        cuisine: 'mexican'
    });
    newRecipe.save((err)=> {
        if(err) {
            console.log('error loading mexican recipes');
        }
    });
});

asian.forEach((recipe) => {
    var newRecipe = new Recipe ({
		instructions: recipe.instructions,
		ingredients: recipe.ingredients,
		title: recipe.title,
        cuisine: 'asian'
    });
    newRecipe.save((err)=> {
        if(err) {
            console.log('error loading asian recipes');
        }
    });
});

italian.forEach((recipe) => {
    var newRecipe = new Recipe ({
		instructions: recipe.instructions,
		ingredients: recipe.ingredients,
		title: recipe.title,
        cuisine: 'italian'
    });
    newRecipe.save((err)=> {
        if(err) {
            console.log('error loading italian recipes');
        }
    });
});

// route for creating a new account
app.use('/createPerson', (req, res) => {
	var newPerson = new Person ({
		firstname: req.query.firstname,
		lastname: req.query.lastname,
		username: req.query.username,
        passwordHash: req.query.passwordHash,
        ingredients: []
    });
    console.log('createPerson called');
    newPerson.save((err)=> {
        if(err) {
            res.json({'status': err});
        } else {
            res.json({'status': 'success'});
        }
    });
});

// route for adding an ingredient
app.use('/addIngredient', (req, res) => {
    var queryObj = {username: req.query.username};
    var ingredient = req.query.ingredient;
    Person.findOne(queryObj, (err, person)=> {
        if(err) {
            res.json({'status': err});
        } else if(!person) {
            res.json({'status': 'no account found'})
        } else {
            if(person.ingredients.contains(ingredient)) {
                res.json({'status': 'ingredient already in pantry'})
            } else {
                person.ingredients = person.ingredients.push(ingredient);
                person.save((err) => {
                    if(err) {
                        res.json({'status': err});
                    } else {
                        res.json({'status': 'success'});
                    }
                });
            }
        }
    });
});

// route for removing an ingredient
app.use('/removeIngredient', (req, res) => {
    var queryObj = {username: req.query.username};
    var ingredient = req.query.ingredient;
    Person.findOne(queryObj, (err, person)=> {
        if(err) {
            res.json({'status': err});
        } else if(!person) {
            res.json({'status': 'no account found'})
        } else {
            if(person.ingredients.contains(ingredient)) {
                person.ingredients = person.ingredients.filter(ing => {
                    return ing != ingredient;
                });
                person.save((err) => {
                    if(err) {
                        res.json({'status': err});
                    } else {
                        res.json({'status': 'success'});
                    }
                });
            } else {
                res.json({'status': 'ingredient not in pantry'})
            }
        }
    });
});

app.use('/getPerson', (req, res) => {
    var queryObj = {username: req.query.username};
    Person.findOne(queryObj, (err, person)=> {
        if(err) {
            res.json({'status': err});
        } else if(!person) {
            res.json({'status': 'no worker found'})
        } else {
            res.json(person);
        }
    });
});

app.use('/getAllRecipes', (req, res) => {
		
    // find all the Recipe objects in the database
    Recipe.find({}, (err, recipes) => {
        if (err) {
            res.json({});
        }
        else {
            res.json(recipes);
        }
    });
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

//clears all people
app.use('/clearPeople', (req, res) => {
	Person.remove({}, (err) => {
		if(err) {
			res.send(err);
		}
	});
	res.send('Removed people');
});

//clears all recipes
app.use('/clearRecipes', (req, res) => {
	Recipe.remove({}, (err) => {
		if(err) {
			res.send(err);
		}
	});
	res.send('Removed recipes');
});

app.listen(3000,  () => {
	console.log('Listening on port 3000');
})