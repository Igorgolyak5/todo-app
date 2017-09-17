// Gulpfile
var gulp = require('gulp'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat');

var connect = require('gulp-connect');
var bower = require('gulp-bower');
var jsSources = ['app/js/*.js', 'app/js/tasks/*.js', 'app/js/shared/*.js'],
    htmlSources = ['**/*.html'],
    cssSources = ['app/styles/*.css'],
    outputDir = 'assets';


gulp.task('copy', function() {
    gulp.src('index.html')
        .pipe(gulp.dest(outputDir))
});

gulp.task('css', function() {
    gulp.src(cssSources)
        .pipe(concat('main.css'))
        .pipe(gulp.dest(outputDir))
        .pipe(connect.reload())
});

gulp.task('js', function() {
    gulp.src(jsSources)
        .pipe(uglify())
        .pipe(concat('script.js'))
        .pipe(gulp.dest(outputDir))
        .pipe(connect.reload())
});

gulp.task('bower', function() {
    return bower();
});

gulp.task('watch', function() {
    gulp.watch(jsSources, ['js']);
    gulp.watch(htmlSources, ['html']);
    gulp.watch(cssSources, ['css']);
});

gulp.task('connect', function() {
    connect.server({
        root: '.',
        livereload: true,
        port: 9000
    })
});

gulp.task('html', function() {
    gulp.src(htmlSources)
        .pipe(connect.reload())
});

gulp.task('default', ['html', 'js', 'css', 'connect', 'watch']);
