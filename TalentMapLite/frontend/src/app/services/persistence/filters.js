/**
 * Created by SSE3361 on 11/02/2015.
 */

angular.module('myApp.services.persistence.filters', ['ngRoute', 'myApp.config'])

.service("filtersService", ['skillFactory',
    function(skillFactory, filtersService) {

        //Variable privée
        var filtersData = [];

        this.getFiltersData = function(){
            return filtersData;
        };

        //Add a filter to the list of filters
        this.addFiltersData = function(filter) {
            filtersData.push(filter);
            updateFilter = {};
        };

        //Filter to update
        var updateFilter = {};

        this.getUpdateFilter = function() {
            return updateFilter;
        };

        //set the filter to update and remove it from the list of filters
        this.setUpdateFilter = function(filter) {
            updateFilter = filter;
            var keepedFilters = [];
            angular.forEach(filtersData, function(currentFilter, key) {

                if(filter !== currentFilter)
                {
                    keepedFilters.push(currentFilter);
                }
            });
            filtersData = keepedFilters;
        };

        //Remove a filter from the list
        this.removeFilter = function(skillName, callback) {

            var keepedFilters = [];
            angular.forEach(filtersData, function(filter, key) {

                if(filter.skillName !== skillName)
                {
                    keepedFilters.push(filter);
                }
            });

            filtersData = keepedFilters;
            callback(keepedFilters);
        };

    }
]);
