var gulp = require('gulp');
var browserify = require('browserify');
var transform = require('vinyl-transform');

var dev_config = require('../dev_config.js');

gulp.task('browserify', ['copyDev'], function () {

    var browserified = transform(function(filename) {
        var b = browserify(filename);
        return b.bundle();
    });

    return gulp.src([
        dev_config.appDir + '/**/app/**/*.js',
    ])
        .pipe(browserified)
        .pipe(gulp.dest(dev_config.buildDir));
});
