var continentApp = angular.module("continentApp", ['ngCookies']);

continentApp.controller("continentCtrl", function ($scope, $http, $cookies) {
    $http.get('http://localhost:8080/hash_passes').then(function (response) {
        var userRole = $cookies.get("user");
        if( userRole !== "ADMIN" ) {
            $scope.isAdmin = false
            $http.get('http://localhost:8080/continent/infections_current/' + window.location.href.substring(window.location.href.lastIndexOf('/'))).then(function (response) {
                $scope.continent = response.data;
                if ($scope.continent.length > 0) {
                    $scope.name = $scope.continent[0].country.name;
                    $scope.chart_data = {
                        "totalCases": [["Country", "totalCases"]],
                        "newCases": [["Country", "newCases"]],
                        "totalDeaths": [["Country", "totalDeaths"]],
                        "newDeaths": [["Country", "newDeaths"]],
                        "totalRecovered": [["Country", "totalRecovered"]],
                        "activeCases": [["Country", "activeCases"]],
                        "seriousCritical": [["Country", "seriousCritical"]],
                        "totCases1Mpop": [["Country", "totCases1Mpop"]],
                        "deaths1Mpop": [["Country", "deaths1Mpop"]],
                        "totalTests": [["Country", "totalTests"]],
                        "tests1Mpop": [["Country", "tests1Mpop"]]
                    };
                    for (var key in $scope.chart_data) {
                        var len = $scope.continent.length;
                        for (var i = 0; i < len; i++) {
                            $scope.chart_data[key].push([$scope.continent[i].country.name, $scope.continent[i][key]])
                        }
                        $scope.chart_data[key].sort(function (a, b) {
                            if (a[0] === "Country")
                                return 0;
                            return b[1] - a[1];
                        });
                    }
                }
            });
        } else {
            $scope.isAdmin = true
            $http.get('http://localhost:8080/continent/infections/' + window.location.href.substring(window.location.href.lastIndexOf('/'))).then(function (response) {
                $scope.data = response.data;
                $scope.continentTemp = {};
                var data_len = $scope.data.length;
                for(var i = 0; i < data_len; i++) {
                    if($scope.data[i].creationTime in $scope.continentTemp){
                        $scope.continentTemp[$scope.data[i].creationTime].push($scope.data[i]);
                    } else {
                        $scope.continentTemp[$scope.data[i].creationTime] = [$scope.data[i]];
                    }
                }
                $scope.continent = [];
                for(var key in $scope.continentTemp){
                    $scope.continent.push($scope.continentTemp[key]);
                }
                $scope.all_data = {}
                var continent_len = $scope.continent.length;
                for(var j = 0 ; j < continent_len; j++) {
                    $scope.all_data[$scope.continent[j][0].creationTime]={
                        "totalCases": [["Country", "totalCases"]],
                        "newCases": [["Country", "newCases"]],
                        "totalDeaths": [["Country", "totalDeaths"]],
                        "newDeaths": [["Country", "newDeaths"]],
                        "totalRecovered": [["Country", "totalRecovered"]],
                        "activeCases": [["Country", "activeCases"]],
                        "seriousCritical": [["Country", "seriousCritical"]],
                        "totCases1Mpop": [["Country", "totCases1Mpop"]],
                        "deaths1Mpop": [["Country", "deaths1Mpop"]],
                        "totalTests": [["Country", "totalTests"]],
                        "tests1Mpop": [["Country", "tests1Mpop"]]
                    };
                    for (var key in $scope.all_data[$scope.continent[j][0].creationTime]) {
                        len = $scope.continent[j].length;
                        for (i = 0; i < len; i++) {
                            $scope.all_data[$scope.continent[j][0].creationTime][key].push([$scope.continent[j][i].country.name, $scope.continent[j][i][key]])
                        }
                        $scope.all_data[$scope.continent[j][0].creationTime][key].sort(function (a, b) {
                            if (a[0] === "Country")
                                return 0;
                            return b[1] - a[1];
                        });
                    }
                }
                $scope.sorted = [];
                for(key in $scope.all_data) {
                    $scope.sorted[$scope.sorted.length] = key;
                }
                $scope.sorted.sort(function (a,b) {
                    return new Date(b) - new Date(a);
                });
                $scope.current_date = 0;
                $scope.chart_data = $scope.all_data[$scope.sorted[$scope.current_date]];
            });
        }
    });
    $scope.edit = true;
    $scope.error = false;
    $scope.incomplete = false;
    $scope.hideform = true;
    $scope.changeDate = function (date_d) {
        var new_date = $scope.current_date + date_d;
        if(new_date < $scope.sorted.length && new_date > -1) {
            $scope.current_date = new_date;
            $scope.chart_data = $scope.all_data[$scope.sorted[$scope.current_date]];
        }
        window.location = window.location;
    }
})