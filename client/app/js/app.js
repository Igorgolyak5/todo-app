var app = angular.module("taskApp", []);

/**
 * Interceptor for adding custom logic to http request.
 */
app.factory('httpRequestInterceptor', function () {
    return {
        request: function (config) {
            config.headers['Access-Control-Allow-Origin'] = '*';
            config.headers['Content-Type'] = 'application/json';
            config.url =  "http://localhost:8080" + config.url;

            return config;
        }
    };
});

/**
 * Configuration http provider
 */
app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpRequestInterceptor');
}]);
