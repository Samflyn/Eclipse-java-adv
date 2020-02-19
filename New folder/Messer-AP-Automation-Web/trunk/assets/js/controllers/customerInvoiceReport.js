'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller('customerInvoiceReports', ["$q","$compile","InvoiceReportsService","DTOptionsBuilder", "DTColumnBuilder","$scope","$rootScope", "$http","$cookies","$filter","GenerateInvoiceService", "$timeout", "$stateParams","$window",function ($q,$compile,InvoiceReportsService,DTOptionsBuilder, DTColumnBuilder,$scope,$rootScope, $http,$cookies,$filter,GenerateInvoiceService, $timeout,$stateParams) {
	$scope.data ={};
	var curr = new Date; 
	var first = curr.getDate() - curr.getDay(); 
	var last = first + 6;
	var firstday = new Date(curr.setDate(first));
	var lastday = new Date(curr.setDate(last));
	$scope.data.fromDate=$filter('date')(firstday,'dd/MM/yyyy');
	$scope.data.toDate=$filter('date')(lastday,'dd/MM/yyyy');
	$scope.loginDetails={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	$scope.loginDetails.roleName=$cookies.get('roleName');

	var vm = this;
	vm.singleRecord={};
	$scope.tenantType = $cookies.get('tenantType');
	$scope.dtInstance={};
	var tableIndex = "0";	
	vm.reloadData = reloadData;
	$scope.form = {
			submit: function (form) {
				if(!($scope.data.fromDate && $scope.data.toDate) && !($scope.data.returnPeriod) && !($scope.data.invNo) ){
					swal({
						title: "",
						text: "FromDate & To Date (OR) InvoiceNumber (Or) Return Period",
					});
				}else{
					if($scope.data.fromDate && $scope.data.toDate){
						var fromDate=new Date($scope.data.fromDate);
					    var toDate=new Date($scope.data.toDate);
					    var difference=new Date(fromDate.getTime()-toDate.getTime());
					    if(difference>0){
					    	swal({
								title: "",
								text: "fromDate should not be greaterthan toDate",
							});
					    }else{
					    	vm.reloadData($scope.data.fromDate,$scope.data.toDate,$scope.data.returnPeriod,$scope.data.invNo,$scope.data.workStatus);	
					    }	
					}
				}
				}
	}; 	
	$scope.form1 = {
			submit1: function (form) {
				if(!($scope.data.fromDate && $scope.data.toDate) && !($scope.data.returnPeriod) && !($scope.data.invNo) ){
					swal({
						title: "",
						text: "FromDate & To Date (OR) InvoiceNumber (Or) Return Period",
					});
				}else{
					if($scope.data.fromDate && $scope.data.toDate){
						var fromDate=new Date($scope.data.fromDate);
					    var toDate=new Date($scope.data.toDate);
					    var difference=new Date(fromDate.getTime()-toDate.getTime());
					    if(difference>0){
					    	swal({
								title: "",
								text: "fromDate should not be greaterthan toDate",
							});
					    }else{
					    	InvoiceReportsService.exportInvoceReport($scope.data.fromDate,$scope.data.toDate,$scope.loginDetails,$scope.data.returnPeriod,$scope.data.invNo,$scope.data.workStatus).then(function (result){
								var blob = new Blob([result.data]);
								var objectUrl = URL.createObjectURL(blob);
								var a = document.createElement("a");
								a.style = "display:none";
								a.href = objectUrl;
								a.download = "InvoiceReports.xlsx";
								a.click();                      
							})
							.catch(function(response){
								var decodedString = String.fromCharCode.apply(null, new Uint8Array(response.data));
								var obj = JSON.parse(decodedString);
								var message = obj['errorMessage'];
								$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+message+'</strong> </div>').fadeOut(5000);

							});	
					    }	
					}
				}
			}
	}; 
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
		var defer = $q.defer();
		
		InvoiceReportsService.getInvoceReport($scope.data.fromDate,$scope.data.toDate,$scope.loginDetails,$scope.data.returnPeriod,$scope.data.invNo,$scope.data.workStatus).then(function (result) {  
			defer.resolve(result.data);  
		}, function(err){
			defer.resolve([]);  
		});  
		return defer.promise;  
	})
	.withPaginationType('full_numbers')
	.withOption('createdRow', function(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	});  

	vm.dtColumns = [  
		DTColumnBuilder.newColumn(null).withTitle('S. No').notSortable().renderWith(indexVal),  
		DTColumnBuilder.newColumn('bpRegisteredId').withTitle(' Vendor RegisterID'),  
		DTColumnBuilder.newColumn('bpName').withTitle('Vendor Name'),  
		DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
		DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date'),  
		DTColumnBuilder.newColumn('dueDate').withTitle('Due date'),  
		DTColumnBuilder.newColumn('purchaseOrderNo').withTitle('PO No.'),  
		DTColumnBuilder.newColumn('workFlowStatus').withTitle('Status').renderWith(status),  
		DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml)
		];  

	function status(data, type, full, meta) {
		if(full.workFlowStatus==0){
			return "Drafted";
		}
		if(full.workFlowStatus==1){
			return $scope.tenantType=='CUSTOMER' ? 'Received' : 'Submitted';
		}
		if(full.workFlowStatus==2){
			return "Saved";
		}
		if(full.workFlowStatus==3){
			return "Matched";
		}
		if(full.workFlowStatus==4){
			return "Not Matched";
		}
		if(full.workFlowStatus==5){
			return "Approved";
		}
		if(full.workFlowStatus==6){
			return "Payment Received";
		}
	}
	//To get the serial No in Data Table 
	function indexVal() {
		return ++tableIndex;
	}
	
	function dateformat(data, type, full, meta) {
		return  moment(full.invoiceDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
	}
	
	function actionsHtml(data, type, full, meta) {
		vm.singleRecord[full.mailConfigId]=full;
		return '<button type="button"  class="btn btn-primary margin-right-5" ng-click="viewInvoice(customerreports.singleRecord['+full.mailConfigId+'])">' +'<i class="fa fa-eye"></i>' +'</button>';
	}
	$scope.report={};
	$scope.viewInvoice = function(data) {
		$scope.report=data;
		$('#viewItemModal').modal();
	}
	function reloadData(fromDate,toDate,returnPeriod,invNo,workflow)
	{
		$scope.dtInstance.reloadData(function() {
			var defer = $q.defer();  
			InvoiceReportsService.getInvoceReport(fromDate,toDate,$scope.loginDetails,returnPeriod,invNo,workflow).then(function (result) {
				defer.resolve(result.data);  
			});  
			return defer.promise; 
		}, true); 
	}

}]);
