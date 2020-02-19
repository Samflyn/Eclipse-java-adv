'use strict';
/** 
  * controllers used for the dashboard
*/
app.filter('monthName', [function() {
    return function (monthNumber) { //1 = January
        var monthNames = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ];
        return monthNames[monthNumber - 1];
    }
}]);
app.controller('dashCustomerInvoiceCount', ["$scope","$rootScope", "$http","$cookies", function ($scope,$rootScope, $http,$cookies) {
	$scope.loginDetails={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 $scope.monthlyReport=[];
	 $scope.hide=true;
	var counts=[];
	$scope.getCustomerCount=function(){
		var totalcount=0;
		$http({
		    method: 'POST',
		    url: $rootScope.ctx +'/getMonthlyReport',
		    data: $scope.loginDetails
		   }).then(function(response) {
			   console.log("reponse",response.data);
			   $scope.hide=false;
			   $scope.yearlyList=[];
			   $scope.monthlyPIReport=response.data.PIReport;
			   $scope.monthlyCIReport=response.data.CIReport;
			   $scope.PIReport = [];
			   $scope.CIReport = [];
			  // $scope.map = new Object();
			   angular.forEach($scope.monthlyPIReport, function(value, key) {
				   var month= key.substring(0,2);
				  // $scope.map[month]=value;
				   $scope.PIYear=key.substring(2,6);
				   var data={};
				   console.log("COMPLETED",value);
				   data["month"]= parseInt(month);
				   data["COMPLETED"]= value.COMPLETED;
				   data["FAILED"]= value.FAILED;
				   data["OPEN"]= value.OPEN;
				   $scope.PIReport.push(data); 
			   });
			   angular.forEach($scope.monthlyCIReport, function(value, key) {
				   var month= key.substring(0,2);
				  // $scope.map[month]=value;
				   $scope.CIYear=key.substring(2,6);
				   var data={};
				   console.log("COMPLETED",value);
				   data["month"]= parseInt(month);
				   data["COMPLETED"]= value.COMPLETED;
				   data["FAILED"]= value.FAILED;
				   data["OPEN"]= value.OPEN;
				   $scope.CIReport.push(data); 
			   });
			   console.log(" $scope.colors", $scope.colors)
			  /* Object.keys($scope.map)
			      .sort()
			      .forEach(function(v, i) {
			    	  $scope.yearlyList.push($scope.map[v])  
			       });
			  console.log("finallist",$scope.yearlyList);
			   $scope.getYearlyreport();*/
		 	}).catch(function onError(response){
		 		 console.log("err {}",response);
		   });
	$http({
	    method: 'POST',
	    url: $rootScope.ctx +'/getInvoiceCountsByWorkFlow',
	    data: $scope.loginDetails
	   }).then(function(response) {
		   $scope.invoiceCounts=response.data;
		   console.log("$scope.invoiceCounts",$scope.invoiceCounts)
		   angular.forEach($scope.invoiceCounts,function(k,v){
		   	   counts.push(k);
		   	 totalcount=totalcount+parseInt(k);
		   });
		   $scope.pieChart(totalcount);
           $scope.barChart();
	 	}).catch(function onError(response){
	 		 console.log("err {}",response);
	   });

	}
	$scope.getCustomerCount();
  
	$scope.barChart =  function () {
		     // Chart.js Data
		     $scope.data = {
		         labels: ['Saved','Matched', 'Not Matched', 'Approved', 'Payment'],
		         datasets: [
		             {
                        label: 'Invoice Count',
                         backgroundColor:["#007bff","#5cb85c","#d43f3a", "#5cb85c", "#17a2b8"],
                         borderColor: 'rgba(151,187,205,1)',
                         pointColor: 'rgba(151,187,205,1)',
                         pointStrokeColor: '#fff',
                         pointHighlightFill: '#fff',
                         pointHighlightStroke: 'rgba(151,187,205,1)',
                         data: counts
		             }
		         ]
		     };

		  // Chart.js Options
		     $scope.options = {
		     maintainAspectRatio: false,
		     // Sets the chart to be responsive
		     responsive: true,
		     //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
		     scaleBeginAtZero: true,
		     //Boolean - Whether grid lines are shown across the chart
		     scaleShowGridLines: true,
		     //String - Colour of the grid lines
		     scaleGridLineColor: "rgba(0,0,0,.05)",
		     //Number - Width of the grid lines
		     scaleGridLineWidth: 1,
		     //Boolean - If there is a stroke on each bar
		     barShowStroke: true,
		     //Number - Pixel width of the bar stroke
		     barStrokeWidth: 2,
		     //Number - Spacing between each of the X value sets
		     barValueSpacing: 5,
		     //Number - Spacing between data sets within X values
		     barDatasetSpacing: 1,
		     //String - A legend template
		     legend: {
		         display: false
		     },
		     legendCallback: function (chart) {
		            // Return the HTML string here.
		            var legendTemplate = '<ul class="tc-chart-js-legend">';
		            for (var i = 0; i < chart.legend.legendItems.length; i++) {
		                var legendItem = chart.legend.legendItems[i];
		                legendTemplate = legendTemplate + '<li><span style="background-color:' + legendItem.strokeStyle + '"></span>' + legendItem.text + '</li>';
		            }
		            legendTemplate = legendTemplate + '</ul>';
		            return legendTemplate;
		     }
		    };
		}
/*	$scope.getYearlyreport=function(){
		  $scope.yearData = {
			        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
			        datasets: [
			            {
			                label: 'OCR Calls',
			                backgroundColor: 'rgba(151,187,205,0.2)',
			                borderColor: 'rgba(151,187,205,1)',
			                pointColor: 'rgba(151,187,205,1)',
			                pointStrokeColor: '#fff',
			                pointHighlightFill: '#fff',
			                pointHighlightStroke: 'rgba(151,187,205,1)',
			                lineTension: 0,
			                data: $scope.yearlyList
			            }
			        ]
			    };

			    $scope.options = {
			        maintainAspectRatio: false,
			        // Sets the chart to be responsive
			        responsive: true,
			        ///Boolean - Whether grid lines are shown across the chart
			        scaleShowGridLines: true,
			        //String - Colour of the grid lines
			        scaleGridLineColor: 'rgba(0,0,0,.05)',
			        //Number - Width of the grid lines
			        scaleGridLineWidth: 1,
			        //Boolean - Whether the line is curved between points
			        bezierCurve: false,
			        //Number - Tension of the bezier curve between points
			        bezierCurveTension: 0.4,
			        //Boolean - Whether to show a dot for each point
			        pointDot: true,
			        //Number - Radius of each point dot in pixels
			        pointDotRadius: 4,
			        //Number - Pixel width of point dot stroke
			        pointDotStrokeWidth: 1,
			        //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
			        pointHitDetectionRadius: 20,
			        //Boolean - Whether to show a stroke for datasets
			        datasetStroke: true,
			        //Number - Pixel width of dataset stroke
			        datasetStrokeWidth: 2,
			        //Boolean - Whether to fill the dataset with a colour
			        datasetFill: true,
			        // Function - on animation progress
			        onAnimationProgress: function () { },
			        // Function - on animation complete
			        onAnimationComplete: function () { },
			        //String - A legend template
			        legend: {
			            display: false
			        },
			        legendCallback: function (chart) {
			            // Return the HTML string here.
			            var legendTemplate = '<ul class="tc-chart-js-legend">';
			            for (var i = 0; i < chart.legend.legendItems.length; i++) {
			                var legendItem = chart.legend.legendItems[i];
			                legendTemplate = legendTemplate + '<li><span style="background-color:' + legendItem.strokeStyle + '"></span>' + legendItem.text + '</li>';
			            }
			            legendTemplate = legendTemplate + '</ul>';
			            return legendTemplate;
			        }
			    };
	} */
	
	   $scope.pieChart = function(totalcount){
		   
		     // Pie Chart.js Data
		     $scope.data1 = {
		     		 labels: ['Saved','Matched', 'Not Matched', 'Approved', 'Paid'],
		         datasets: [{
		             label: "Invoice Count",
		             data: counts,
		             backgroundColor: ["#007bff","#5cb85c","#d43f3a", "#5cb85c", "#17a2b8"]
		         }]
		     }
		    
		    
		 	
		     $scope.total =totalcount;
		     // Chart.js Options
		     $scope.pieoptions = {
		         // Sets the chart to be responsive
		         responsive: false,
		         //Boolean - Whether we should show a stroke on each segment
		         segmentShowStroke: true,
		         //String - The colour of each segment stroke
		         segmentStrokeColor: '#fff',
		         //Number - The width of each segment stroke
		         segmentStrokeWidth: 2,
		         //Number - The percentage of the chart that we cut out of the middle
		         percentageInnerCutout: 50, // This is 0 for Pie charts
		         //Number - Amount of animation steps
		         animationSteps: 100,
		         //String - Animation easing effect
		         animationEasing: 'easeOutBounce',
		         //Boolean - Whether we animate the rotation of the Doughnut
		         animateRotate: true,
		         //Boolean - Whether we animate scaling the Doughnut from the centre
		         animateScale: false,
		         //String - A legend template
		         legend: {
		             display: false
		         },
		         legendCallback: function (chart) {
		             // Return the HTML string here.
		             var legendTemplate = '<ul class="tc-chart-js-legend">';
		             for (var i = 0; i < chart.legend.legendItems.length; i++) {
		                 var legendItem = chart.legend.legendItems[i];
		                 legendTemplate = legendTemplate + '<li><span style="background-color:' + legendItem.fillStyle + '"></span>' + legendItem.text + '</li>';
		             }
		             legendTemplate = legendTemplate + '</ul>';
		             return legendTemplate;
		         }
		     };

	   }

	   $scope.getAllUploadedCount = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/bulkupload/getAllUploadedCounts',
		          params : {userid:$scope.loginDetails.userId,"tenantId":$scope.loginDetails.tenantId}
		    }).then(function (response) {
		    	console.log("reponse load",response.data);
		    		  $scope.PIUploaded = response.data.PI.totalUplaod;
		    		  $scope.PIProcessed = response.data.PI.COMPLETED;
		    		  $scope.PIFailed = response.data.PI.FAILED;
		    		  $scope.CIUploaded = response.data.CI.totalUplaod;
		    		  $scope.CIProcessed = response.data.CI.COMPLETED;
		    		  $scope.CIFailed = response.data.CI.FAILED;
		    },function (error){
		        console.log(error, 'can not get getDashboardCounts.');
		    });
	     }
    
	   $scope.getAllUploadedCount();
   
    
}]);

