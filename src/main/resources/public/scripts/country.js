var countryApp = angular.module("countryApp", ['ngCookies']);

countryApp.controller("countryCtrl", function ($scope, $http, $cookies) {
    $http.get('http://localhost:8080/hash_passes').then(function (response) {
        var userRole = $cookies.get("user");
        if( userRole !== "ADMIN" ) {
            $scope.isAdmin = false
            $http.get('http://localhost:8080/countries/infections_current/' + window.location.href.substring(window.location.href.lastIndexOf('/'))).then(function (response) {
                $scope.country = response.data;
                $scope.chart_data = {
                    "totalCases": [
                        ["Country", "totalCases"],
                        ["totalCases", $scope.country.totalCases],
                        ["healthy", $scope.country.population - $scope.country.totalCases]
                    ],
                    "patientsResults": [
                        ["Country", "patientsResults"],
                        ["totalDeaths", $scope.country.totalDeaths],
                        ["totalRecovered", $scope.country.totalRecovered],
                        ["activeCases", $scope.country.activeCases]
                    ],
                    "patientsStates": [
                        ["Country", "patientsStates"],
                        ["seriousCritical", $scope.country.seriousCritical],
                        ["other", $scope.country.activeCases - $scope.country.seriousCritical]
                    ]
                };
                for(var key in $scope.chart_data) {
                    $scope.chart_data[key].sort(function (a, b) {
                        if(a[0] === "Country")
                            return 0;
                        return b[1] - a[1];
                    });
                }
                $scope.all_data = null;
            })
        } else {
            $scope.isAdmin = true;
            $http.get('http://localhost:8080/countries/infections/' + window.location.href.substring(window.location.href.lastIndexOf('/'))).then(function (response) {
                $scope.country = response.data;
                $scope.all_data = {}
                var len = $scope.country.length;
                for(var i = 0 ; i < len; i++) {
                    $scope.all_data[$scope.country[i].creationTime]={
                        "totalCases": [
                            ["Country", "totalCases"],
                            ["totalCases", $scope.country[i].totalCases],
                            ["healthy", $scope.country[i].population - $scope.country[i].totalCases]
                        ],
                        "patientsResults": [
                            ["Country", "patientsResults"],
                            ["totalDeaths", $scope.country[i].totalDeaths],
                            ["totalRecovered", $scope.country[i].totalRecovered],
                            ["activeCases", $scope.country[i].activeCases]
                        ],
                        "patientsStates": [
                            ["Country", "patientsStates"],
                            ["seriousCritical", $scope.country[i].seriousCritical],
                            ["other", $scope.country[i].activeCases - $scope.country[i].seriousCritical]
                        ]
                    }
                    for(var key in $scope.all_data[$scope.country[i].creationTime]) {
                        $scope.all_data[$scope.country[i].creationTime][key].sort(function (a, b) {
                            if(a[0] === "Country")
                                return 0;
                            return b[1] - a[1];
                        });
                    }
                }
                $scope.sorted = [];
                for(var key in $scope.all_data) {
                    $scope.sorted[$scope.sorted.length] = key;
                }
                $scope.sorted.sort(function (a,b) {
                    return new Date(b) - new Date(a);
                });
                $scope.current_date = 0;
                $scope.chart_data = $scope.all_data[$scope.sorted[$scope.current_date]];
            })
        }
    });
    $scope.edit = true;
    $scope.error = false;
    $scope.incomplete = false;
    $scope.hideform = true;
    $scope.changeDate = function (date_d) {
        var new_date = $scope.current_date + date_d;
        if(new_date < $scope.sorted.length && new_date > -1) {
            console.log(date_d);
            $scope.current_date = new_date;
            $scope.chart_data = $scope.all_data[$scope.sorted[$scope.current_date]];
        }
        window.location = window.location;
    }
})