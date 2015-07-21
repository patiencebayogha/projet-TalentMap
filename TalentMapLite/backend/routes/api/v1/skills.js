var express = require('express');
var router = express.Router();

var skillService = require('../../../services/skills.js');
var authService = require('../../../services/passport/auth.js');


var debug = require('debug')('TalentMapLite:api:skills');

var serviceUtils = require('./utils.js');

/* ACTIONS */
var SKILL_URI_LIST = '/list';
var SKILL_URI_GET = '/get';

/* PARAMETERS */
var SKILL_PARAM_CATEGORY = 'category';
var SKILL_PARAM_NAME = 'skill';



/*
 * Get skills list.
 *
 * @param optional : category
 *
 * Example: GET
 * http://127.0.0.1:3000/api/v1/skills/list
 */
router.get(SKILL_URI_LIST, authService.ensureAuthenticated, function(req, res) {

    skillService.listSkills(
        function (err, items) {
            serviceUtils.callback(err, skillService.safeCleanSkills(items), res, debug);
        });

});



/*
 * Get skills by category.
 *
 * Example: POST
 * http://127.0.0.1:3000/api/v1/skills/list/
 * {
 *  category:c09024a4-a640-11e4-89d3-123b93f75cba
 * }
 */
router.post(SKILL_URI_LIST, authService.ensureAuthenticated, function(req, res) {

    var params = [SKILL_PARAM_CATEGORY];
    serviceUtils.bodyCheck(params, req.body, function(err) {
        if(err) {
            serviceUtils.callback(err, {}, res, debug);
            return;
        }

        skillService.getSkillsByCategory(
            req.body[SKILL_PARAM_CATEGORY],
            function (err, items) {
                serviceUtils.callback(err, skillService.safeCleanSkills(items), res, debug);
            });
    });

});

/*
 * Get a skill
 *
 * Example: GET
 * http://127.0.0.1:3000/api/v1/skills/get/
 * {
 *  skill:"Angular"
 * }
 */
router.post(SKILL_URI_GET, authService.ensureAuthenticated, function(req, res) {

    var params = [SKILL_PARAM_NAME];
    serviceUtils.bodyCheck(params, req.body, function(err) {
        if(err) {
            serviceUtils.callback(err, {}, res, debug);
            return;
        }

        skillService.getSkill(
            req.body[SKILL_PARAM_NAME],
            function (err, item) {
                serviceUtils.callback(err, skillService.safeCleanSkill(item), res, debug);
            });
    });
});



module.exports = router;
