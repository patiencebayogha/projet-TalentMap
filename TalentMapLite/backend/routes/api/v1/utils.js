/**
 * Created by JBA3353 on 13/02/2015.
 */



var utils = {

    callback: function (err, items, res, debug) {
        if(err) {
            debug(err);
            res.json({code : 500, msg: 'error: ' + err});
        } else {
            res.json(items);
        }
    },


    bodyCheck: function (params, body, callback) {

        var valid = true;
        params.forEach(function(param) {
            if(!body[param]) {
                callback('parameter "'+param+'" was expected.');
                valid = false;
                return;
            }
        });

        if(valid) {
            callback();
        }
    }

};

module.exports = utils;
