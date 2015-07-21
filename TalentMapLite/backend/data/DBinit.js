/**
 * Created by JBA3353 on 27/01/2015.
 */

var mongoose = require('mongoose');

var initUserStoy6 = require('./DBinit-UserStory6.js');
var initUserStoy13 = require('./DBinit-UserStory13.js');

var userSchema = require('../schemas/users.js');
var skillSchema = require('../schemas/skills.js');

module.exports = {

    /*
     * Apply DB init
     */
    apply: function () {

        initUserStoy6.apply();
        initUserStoy13.apply();
    },


    /*
     * Drop DB
     */
    drop: function() {

        userSchema.collection.drop();
        skillSchema.collection.drop();
        console.log('Database dropped');
    }

};
