
var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({lazy: false});

var prod_config = require('../prod_config.js');

gulp.task('copyIndex', function () {
    return gulp.src(prod_config.appDir + '/index.html')
    .pipe(plugins.template({
        styles: ['app.css'],
        scripts: ['app.js', 'global.js', 'prod_config-app.js'],
        templates: ['templates.js'],
        libs: ['lib.js']
    }))
    .pipe(gulp.dest(prod_config.buildDir));
});
