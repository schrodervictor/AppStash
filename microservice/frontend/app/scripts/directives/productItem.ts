eshop.directive("shopProductItem", function(){
    scope: true;

    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/productItem.html";
    directive.replace = true;

    return directive;
});





