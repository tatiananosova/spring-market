angular.module('app', ['ngStorage'])
    .controller('indexController', function ($scope, $http, $location, $localStorage) {

    const contextPath = 'http://localhost:8086/api/v1'

    $scope.loadProducts = function (pageNumber) {
        console.log("call")
        $http({
            url: "/api/v1/product",
            method: "GET"
            // params: {
            //     p: page
            // }
        }).then(function (response) {
            $scope.products = response.data;
            console.log($scope.products)
        });
    };

    $scope.saveProduct = function () {
        console.log($scope.newProduct)
        $http.post(contextPath + "/product", $scope.newProduct)
            .then(function successCallback(response) {
                console.log(response);
                $scope.loadProducts(1);

            }, function errorCallback(response) {
                console.log(response)

            });
    };

    $scope.loadProducts(1)

});