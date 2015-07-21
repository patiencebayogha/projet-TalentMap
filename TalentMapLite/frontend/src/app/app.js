/**
 * Created by JBA3353 on 23/01/2015.
 */

'use strict';

require('angular');
require('angular-route');
require('angular-animate');



angular.module('myApp', [
    'myApp.config',

    'ngRoute',

    /* CONTROLLERS */
    'myApp.controllers.profile',
    'myApp.controllers.search',
    'myApp.controllers.skillCreation',
    'myApp.controllers.filters',
    'myApp.controllers.connexion',


    /* services */
    'myApp.services.users',
    'myApp.services.categories',
    'myApp.services.skills',
    'myApp.services.connexion',

    /* persistence */
    'myApp.services.persistence.filters',
    'myApp.services.persistence.users',

    /* components */
    'myApp.components.menu',
    'myApp.components.starRating',
    'myApp.components.levelSelector'
])


.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {

   $httpProvider.defaults.withCredentials = true;

    $routeProvider.
        otherwise({
            redirectTo: '/me'
        });
}])

.run(['connexionFactory', function(connexionFactory) {
    connexionFactory.testRedirect();
}])



;
