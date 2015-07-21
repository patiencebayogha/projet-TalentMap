var gulp = require('gulp');
var dev_config = require('../dev_config.js');

gulp.task('copyFontsDev', function () {
    return gulp.src([
        dev_config.appDir + '/fonts/*.eot',
        dev_config.appDir + '/fonts/*.svg',
        dev_config.appDir + '/fonts/*.ttf',
        dev_config.appDir + '/fonts/*.woff'
    ])
        .pipe(gulp.dest(dev_config.buildDir + '/stylesheets/fonts/'));
});