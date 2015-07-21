

/**
* Requires
*/

/**
* Init vars
*/
var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({lazy: false});

var htmlreplace = require('gulp-html-replace');

var dev_config = require('./dev_config.js');


var fs = require('fs');
var onlyScripts = require('./util/scriptFilter');


/* REQUIRES */

var prod_tasks = fs.readdirSync('./gulp/prod/').filter(onlyScripts);
prod_tasks.forEach(function(task) {
    require('./prod/' + task);
});

var dev_tasks = fs.readdirSync('./gulp/dev/').filter(onlyScripts);
dev_tasks.forEach(function(task) {
    require('./dev/' + task);
});



/* TASKS */

gulp.task('clean', function () {
    return gulp.src([dev_config.buildDir], {read: false})
    .pipe(plugins.clean());
});


gulp.task('cleanBuild', function () {
    return gulp.src(dev_config.buildDir + '/**')
    .pipe(plugins.rimraf());
});


/* COMMON TASKS */

gulp.task('jshint', function () {
    //combine all js files of the app
    return gulp.src([
        dev_config.appDir + '/**/*.js'
    ])
    .pipe(plugins.jshint('.jshintrc'))
    .pipe(plugins.jshint.reporter('jshint-stylish'))
    .pipe(plugins.jshint.reporter('fail'));
});

gulp.task('templates', function () {
    //combine all template files of the app into a js file
    return gulp.src([
        dev_config.appDir + '/**/*.html'
    ])
    .pipe(plugins.angularTemplatecache('templates.js', {standalone:true}))
    .pipe(gulp.dest(dev_config.buildDir));
});

gulp.task('vendorJs', function () {
    //concatenate vendor JS files
    return gulp.src([
    ])
    .pipe(plugins.concat('lib.js'))
    .pipe(gulp.dest(dev_config.buildDir));
});

gulp.task('copyAngularApp', function () {
    return gulp.src([
        dev_config.appDir + '/app/**/*.html'
    ])
    .pipe(gulp.dest(dev_config.buildDir + '/app/'));
});


