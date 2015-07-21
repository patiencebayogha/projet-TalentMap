var express = require('express');
var path = require('path');

//var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var compress = require('compression');

var passport = require('passport');
var expressSession = require('express-session');


var mongoose = require('mongoose');



// DB init
var DBinit = require('./data/DBinit.js');

// API
var ApiVersion = 'v1';
var ApiPath = 'api/'+ApiVersion;


var app = express();

// http/https setup
var keyFile   = __dirname+'/cert/key.pem';
var certFile  = __dirname+'/cert/key.crt';
app.set('key_file', keyFile);
app.set('cert_file', certFile);

// body parser setup
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(compress());

// Configuring Passport
app.use(expressSession(
    {
        secret : 'this is a secret',
        cookie : {
            httpOnly: false,
            maxAge: 3600000
        }
    }
));

app.use(passport.initialize());
app.use(passport.session());


// Auth
var authStrategy = require('./services/passport/auth.js');

passport.serializeUser(authStrategy.serializeUser);
passport.deserializeUser(authStrategy.deserializeUser);

passport.use('login', authStrategy.loginStrategy);


// Routes
var authRoute = require('./routes/'+ApiPath+'/auth')(passport);
var usersRoute = require('./routes/'+ApiPath+'/users');
var skillsRoute = require('./routes/'+ApiPath+'/skills');



var env = (process.env.NODE_ENV == 'production') ? 'production' : 'development';
var config;

// NODE_ENV=development only development profile
if ('development' == env) {

    config = require('./config-dev.js');
    app.use(logger('dev'));

    console.info('Development profile');
}

// NODE_ENV=production only production profile
if ('production' == env) {

    config = require('./config-prod.js');
    console.info('Production profile');
}

app.use('/data', express.static(__dirname + '/data/public'));
var db = mongoose.connect('mongodb://'+config.mongoServer+'/'+config.mongoDatabase);

if ('development' == env) {

    DBinit.drop();
    DBinit.apply();
}


// profile settings
app.set('use_https', config.use_https);
app.set('port', config.port);

// Routes
app.use('/'+ApiPath+'/auth', authRoute);
app.use('/'+ApiPath+'/users', usersRoute);
app.use('/'+ApiPath+'/skills', skillsRoute);



// catch 404 and forward to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500)
            .send({
                message: err.message,
                error: err
            });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500)
        .send({
            message: err.message,
            error: {}
        });
});


module.exports = app;
