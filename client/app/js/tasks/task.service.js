/**
 * Service needed for executing http request to back-end and processing business logic.
 *
 * @author Igor Holiak
 */
angular.module('taskApp').service("taskService", ['$http', function($http) {

    /**
     * Method for getting all task.
     *
     * @returns {HttpPromise}
     */
    this.getAll = function() {
        return $http.get("/tasks/list");
    };

    /**
     * Method needed for remove task.
     *
     * @param id - identifier.
     * @returns {HttpPromise}
     */
    this.remove = function (id) {
        return $http.post("/tasks/remove/" + id, {});
    };

    /**
     * Method changing status in task.
     *
     * @param id identifier of task.
     * @returns {HttpPromise}
     */
    this.changeStatus = function (id) {
        return $http.post("/tasks/change-status/" + id, {});
    };

    /**
     * Method needed for adding new task.
     *
     * @param name of task.
     * @param date of task.
     * @returns {HttpPromise}
     */
    this.add = function (name,date) {
        return $http.post("/tasks/add/", {name : name, date : new Date(date)});
    };

    /**
     * Method needed for update task.
     *
     * @param task task
     * @returns {HttpPromise}
     */
    this.update = function (task) {
        task.date = new Date(task.date);
        return $http.post("/tasks/update/", task);
    };
}]);