/**
 * Created by SSE3361 on 11/02/2015.
 */

angular.module('myApp.services.filters', ['ngRoute', 'myApp.config'])

.service("filtersService", ['userFactory', '$filter', function(userFactory, $filter) {

        //Variable privée
        var filtersData = [];

        this.getFiltersData = function(){
            return filtersData;
        };

        //Add a filter to the list of filters
        this.addFiltersData = function(filter) {
            filtersData.push(filter);
        };

        this.updateFilter = function (filterName, level) {
            var filter = $filter('filter')(filtersData, {skillName: filterName});
            filter.level = level;
        }

        //Filter to update
        var updateFilter = {};

        this.getUpdateFilter = function() {
            return updateFilter;
        }

        this.setUpdateFilter = function(filter) {
            updateFilter = filter;
            filtersData.splice(filter);
        }

        var persistentUsers = [];
        this.getPersistentUsers = function(callback){

            if(persistentUsers.length == 0) {
                this.updatePersistentUsers(callback);
                return;
            }
            callback(persistentUsers);
        };

        //Add a filter to filters
        this.updatePersistentUsers = function(callback) {
            userFactory.listUsers().success(function (data) {
                persistentUsers = data;
                callback(data);
            });
        };

        this.updatePersistentUsers(function() {});
    }
]);
