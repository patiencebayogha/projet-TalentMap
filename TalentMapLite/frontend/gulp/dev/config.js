/**
 * Created by JBA3353 on 02/02/2015.
 */

var gulp = require('gulp');
var gulpNgConfig = require('gulp-ng-config');

var dev_config = require('../dev_config.js');

gulp.task('configDev', function () {
    gulp.src([
            dev_config.appDir +'/dev_config-app.json'
        ])
        .pipe(gulpNgConfig('myApp.config'))
        .pipe(gulp.dest(dev_config.buildDir+'/app/'));
});

