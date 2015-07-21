/**
 * Created by JBA3353 on 02/02/2015.
 */

var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({lazy: false});

var prod_config = require('../prod_config.js');

gulp.task('connectProd', plugins.connect.server({
    root: [prod_config.buildDir],
    port: prod_config.port,
    livereload: prod_config.livereload
}));
