/**
 * Created by JBA3353 on 11/02/2015.
 */

var user = require('../schemas/users.js');

var userService = require('../services/users.js');
var skillService = require('../services/skills.js');

var mock_users = require('./DBinit-mockUsers.json');
var mock_skills = require('./DBinit-mockSkills.json');

var DBinit16 = {


    /*
     * Apply DB init
     */
    apply: function() {

        this.createSkills();
        this.createUsers();
    },


    /*
     * Create mock skills
     */
    createSkills: function() {


        // For each mock users
        mock_skills.forEach(function(mockItem) {

            skillService.addSkill(mockItem.name, true, mockItem.category, function (err, data) {

                console.log('Creaing skill ' + mockItem.name + ' from mock data.');
            });
        });

    },


    /*
     * Create mock users
     */
    createUsers: function() {

        // For each mock users
        mock_users.forEach(function(mockItem) {

            // Forge the fake user
            var newUser = new user();
            newUser.email = mockItem.email;
            newUser.surname = mockItem.first_name;
            newUser.name = mockItem.last_name;
            newUser.password = mockItem.password;
            newUser.photo = "http://dummyimage.com/200x200&text="+mockItem.email,
            newUser.active = 1;

            // Find existing document
            user.findOne({email: newUser.email}, function (err, result) {

                if (result !== null) {
                    return;
                }

                console.log('Creaing user ' + newUser.email + ' with mock data.');
                newUser.save(function (err) {

                    /* Create random skills for the current user */
                    var nbSkills = DBinit16.random(5, 15);
                    for(var i = 0; i < nbSkills; i++) {

                        var currSkill = mock_skills[DBinit16.random(0, mock_skills.length-1)];

                        userService.addUserSkill(newUser.email,
                            currSkill.name,
                            DBinit16.random(1, 5),
                            currSkill.category,
                            function() {}
                        );

                    }

                });
            });

        });

    },

    /*
     * Random number in range
     */
    random: function (low, high) {
        return Number(Math.random() * (high - low) + low).toFixed(0);
    }

};


module.exports = DBinit16;
