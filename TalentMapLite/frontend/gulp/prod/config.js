/**
 * Created by JBA3353 on 02/02/2015.
 */

var gulp = require('gulp');
var gulpNgConfig = require('gulp-ng-config');

var prod_config = require('../prod_config.js');

gulp.task('configProd', function () {
    gulp.src([
            prod_config.appDir +'/prod_config-app.json'
        ])
        .pipe(gulpNgConfig('myApp.config'))
        .pipe(gulp.dest(prod_config.buildDir));
});