app.controller('SparklineCtrl', ["$scope", function ($scope) {
    $scope.sales = [600, 923, 482, 1211, 490, 1125, 1487];
    $scope.earnings = [400, 650, 886, 443, 502, 412, 353];
    $scope.referrals = [4879, 6567, 5022, 5890, 9234, 7128, 4811];
}]);


  /*  $scope.data = {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: [
            {
                label: 'My dataset',
                backgroundColor: 'rgba(151,187,205,0.2)',
                borderColor: 'rgba(151,187,205,1)',
                pointColor: 'rgba(151,187,205,1)',
                pointStrokeColor: '#fff',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(151,187,205,1)',
                lineTension: 0,
                data: [28, 48, 40, 19, 86, 27, 90, 102, 123, 145, 60, 80]
            }
        ]
    };*/

 /*   $scope.options = {
        maintainAspectRatio: false,
        // Sets the chart to be responsive
        responsive: true,
        ///Boolean - Whether grid lines are shown across the chart
        scaleShowGridLines: true,
        //String - Colour of the grid lines
        scaleGridLineColor: 'rgba(0,0,0,.05)',
        //Number - Width of the grid lines
        scaleGridLineWidth: 1,
        //Boolean - Whether the line is curved between points
        bezierCurve: false,
        //Number - Tension of the bezier curve between points
        bezierCurveTension: 0.4,
        //Boolean - Whether to show a dot for each point
        pointDot: true,
        //Number - Radius of each point dot in pixels
        pointDotRadius: 4,
        //Number - Pixel width of point dot stroke
        pointDotStrokeWidth: 1,
        //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        pointHitDetectionRadius: 20,
        //Boolean - Whether to show a stroke for datasets
        datasetStroke: true,
        //Number - Pixel width of dataset stroke
        datasetStrokeWidth: 2,
        //Boolean - Whether to fill the dataset with a colour
        datasetFill: true,
        // Function - on animation progress
        onAnimationProgress: function () { },
        // Function - on animation complete
        onAnimationComplete: function () { },
        //String - A legend template
        legend: {
            display: false
        },
        legendCallback: function (chart) {
            // Return the HTML string here.
            var legendTemplate = '<ul class="tc-chart-js-legend">';
            for (var i = 0; i < chart.legend.legendItems.length; i++) {
                var legendItem = chart.legend.legendItems[i];
                legendTemplate = legendTemplate + '<li><span style="background-color:' + legendItem.strokeStyle + '"></span>' + legendItem.text + '</li>';
            }
            legendTemplate = legendTemplate + '</ul>';
            return legendTemplate;
        }
    };*/



