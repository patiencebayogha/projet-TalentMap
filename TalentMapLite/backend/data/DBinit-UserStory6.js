/**
 * Created by JBA3353 on 27/01/2015.
 */

var user = require('../schemas/users.js');
var userService = require('../services/users.js');

/*
 user with empty profile:
    http://localhost:3000/api/v1/users/get/adam.troijours@viseo.com
    http://localhost:3000#/profile/adam.troijours@viseo.com

 user with no profile photo :
     http://localhost:3000/api/v1/users/get/celine.evitable@viseo.com
     http://localhost:3000#/profile/celine.evitable@viseo.com

 user with one skill :
     http://localhost:3000/api/v1/users/get/eleonore.ileosud@viseo.com
     http://localhost:3000#/profile/eleonore.ileosud@viseo.com

 user with many skills :
     http://localhost:3000/api/v1/users/get/justin.ptipeu@viseo.com
     http://localhost:3000#/profile/justin.ptipeu@viseo.com

 */

module.exports = {


    /*
     * Apply DB init
     */
    apply: function() {

        this.createUsers();
    },

    /*
     * Create users
     */
    createUsers: function() {

        this.createEmptyProfile();
        this.createProfileWithoutPhoto();
        this.createProfileWithOneSkill();
        this.createProfileWithTwentySkill();
    },

    /*
     * Create user with empty profile
     */
    createEmptyProfile: function() {

        var newUser = new user();
        newUser.email = "adam.troijours@viseo.com";
        newUser.surname = "Adam";
        newUser.rname = "Troijours";
        newUser.password = "pass";
        newUser.photo = "http://www.exactitudes.com/content/600/154_03.jpg",
        newUser.active = 1;

        // Find existing document
        user.findOne({email: newUser.email}, function (err, result) {

            if (result !== null) {
                return;
            }

            console.log('Creaing user ' + newUser.email + ' with empty profile.');
            newUser.save(function (err) {
            });
        });
    },

    /*
     * Create user without photo but with name and surname
     */
    createProfileWithoutPhoto: function() {

        var newUser = new user();
        newUser.email = "celine.evitable@viseo.com";
        newUser.surname = "Céline";
        newUser.name = "Évitable";
        newUser.password = "pass";
        newUser.photo = "",
        newUser.active = 1;

        // Find existing document
        user.findOne({email: newUser.email}, function (err, result) {

            if (result !== null) {
                return;
            }

            console.log('Creaing user ' + newUser.email + ' with empty profile.');
            newUser.save(function (err) {
            });
        });

    },


    /*
     * Create user with 1 skill
     */
    createProfileWithOneSkill: function() {

        var newUser = new user();
        newUser.email = "eleonore.ileosud@viseo.com";
        newUser.surname = "Éléonore";
        newUser.name = "Iléosud";
        newUser.password = "pass";
        newUser.photo = "http://www.exactitudes.com/content/600/152_09.jpg",
        newUser.active = 1;


        // Find existing document
        user.findOne({email: newUser.email}, function (err, result) {

            if (result !== null) {
                return;
            }

            console.log('Creaing user ' + newUser.email + ' with empty profile.');
            newUser.save(function (err) {

                userService.addUserSkill(newUser.email,
                    "Anglais",
                    5,
                    "b68d759c-a640-11e4-89d3-123b93f75cba",
                    function() {}
                );

            });
        });

    },

    /*
     * Create user with 20 skill
     */
    createProfileWithTwentySkill: function() {

        var newUser = new user();
        newUser.email = "justin.ptipeu@viseo.com";
        newUser.surname = "Justin";
        newUser.name = "Ptipeu";
        newUser.password = "pass";
        newUser.photo = "http://www.exactitudes.com/content/600/024_09.jpg",
        newUser.active = 1;


        // Find existing document
        user.findOne({email: newUser.email}, function (err, result) {

            if (result !== null) {
                return;
            }

            console.log('Creaing user ' + newUser.email + ' with empty profile.');
            newUser.save(function (err) {


                userService.addUserSkill(newUser.email,
                    "Anglais",
                    3,
                    "b68d759c-a640-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    "Espagnol",
                    4,
                    "b68d759c-a640-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    ".NET",
                    2,
                    "c09024a4-a640-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    "C++",
                    4,
                    "c09024a4-a640-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    "Angular",
                    3,
                    "c09024a4-a640-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    "Java",
                    5,
                    "c09024a4-a640-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    "Management de projet",
                    4,
                    "d06bfe2a-a640-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    "MS Office",
                    3,
                    "5801da44-a641-11e4-89d3-123b93f75cba",
                    function() {}
                );

                userService.addUserSkill(newUser.email,
                    "MS Project",
                    2,
                    "5801da44-a641-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    "Tennis",
                    4,
                    "6103760c-a641-11e4-89d3-123b93f75cba",
                    function() {}
                );
                userService.addUserSkill(newUser.email,
                    "Musique classique",
                    1,
                    "6103760c-a641-11e4-89d3-123b93f75cba",
                    function() {}
                );


            });
        });
    }
}

