var gulp = require('gulp');
var path = require('path');
var plugins = require('gulp-load-plugins')({lazy: false});

var dev_config = require('../dev_config.js');

global.cssFiles = [];

gulp.task('findCss', ['copyCssDev'], function () {
    return gulp.src([
        dev_config.buildDir + '/**/*.css'
    ])
    .on('data', function (data) {

        global.cssFiles.push(data.path.replace(/\\/g, "/").replace(path.normalize(__dirname+'/../../').replace(/\\/g, "/") + dev_config.buildDir, '')
            .replace('.scss', '.css'));
    });
});


gulp.task('copyCssDev', function () {
    return gulp.src([
        dev_config.appDir + '/stylesheets/**/*.scss'

    ])
        .pipe(plugins.rubySass({ style: 'expanded' }))
        .pipe(plugins.autoprefixer('last 2 version', 'safari 5', 'ie 8', 'ie 9', 'opera 12.1', 'ios 6', 'android 4'))
        .pipe(gulp.dest(dev_config.buildDir + '/stylesheets/'));
});
