'use strict';
app.controller('costInvoiceBulkUploadCtrl', ['$scope', 'FileUploader','$cookies','$http','$rootScope', function($scope, FileUploader,$cookies, $http,$rootScope) {
	$scope.uploadQueueSize=0;
	var uploader = $scope.uploader = new FileUploader({
		//url: 'upload.php'
	});
	// FILTERS
	// a sync filter
	uploader.filters.push({
		name: 'syncFilter',
		fn: function(item /*{File|FileLikeObject}*/, options) {
			console.log('syncFilter');
			return this.queue.length <100;
		}
	});

	// an async filter
	uploader.filters.push({
		name: 'asyncFilter',
		fn: function(item /*{File|FileLikeObject}*/, options, deferred) {
			console.log('asyncFilter');
			setTimeout(deferred.resolve, 1e3);
		}
	});

	// CALLBACKS

	uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
	};
	$scope.files=[];
	uploader.onAfterAddingFile = function(fileItem) {
		$('.overlay').show();
		if(fileItem.file.size/1024/1024>parseInt($scope.fileSize)){
			uploader.queue.splice(uploader.queue.indexOf(fileItem),1);
			$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+fileItem.file.name+ ' file size exceeded '+$scope.fileSize+'MB so skipped from queue</strong> </div>').fadeOut(7000);
			$('.overlay').hide();
		}else{
			$scope.uploadQueueSize= $scope.uploadQueueSize+fileItem.file.size/1024/1024;
		}
	};
	$scope.filedata=[];
	uploader.onAfterAddingAll = function(addedFileItems) {
		$scope.filedata=addedFileItems;
		$('.overlay').hide();
	};
	uploader.onBeforeUploadItem = function(item) {
	};
	uploader.onProgressItem = function(fileItem, progress) {
	};
	uploader.onProgressAll = function(progress) {
	};
	uploader.onSuccessItem = function(fileItem, response, status, headers) {
	};
	uploader.onErrorItem = function(fileItem, response, status, headers) {
	};
	uploader.onCancelItem = function(fileItem, response, status, headers) {
	};
	uploader.onCompleteItem = function(fileItem, response, status, headers) {
	};
	uploader.onCompleteAll = function() {
	};
	$scope.uploadQueueSize;
	console.info('uploader', uploader);
	$scope.data ={};
	$scope.data.items = [];
	$scope.loginDetails={};
	$scope.companydetails={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	$scope.loginDetails.userLoginId=$cookies.get('userLoginId');
	$scope.queuesize=$cookies.get('fileSize');
	var split=$scope.queuesize.split(',');
	$scope.fileSize = split[0].replace(/\D/g, '');
	$scope.requestSize = split[1].replace(/\D/g, '');
	console.log($scope.requestSize)
	$scope.remaingFiles=[];
	$scope.remove=function(item){
		$scope.uploadQueueSize=$scope.uploadQueueSize-item.file.size/1024/1024
		var index= $scope.filedata.indexOf(item);
		$scope.filedata.splice(index, 1);
		if($scope.remaingFiles.length>0){
			var j=$scope.remaingFiles.length;
			for(var i=0;i<$scope.remaingFiles.length;i++){
				$scope.remaingFiles[j]=$scope.filedata[i]
			}	
		}else{
			$scope.remaingFiles=$scope.filedata;
		}
		$scope.filedata=[];
		uploader.queue.splice(uploader.queue.indexOf(item),1);
	}
	$scope.clearQueue=function(){
		uploader.clearQueue();
		$scope.filedata=[];
		$scope.files=[];
		$scope.remaingFiles=[];
		$scope.uploadQueueSize=0;
	}
	$scope.uploadFiles = function (item) {
		if(item){
			var formData = new FormData();
				formData.append("file",item._file);
				$scope.doUpload(formData,item)
		}else{
			if($scope.remaingFiles.length>0){
				if($scope.filedata.length>0){
					var j=$scope.filedata.length;
					for(var k=0;k<$scope.remaingFiles.length;k++){
						$scope.filedata[j]=$scope.remaingFiles[k];
						j++;
					}
				}else{
					for(var p=0;p<$scope.remaingFiles.length;p++){
						$scope.filedata[p]=$scope.remaingFiles[p];
					}
				}
			}
			var formData = new FormData();
			for(var i=0;i<$scope.filedata.length;i++){
					formData.append("file", $scope.filedata[i]._file);
			}
				$scope.doUpload(formData);
		}

	}
	$scope.doUpload=function(formData,item){
		$('.overlay').show();
		if($scope.uploadQueueSize<=$scope.requestSize){
			formData.append("invoiceType","CI")
			$http({
				method: 'POST',
				url: $rootScope.ctx +'/uploadMultipleFiles',
				data: formData,
				transformRequest: angular.identity,
				headers: {'Content-Type': undefined,'loginDetails':JSON.stringify($scope.loginDetails)}
			}).then(function onSuccess(response) {
				$('.overlay').hide();
				if(item){
					$scope.uploadQueueSize=$scope.uploadQueueSize-item.file.size/1024/1024
					var index= $scope.filedata.indexOf(item._file);
					$("#saveMsg").show().html('<div class="alert alert-success"<strong>File Uploaded Successfully</strong> </div>').fadeOut(7000);
					$scope.filedata.splice(index, 1);
					$scope.remainingfiles=$scope.filedata;
					uploader.queue.splice(uploader.queue.indexOf(item),1);
				}else{
					if($scope.files.length==1){
						$("#saveMsg").show().html('<div class="alert alert-success"<strong>File Uploaded Successfully</strong> </div>').fadeOut(7000);
					}else{
						$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data+'</strong> </div>').fadeOut(7000);
					}
					$scope.clearQueue();
				}
			}, function(err) {
				$('.overlay').hide();
				$scope.clearQueue();
				$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+err.data.errorMessage+'</strong> </div>').fadeOut(7000);
			}); 
		}else{
			$("#saveMsg").show().html('<div class="alert alert-danger"<strong>Total size of Queue Should not exceed '+$scope.requestSize+'</strong> </div>');
		}
	}
}]);



