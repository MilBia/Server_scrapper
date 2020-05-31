var auApp = angular.module("auApp", ['ngCookies']);

auApp.controller("auCtrl", function ($scope, $http, $cookies) {
    $http.get('http://localhost:8080/hash_passes').then(function (response) {
        var userRole = $cookies.get("user");
        if( userRole != null) {
            $scope.isLogIn = true;
            $scope.user = $cookies.get("username");
            if (userRole === "ADMIN") {
                $scope.isAdmin = true;
            } else {
                $scope.isAdmin = false
            }
        } else {
            $scope.isLogIn = false;
            $scope.user = "";
        }
    })
    $scope.logIn = function (username, password) {
        fetch('http://localhost:8080/login',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        }).then(function () {
            window.location = window.location;
        })
    }
    $scope.logOut = function () {
        $http.get('http://localhost:8080/logout').then(function (response) {})
    }
})
