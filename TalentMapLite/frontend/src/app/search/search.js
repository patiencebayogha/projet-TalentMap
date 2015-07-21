/**
 * Created by JBA3353 on 09/02/2015.
 */


'use strict';

angular.module('myApp.controllers.search', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/search', {
            templateUrl: 'app/search/search.html',
            controller: 'searchCtrl'
        });
    }])


    .controller('searchCtrl', [ '$scope', '$routeParams', '$location', '$timeout', 'userFactory', 'filtersService', 'usersPersistenceService', 'connexionFactory',
        function($scope, $routeParams, $location, $timeout, userFactory, filtersService, usersPersistenceService, connexionFactory) {

            connexionFactory.testRedirect();


            //go back to the profile page
            $scope.back = function () {
                $location.path('/me');
            };

            //search with the filters selected
            $scope.getResults = function(){

                //users found
                $scope.results = [];

                angular.forEach($scope.users, function (user, key) {
                    /* Valid by default, this is important.
                     * If no filters, then all users are selected.
                     */
                    var valid = true;

                    /* Check each filters */
                    $scope.filters = filtersService.getFiltersData();
                    angular.forEach($scope.filters, function (filter, key) {

                        var validFilter = false;

                        /* Check each filters for each skills */
                        angular.forEach(user.skills, function (skill, key) {

                            if (skill.skill == filter.skillName && skill.level >= filter.level) {
                                validFilter = true;
                            }
                        });
                        /*  Each filters should be positive */
                        if (!validFilter) {
                            valid = false;
                        }

                    });
                    /* add the user to the result if valid */
                    if (valid) {
                        $scope.results.push(user);
                    }
                });
            };

            //get the list of users
            $scope.users = {};
            usersPersistenceService.getPersistentUsers(function(data) {
                $scope.users = data;
                $scope.getResults();
            });

            //Get the filters for the search
            $scope.filters = filtersService.getFiltersData();

            //Add a filter to the search
            $scope.goFilter = function(){
                $location.path('/filters');
            };


            $scope.deleteFilter = function(skill){
                filtersService.removeFilter(skill, function(filters) {
                    $scope.filters = filters;
                    $scope.getResults();
                });
            };


            //go to the profile of the selected user
            $scope.accessProfile = function(email){
                userFactory.getUser(email)
                    .success(function (data) {
                        $location.path('/profile/'+email);
                    });
            };

            /* LOADING PROGRESS */
            $scope.loadingProgress = 0;
            $scope.onTimeout = function(){
                if($scope.loadingProgress >= 100) {
                    $timeout.cancel(myTimeout);
                }

                $scope.loadingProgress++;
                myTimeout = $timeout($scope.onTimeout,40);
            };
            var myTimeout = $timeout($scope.onTimeout,40);

            /* FILTER ANIMATION */
            filtersScrollAnimation();

            //if a filter is clicked, go to the filter page
            $scope.updateFilter = function(filter){
                filtersService.setUpdateFilter(filter);
                $location.path('/filters');
            }

        }])

    //do lazy loading for the list of users
    .directive('lazyLoadingDirective', function() {
        return function(scope, element, attrs) {
            if (scope.$last){
                lazyLoadingInit('skill-creation_apply');
            }
        };
    })



;
