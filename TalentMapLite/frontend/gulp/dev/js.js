var gulp = require('gulp');
var path = require('path');
var dev_config = require('../dev_config.js');

global.jsFiles = [];

gulp.task('findJs', ['copyDev'], function () {
    return gulp.src([
        dev_config.buildDir + '/**/*.js'
    ])
    .on('data', function (data) {
        global.jsFiles.push(data.path.replace(/\\/g, "/").replace(path.normalize(__dirname+'/../../').replace(/\\/g, "/") + dev_config.buildDir, ''));
    });
});