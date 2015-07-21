
var express = require('express');
var router = express.Router();

var userService = require('../../../services/users.js');
var authService = require('../../../services/passport/auth.js');

var debug = require('debug')('TalentMapLite:api:users');

var serviceUtils = require('./utils.js');

/* ACTIONS */
var USER_URI_LIST = '/list';
var USER_URI_GET = '/get';
var USER_URI_ADD_SKILL = '/add-skill';
var USER_URI_UPDATE = '/update';
var USER_URI_DELETE = '/delete-skill';
var USER_URI_UPDATE_NAME = '/update-name';

/* PARAMETERS */
var USER_PARAM_EMAIL = 'email';
var USER_PARAM_NAME = 'name';
var USER_PARAM_SURNAME = 'surname';
var USER_PARAM_SKILL = 'skill';
var USER_PARAM_LEVEL = 'level';
var USER_PARAM_CATEGORY = 'category';



/*
 * Get users list.
 *
 * Example: GET
 * http://127.0.0.1:3000/api/v1/users/list
 */
router.get(USER_URI_LIST, authService.ensureAuthenticated, function(req, res) {
    userService.listUsers(
        function (err, items) {
            serviceUtils.callback(err, userService.safeCleanUsers(items), res);
        });
});


/*
 * Get user.
 *
 * Example: GET
 * http://127.0.0.1:3000/api/v1/users/get/
 * {
 *  email:justin.ptipeu@viseo.com
 * }
 */
router.post(USER_URI_GET, authService.ensureAuthenticated, function(req, res) {

    var params = [USER_PARAM_EMAIL];
    serviceUtils.bodyCheck(params, req.body, function(err) {
        if(err) {
            serviceUtils.callback(err, {}, res);
            return;
        }

        userService.getUser(
            req.body[USER_PARAM_EMAIL],
            function (err, item) {
                serviceUtils.callback(err, userService.safeCleanUser(item), res, debug);
            });

    });


});


/*
 * Add user skill.
 *
 * Example: POST
 * http://127.0.0.1:3000/api/v1/users/add-skill/
 * {
 *  email:justin.ptipeu@viseo.com
 *  skill:amazing
 *  level:3
 *  category:6103760c-a641-11e4-89d3-123b93f75cba
 * }
 */
router.post(USER_URI_ADD_SKILL, authService.ensureAuthenticated, function(req, res) {


    var params =
        [
            USER_PARAM_EMAIL,
            USER_PARAM_SKILL,
            USER_PARAM_LEVEL
        ];

    serviceUtils.bodyCheck(params, req.body, function(err) {
        if (err) {
            serviceUtils.callback(err, {}, res, debug);
            return;
        }

        authService.ensureHasRole(req, res, req.body[USER_PARAM_EMAIL], function() {

            userService.addUserSkill(
                req.body[USER_PARAM_EMAIL],
                req.body[USER_PARAM_SKILL],
                req.body[USER_PARAM_LEVEL],
                req.body[USER_PARAM_CATEGORY],
                function (err, items) {
                    serviceUtils.callback(err, items, res, debug);
                });
        });

    });
});

/*
 * Update user skills.
 *
 * Example: PATCH
 * http://127.0.0.1:3000/api/v1/users/update/
 * {
 *  email:justin.ptipeu@viseo.com
 *  skill:Angular
 *  level:5
 * }
 */
router.patch(USER_URI_UPDATE, authService.ensureAuthenticated, function(req, res) {

        var params =
            [
                USER_PARAM_EMAIL,
                USER_PARAM_SKILL,
                USER_PARAM_LEVEL
            ];

        serviceUtils.bodyCheck(params, req.body, function(err) {
            if (err) {
                serviceUtils.callback(err, {}, res, debug);
                return;
            }

            authService.ensureHasRole(req, res, req.body[USER_PARAM_EMAIL], function() {

                userService.updateUserSkill(
                    req.body[USER_PARAM_EMAIL],
                    req.body[USER_PARAM_SKILL],
                    req.body[USER_PARAM_LEVEL],
                    function (err, items) {
                        serviceUtils.callback(err, items, res, debug);
                    });
            });
        });
});

/*
 * Update user name.
 *
 * Example: PATCH
 * http://127.0.0.1:3000/api/v1/users/update-name/
 * {
 *  email:justin.ptipeu@viseo.com
 *  name:petitpeu
 *  surname:paul
 * }
 */
router.patch(USER_URI_UPDATE_NAME, function(req, res) {

    var params =
        [
            USER_PARAM_EMAIL,
            USER_PARAM_NAME,
            USER_PARAM_SURNAME
        ];

    serviceUtils.bodyCheck(params, req.body, function(err) {
        if(err) {
            serviceUtils.callback(err, {}, res, debug);
            return;
        }

        authService.ensureHasRole(req, res, req.body[USER_PARAM_EMAIL], function() {

            userService.updateUserName(
                req.body[USER_PARAM_EMAIL],
                req.body[USER_PARAM_NAME],
                req.body[USER_PARAM_SURNAME],
                function (err, items) {
                    serviceUtils.callback(err, items, res, debug);
                });

        });
    });
});


/*
 * Delete user skill.
 *
 * Example: POST
 * http://127.0.0.1:3000/api/v1/users/delete-skill/
 * {
 *  email:justin.ptipeu@viseo.com
 *  skill:Angular
 * }
 */
router.post(USER_URI_DELETE, authService.ensureAuthenticated, function(req, res) {

        var params =
            [
                USER_PARAM_EMAIL,
                USER_PARAM_SKILL
            ];

        serviceUtils.bodyCheck(params, req.body, function(err) {
            if (err) {
                serviceUtils.callback(err, {}, res, debug);
                return;
            }

            authService.ensureHasRole(req, res, req.body[USER_PARAM_EMAIL], function() {

                userService.deleteUserSkill(
                    req.body[USER_PARAM_EMAIL],
                    req.body[USER_PARAM_SKILL],
                    function (err, items) {
                        serviceUtils.callback(err, items, res, debug);
                    });
            });
        });
});


module.exports = router;
