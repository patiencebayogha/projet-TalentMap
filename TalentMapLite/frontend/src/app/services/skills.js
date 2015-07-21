/**
 * Created by JBA3353 on 06/02/2015.
 */

'use strict';

angular.module('myApp.services.skills', ['ngRoute', 'myApp.config'])

    .factory('skillFactory', ['$http', 'api_url', 'api_version', function($http, api_url, api_version) {

        var skillFactory = {};

        /**
         * get the list of skills
         * @returns {HttpPromise}
         */
        skillFactory.getSkills = function () {
            return $http.get(api_url+'/api/'+api_version + '/skills/list');
        };

        /**
         * get the skill of a chosen category
         * @param categoryUid the uid of the category selected
         * @returns {HttpPromise}
         */
        skillFactory.getSkillsByCategory = function (categoryUid) {
            return $http.post(api_url+'/api/'+api_version + '/skills/list/', {
                category: categoryUid
            });
        };

        /**
         * get a skill
         * @param skillName name of the skill selected
         * @returns {HttpPromise}
         */
        skillFactory.getSkill = function (skillName) {
            return $http.post(api_url+'/api/'+api_version+'/skills/get/', {
                skill: skillName
            });
        };

        return skillFactory;

    }]);

