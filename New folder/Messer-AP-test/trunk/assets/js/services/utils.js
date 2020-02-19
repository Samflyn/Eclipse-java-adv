app.service('UtilService', function () {
    var obj = {
    	getServiceUrl: getServiceUrl
    };
    /*obj.message = 'Sample meesage';
    obj.setMessage = function (newMessage) {
        obj.message = newMessage;
    };*/
    function getServiceUrl(){
    	var url = 'http://192.168.0.192:9090/dis';
    	return url; 
    }

    return obj;
});