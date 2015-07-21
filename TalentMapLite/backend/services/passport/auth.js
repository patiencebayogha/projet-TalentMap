/**
 * Created by JBA3353 on 13/02/2015.
 */

var LocalStrategy = require('passport-local').Strategy;

var debug = require('debug')('TalentMapLite:services:passport:auth');

var userService = require('../users.js');

var USER_PARAM_EMAIL = 'email';
var USER_PARAM_PASSWORD = 'password';


var auth = {

    loginStrategy: new LocalStrategy({
        passReqToCallback: true,

        usernameField: 'email',
        passwordField: 'password',

        badRequestMessage: 'Account credentials was expected to login'
    },
    function (req, username, password, done) {

        debug('User ' + username + ' is authenticating');

        userService.getUser(username,
            function (err, user) {

                if (err) {
                    debug(err);
                    return done(err);
                }

                if (!user) {
                    debug('User Not Found with username ' + username);
                    return done(null, false, 'User Not found.');
                }
                // User exists but wrong password, log the error
                if (!auth.isValidPassword(user, password)) {
                    debug('Invalid Password for user ' + username);
                    return done(null, false, 'Invalid Password');
                }
                // User and password both match, return user from
                // done method which will be treated like success
                return done(null, user);
            }
        );
    }),

    ensureAuthenticated: function(req, res, next) {
        if (req.isAuthenticated()) {
            return next();
        }

        debug('Unauthorized access declined');
        res.send (401, {code : 401, msg: 'error: Authentication is required'});
    },

    ensureHasRole: function(req, res, email, next) {
        if (req.isAuthenticated() && req.user.email == email) {
            return next();
        }

        debug('Unauthorized action, request declined');
        res.send (401, {code : 401, msg: 'error: Authorization is required for this action'});
    },

    serializeUser: function(user, done) {
        done(null, user.email);
    },

    deserializeUser: function(email, done) {
        userService.getUser(email, function(err, user) {
            done(err, user.toObject());
        });
    },

    isValidPassword: function (user, password) {
        return password === user.password;
    }

};

module.exports = auth;

