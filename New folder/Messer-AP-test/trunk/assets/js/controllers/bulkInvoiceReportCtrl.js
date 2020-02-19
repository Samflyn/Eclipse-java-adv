

app.controller('bulkInvoiceReportCtrl', ["InvoiceReportsService","$scope","$rootScope", "$http","$q","$cookies","$filter","$compile","DTOptionsBuilder","DTColumnBuilder", "$timeout",  "$stateParams","$window",function (InvoiceReportsService,$scope,$rootScope,$http,$q,$cookies,$filter,$compile,DTOptionsBuilder, DTColumnBuilder,$timeout,$stateParams,$window) {
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
	    
	   $(function() {
	       $('#fromDate').datepicker({
	            format: 'dd/mm/yyyy',
	            "setDate": new Date(),
	            autoclose: true,
	            todayHighlight: true,
	        });
	       $('#toDate').datepicker({
	           format: 'dd/mm/yyyy',
	           "setDate": new Date(),
	           autoclose: true,
	           todayHighlight: true,
	       });
	      $(document).on('click', '#close-preview', function(){ 
	        $('.image-preview').popover('hide');
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
	        // Set the popover default content
	        $('.image-preview').popover({
	            trigger:'manual',
	            html:true,
	            title: "<strong>Preview</strong>"+$(closebtn)[0].outerHTML,
	            content: "There's no image",
	            placement:'bottom'
	        });
	        // Clear event
	        $('.image-preview-clear').click(function(){
	            $('.image-preview').attr("data-content","").popover('hide');
	            $('.image-preview-filename').val("");
	            $('.image-preview-clear').hide();
	            $('.image-preview-input input:file').val("");
	            $(".image-preview-input-title").text("Browse"); 
	        }); 
	        // Create the preview image
	        $(".image-preview-input input:file").change(function (){     
	            var img = $('<img/>', {
	                id: 'dynamic',
	                width:250,
	                height:200
	            });      
	            var file = this.files[0];
	            var reader = new FileReader();
	            // Set preview image into the popover data-content
	            reader.onload = function (e) {
	                $(".image-preview-input-title").text("Change");
	                $(".image-preview-clear").show();
	                $(".image-preview-filename").val(file.name);            
	                img.attr('src', e.target.result);
	                $(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
	            }        
	            reader.readAsDataURL(file);
	        });  
	      });	    
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
	        	            	if (!($scope.data.fromDate && $scope.data.toDate)) {
	        						$("#saveMsg").show().html('<div class="alert alert-danger"<strong>from date and to date are required</strong> </div>');
	        					}else{
	        						$("#saveMsg").show().html('');
	        						InvoiceReportsService.exportBulkInvoceReport($scope.data.fromDate,$scope.data.toDate,$scope.loginDetails).then(function (result){
		    							var blob = new Blob([result.data]);
		    							var objectUrl = URL.createObjectURL(blob);
		    							var a = document.createElement("a");
		    							a.style = "display:none";
		    							a.href = objectUrl;
		    							a.download = "InvoiceReport.xlsx";
		    							a.click();                      
		    						})
		    						.catch(function(response){
		    							var decodedString = String.fromCharCode.apply(null, new Uint8Array(response.data));
		    							var obj = JSON.parse(decodedString);
		    							var message = obj['errorMessage'];
		    							$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+message+'</strong> </div>');

		    						});
	        					}
	        		            }
	        	            }
	        	        };
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
}]);
