var gulp = require('gulp');


/**
* Builds compiled and minified sources
* @return {void}
*/
gulp.task('server_prod', ['build_prod'], function () {
    gulp.start('connectProd');
});

gulp.task('build_prod', ['configProd', 'copyAngularApp', 'browserifyMin', 'copyJs', 'templates', 'cssMin', 'copyFonts', 'imgMin', 'favicon', 'copyIndex', 'vendorJs']);

