
var gulp = require('gulp');
var browserify = require('browserify');
var transform = require('vinyl-transform');
var plugins = require('gulp-load-plugins')({lazy: false});

var prod_config = require('../prod_config.js');

 gulp.task('browserifyMin', function () {

    var browserified = transform(function(filename) {
        var b = browserify(filename);
        return b.bundle();
    });

    return gulp.src([
        prod_config.appDir + '/app/**/*.js'
    ])
    .pipe(browserified)
    .pipe(plugins.concat('app.js'))
    .pipe(plugins.uglify())
    .pipe(gulp.dest(prod_config.buildDir));
});
