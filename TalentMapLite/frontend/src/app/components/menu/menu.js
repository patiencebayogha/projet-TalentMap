/**
 * Created by SSE3361 on 09/02/2015.
 */

angular.module('myApp.components.menu', ['ngAnimate'])

.directive('dropdownMenu', ['$document', '$location', function($document, $location) {

        return {
            templateUrl: 'app/components/menu/menu.html',
            link: function (scope, element) {

                //menu items
                scope.navLinks = [{
                    Title: 'me',
                    LinkText: 'Mon profil',
                    Image : 'images/account.png'
                }, {
                    Title: 'search',
                    LinkText: 'Rechercher',
                    Image : 'images/search.png'
                }];

                //if menu is open or not
                scope.close = true;

                //when menu icon is clicked, open or close the menu
                scope.toggleButton = function ()
                {
                    scope.close = !scope.close;
                };


                element.bind('click', function(e) {
                    // this part keeps it from firing the click on the document.
                    e.stopPropagation();

                    //Check if the item selected is not already the current page
                    if(e.target.className == "menu-name ng-binding" && e.target.parentElement.parentElement.href.split('#')[1] == $location.path()){
                        scope.close = true;
                        scope.$apply();
                    }
                });

                //Close the menu if user clicks outside
                $document.bind('click', function(){
                    scope.close = true;
                    scope.$apply();
                });

            }
        }
    }]);
