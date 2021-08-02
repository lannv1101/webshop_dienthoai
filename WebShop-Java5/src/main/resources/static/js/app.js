var app = angular.module("myApp", ["ngRoute","ngCookies"]);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'home.html'

        })
        .when('/subjects', {
            templateUrl: 'subjects.html',
            controller: "subjectCtrl"
        })
        .when('/feedback', {
            templateUrl: 'feeback1.html',
          
        })
        .when("/quiz/:id/:name", {
            templateUrl: "quiz-app.html",
            controller: "quizsCtrl"
        })

})
app.controller("subjectCtrl", function ($scope, $http) {
    $scope.list_subject = list;
    // $scope.list_subject = [];
    // $http.get("../db/Subjects.js").then(function (response) { ///sua cho nay de chay phan trang
    //     $scope.list_subject = response.data;
    // })
    $scope.begin = 0;
    $scope.pageCount = Math.ceil($scope.list_subject.length / 6);

    $scope.first = function () {
        $scope.begin = 0;
    }
    $scope.prev = function () {
        if ($scope.begin > 0) {
            $scope.begin -= 6;
        }
    }
    $scope.next = function () {
        if ($scope.begin < ($scope.pageCount - 1) * 6) {
            $scope.begin += 6;
        }
    }
    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * 6;
    }
});

app.controller("quizsCtrl", function ($scope, $http, $routeParams, quizFactory) {
    $http.get("../db/Quizs/" + $routeParams.id + ".js").then(function (res) {
        //     // $scope.list_subject=res.data;

        quizFactory.questions = res.data;
    });





});


app.directive('quizfpoly', function (quizFactory, $routeParams, $interval,$cookies) {
    return {
        restrict: 'AE',
        scope: {},
        templateUrl: 'teamplate-quiz.html',
        link: function (scope, elem, attrs) {
            
            scope.start = function () {
                if ($cookies.getObject('taikhoan')==null) {
                    alert("Vui lòng đăng nhập để thực hiện bài kiểm tra");
                    return;
                }else{
                    quizFactory.getQuestions().then(function () {
                        scope.subjectsName = $routeParams.name
                        scope.id = 1;
                        scope.inProgess = true;
                        scope.getQuestion();
                        scope.quizOver = false;
                        scope.startCount();
                       
    
    
    
                    });

                }

               


            };
             //starttime
             scope.counter = 600;
             var promise;
             scope.startCount = function () {
                 $interval.cancel(promise);
                 promise = $interval(function () {
                     console.log(scope.counter--)
                     if (scope.counter == 0) {
                         scope.stopCount();
                     }
                 }, 1000);
             }
             scope.stopCount = function () {
                 $interval.cancel(promise);
                 scope.quizOver = true;
                 scope.counter =600;
             }
            scope.reset = function () {
                scope.inProgess = false;
                scope.score = 0;
            };

            scope.getQuestion = function () {
                var quiz = quizFactory.getQuestion(scope.id);

                if (quiz) {
                    scope.question = quiz.Text
                    scope.options = quiz.Answers;
                    scope.answer = quiz.AnswerId;

                    //scope.answerMode=true;
                } else {
                    scope.quizOver = true;

                }

            }
            var kq = document.getElementById("submit");
            scope.checkAnswer = function () {


                if (!$("input[name=answer]:checked").length)
                    return;


                var ans = $("input[name=answer]:checked").val();

                if (ans == scope.answer) {

                    scope.score += 10;
                    scope.correctAns = true;
                    kq.textContent = 'Đúng rồi'
                } else {
                    scope.correctAns = false;
                    kq.textContent = 'Không đúng rồi'

                }

                //scope.answerMode=false;

            }
            scope.reset();

            var id = 0;

            scope.nextQuestion = function () {
                var selectedOption = document.querySelector('input[type=radio]:checked');
                if (!selectedOption) {
                    alert('Please select your answer!');
                    return;
                }
                scope.id++;
                scope.getQuestion()
                kq.textContent = ''


            }
            scope.previewQuestion = function () {
                if (!selectedOption) {
                    alert('Please select your answer!');
                    return;
                }

                scope.id--;
                scope.getQuestion()

            }



        }
    }

});
app.filter('counter', [function () { //Filter trong AngularJs thường được dùng để lọc hoặc format dữ liệu.
    return function (seconds) {
        return new Date(1970, 0, 1).setSeconds(seconds);
    };
}])
app.factory("quizFactory", function ($http, $routeParams) {

    return {


        getQuestions: function () {
            return $http.get('../db/Quizs/' + $routeParams.id + '.js').then(function (res) {

                questions = res.data;
            });

        },
        getQuestion: function (id) {
            var randomItem = questions[Math.floor(Math.random() * questions.length)];
            var nextButton = document.getElementById("nextButton");
            var count = questions.length;
            if (count > 11) {

                count = 11;



            }

            if (id < 11) {

                if (id == 10) {
                    nextButton.textContent = "Finish";

                } else {
                    nextButton.textContent = "Next Question";

                }
                return randomItem;
            }

            else {

                return false;
            }



        }
    }

});

