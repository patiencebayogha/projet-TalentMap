/**
 * Created by JBA3353 on 28/01/2015.
 */

'use strict';

angular.module('myApp.components.starRating', [])

.directive('starRating', function () {
    return {
        restrict: 'A',
        templateUrl: 'app/components/star-rating/star-rating.html',
        scope: {
            ratingValue: '=',
            editable: '=',
            onRatingSelected: '&'
        },
        link: function (scope, elem, attrs) {

            //max of 5 stars
            scope.max = 5;

            //filled the stars depending on the rating value selected
            var updateStars = function () {
                scope.stars = [];
                for (var i = 0; i < scope.max; i++) {
                    scope.stars.push({
                        filled: i < scope.ratingValue
                    });
                }
            };

            //when a star is clicked, update the rating value
            scope.toggle = function (index) {

                if(!scope.editable) {
                    return;
                }
                scope.ratingValue = index + 1;
                scope.onRatingSelected({
                    rating: index + 1
                });
            };

            //when the rating value changes
            scope.$watch('ratingValue', function (oldVal, newVal) {
                if (newVal) {
                    updateStars();
                }
            });

        }
    };
})

;
