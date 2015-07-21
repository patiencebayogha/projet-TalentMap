/**
 * Created by JBA3353 on 12/02/2015.
 */

var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({lazy: false});

var prod_config = require('../prod_config.js');

gulp.task('copyJs', function () {
    return gulp.src([
        prod_config.appDir + '/js/*.js'
    ])
        .pipe(plugins.concat('global.js'))
        .pipe(plugins.uglify())
        .pipe(gulp.dest(prod_config.buildDir));
});
