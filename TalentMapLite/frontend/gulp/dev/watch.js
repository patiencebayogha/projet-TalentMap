var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({lazy: false});

var dev_config = require('../dev_config.js');



gulp.task('watch_dev', function () {
    gulp.watch([
        'build/**/*.html',
        'build/**/*.js',
        'build/**/*.css'
    ], function(event) {
        return gulp.src(event.path)
        .pipe(plugins.connect.reload());
    });

    gulp.watch([dev_config.appDir + '/**/*.js'], ['browserify']);
    gulp.watch([dev_config.appDir + '/**/*.html'], ['browserify']);
    gulp.watch(dev_config.appDir + '/**/*.scss', ['copyCssDev']);
    gulp.watch(dev_config.appDir + '/index.html', ['copyIndexDev']);
    gulp.watch(dev_config.appDir + '/**/images/**', ['copyImgDev']);
});
