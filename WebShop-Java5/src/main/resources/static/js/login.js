
var app = angular.module('myApp', []);
app.controller('customersCtrl', function ($scope, $http, $window, $location, $rootScope) {
  $scope.user = [];
  $http.get("db/Students.json")
    .then(function (response) { $scope.user = response.data.listStudent; });
  $scope.name1;

  $scope.user2;


  //RETRIEVE VALUE
  //$scope.name = JSON.parse($window.sessionStorage.getItem('SavedString')) ;

  $scope.name2 = function () {
    if (localStorage.getItem('listStudent')) {

      $scope.name1 = JSON.parse(localStorage.getItem("listStudent"));
    }
    else {
      localStorage.setItem("listStudent", JSON.stringify($scope.user));
      $scope.name1 = JSON.parse(localStorage.getItem("listStudent"));
    }

    var a;
    for (var i = 0; i < $scope.user.length; i++) {
      if ($scope.user[i].username == $scope.Username && $scope.user[i].password == $scope.password) {
        $window.sessionStorage.setItem("taikhoan", JSON.stringify($scope.user[i]));
        // $cookies.putObject("taikhoan", $scope.user[i]);
        $window.location.href = 'index.html';
        alert('đăng nhập thành công');
        a = 0;
      }
    }
    if (a != 0) {
      alert('sai Username hoặc password ');
    }
  }
});
app.controller("session", function($scope){
  $scope.user = sessionStorage.getItem("taikhoan");
})
