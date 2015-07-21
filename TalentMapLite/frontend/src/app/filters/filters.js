/**
 * Created by JBA3353 on 30/01/2015.
 */


'use strict';

angular.module('myApp.controllers.filters', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/filters', {
            templateUrl: 'app/filters/filters.html',
            controller: 'filtersCtrl'
        });
    }])


    .controller('filtersCtrl', [ '$scope', '$routeParams', '$location', '$filter', 'userFactory', 'categoryFactory', 'skillFactory', 'filtersService', 'connexionFactory',
        function($scope, $routeParams, $location, $filter, userFactory, categoryFactory, skillFactory, filtersService, connexionFactory) {

            connexionFactory.testRedirect()

            // Get Me
            $scope.me = {};
            userFactory.getMe().success(function (data) {
                $scope.me = data;
            });

            //get the list of categories for the skills
            categoryFactory.getCategories()
                .success(function (data) {
                    $scope.categories = data;
                });

            //check if the page is to update a filter or to add one
            $scope.skill = {};
            $scope.skill = filtersService.getUpdateFilter();

            //update the skill level
            $scope.skillLevelToggle = function (level) {
                $scope.skill.level = level;
            };

            $scope.skills = [];
            //get the list of skills in database
            skillFactory.getSkills().success(function (data) {
                $scope.skills = data;
            });


            //check the inputs for validation
            $scope.check = function (callback) {

                var valid = true;
                var formCategoryUid = document.forms['skillCreationForm']['categoryUid'];

                //if the category is correctly selected
                if(!$scope.skillCreationForm.categoryUid.$valid || !$scope.categories[$scope.skill.categoryUid]) {
                    formCategoryUid.classList.add('redBorder');
                    valid = false;
                } else {
                    formCategoryUid.classList.remove('redBorder');
                }

                var formskillName = document.forms['skillCreationForm']['skillName'];

                //if the skill is correctly selected
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


            //Check if the skill selected exists and is not already a filter
            $scope.skillExists = function (name, callback) {
                var valid = true;
                var formskillName = document.forms['skillCreationForm']['skillName'];

                //search for the skill selected in the list of skills
                var skills = $filter('filter')($scope.skills, {name: name});

                //search for the skill selected in the current list of filters
                var filter = $filter('filter')(filtersService.getFiltersData(), {skillName: name});
                var updateFilter = filtersService.getUpdateFilter();

                if(!skills.length || filter.length) {
                    formskillName.classList.add('redBorder');
                    valid = false;
                }

                if(valid && typeof(callback) == "function") {
                    callback();
                }
            };


            //when the submit button is clicked
            $scope.apply = function (name, level, categoryUid) {

                $scope.check(function() {
                    //if the filter is a new one, check if it does not exist already
                    $scope.skillExists(name, function () {
                        $scope.filter = {
                            categoryUid: categoryUid,
                            skillName: name,
                            level: level
                        }

                        //add the filter to the list of filters and go back to the search
                        filtersService.addFiltersData($scope.filter);
                        $location.path("/search");
                    });
                });
            };

            //autocompletion on the skill input
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

            //go back to the profile
            $scope.back = function () {
                virtualKeyboardFixPosReset();
                $location.path('/me');
            };

            //cancel effects of the keyboard on inputs (zoom)
            virtualKeyboardFixPosInit();

        }])



;
