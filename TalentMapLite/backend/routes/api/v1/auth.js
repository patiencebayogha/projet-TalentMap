/**
 * Created by JBA3353 on 13/02/2015.
 */

var express = require('express');
var router = express.Router();

var authService = require('../../../services/passport/auth.js');

module.exports = function(passport){

   /* User authentication
    *
    * Example: POST
    * http://127.0.0.1:3001/api/v1/auth/login
    *
    * {
    *  "email":"justin.ptipeu@viseo.com",
    *  "password":"pass"
    * }
    * */
    router.post('/login', passport.authenticate('login'),
        function(req, res) {
            // Successful authentication
            res.json ({code : 200, msg: 'Authentication successful'});
        });



    /* Is user logged
     *
     * Example: GET
     * http://127.0.0.1:3001/api/v1/auth/islogged
     * */
    router.get('/islogged', authService.ensureAuthenticated,function(req, res) {

            res.json ({code : 200, msg: 'You are logged-in'});
        });

    /*
     * Get current user
     *
     * EXAMPLE: GET
     * http://127.0.0.1:3001/api/v1/auth/getMe
     *
     */
    router.get('/getMe', authService.ensureAuthenticated, function(req, res) {
            res.json(req.user);
    });

    return router;



};
