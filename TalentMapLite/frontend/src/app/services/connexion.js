/**
 * Created by JBA3353 on 06/02/2015.
 */

'use strict';

angular.module('myApp.services.connexion', ['ngRoute', 'myApp.config'])

    .factory('connexionFactory', ['$http', '$location', 'api_url', 'api_version',
        function($http, $location, api_url, api_version) {

        var connexionFactory = {};

        /**
         * request a connexion
         * @param email of the user
         * @param password of the user
         * @returns {HttpPromise}
         */
        connexionFactory.getConnexion = function (email, password) {
            return $http.post(api_url+'/api/'+api_version + '/auth/login/', {
                email: email,
                password : password//,
                //withCredentials: true
            });
        };


        /**
         * Test authentication
         *
         * @returns {HttpPromise}
         */
        connexionFactory.testAuthentication = function () {
            return $http.get(api_url+'/api/'+api_version + '/auth/islogged');
        };

        connexionFactory.testRedirect = function() {
            connexionFactory.testAuthentication()
                .error(function() {
                    $location.path('/connexion');
                });
        };

        return connexionFactory;

    }]);

