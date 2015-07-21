var gulp = require('gulp');

gulp.task('config', function () {
    gulp.src('../dev_config-app.json')
        .pipe(ngConstant())
        // Writes config.js to dist/ folder
        .pipe(gulp.dest('dist'));
});


/**
* Runs localhost server with non compiled files
* @return {void}
*/
gulp.task('server_dev', ['build_dev'], function () {
    gulp.start('connectDev');
    gulp.start('watch_dev');
});

gulp.task('build_dev', ['configDev', 'browserify', 'copyCssDev', 'copyImgDev', 'faviconDev', 'copyFontsDev', 'copyIndexDev', 'vendorJs']);

