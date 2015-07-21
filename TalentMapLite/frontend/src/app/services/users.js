/**
 * Created by JBA3353 on 26/01/2015.
 */

'use strict';

angular.module('myApp.services.users', ['ngRoute', 'myApp.config'])

.factory('userFactory', ['$http', 'api_url', 'api_version', function($http, api_url, api_version) {

    var userFactory = {};

        /**
         * get the list of users
         * @returns {HttpPromise}
         */
    userFactory.listUsers = function () {
        return $http.get(api_url+'/api/'+api_version+'/users/list');
    };


        /**
         * get a user
         * @param userEmail email of the user selected
         * @returns {HttpPromise}
         */
    userFactory.getUser = function (userEmail) {
        console.log('getUser');
        return $http.post(api_url+'/api/'+api_version+'/users/get/', {
            email: userEmail
        });
    };

    /* This will be removed in future commits*/
    // this is my mail address, should be replaced later when authentication system will be made
    userFactory.getMe = function () {
        return $http.get(api_url+'/api/'+api_version+'/auth/getMe/');
    };

        /**
         * add a new skill to a user
         * @param userEmail mail of the user
         * @param skillName name of the new skill
         * @param skillLevel level of the new skill
         * @param categoryUid uid of the skill category
         * @returns {HttpPromise}
         */
    userFactory.addUserSkill = function (userEmail, skillName, skillLevel, categoryUid) {
        return $http.post(api_url+'/api/'+api_version+ '/users/add-skill/', {
            email: userEmail,
            skill: skillName,
            level: skillLevel,
            category: categoryUid
        });
    };


        /**
         * update a user skill
         * @param userEmail email of the user
         * @param skillName name of the skill to update
         * @param skillLevel level of the skill
         * @returns {HttpPromise}
         */
    userFactory.updateUserSkill = function (userEmail, skillName, skillLevel) {
        return $http.patch(api_url+'/api/'+api_version+'/users/update/', {
            email: userEmail,
            skill: skillName,
            level: skillLevel
        });
    };

        /**
         * update the skill of the user connected
         * @param skillName name of the skill to update
         * @param skillLevel level of the skill
         */
    userFactory.updateMySkill = function (skillName, skillLevel) {

        userFactory.getMe()
            .success(function (data) {
                var me = data;
                return userFactory.updateUserSkill(me.email, skillName, skillLevel);
            });
    };

        /**
         * update the name and surname of a user
         * @param email of the user to update
         * @param name new name
         * @param surname new surname
         * @returns {HttpPromise}
         */
    userFactory.updateMyName = function (email, name, surname) {
        return $http.patch(api_url+'/api/'+api_version+'/users/update-name/', {
            email: email,
            name: name,
            surname: surname
        });
    };

        /**
         * delete a user skill
         * @param userEmail email of the user
         * @param skillName name of the skill to delete
         * @returns {HttpPromise}
         */
    userFactory.deleteUserSkill = function (userEmail, skillName) {
        return $http.post(api_url+'/api/'+api_version+'/users/delete-skill/', {
            email: userEmail,
            skill: skillName
        });
    };

    return userFactory;

}]);
