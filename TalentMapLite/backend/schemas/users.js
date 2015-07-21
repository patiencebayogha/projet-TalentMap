var mongoose = require('mongoose'), Schema = mongoose.Schema;

var skillSchema = require('../schemas/skills.js');
/**
 * Users schema definition
 */

/*
{
    _id: 7891,
    email: "surname.name@viseo.com",
    password: "13b104005f3c996a480f5899613c449e",
    skills: [
        {
            skill: "HTML",
            category: "c09024a4-a640-11e4-89d3-123b93f75cba",
            level: 5
        },
        {
            skill: "JEE",
            category: "c09024a4-a640-11e4-89d3-123b93f75cba",
            level: 3
        }
    ],
    name: "name",
    surname: "surname",
    photo: "",
    active: false
}
*/
var usersSchema = new Schema({
    email: String, // identifier
    password: String,
    skills: [{
        skill: String,
        category: String,
        level: Number
    }],
    name: String,
    surname: String,
    photo: String,
    active: Boolean
});

var User = mongoose.model(
    'User',         // name
    usersSchema,    // schema
    'users'         // collecion
);


if (!usersSchema.options.toObject) {
    usersSchema.options.toObject = {};
}

usersSchema.options.toObject.transform = function (doc, ret, options) {
    // remove the _id of every document before returning the result
    delete ret._id;
};

module.exports = User;
