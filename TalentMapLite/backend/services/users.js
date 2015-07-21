
'use strict';
var debug = require('debug')('TalentMapLite:services:users');

var userSchema = require('../schemas/users.js');

var categoryList = require('../data/public/categories.json')

var skillService = require('./skills.js');


var usersService = {

    /*
     * Return array of users object without their ID
     * This function is ideal for REST exposition
     */
    safeCleanUsers: function(users) {
        if(users == null) {
            return [];
        }

        var cleanedUsers = [];
        users.forEach(function(user) {
            cleanedUsers.push(usersService.safeCleanUser(user));
        });
        return cleanedUsers;
    },


    /*
     * Return an user object without his ID
     * This function is ideal for REST exposition
     */
    safeCleanUser: function(user) {
        if(user == null) {
            return [];
        }

        var cleanedUser = user.toObject();

        cleanedUser.skills.forEach(function(skill) {
            delete skill._id;
        });

        delete cleanedUser.password;

        return cleanedUser;
    },


    /*
     * GET users list.
     */
    listUsers: function (callback) {
        userSchema.find({}, function (err, users) {
            if(err) debug(err);
            callback(err, users);
        });
    },

    /*
     * GET user.
     */
    getUser: function (userEmail, callback) {
        userSchema.findOne({email: userEmail}, function (err, user) {
            if(err) debug(err);
            callback(err, user);
        });
    },


    /*
    * GET current user
    */
    getMe : function(){

    },


    /*
     * FIND user with specified skill.
     */
    findUserWithSkill: function (skillName, callback) {
        userSchema.findOne({'skills.skill': skillName}, function (err, items) {
            if(err) debug(err);
            callback(err, items);
        });
    },


    /*
     * ADD skill for user.
     */
    addUserSkill: function (userEmail, skillName, skillLevel, categoryUid, callback) {
        userSchema.findOne({email: userEmail}, function (err, userItem) {

            if(err) {
                debug(err);
                callback(err, {});
                return;
            }

            /* test if user exist */
            if (userItem === null) {
                callback("User doesn't exist", null);
                return;
            }

            /* test if skill already exist */
            var skills = userItem.skills.filter(function (item) {
                return (item.skill == skillName);
            });
            if (skills.length > 0) {
                callback("Skill already exist, use update instead.", skills);
                return;
            }

            /* test level between 1 and 5 */
            if (skillLevel < 1 || skillLevel > 5) {
                callback("Skill level must be between 1 and 5.", skills);
                return;
            }

            /* test that category exists */
            if (!categoryList[categoryUid]) {
                callback("Category doesn't exist.", skills);
                return;
            }

            var newSkill = {
                skill: skillName,
                level: skillLevel,
                category: categoryUid
            };

            userItem.skills.push(newSkill);

            userItem.save(function (err) {

                skillService.addSkill(skillName, true, categoryUid, function (err, items) {
                });
                callback(err, newSkill);
            });

        });
    },


    /*
     * DELETE skill of user.
     */
    deleteUserSkill: function (userEmail, skillName, callback) {
        userSchema.findOne({email: userEmail}, function (err, userItem) {

            if(err) {
                debug(err);
                callback(err, {});
                return;
            }

            /* Test if user exist */
            if (userItem === null) {
                callback("User doesn't exist", null);
                return;
            }

            /* Looking for the specified user skill */
            var found = false;
            userItem.skills.forEach(function (skillItem) {

                if (skillItem.skill == skillName) {

                    /* Remove the skill */
                    skillItem.remove();
                    userItem.save(function (err) {
                        if(err) {
                            debug(err);
                            return;
                        }
                        callback(err, {});
                    });

                    found = true;
                }
            });

            /* Remove the skill definitely if needed */
            if (found) {

                skillService.getSkill(skillName, function (err, skillItem) {

                    if(err) {
                        debug(err);
                        return;
                    }
                    if (skillItem !== null && skillItem.volatile) {

                        /* Check if someone has the skill before removing it */
                        usersService.findUserWithSkill(skillName, function (err, userItems) {

                            if(err) {
                                debug(err);
                                return;
                            }
                            if (userItems === null) {
                                skillService.deleteSkill(skillName, function (err) {
                                    if(err) debug(err);
                                });
                            }
                        });
                    }
                });

            } else {
                callback("Skill doesn't exist or have been already deleted.", {});
                return;
            }
        });
    },

    /*
     * UPDATE a user skill
     */
    updateUserSkill : function(userEmail, skillName, skillLevel, callback) {

        if(skillLevel < 1 || skillLevel > 5){
            callback("Skill level should be between 1 and 5", {});
            return;
        }

        userSchema.update({email: userEmail, 'skills.skill' : skillName}, {$set: {'skills.$.level' : skillLevel}}, function(err, items) {
            if(err) debug(err);
            callback(err, items);
        });
    },

    /**
     * update the name and surname of a user
     * @param email of the user
     * @param name the new name
     * @param surname the new surname
     */
    updateUserName : function(email, name, surname, callback) {

        userSchema.update({email: email}, {$set: {name: name, surname : surname}}, function (err) {
            if(err) debug(err);
            callback(err, email);
        });
    }

};


module.exports = usersService;
