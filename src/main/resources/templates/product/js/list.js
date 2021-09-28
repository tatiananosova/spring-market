angular.module('list', ['ngStorage'])
    .controller('listController', function ($scope, $http) {

        $scope.loadProducts = function (pageNumber) {
            console.log("call")
            $http({
                url: "/api/v1/product",
                method: "GET"
            }).then(function (response) {
                $scope.products = response.data;
                console.log($scope.products)
            });
        };

        $scope.loadProducts(1)

});