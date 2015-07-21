
var gulp = require('gulp');
var imagemin = require('gulp-imagemin');

var prod_config = require('../prod_config.js');

gulp.task('imgMin', function () {
    return gulp.src([
        prod_config.appDir + '/images/**',
        prod_config.appDir + '/stylesheets/images/**'
    ])
    .pipe(imagemin())
    .pipe(gulp.dest(prod_config.buildDir + '/images'));
});


gulp.task('favicon', function () {
    return gulp.src([
        prod_config.appDir + '/favicon.ico',
        prod_config.appDir + '/favicon.png'
    ])
        .pipe(imagemin())
        .pipe(gulp.dest(prod_config.buildDir));
});
