/**
 * Controller needed for work with tasks.
 *
 * @author Igor Holiak
 */
angular.module('taskApp').controller("TaskController", ['$scope', 'taskService', function($scope, taskService) {
    $scope.tasks = [];
    $scope.activeModal = false;

    /**
     * Method needed for getting all tasks from back-end.
     */
    function getAll() {
        taskService.getAll().success(function(response) {
            $scope.tasks = response;
        });
    }
    getAll();

    /**
     * Method needed for update task.
     *
     * @param task
     */
    $scope.update = function (task) {
        taskService.update(task).success(function () {
            $scope.activeModal = false;
            getAll();
        });
    };

    /**
     * Method for change status.
     *
     * @param id identifier of task.
     */
    $scope.changeStatus = function (id) {
        taskService.changeStatus(id).success(function () {
            getAll();
        });
    };

    /**
     * Method for remove task by id.
     *
     * @param id identifier of task.
     */
    $scope.remove = function (id) {
        taskService.remove(id).success(function () {
            getAll();
        });
    };

    /**
     * Method for add new task.
     *
     * @param task
     */
    $scope.add = function (name, date) {
        taskService.add(name, date).success(function () {
            getAll();
        });
    };

    /**
     * Hide modal window.
     */
    $scope.hide = function(m){
        $scope.activeModal = false;
    };

    /**
     * Show modal window for update task.
     *
     * @param task
     */
    $scope.showModal = function (task) {
        $scope.activeModal = true;
        $scope.changeTask = angular.copy(task);
        $scope.changeTask.date =  moment(new Date(task.date), moment.ISO_8601).format('MM/DD/YYYY');
    };
}]);