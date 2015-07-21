/**
 * Created by JBA3353 on 30/01/2015.
 */


'use strict';

angular.module('myApp.controllers.connexion', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/connexion', {
            templateUrl: 'app/connexion/connexion.html',
            controller: 'connexionCtrl'
        });
    }])


    .controller('connexionCtrl', [ '$scope', '$routeParams', '$location', 'connexionFactory',
        function($scope, $routeParams, $location, connexionFactory) {

            var formEmail = document.forms['connexionForm']['email'];
            var formPass = document.forms['connexionForm']['password'];

            $scope.connexion = function() {

                if(!$scope.check()) {
                    $scope.alertMsg = 'Bad email / password';
                    return;
                }

                connexionFactory.getConnexion($scope.email, $scope.password)
                    .success(function () {
                        $location.path('/me');
                    })
                    .error(function(data, status, headers, config) {
                        if(status == 401) {
                            $scope.alertMsg = 'Bad email / password';
                            $scope.toggle(false);
                        }
                    });
            };

            $scope.check = function() {

                var valid = true;
                if ($scope.connexionForm.email.$valid) {
                    formEmail.classList.remove('redBorder');
                } else {
                    formEmail.classList.add('redBorder');
                    valid = false;
                }

                if ($scope.connexionForm.password.$valid) {
                    formPass.classList.remove('redBorder');
                } else {
                    formPass.classList.add('redBorder');
                    valid = false;
                }
                return valid;
            };

            $scope.toggle = function(b) {

                if(!b) {
                    formEmail.classList.add('redBorder');
                    formPass.classList.add('redBorder');
                } else {
                    formEmail.classList.remove('redBorder');
                    formPass.classList.remove('redBorder');
                }

            };

        }])
;
