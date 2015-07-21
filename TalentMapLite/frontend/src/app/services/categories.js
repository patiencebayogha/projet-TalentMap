
/**
 * Created by JBA3353 on 26/01/2015.
 */

'use strict';

angular.module('myApp.services.categories', ['ngRoute', 'myApp.config'])

    .factory('categoryFactory', ['$http', 'api_url', function($http, api_url) {

        var categoryFactory = {};

        /**
         * get the list of categories
         * @returns {HttpPromise}
         */
        categoryFactory.getCategories = function () {
            return $http.get(api_url + '/data/categories.json');
        };

        return categoryFactory;

    }]);
