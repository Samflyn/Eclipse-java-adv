app.controller('reportCtrl',['$scope','$http','$window','$rootScope','toaster','$filter','$cookies', function($scope,$http,$window,$rootScope,toaster,$filter,$cookies ) {
	$scope.data={};
	$scope.data.reportName="Purchase Invoice";
	$scope.loginDetails={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	$scope.emptyCheckList=function(reportName){
		$scope.data={};
		$scope.checkedData=[];
		$scope.data.reportName=reportName;
	}
  	$scope.submitData = function(){ 
  		$scope.checkedData=[];
  		angular.forEach($scope.data,function(val,key){
  			if(val==true){
  				$scope.checkedData.push(key);
  			}
  		});
  		if($scope.checkedData.length>0){
        	$(".overlay").show();
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/reportDownload/'+$scope.checkedData+'/'+$scope.data.reportName,
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)},
		          responseType: 'arraybuffer',
		    }).then(function (response) {
		    	var blob = new Blob([response.data]);
				var objectUrl = URL.createObjectURL(blob);
				var a = document.createElement("a");
				a.style = "display:none";
				a.href = objectUrl;
				a.download = "report.xlsx";
				a.click();  
				$(".overlay").hide();
		      
		    }).catch(function(response){
				var decodedString = String.fromCharCode.apply(null, new Uint8Array(response.data));
				var obj = JSON.parse(decodedString);
				var message = obj['errorMessage'];
				$(".overlay").hide();

			});
  		}else{
  	      swal({
              title: "",
              text: "Please Check atleast one checkbox",
              confirmButtonColor: "#008000",
              confirmButtonText: "Ok",	
              closeOnConfirm: true
          });
  		}
  	}
}]);