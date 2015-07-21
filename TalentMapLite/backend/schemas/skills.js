var mongoose = require('mongoose'), Schema = mongoose.Schema;

/**
 * Skills schema definition
 */

/*
{
    _id: 4567,
   name: "JEE",
   volatile: false,
   category: c09024a4-a640-11e4-89d3-123b93f75cba
},
{
    _id: 8546,
   name: "Anglais",
   volatile: true,
   category: c09024a4-a640-11e4-89d3-123b93f75cba
}
*/
var skillsSchema = new Schema({
    name: String,
    volatile: Boolean,
    category: String
});

var Skill = mongoose.model(
    'Skill',        // name
    skillsSchema,   // schema
    'skills'        // collection
);


if (!skillsSchema.options.toObject) {
    skillsSchema.options.toObject = {};
}

skillsSchema.options.toObject.transform = function (doc, ret, options) {
    // remove the _id of every document before returning the result
    delete ret._id;
};

module.exports = Skill;
