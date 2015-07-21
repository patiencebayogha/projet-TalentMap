var gulp = require('gulp');
var dev_config = require('../dev_config.js');

gulp.task('copyDev', function () {
    return gulp.src([
        dev_config.appDir + '/**/app/**/**',
        dev_config.appDir + '/**/js/**/**',
        dev_config.appDir + '/**/data/**/**',
        dev_config.appDir + '/**/images/**/**',
        '!'+dev_config.appDir + '/stylesheets/images/**/**'
    ])
        .pipe(gulp.dest(dev_config.buildDir));
});