app.controller('customersCtrl', function ($scope, $http, $window,$cookies) {
    $scope.user = [];
    $http.get("db/Students.json")
      .then(function (response) { $scope.user = response.data.listStudent; });
    $scope.name1;
  
    $scope.user2;
    if ($cookies.getObject('taikhoan')==null) {
        $scope.isLogin=false;
        //  alert('cookies null');
        //  $window.location.href = 'index.html';


        
    }else{
        $scope.isLogin=true;
    }
    

  
  
    //RETRIEVE VALUE
    //$scope.name = JSON.parse($window.sessionStorage.getItem('SavedString')) ;
  
    $scope.login = function () {
    //   if (localStorage.getItem('listStudent')) {
  
    //     $scope.name1 = JSON.parse(localStorage.getItem("listStudent"));
    //   }
    //   else {
    //     localStorage.setItem("listStudent", JSON.stringify($scope.user));
    //     $scope.name1 = JSON.parse(localStorage.getItem("listStudent"));
    //   }
  
      var a;
      for (var i = 0; i < $scope.user.length; i++) {
        if ($scope.user[i].username == $scope.Username && $scope.user[i].password == $scope.password) {
        //   $window.sessionStorage.setItem("taikhoan", JSON.stringify($scope.user[i]));
          $cookies.putObject("taikhoan", $scope.user[i]);
          $window.location.href = 'index.html';
          alert('Đăng nhập thành công');
          $scope.isLogin=true;
          

          a = 0;
         
         
        }
      }
    //   if ($cookies.getObject('taikhoan')==null) {
    //     $scope.isLogin=false;
    //      alert('cookies null');
        
    // }else{
    //     $scope.isLogin=true;
    // }
      
     
      if (a != 0) {
        alert('Sai tên tài khoản hoặc mật khẩu ');
        $scope.isLogin= false;
      
      }
    }


    $scope.logoff= function(){
    
        $cookies.remove("taikhoan");
        $scope.isLogin= false;
        // $window.location.href = 'index.html';
        alert('đăng xuất thành công');
    }
    // $scope.checkLogin = function(){
    //     if ($cookies.getObject('taikhoan')==null) {
    //         $scope.isLogin=false;
    //          alert('cookies null');
            
    //     }else{
    //         $scope.isLogin=true;
    //     }
    // }
  });
  app.controller("cookies", function($scope, $cookies){
    $scope.taikhoan = $cookies.getObject('taikhoan');
  })
app.controller("changeprofileCtrl",function ($scope, $http,$cookies,$routeParams,$rootScope){
        // var idchange =$scope.taikhoan.id
    //    var id = $scope.taikhoan.id = $cookies.getObject('taikhoan')
    $http.get("http://localhost:3000/listStudent").then(function(res){
        $scope.listStudents = res.data;
        $rootScope.student1 = $cookies.getObject("taikhoan");
        // $rootScope.student = $rootScope.student1.id;
    });


    $scope.putdata = function(even){
        var data={
           
            // id : $scope.id,
            username: $rootScope.student1.username , // lay thong tin tk cu
            password: $scope.password,
            fullname: $scope.fullname,
            email: $scope.email,
            gender: $scope.gender,
            date: $scope.date,
    
    
        }
        $http.put(`http://localhost:3000/listStudent/${$rootScope.student1.id}`,data).then(function(res){
            $cookies.putObject("taikhoan",data);
        alert("Cập nhật thành công");
    
        },function(error){
            alert("Cập nhật không thành công");
        }
        
        )
    }
    $scope.changepass = function(even){

        if ($rootScope.student1.password == $scope.password && $scope.password1 == $scope.password2) {
            var data={
           
                // id : $scope.id,
                username: $rootScope.student1.username , // lay thong tin tk cu
                password: $scope.password2,
                fullname: $rootScope.student1.fullname,
                email: $rootScope.student1.email,
                gender: $rootScope.student1.gender,
                date: $rootScope.student1.date,
                id: $rootScope.student1.id
               
        
        
            }
            $http.put(`http://localhost:3000/listStudent/${$rootScope.student1.id}`,data).then(function(res){
        
            alert("Cập nhật mật khẩu thành công");
                $cookies.putObject("taikhoan",data);
        
            },function(error){
                alert("Cập nhật mật khẩu không thành công");
            }
            
            )
        }else{
            alert("Mật khẩu cũ không khớp hoặc Mật khẩu mới không trùng nhau");
        }
       
    }
    $scope.forgotpass = function(even){

        



        for (let i = 0; i < $scope.listStudents.length; i++) {
            if ( $scope.listStudents[i].email == $scope.email) {
                var data={
               
                    // id : $scope.id,
                    username: $scope.listStudents[i].username , // lay thong tin tk cu
                    password: 'abc123',
                    fullname: $scope.listStudents[i].fullname,
                    email: $scope.listStudents[i].email,
                    gender: $scope.listStudents[i].gender,
                    date: $scope.listStudents[i].date,
            
            
                }
                $http.put(`http://localhost:3000/listStudent/${$scope.listStudents[i].id}`,data).then(function(res){
            
                alert("Mật khẩu mới của bạn là 'abc123'");
            
                },function(error){
                    alert("Lấy mật khẩu không thành công");
                }
                
                )
            }
            
        }
      
    }
})