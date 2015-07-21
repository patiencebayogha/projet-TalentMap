/**
 * Created by JBA3353 on 26/01/2015.
 */

'use strict';

angular.module('myApp.controllers.profile', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/me', {
        templateUrl: 'app/profile/profile.html',
        controller: 'profileCtrl'
    });

    $routeProvider.when('/profile/:email', {
        templateUrl: 'app/profile/profile.html',
        controller: 'profileCtrl'
    });
}])



.controller('profileCtrl', [ '$scope', '$routeParams', '$location', '$filter', 'userFactory', 'categoryFactory', 'connexionFactory',
        function($scope, $routeParams, $location, $filter, userFactory, categoryFactory, connexionFactory) {

            connexionFactory.testRedirect();


            console.log('profileCtrl');

            // Get Me
            $scope.me = {};
            userFactory.getMe().success(function (data) {

                $scope.me = data;
                $scope.user = {};

                // Get Categories
                $scope.getCategories = function() {
                    $scope.user.categories = {};
                    categoryFactory.getCategories()
                        .success(function (data) {

                            $scope.categories = [];

                            // Foreach user categories, set the related skills
                            angular.forEach(data, function (category, uuid) {

                                var skills = $filter('filter')($scope.user.skills, {category: uuid});
                                $scope.categories.push({category: category, skills: skills});
                            });
                        });
                };

                if ($routeParams.email !== undefined) {
                    $scope.email = $routeParams.email;

                    //get the user of the current profile
                    userFactory.getUser($scope.email)
                        .success(function (data) {
                            $scope.user = data;
                            $scope.getCategories();
                        });

                } else {
                    // default page is mine
                    $scope.email = $scope.me.email;
                    $scope.user = $scope.me;
                    $scope.getCategories();
                }

                // only my profile is editable
                if ($scope.me.email == $scope.email) {
                    $scope.editable = true;
                }


                //got to the skill page
                $scope.addSkill = function () {
                    headerScrollAnimationReset();
                    $location.path('/me/skillCreation');
                };

                // Remove a skill
                $scope.deleteSkill = function (skill, skillIndex, categoryIndex) {
                    userFactory.deleteUserSkill($scope.me.email, skill)
                        .success(function (data) {
                            $scope.categories[categoryIndex].skills.splice(skillIndex, 1);
                        });
                };

                //update the level of the skill after clicking on stars
                $scope.getSelectedRating = function (skill, rating) {
                    userFactory.updateUserSkill($scope.me.email, skill, rating);
                };


                headerScrollAnimationInit();

                //the user can edit his name and surname
                $scope.editingName = false;
                $scope.editingSurname = false;

                $scope.editName = function() {
                    if($scope.editable) {
                        $scope.editingName = true;
                        $scope.editingSurname = false;
                    }
                };

                $scope.editSurname = function() {
                    if($scope.editable){
                        $scope.editingSurname = true;
                        $scope.editingName = false;
                    }
                };

                $scope.doneEditingSurname = function() {
                    $scope.editingSurname = false;
                    userFactory.updateMyName($scope.me.email, $scope.user.name, $scope.user.surname);

                };

                $scope.doneEditingName = function() {
                    $scope.editingName = false;
                    userFactory.updateMyName($scope.me.email, $scope.user.name, $scope.user.surname);
                };

            });

        }]);

