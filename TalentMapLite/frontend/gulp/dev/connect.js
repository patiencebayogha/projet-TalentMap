/**
 * Created by JBA3353 on 02/02/2015.
 */

var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({lazy: false});

var dev_config = require('../dev_config.js');

gulp.task('connectDev', plugins.connect.server({
    root: [dev_config.buildDir],
    port: dev_config.port,
    livereload: dev_config.livereload
}));
