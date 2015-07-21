/**
 * Created by JBA3353 on 13/02/2015.
 */


angular.module('myApp.services.persistence.users', ['ngRoute', 'myApp.config'])

    .service("usersPersistenceService", ['userFactory',
        function(userFactory, usersPersistenceService) {

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
