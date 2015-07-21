var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({lazy: false});

var dev_config = require('../dev_config.js');

gulp.task('copyIndexDev', ['findCss', 'findJs'], function () {
    return gulp.src(dev_config.appDir + '/index.html')
        .pipe(plugins.template({
            styles: global.cssFiles,
            scripts: global.jsFiles,
            templates: [],
            libs: []
        }))
        .pipe(gulp.dest(dev_config.buildDir));
});