app.controller('OnotherCtrl', ["$scope", function ($scope) {

    // Chart.js Data
    $scope.data = {
        labels: ['Recived Invoice', 'Validated Invoice', 'Faield Invoice', 'Submited To ERP', 'Invoice Payment Update'],
        datasets: [{
            label: "My First Dataset",
            data: [300, 50, 100,240],
            backgroundColor: ["#dc3912", "#31951a", "#3366cc", "#f99902"]
        }]
    }

    $scope.total = 690;
    // Chart.js Options
    $scope.options = {
        // Sets the chart to be responsive
        responsive: false,
        //Boolean - Whether we should show a stroke on each segment
        segmentShowStroke: true,
        //String - The colour of each segment stroke
        segmentStrokeColor: '#fff',
        //Number - The width of each segment stroke
        segmentStrokeWidth: 2,
        //Number - The percentage of the chart that we cut out of the middle
        percentageInnerCutout: 50, // This is 0 for Pie charts
        //Number - Amount of animation steps
        animationSteps: 100,
        //String - Animation easing effect
        animationEasing: 'easeOutBounce',
        //Boolean - Whether we animate the rotation of the Doughnut
        animateRotate: true,
        //Boolean - Whether we animate scaling the Doughnut from the centre
        animateScale: false,
        //String - A legend template
        legend: {
            display: false
        },
        legendCallback: function (chart) {
            // Return the HTML string here.
            var legendTemplate = '<ul class="tc-chart-js-legend">';
            for (var i = 0; i < chart.legend.legendItems.length; i++) {
                var legendItem = chart.legend.legendItems[i];
                legendTemplate = legendTemplate + '<li><span style="background-color:' + legendItem.fillStyle + '"></span>' + legendItem.text + '</li>';
            }
            legendTemplate = legendTemplate + '</ul>';
            return legendTemplate;
        }
    };

}]);
app.controller('LastCtrl', ["$scope", function ($scope) {

    // Chart.js Data
    $scope.data = {
        labels: ['Eating', 'Drinking', 'Sleeping', 'Designing', 'Coding', 'Cycling', 'Running'],
        datasets: [
            {
                label: 'My First dataset',
                backgroundColor: 'rgba(220,220,220,0.2)',
                borderColor: 'rgba(220,220,220,1)',
                pointColor: 'rgba(220,220,220,1)',
                pointStrokeColor: '#fff',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(220,220,220,1)',
                data: [65, 59, 90, 81, 56, 55, 40]
            },
            {
                label: 'My Second dataset',
                backgroundColor: 'rgba(151,187,205,0.2)',
                borderColor: 'rgba(151,187,205,1)',
                pointColor: 'rgba(151,187,205,1)',
                pointStrokeColor: '#fff',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(151,187,205,1)',
                data: [28, 48, 40, 19, 96, 27, 100]
            }
        ]
    };

    // Chart.js Options
    $scope.options = {
        // Sets the chart to be responsive
        responsive: true,
        //Boolean - Whether to show lines for each scale point
        scaleShowLine: true,
        //Boolean - Whether we show the angle lines out of the radar
        angleShowLineOut: true,
        //Boolean - Whether to show labels on the scale
        scaleShowLabels: false,
        // Boolean - Whether the scale should begin at zero
        scaleBeginAtZero: true,
        //String - Colour of the angle line
        angleLineColor: 'rgba(0,0,0,.1)',
        //Number - Pixel width of the angle line
        angleLineWidth: 1,
        //String - Point label font declaration
        pointLabelFontFamily: '"Arial"',
        //String - Point label font weight
        pointLabelFontStyle: 'normal',
        //Number - Point label font size in pixels
        pointLabelFontSize: 10,
        //String - Point label font colour
        pointLabelFontColor: '#666',
        //Boolean - Whether to show a dot for each point
        pointDot: true,
        //Number - Radius of each point dot in pixels
        pointDotRadius: 3,
        //Number - Pixel width of point dot stroke
        pointDotStrokeWidth: 1,
        //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        pointHitDetectionRadius: 20,
        //Boolean - Whether to show a stroke for datasets
        datasetStroke: true,
        //Number - Pixel width of dataset stroke
        datasetStrokeWidth: 2,
        //Boolean - Whether to fill the dataset with a colour
        datasetFill: true,
        //String - A legend template
        legend: {
            display: false
        },
        legendCallback: function (chart) {
            // Return the HTML string here.
            var legendTemplate = '<ul class="tc-chart-js-legend">';
            for (var i = 0; i < chart.legend.legendItems.length; i++) {
                var legendItem = chart.legend.legendItems[i];
                legendTemplate = legendTemplate + '<li><span style="background-color:' + legendItem.strokeStyle + '"></span>' + legendItem.text + '</li>';
            }
            legendTemplate = legendTemplate + '</ul>';
            return legendTemplate;
        },
        scale: {

            ticks: {
                beginAtZero: true,
                userCallback: function (value, index, values) {
                    return '';
                }
            }
        }
    };

}]);
