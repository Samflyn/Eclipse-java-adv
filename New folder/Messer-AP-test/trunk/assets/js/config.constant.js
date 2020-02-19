'use strict';

/**
 * Config constant
 */
app.constant('APP_MEDIAQUERY', {
    'desktopXL': 1200,
    'desktop': 992,
    'tablet': 768,
    'mobile': 480
});
app.constant('JS_REQUIRES', {
    //*** Scripts
    scripts: {
        //*** Javascript Plugins
        'modernizr': ['../bower_components/modernizr/modernizr.js'],
        'moment': ['../bower_components/moment/min/moment.min.js'],
        'spin': '../bower_components/spin.js/spin.js',

        //*** jQuery Plugins
        'perfect-scrollbar-plugin': ['../bower_components/perfect-scrollbar/js/min/perfect-scrollbar.jquery.min.js', '../bower_components/perfect-scrollbar/css/perfect-scrollbar.min.css'],
        'ladda': ['../bower_components/ladda/dist/ladda.min.js', '../bower_components/ladda/dist/ladda-themeless.min.css'],
        'sweet-alert': ['../bower_components/sweetalert/dist/sweetalert.min.js', '../bower_components/sweetalert/dist/sweetalert.css'],
        'chartjs': '../bower_components/chart.js/dist/Chart.min.js',
        'jquery-sparkline': '../bower_components/jquery.sparkline.build/dist/jquery.sparkline.min.js',
        'ckeditor-plugin': '../bower_components/ckeditor/ckeditor.js',
        'jquery-nestable-plugin': ['../bower_components/jquery-nestable/jquery.nestable.js'],
        'touchspin-plugin': ['../bower_components/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.js', '../bower_components/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.css'],
        'spectrum-plugin': ['../bower_components/spectrum/spectrum.js', '../bower_components/spectrum/spectrum.css'],
        //'ng-dataTable': ['assets/js/datatable/angular-datatables.min.css', 'assets/js/datatable/jquery.dataTables.min.js', 'assets/js/datatable/angular-datatables.min.js'],

        //*** Controllers
        'iconsCtrl': 'assets/js/controllers/iconsCtrl.js',
        'vAccordionCtrl': 'assets/js/controllers/vAccordionCtrl.js',
        'ckeditorCtrl': 'assets/js/controllers/ckeditorCtrl.js',
        'laddaCtrl': 'assets/js/controllers/laddaCtrl.js',
        'ngTableCtrl': 'assets/js/controllers/ngTableCtrl.js',
        'cropCtrl': 'assets/js/controllers/cropCtrl.js',
        'asideCtrl': 'assets/js/controllers/asideCtrl.js',
        'toasterCtrl': 'assets/js/controllers/toasterCtrl.js',
        'sweetAlertCtrl': 'assets/js/controllers/sweetAlertCtrl.js',
        'mapsCtrl': 'assets/js/controllers/mapsCtrl.js',
        'chartsCtrl': 'assets/js/controllers/chartsCtrl.js',
        'calendarCtrl': 'assets/js/controllers/calendarCtrl.js',
        'nestableCtrl': 'assets/js/controllers/nestableCtrl.js',
        'validationCtrl': ['assets/js/controllers/validationCtrl.js'],
        'userCtrl': ['assets/js/controllers/userCtrl.js'],
        'selectCtrl': 'assets/js/controllers/selectCtrl.js',
        'wizardCtrl': 'assets/js/controllers/wizardCtrl.js',
        'uploadCtrl': 'assets/js/controllers/uploadCtrl.js',
        'treeCtrl': 'assets/js/controllers/treeCtrl.js',
        'inboxCtrl': 'assets/js/controllers/inboxCtrl.js',
        'xeditableCtrl': 'assets/js/controllers/xeditableCtrl.js',
        'chatCtrl': 'assets/js/controllers/chatCtrl.js',
        'dynamicTableCtrl': 'assets/js/controllers/dynamicTableCtrl.js',
        'NotificationIconsCtrl': 'assets/js/controllers/notificationIconsCtrl.js',

        //*** Filters
        'htmlToPlaintext': 'assets/js/filters/htmlToPlaintext.js',

        // Custom Controllers
        'dashboardCtrl': 'assets/js/controllers/dashboards/dashboardCtrl.js',
        'adminDashboardCtrl': 'assets/js/controllers/dashboards/adminDashboardCtrl.js',
        'superadminDashboardCtrl': 'assets/js/controllers/dashboards/superadminDashboardCtrl.js',
        'generateCustomerInvoiceCtrl': 'assets/js/controllers/generateCustomerInvoiceCtrl.js',
        'CustomerCostInvoiceCtrl': 'assets/js/controllers/cost/CustomerCostInvoiceCtrl.js',
        'costInvoiceQueueCtrl': 'assets/js/controllers/costInvoiceQueueCtrl.js',
        'costInvoiceBulkUploadCtrl': 'assets/js/controllers/costInvoiceBulkUploadCtrl.js',
        'GenerateInvoiceService': 'assets/js/services/generateInvoice.js',
        'ItemMasterService': 'assets/js/services/masters/itemMasterService.js',
        'UserService': 'assets/js/services/userService.js',
        'DepartmentService': 'assets/js/services/departmentService.js',
        'DimensionTypeService': 'assets/js/services/dimensionTypeService.js',
        'LedgerAccountService': 'assets/js/services/ledgerAccountService.js',
        'CompanyService': 'assets/js/services/masters/companyService.js',
        'viewInvoiceCtrl': 'assets/js/controllers/viewInvoiceCtrl.js',
        'viewInvoiceService': 'assets/js/services/viewInvoice.js',
        'editInvoiceCtrl': 'assets/js/controllers/editInvoiceCtrl.js',
        'editInvoiceService': 'assets/js/services/editInvoice.js',
        'QueueService': 'assets/js/services/queue.js',
        'QueueCtrl': 'assets/js/controllers/QueueCtrl.js',
        'loginCtrl': 'assets/js/controllers/loginCtrl.js',
        'LoginService': 'assets/js/services/login.js',
        'forgotCtrl': 'assets/js/controllers/forgotCtrl.js',
        'resetCtrl': 'assets/js/controllers/resetCtrl.js',
        'changePasswordCtrl': 'assets/js/controllers/changePasswordCtrl.js',
        'manageCustomerCtrl': 'assets/js/controllers/masters/manageCustomers.js',
        'manageUqcMappingCtrl': 'assets/js/controllers/masters/ManageUqcMapping.js',
        'manageItemMappingCtrl': 'assets/js/controllers/masters/manageItemMapping.js',
        'manageErrorMasterCtrl': 'assets/js/controllers/masters/manageErrorMaster.js',
        'customerInvoiceReportCtrl': 'assets/js/controllers/customerInvoiceReport.js',
        'managecustomerPaymentDueCtrl': 'assets/js/controllers/customerPaymentDueCtrl.js',
        'exportLogCtrl': 'assets/js/controllers/exportLog.js',
        'viewCostInvoiceCtrl': 'assets/js/controllers/viewCostInvoiceCtrl.js',
        'viewCustomerPaymentService': 'assets/js/services/customerPaymentDueService.js',
        'viewBulkStatusQueueCtrl': 'assets/js/controllers/bulkStatusQueueCtrl.js',
        'viewBulkStatusQueueService': 'assets/js/services/bulkStatusQueueService.js',
        'ViewCostInvoiceService': 'assets/js/services/viewInvoice.js',
        'customerCostInvoiceReportCtrl': 'assets/js/controllers/customerCostInvoiceReport.js',
        'costInvoicReportsService': 'assets/js/services/costInvoicReportsService.js',
        'ProcessedDataQueueCtrl': 'assets/js/controllers/processedDataQueueCtrl.js',
        'processedDataQueueService': 'assets/js/services/processedDataQueueService.js',

        //Master Page Controllers
        'manageCountryCtrl': 'assets/js/controllers/masters/manageCountryCtrl.js',
        'ManageCountryService': '/assets/js/services/masters/manageCountryService.js',
        'manageItemMasterCtrl': 'assets/js/controllers/masters/manageItemMasterCtrl.js',
        'manageRoleCtrl': 'assets/js/controllers/masters/manageRoleCtrl.js',
        'ManageRoleService': '/assets/js/services/masters/manageRoleService.js',
        'manageStateCodeCtrl': 'assets/js/controllers/masters/manageStateCodeCtrl.js',
        'ManageStateService': '/assets/js/services/masters/manageStateCodeService.js',
        'manageVendorCtrl': 'assets/js/controllers/masters/manageVendorCtrl.js',
        'manageCustomerUserCtrl': 'assets/js/controllers/masters/manageCustomerUserCtrl.js',
        'manageDepartmentCtrl': 'assets/js/controllers/masters/manageDepartmentCtrl.js',
        'manageDimensionTypeCtrl': 'assets/js/controllers/masters/manageDimensionTypeCtrl.js',
        'manageLedgerAccountCtrl': 'assets/js/controllers/masters/manageLedgerAccountCtrl.js',
        'manageEmailConfig': 'assets/js/controllers/masters/emailConfigCtrl.js',
        'manageUqcCtrl': 'assets/js/controllers/masters/manageUqcCtrl.js',
        'UqcMasterService': 'assets/js/services/masters/uqcmasterservice.js',
        'UqcMappingService': 'assets/js/services/masters/uqcMappingService.js',
        'ItemMappingService': 'assets/js/services/masters/itemMappingService.js',
        'ErrorMasterService': 'assets/js/services/masters/ErrorMasterService.js',
        'InvoiceReportsService': 'assets/js/services/invoicReportsService.js',
        'bulkUpload': 'assets/js/controllers/bulkUploadCtrl.js',
        'bulkInvoiceReportCtrl': 'assets/js/controllers/bulkInvoiceReportCtrl.js',
        'reportCtrl': 'assets/js/controllers/reportCtrl.js',

    },
    //*** angularJS Modules
    modules: [{
        name: 'angularMoment',
        files: ['../bower_components/angular-moment/angular-moment.min.js']
    }, {
        name: 'toaster',
        files: ['../bower_components/AngularJS-Toaster/toaster.js', '../bower_components/AngularJS-Toaster/toaster.css']
    }, {
        name: 'angularBootstrapNavTree',
        files: ['../bower_components/angular-bootstrap-nav-tree/dist/abn_tree_directive.js', '../bower_components/angular-bootstrap-nav-tree/dist/abn_tree.css']
    }, {
        name: 'angular-ladda',
        files: ['../bower_components/angular-ladda/dist/angular-ladda.min.js']
    }, {
        name: 'ngTable',
        files: ['../bower_components/ng-table/dist/ng-table.min.js', '../bower_components/ng-table/dist/ng-table.min.css']
    }, {
        name: 'ui.select',
        files: ['../bower_components/angular-ui-select/dist/select.min.js', '../bower_components/angular-ui-select/dist/select.min.css', '../bower_components/select2/dist/css/select2.min.css', '../bower_components/select2-bootstrap-css/select2-bootstrap.min.css', '../bower_components/selectize/dist/css/selectize.bootstrap3.css']
    }, {
        name: 'ui.mask',
        files: ['../bower_components/angular-ui-mask/dist/mask.min.js']
    }, {
        name: 'ngImgCrop',
        files: ['../bower_components/ng-img-crop/compile/minified/ng-img-crop.js', '../bower_components/ng-img-crop/compile/minified/ng-img-crop.css']
    }, {
        name: 'angularFileUpload',
        files: ['../bower_components/angular-file-upload/dist/angular-file-upload.min.js']
    }, {
        name: 'ngAside',
        files: ['../bower_components/angular-aside/dist/js/angular-aside.min.js', '../bower_components/angular-aside/dist/css/angular-aside.min.css']
    }, {
        name: 'truncate',
        files: ['../bower_components/angular-truncate/src/truncate.js']
    }, {
        name: 'oitozero.ngSweetAlert',
        files: ['../bower_components/ngSweetAlert/SweetAlert.min.js']
    }, {
        name: 'monospaced.elastic',
        files: ['../bower_components/angular-elastic/elastic.js']
    }, {
        name: 'ngMap',
        files: ['../bower_components/ngmap/build/scripts/ng-map.min.js']
    }, {
        name: 'tc.chartjs',
        files: ['../bower_components/tc-angular-chartjs/dist/tc-angular-chartjs.min.js']
    }, {
        name: 'flow',
        files: ['../bower_components/ng-flow/dist/ng-flow-standalone.min.js']
    }, {
        name: 'uiSwitch',
        files: ['../bower_components/angular-ui-switch/angular-ui-switch.min.js', '../bower_components/angular-ui-switch/angular-ui-switch.min.css']
    }, {
        name: 'ckeditor',
        files: ['../bower_components/angular-ckeditor/angular-ckeditor.min.js']
    }, {
        name: 'mwl.calendar',
        files: ['../bower_components/angular-bootstrap-calendar/dist/js/angular-bootstrap-calendar-tpls.js', '../bower_components/angular-bootstrap-calendar/dist/css/angular-bootstrap-calendar.min.css', 'assets/js/config/config-calendar.js']
    }, {
        name: 'ng-nestable',
        files: ['../bower_components/ng-nestable/src/angular-nestable.js']
    }, {
        name: 'vAccordion',
        files: ['../bower_components/v-accordion/dist/v-accordion.min.js', '../bower_components/v-accordion/dist/v-accordion.min.css']
    }, {
        name: 'xeditable',
        files: ['../bower_components/angular-xeditable/dist/js/xeditable.min.js', '../bower_components/angular-xeditable/dist/css/xeditable.css', 'assets/js/config/config-xeditable.js']
    }, {
        name: 'checklist-model',
        files: ['../bower_components/checklist-model/checklist-model.js']
    }, {
        name: 'angular-notification-icons',
        files: ['../bower_components/angular-notification-icons/dist/angular-notification-icons.min.js', '../bower_components/angular-notification-icons/dist/angular-notification-icons.min.css']
    }, {
        name: 'angularSpectrumColorpicker',
        files: ['../bower_components/angular-spectrum-colorpicker/dist/angular-spectrum-colorpicker.min.js']
    }]
});
