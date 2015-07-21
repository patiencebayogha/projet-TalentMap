var gulp = require('gulp');
var dev_config = require('../dev_config.js');

gulp.task('copyImgDev', function () {
    return gulp.src([
        dev_config.appDir + '/**/images/**'
    ])
        .pipe(gulp.dest(dev_config.buildDir + '/stylesheets/'));
});


gulp.task('faviconDev', function () {
    return gulp.src([
        dev_config.appDir + '/favicon.ico',
        dev_config.appDir + '/favicon.png'
    ])
        .pipe(gulp.dest(dev_config.buildDir));
});
