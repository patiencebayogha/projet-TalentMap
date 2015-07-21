
var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({lazy: false});

var prod_config = require('../prod_config.js');

gulp.task('cssMin', function () {
    var minifyCSS = require('gulp-minify-css');
    return gulp.src([
        prod_config.appDir + '/stylesheets/**/*.scss'
    ])
    .pipe(plugins.rubySass({ style: 'expanded' }))
    .pipe(plugins.autoprefixer('last 2 version', 'safari 5', 'ie 8', 'ie 9', 'opera 12.1', 'ios 6', 'android 4'))
    .pipe(plugins.concat('app.css'))
    .pipe(minifyCSS())
    .pipe(gulp.dest(prod_config.buildDir));
});
