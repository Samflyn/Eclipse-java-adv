'use strict';

/**
 * Config for the router
 */
app.config(['$stateProvider', '$urlRouterProvider', '$controllerProvider', '$compileProvider', '$filterProvider', '$provide', '$ocLazyLoadProvider', 'JS_REQUIRES',
    function ($stateProvider, $urlRouterProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $ocLazyLoadProvider, jsRequires) {

        app.controller = $controllerProvider.register;
        app.directive = $compileProvider.directive;
        app.filter = $filterProvider.register;
        app.factory = $provide.factory;
        app.service = $provide.service;
        app.constant = $provide.constant;
        app.value = $provide.value;

        // LAZY MODULES

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
            modules: jsRequires.modules
        });

        // APPLICATION ROUTES
        // -----------------------------------
        // For any unmatched url, redirect to /app/dashboard
        $urlRouterProvider.otherwise("login/signin");
        //
        // Set up the states
        $stateProvider.state('app', {
            url: "/app",
            templateUrl: "assets/views/app.html",
            resolve: loadSequence('modernizr', 'moment', 'angularMoment', 'uiSwitch', 'perfect-scrollbar-plugin', 'toaster', 'ngAside', 'vAccordion', 'sweet-alert', 'chartjs', 'tc.chartjs', 'oitozero.ngSweetAlert', 'chatCtrl', 'truncate', 'htmlToPlaintext', 'angular-notification-icons'),
            abstract: true
        }).state('app.dashboard', {
            url: "/dashboard",
            templateUrl: "assets/views/dashboards/dashboard.html",
            resolve: loadSequence('jquery-sparkline', 'dashboardCtrl'),
            title: 'Dashboard',
            ncyBreadcrumb: {
                label: 'Customer_Dashboard'
            },
            requireLogin: true
        }).state('app.adminDashboard', {
            url: "/admin-dashboard",
            templateUrl: "assets/views/dashboards/admin_dashboard.html",
            resolve: loadSequence('jquery-sparkline', 'adminDashboardCtrl'),
            title: 'Dashboard',
            ncyBreadcrumb: {
                label: 'Admin_Dashboard'
            },
            requireLogin: true
        }).state('app.superadminDashboard', {
            url: "/superadmin-dashboard",
            templateUrl: "assets/views/dashboards/superadmin_dashboard.html",
            resolve: loadSequence('jquery-sparkline', 'superadminDashboardCtrl'),
            title: 'Superadmin Dashboard',
            ncyBreadcrumb: {
                label: 'Superadmin_Dashboard'
            },
            requireLogin: true
        }).state('app.customerGenerate', {
            url: '/generate-customer-invoice',
            templateUrl: "assets/views/PO/generate-customer-invoice.html",
            title: 'Generate Invoice',
            resolve: loadSequence('generateCustomerInvoiceCtrl', 'GenerateInvoiceService'),
            ncyBreadcrumb: {
                label: 'Generate Invoice'
            },
            requireLogin: true

        }).state('app.customerCostInvoice', {
            url: '/generate-cost-invoice',
            templateUrl: "assets/views/cost/customer-cost-invoice.html",
            title: 'Cost Invoice',
            resolve: loadSequence('CustomerCostInvoiceCtrl', 'GenerateInvoiceService'),
            ncyBreadcrumb: {
                label: 'Cost Invoice'
            },
            requireLogin: true

        }).state('app.filestatusQueue', {
            url: '/bulkStatusQueue/:invoiceType',
            templateUrl: "assets/views/file_status_Queue.html",
            title: 'Bulk Status Queue',
            resolve: loadSequence('viewBulkStatusQueueCtrl', 'viewBulkStatusQueueService'),
            ncyBreadcrumb: {
                label: 'Bulk Status Queue'
            },
            requireLogin: true

        }).state('app.costInvoiceQueue', {
            url: '/costInvoiceQueue',
            templateUrl: "assets/views/costInvoiceQueue.html",
            title: 'cost Invoice Queue',
            resolve: loadSequence('costInvoiceQueueCtrl'),
            ncyBreadcrumb: {
                label: 'Cost Invoice Queue'
            },
            requireLogin: true

        }).state('app.exportAuditLog', {
            url: '/ExportLog',
            templateUrl: "assets/views/manage-ExportLog.html",
            title: 'ExportLog',
            resolve: loadSequence('exportLogCtrl'),
            ncyBreadcrumb: {
                label: 'Export Log'
            },
            requireLogin: true

        }).state('app.processedDataQueue', {
            url: '/processedDataQueue',
            templateUrl: "assets/views/processed_Data_Queue.html",
            title: 'processed Data Queue',
            resolve: loadSequence('ProcessedDataQueueCtrl', 'processedDataQueueService'),
            ncyBreadcrumb: {
                label: 'Processed Data Queue'
            },
            requireLogin: true

        }).state('app.costInvoiceBulkUpload', {
            url: '/costInvoiceBulkUpload',
            templateUrl: "assets/views/costInvoiceBulkUpload.html",
            title: 'costInvoiceBulkUpload',
            resolve: loadSequence('costInvoiceBulkUploadCtrl'),
            ncyBreadcrumb: {
                label: 'Processed Data Queue'
            },
            requireLogin: true

        }).state('app.compareInvoice', {
            url: '/compare-uploaded-invoice',
            templateUrl: "assets/views/compare-uploaded-invoice.html",
            title: 'Compare Invoice',
            resolve: loadSequence('generateCustomerInvoiceCtrl', 'GenerateInvoiceService'),
            requireLogin: true
        }).state('app.customerInvoiceReport', {
            url: '/customer-Invoice-Report',
            templateUrl: "assets/views/customer-invoice-report.html",
            title: 'Invoice Report',
            resolve: loadSequence('customerInvoiceReportCtrl', 'GenerateInvoiceService', 'InvoiceReportsService'),
            ncyBreadcrumb: {
                label: 'Generate Invoice'
            },
            requireLogin: true

        }).state('app.cutomerCostInvoiceReport', {
            url: '/customer-cost-Invoice-Report',
            templateUrl: "assets/views/customer-cost-invoice-report.html",
            title: 'Cost Invoice Report',
            resolve: loadSequence('customerCostInvoiceReportCtrl', 'costInvoicReportsService'),
            ncyBreadcrumb: {
                label: 'Customer Cost Invoice '
            },
            requireLogin: true

        }).state('app.report', {
            url: '/report',
            templateUrl: "assets/views/report.html",
            title: 'Report',
            resolve: loadSequence('reportCtrl'),
            requireLogin: true
        }).state('app.edit', {
            url: '/edit-customer-invoice/:headerId',
            templateUrl: "assets/views/generate-customer-invoice.html",
            title: 'Edit Invoice',
            resolve: loadSequence('generateCustomerInvoiceCtrl', 'GenerateInvoiceService'),
            ncyBreadcrumb: {
                label: 'Edit Invoice'
            },
            requireLogin: true
        }).state('app.editvendor', {
            url: '/edit-vendor-invoice/:headerId',
            templateUrl: "assets/views/vendor-generate-invoice.html",
            title: 'Edit Invoice',
            resolve: loadSequence('VendorGenerateInvoiceCtrl', 'GenerateInvoiceService'),
            ncyBreadcrumb: {
                label: 'Edit Invoice'
            },
            requireLogin: true
        }).state('app.editCost', {
            url: '/edit-cost-invoice/:headerId',
            templateUrl: "assets/views/customer-cost-invoice.html",
            title: 'Edit Cost Invoice',
            resolve: loadSequence('CustomerCostInvoiceCtrl', 'GenerateInvoiceService'),
            ncyBreadcrumb: {
                label: 'Edit Cost Invoice'
            },
            requireLogin: true
        }).state('app.view', {
            url: '/view-customer-invoice/:headerId',
            templateUrl: "assets/views/view-customer-invoice.html",
            title: 'View Customer-Invoice',
            resolve: loadSequence('viewInvoiceCtrl', 'viewInvoiceService', 'GenerateInvoiceService'),
            ncyBreadcrumb: {
                label: 'View Customer-Invoice'
            },
            requireLogin: true
        }).state('app.costInvoiceView', {
            url: '/costInvoiceView/:headerId/:tabId',
            templateUrl: "assets/views/costInvoiceView.html",
            title: 'View Invoice',
            resolve: loadSequence('viewCostInvoiceCtrl', 'ViewCostInvoiceService', 'GenerateInvoiceService'),
            ncyBreadcrumb: {
                label: 'View Invoice'
            },
            requireLogin: true
        }).state('app.billQueue', {
            url: '/invoice-queue',
            templateUrl: "assets/views/queue.html",
            title: 'Invoice queue',
            resolve: loadSequence('QueueCtrl', 'QueueService'),
            ncyBreadcrumb: {
                label: 'Invoice Queue'
            },
            requireLogin: true
        }).state('app.manageCountry', {
            url: '/manage-country',
            templateUrl: "assets/views/masters/manage-country.html",
            title: 'Manage Country',
            resolve: loadSequence('manageCountryCtrl', 'ManageCountryService'),
            ncyBreadcrumb: {
                label: 'Manage Country'
            },
            requireLogin: true
        }).state('app.manageVendors', {
            url: '/manage-vendor',
            templateUrl: "assets/views/masters/manage-vendors.html",
            title: 'Manage Vendors',
            resolve: loadSequence('manageVendorCtrl', 'CompanyService'),
            ncyBreadcrumb: {
                label: 'Manage Vendors'
            },
            requireLogin: true
        }).state('app.manageCustomer', {
            url: '/manage-customer',
            templateUrl: "assets/views/masters/manage-company.html",
            title: 'Manage Customers',
            resolve: loadSequence('manageCustomerCtrl', 'CompanyService'),
            ncyBreadcrumb: {
                label: 'Manage Customers'
            },
            requireLogin: true
        }).state('app.manageCustomerUser', {
            url: '/manage-customer-user',
            templateUrl: "assets/views/masters/manage-customer-user.html",
            title: 'Manage Customer User',
            resolve: loadSequence('manageCustomerUserCtrl', 'UserService'),
            ncyBreadcrumb: {
                label: 'Manage Customer User'
            },
            requireLogin: true
        }).state('app.manageItemMaster', {
            url: '/manage-item',
            templateUrl: "assets/views/masters/manage-item-master.html",
            title: 'Manage Item',
            resolve: loadSequence('manageItemMasterCtrl', 'ItemMasterService'),
            ncyBreadcrumb: {
                label: 'Manage Item'
            },
            requireLogin: true
        }).state('app.manageItemMapping', {
            url: '/manage-itemMapping',
            templateUrl: "assets/views/masters/manage-item-mapping.html",
            title: 'Manage ItemMapping',
            resolve: loadSequence('manageItemMappingCtrl', 'ItemMappingService'),
            ncyBreadcrumb: {
                label: 'Manage ItemMapping'
            },
            requireLogin: true
        }).state('app.manageRole', {
            url: '/manage-role',
            templateUrl: "assets/views/masters/manage-role.html",
            title: 'Manage Role',
            resolve: loadSequence('manageRoleCtrl', 'ManageRoleService'),
            ncyBreadcrumb: {
                label: 'Manage Role'
            },
            requireLogin: true
        }).state('app.manageStateCode', {
            url: '/manage-state-code',
            templateUrl: "assets/views/masters/manage-state-code.html",
            title: 'Manage State Code',
            resolve: loadSequence('manageStateCodeCtrl', 'ManageStateService'),
            ncyBreadcrumb: {
                label: 'Manage State Code'
            },
            requireLogin: true
        }).state('app.customerPaymentDue', {
            url: '/manage-customerPaymentDue',
            templateUrl: "assets/views/customerPaymentDue.html",
            title: 'Payment Due',
            resolve: loadSequence('managecustomerPaymentDueCtrl', 'viewCustomerPaymentService'),
            ncyBreadcrumb: {
                label: 'Payment Due'
            },
            requireLogin: true
        }).state('app.bulkInvoiceReport', {
            url: '/bulk-invoice-report',
            templateUrl: "assets/views/bulk-invoice-report.html",
            title: 'bulk-invoice-report',
            resolve: loadSequence('bulkInvoiceReportCtrl', 'InvoiceReportsService'),
            requireLogin: true
        }).state('app.emailConfig', {
            url: '/manage-email-config',
            templateUrl: "assets/views/masters/email-config.html",
            title: 'Email Configuration',
            resolve: loadSequence('manageEmailConfig'),
            ncyBreadcrumb: {
                label: 'Email Configuration'
            },
            requireLogin: true
        }).state('app.manageUqc', {
            url: '/manage-uqc',
            templateUrl: "assets/views/masters/manage-uqc-master.html",
            title: 'Manage UQC',
            resolve: loadSequence('manageUqcCtrl', 'UqcMasterService'),
            ncyBreadcrumb: {
                label: 'Manage UQC'
            },
            requireLogin: true
        }).state('app.manageUqcMapping', {
            url: '/manage-uqcmapping',
            'companyRoleMappingCtrl': 'assets/js/controllers/companyRoleMappingCtrl.js',
            templateUrl: "assets/views/masters/manage-uqcmapping.html",
            title: 'Manage UQC',
            resolve: loadSequence('manageUqcMappingCtrl', 'UqcMappingService'),
            ncyBreadcrumb: {
                label: 'Manage UQC'
            },
            requireLogin: true
        }).state('app.bulkUpload', {
            url: '/bulk-upload',
            templateUrl: "assets/views/bulk-upload.html",
            resolve: loadSequence('bulkUpload'),
            requireLogin: true
        }).state('app.changePassword', {
            url: '/change-password',
            templateUrl: "assets/views/change_password.html",
            resolve: loadSequence('changePasswordCtrl'),
            requireLogin: true
        }).state('app.pages', {
            url: '/pages',
            template: '<div ui-view class="fade-in-up"></div>',
            title: 'Pages',
            ncyBreadcrumb: {
                label: 'Pages'
            }
        }).state('app.pages.user', {
            url: '/user',
            templateUrl: "assets/views/pages_user_profile.html",
            title: 'User Profile',
            ncyBreadcrumb: {
                label: 'User Profile'
            },
            resolve: loadSequence('flow', 'userCtrl', 'UserService')
        }).state('app.errorMaster', {
            url: '/errorMaster',
            templateUrl: "assets/views/masters/manage-error-master.html",
            title: 'Error Master',
            ncyBreadcrumb: {
                label: 'Error Master'
            },
            resolve: loadSequence('manageErrorMasterCtrl', 'ErrorMasterService')
        }).state('app.pages.blank', {
            url: '/blank',
            templateUrl: "assets/views/pages_blank_page.html",
            ncyBreadcrumb: {
                label: 'Starter Page'
            }
        }).state('error', {
            url: '/error',
            template: '<div ui-view class="fade-in-up"></div>'
        }).state('error.404', {
            url: '/404',
            templateUrl: "assets/views/utility_404.html",
        }).state('error.500', {
            url: '/500',
            templateUrl: "assets/views/utility_500.html",
        })

            // Login routes

            .state('login', {
                url: '/login',
                template: '<div ui-view class="fade-in-right-big smooth"></div>',
                requireLogin: false
            }).state('login.signin', {
                url: '/signin',
                templateUrl: "assets/views/login_login.html",
                resolve: loadSequence('loginCtrl', 'LoginService'),
                requireLogin: false
            }).state('login.forgot', {
                url: '/forgot',
                templateUrl: "assets/views/login_forgot.html",
                resolve: loadSequence('forgotCtrl', 'sweet-alert', 'oitozero.ngSweetAlert'),
                requireLogin: false
            }).state('login.reset', {
                url: '/reset',
                templateUrl: "assets/views/login_reset.html",
                resolve: loadSequence('resetCtrl', 'sweet-alert', 'oitozero.ngSweetAlert'),
                requireLogin: false
            }).state('login.lockscreen', {
                url: '/lock',
                templateUrl: "assets/views/login_lock_screen.html",
                resolve: loadSequence('loginCtrl', 'LoginService'),
                requireLogin: true
            });

        // Generates a resolve object previously configured in constant.JS_REQUIRES
        // (config.constant.js)
        function loadSequence() {
            var _args = arguments;
            return {
                deps: ['$ocLazyLoad', '$q',
                    function ($ocLL, $q) {
                        var promise = $q.when(1);
                        for (var i = 0, len = _args.length; i < len; i++) {
                            promise = promiseThen(_args[i]);
                        }
                        return promise;

                        function promiseThen(_arg) {
                            if (typeof _arg == 'function')
                                return promise.then(_arg);
                            else
                                return promise.then(function () {
                                    var nowLoad = requiredData(_arg);
                                    if (!nowLoad)
                                        return $.error('Route resolve: Bad resource name [' + _arg + ']');
                                    return $ocLL.load(nowLoad);
                                });
                        }

                        function requiredData(name) {
                            if (jsRequires.modules)
                                for (var m in jsRequires.modules)
                                    if (jsRequires.modules[m].name && jsRequires.modules[m].name === name)
                                        return jsRequires.modules[m];
                            return jsRequires.scripts && jsRequires.scripts[name];
                        }
                    }]
            };
        }
    }]);

app.run(['$rootScope', '$http', '$location', '$localStorage',
    function ($rootScope, $http, $location, $localStorage) {

        // keep user logged in after page refresh
        if ($localStorage.token) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.token;
        }

        // redirect to login page if not logged in and trying to access a restricted page
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            var publicPages = ['/login/signin'];
            var restrictedPage = publicPages.indexOf($location.path()) === -1;
            if (restrictedPage && !$localStorage.token) {
                $location.path('/login/signin');
            }
        });
    }]);