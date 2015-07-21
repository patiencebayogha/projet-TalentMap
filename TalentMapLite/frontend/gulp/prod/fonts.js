
var gulp = require('gulp');
var prod_config = require('../prod_config.js');

gulp.task('copyFonts', function () {
    return gulp.src([
        prod_config.appDir + '/**/*.eot',
        prod_config.appDir + '/**/*.svg',
        prod_config.appDir + '/**/*.ttf',
        prod_config.appDir + '/**/*.woff'
    ])
    .pipe(gulp.dest(prod_config.buildDir));
});