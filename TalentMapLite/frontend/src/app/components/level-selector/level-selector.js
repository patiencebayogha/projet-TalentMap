/**
 * Created by JBA3353 on 02/02/2015.
 */

'use strict';

angular.module('myApp.components.levelSelector', [])

.directive('levelSelector', function () {
    return {
        restrict: 'A',
        templateUrl: 'app/components/level-selector/level-selector.html',
        link: function (scope, elem, attrs) {

            //levels of skills that can be selected
            scope.levels = [{
                name: 'Notions',
                stars: [1]
            },
            {
                name: 'Bases',
                stars: [1,2]
            },
            {
                name: 'Maîtrise',
                stars: [1,2,3]
            },
            {
                name: 'Bonne maîtrise',
                stars: [1,2,3,4]
            },
            {
                name: 'Expert',
                stars: [1,2,3,4,5]
            }];

            //default level
            scope.selected = '2';
            scope.skillLevelToggle(scope.selected);

            /**
             * update the level of the skill after a click
             * @param index number of stars selected-1
             */
            scope.toggle = function (index) {
                scope.selected = index+1;
                scope.skillLevelToggle(scope.selected);
            };
        }
    };
})
;
