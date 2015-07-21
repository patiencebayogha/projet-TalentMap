/**
 * Created by JBA3353 on 30/01/2015.
 */


'use strict';

angular.module('myApp.controllers.skillCreation', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/me/skillCreation', {
            templateUrl: 'app/skill-creation/skill-creation.html',
            controller: 'skillCreationCtrl'
        });
    }])


    .controller('skillCreationCtrl', [ '$scope', '$routeParams', '$location', 'userFactory', 'categoryFactory', 'skillFactory', 'connexionFactory',
        function($scope, $routeParams, $location, userFactory, categoryFactory, skillFactory, connexionFactory) {

            connexionFactory.testRedirect();

            // Get Me
            $scope.me = {};
            userFactory.getMe().success(function (data) {
                $scope.me = data;
            });

            //get the list of categories
            categoryFactory.getCategories()
                .success(function (data) {
                    $scope.categories = data;
                });

            $scope.skill = {};

            //update the level of the skill
            $scope.skillLevelToggle = function (level) {
                $scope.skill.level = level;
            };

            //check inputs before validation
            $scope.check = function (callback) {

                var valid = true;
                var formCategoryUid = document.forms['skillCreationForm']['categoryUid'];

                //if category is correctly selected
                if(!$scope.skillCreationForm.categoryUid.$valid || !$scope.categories[$scope.skill.categoryUid]) {
                    formCategoryUid.classList.add('redBorder');
                    valid = false;
                } else {
                    formCategoryUid.classList.remove('redBorder');
                }

                //if skill is correctly selected
                var formskillName = document.forms['skillCreationForm']['skillName'];
                if(!$scope.skillCreationForm.skillName.$valid) {
                    formskillName.classList.add('redBorder');
                    valid = false;
                } else {
                    formskillName.classList.remove('redBorder');
                }

                if(valid && typeof(callback) == "function") {
                    callback();
                }
            };

            //when the button submit is clicked
            $scope.apply = function () {

                $scope.check(function() {

                    //add the new skill to the user
                    userFactory.addUserSkill(
                        $scope.me.email,
                        $scope.skill.skillName,
                        $scope.skill.level,
                        $scope.skill.categoryUid
                    )
                        .success(function(){
                            virtualKeyboardFixPosReset();
                            $location.path('/me');
                        });
                });
            };

            //autocompletion for the skill input
            $scope.autocompletion = {};
            $scope.categoryChange = function() {

                $scope.check();

                if($scope.skillCreationForm.categoryUid.$valid && $scope.categories[$scope.skill.categoryUid]) {

                    skillFactory.getSkillsByCategory($scope.skill.categoryUid)
                        .success(function (data) {
                            $scope.autocompletion.skills = data;
                        });
                }

            };

            //go back to the profile page
            $scope.back = function () {
                virtualKeyboardFixPosReset();
                $location.path('/me');
            };


            virtualKeyboardFixPosInit();

        }])



;
