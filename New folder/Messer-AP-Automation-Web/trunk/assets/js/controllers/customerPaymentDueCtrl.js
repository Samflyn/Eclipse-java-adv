

app.controller('viewCustomerPaymentDueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$compile","DTOptionsBuilder","DTColumnBuilder","viewCustomerPaymentService", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$compile,DTOptionsBuilder, DTColumnBuilder,viewCustomerPaymentService,$timeout,$stateParams,$window) {
	$scope.data ={};
	$scope.data.fromDate1=$filter('date')(new Date(),'dd/MM/yyyy');
	$scope.data.toDate1=$filter('date')(new Date(),'dd/MM/yyyy');
	$scope.loginDetails={};
    $scope.data.tenantDto;
	
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	
	 var tenantId=$cookies.get('tenantId')
	 	
	  $scope.getCustomerVendors=function() {
	   		$http({
	   			method : 'GET',
	   			url : $rootScope.ctx +'/getAllTenants/'+tenantId,
	   			async : false,
	   		}).then(function(response) {
	   			$scope.vendors=response.data;
	   		 
	   		}, function(error) {
	   			console.log(error, 'can not get roles.');
	   		});
	   		
	   	}
	
	
	var vm= this;
	 vm.singleRecord={};
	 $scope.dtInstance={};
	 var serialNo=1;
	
	 vm.reloadData=function(data) {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
	    		  console.log('reload')
	    		  var defer = $q.defer();  
	    		  viewCustomerPaymentService.invoiceQueue(data).then(function (result) { 
			            defer.resolve(result.data);
			        }); 
	    		 
			        return defer.promise;  
	    		  }, true);
		   
		   
	      }   
		 
		 if($scope.data.tenantDto){
			 
    		 $scope.queryPath= {fromDate:$scope.data.fromDate1,toDate:$scope.data.toDate1, tenantId:$scope.data.tenantDto} 
    		 $scope.queryPath.status=0;
        	 }
        	 else{
       	 $scope.queryPath= {fromDate:$scope.data.fromDate1,toDate:$scope.data.toDate1, tenantId: $cookies.get('tenantId')}
       	 $scope.queryPath.status=2;
        	 }
		// $scope.queryPath.status=2;
		 var tableIndex = "0";	
		 
	    vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
	        var defer = $q.defer();  
	   
	        viewCustomerPaymentService.invoiceQueue($scope.queryPath).then(function (result) {  
	            defer.resolve(result.data);  
	            console.log("InvoiceQueue",result.data);
	        }, function(err){
	              defer.resolve([]);  
	        });  
	        return defer.promise;  
	    })
	    .withPaginationType('full_numbers') 
	    .withOption('createdRow', function(row, data, dataIndex) {
	        // Recompiling so we can bind Angular directive to the DT
	        $compile(angular.element(row).contents())($scope);
	    });  
	 
	    vm.dtColumns = [  
	        DTColumnBuilder.newColumn('null').withTitle('S.No').notSortable().renderWith(serialNoHtml),
	        DTColumnBuilder.newColumn('bpName').withTitle('Vendor Name'),
	        DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
	        DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date').renderWith(dateformat),  
	        DTColumnBuilder.newColumn('purchaseOrderNo').withTitle('PO Number'), 
	        DTColumnBuilder.newColumn('dueDate').withTitle('Due Date'),  
	        DTColumnBuilder.newColumn('invoiceValue').withTitle('Due Amount'),  
	        DTColumnBuilder.newColumn('null').withTitle('Actions')
			.notSortable().renderWith(actionsHtml)
	    ];  
	    
	    
	    function actionsHtml(data, type, full, meta) {
			   
		      vm.singleRecord[full.userId]=full;
	  	  return '' ;
	    }
	    
	    function serialNoHtml() {
	    	return serialNo++;
	    }
	    
	 

	    function dateformat(data, type, full, meta) {
			return  moment(full.invoiceDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
		}
	    
	    $('#toDate1').datepicker({
	        format: 'dd/mm/yyyy',
	        "setDate": new Date(),
	        autoclose: true,
	        todayHighlight: true, }).on("changeDate", function (date) {
	        	
	            var date1 = $('#fromDate1').datepicker('getDate');           
	            var date = new Date( Date.parse( date1 ) ); 
	            date.setDate( date.getMonth() +2 );        
	            var newDate = date.toDateString(); 
	            newDate = new Date( Date.parse( newDate ) );                      
	            $('#toDate1').datepicker("option","maxDate",newDate);
	    });
	    
	    
	   $('#fromDate1').datepicker({
	       format: 'dd/mm/yyyy',
	       "setDate": new Date(),
	       autoclose: true,
	       todayHighlight: true, }).on("changeDate", function (date){
		    	
	   });
	
	   $(document).on('click', '#close-preview', function(){ 
		    $('.image-preview').popover('hide');
		    // Hover befor close the preview
		    $('.image-preview').hover(
		        function () {
		           $('.image-preview').popover('show');
		        }, 
		         function () {
		           $('.image-preview').popover('hide');
		        }
		    );    
		});

		    // Create the close button
		    var closebtn = $('<button/>', {
		        type:"button",
		        text: 'x',
		        id: 'close-preview',
		        style: 'font-size: initial;',
		    });
		    closebtn.attr("class","close pull-right");   
	    
	    
	    
	    //method called for view button
	      		$scope.form = {
	        	        submit: function (form) {
	        	            var firstError = null;
	        	            if (form.$invalid) {
	        	                var field = null;
	        	                for (field in form) {
	        	                    if (field[0] != '$') {
	        	                        if (firstError === null && !form[field].$valid) {
	        	                            firstError = form[field].$name;
	        	                        }

	        	                        if (form[field].$pristine) {
	        	                            form[field].$dirty = true;
	        	                        }
	        	                    }
	        	                }
	        	                return;
	        	            } else {
	        	            	
	        	            	 if($scope.data.tenantDto){
	        	            		 
	        	        		
	        	            		 $scope.queryPath= {fromDate:$scope.data.fromDate1,toDate:$scope.data.toDate1, tenantId:$scope.data.tenantDto} 
	        	            		  $scope.queryPath.status=0;
	        	            	 }
	        	            	 else{
	        	            		
	        	            		 $scope.queryPath= {fromDate:$scope.data.fromDate1,toDate:$scope.data.toDate1, tenantId: $cookies.get('tenantId')}
	        	            		  $scope.queryPath.status=2;
	        	            	 }
	        	            	 
	        	            	 var mydate = moment( $scope.data.fromDate1, 'DD/MM/YYYY');
	        		    			moment(mydate).format("MM/DD/YYYY");
	        		    			
	        		    			var mydate1 = moment($scope.data.toDate1, 'DD/MM/YYYY');
	        		    			moment(mydate1).format("MM/DD/YYYY");
	        		    			
	        		    			var date1 = new Date(mydate);
	        		    			
	        		    			var date2 = new Date(mydate1);
	        		    			
	        		    			var timeDiff = Math.abs(date1.getTime() - date2.getTime());
	        		    			var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	        		    		  if(diffDays>31 && $("button").val()!="export"){
	        		    		  swal({
	        		    	             title: "View",
	        		    	             text: "view available for only one month",
	        		    	             type: "warning",
	        		    	             showCancelButton:false,
	        		    	             confirmButtonColor: "#007AFF",
	        		    	             confirmButtonText: "Yes",
	        		    	             closeOnConfirm: true
	        		    	         });
	        		    	     	 
	        		    		  }
	        	            	 
	        		    		  else{
	        		    			//  $scope.queryPath.status=2;
	 	        	        		 vm.reloadData($scope.queryPath);
	        		    		  }
	        	            	 
	        	        		
	        	        		 
	        		            }
	        	            }
	        	        };
	        	    	

	      	//method to export excel file	
	      		$scope.exportExcelPayement=function(){
	            	 if($scope.data.tenantDto){
	            		 $scope.queryPath= {fromDate:$scope.data.fromDate1,toDate:$scope.data.toDate1,loginDetails:$scope.loginDetails, tenantId:$scope.data.tenantDto} 	
	            		 $scope.queryPath.status=0;
	            	 }
	            	 else{
	            		 $scope.queryPath= {fromDate:$scope.data.fromDate1,toDate:$scope.data.toDate1,loginDetails:$scope.loginDetails, tenantId:$cookies.get('tenantId')}
	            		 $scope.queryPath.status=2;
	            	 }
	            	
	      			 viewCustomerPaymentService.invoiceQueueForExcel($scope.queryPath).then(function (result){
							var blob = new Blob([result.data]);
							var objectUrl = URL.createObjectURL(blob);
							var a = document.createElement("a");
							a.style = "display:none";
							a.href = objectUrl;
							a.download = "PaymentDueReports.xlsx";
							a.click();                      
						})
						.catch(function(response){
							var decodedString = String.fromCharCode.apply(null, new Uint8Array(response.data));
							var obj = JSON.parse(decodedString);
							var message = obj['errorMessage'];
							$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+message+'</strong> </div>');

						}); 
	    			 
	      		}
	      		

	
        
        //getting to fill ship To details
        $scope.getTenant = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getTenant/'+$cookies.get('tenantId'),
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.data.registeredName=response.data.tenantName;
		    	$scope.data.registeredId=response.data.registredId;
		    	$scope.data.registeredAddress=response.data.registredAddress;	
		          	console.log(response.data)
		    },function (error){
		        console.log(error, 'can not get getBusinessPatner.');
		    });
         } 
       
 		
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
	
	    
    
       
        $scope.getTenant();
       $scope.getCustomerVendors();
       
    

}]);
