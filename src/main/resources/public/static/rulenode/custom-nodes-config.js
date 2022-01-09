!function(e,t){"object"==typeof exports&&"undefined"!=typeof module?t(exports,require("@angular/core"),require("@angular/common"),require("@home/components/public-api"),require("@ngx-translate/core"),require("@shared/public-api"),require("@ngrx/store"),require("@angular/forms")):"function"==typeof define&&define.amd?define("custom-nodes-config",["exports","@angular/core","@angular/common","@home/components/public-api","@ngx-translate/core","@shared/public-api","@ngrx/store","@angular/forms"],t):t((e=e||self)["custom-nodes-config"]={},e.ng.core,e.ng.common,e.publicApi,e["ngx-translate"],e.shared,e["ngrx-store"],e.ng.forms)}(this,(function(e,t,o,n,r,u,a,i){"use strict";
/*! *****************************************************************************
    Copyright (c) Microsoft Corporation.

    Permission to use, copy, modify, and/or distribute this software for any
    purpose with or without fee is hereby granted.

    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
    REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
    AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
    INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
    LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
    OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
    PERFORMANCE OF THIS SOFTWARE.
    ***************************************************************************** */var l=function(e,t){return(l=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o])})(e,t)};function m(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Class extends value "+String(t)+" is not a constructor or null");function o(){this.constructor=e}l(e,t),e.prototype=null===t?Object.create(t):(o.prototype=t.prototype,new o)}function c(e,t,o,n){var r,u=arguments.length,a=u<3?t:null===n?n=Object.getOwnPropertyDescriptor(t,o):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,o,n);else for(var i=e.length-1;i>=0;i--)(r=e[i])&&(a=(u<3?r(a):u>3?r(t,o,a):r(t,o))||a);return u>3&&a&&Object.defineProperty(t,o,a),a}function f(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)}Object.create;Object.create;var s=function(e){function o(t,o){var n=e.call(this,t)||this;return n.store=t,n.fb=o,n}return m(o,e),o.prototype.configForm=function(){return this.checkKeyConfigForm},o.prototype.onConfigurationSet=function(e){this.checkKeyConfigForm=this.fb.group({key:[e?e.key:null,[i.Validators.required]]})},o.ctorParameters=function(){return[{type:a.Store},{type:i.FormBuilder}]},o=c([t.Component({selector:"tb-filter-node-check-key-config",template:'<section [formGroup]="checkKeyConfigForm" fxLayout="column">\n    <mat-form-field class="mat-block">\n        <mat-label translate>tb.rulenode.msg-key</mat-label>\n        <input matInput formControlName="key" required>\n    </mat-form-field>\n</section>\n'}),f("design:paramtypes",[a.Store,i.FormBuilder])],o)}(u.RuleNodeConfigurationComponent),p=function(){function e(){}return e=c([t.NgModule({declarations:[s],imports:[o.CommonModule,u.SharedModule],exports:[s]})],e)}(),d=function(e){function o(t,o){var n=e.call(this,t)||this;return n.store=t,n.fb=o,n}return m(o,e),o.prototype.configForm=function(){return this.getSumIntoMetadataConfigForm},o.prototype.onConfigurationSet=function(e){this.getSumIntoMetadataConfigForm=this.fb.group({inputKey:[e?e.inputKey:null,[i.Validators.required]],outputKey:[e?e.outputKey:null,[i.Validators.required]]})},o.ctorParameters=function(){return[{type:a.Store},{type:i.FormBuilder}]},o=c([t.Component({selector:"tb-enrichment-node-sum-into-metadata-config",template:'<section [formGroup]="getSumIntoMetadataConfigForm" fxLayout="column">\n    <mat-form-field class="mat-block">\n        <mat-label translate>tb.rulenode.input-key</mat-label>\n        <input matInput formControlName="inputKey" required>\n    </mat-form-field>\n    <mat-form-field class="mat-block">\n        <mat-label translate>tb.rulenode.output-key</mat-label>\n        <input matInput formControlName="outputKey" required>\n    </mat-form-field>\n</section>\n'}),f("design:paramtypes",[a.Store,i.FormBuilder])],o)}(u.RuleNodeConfigurationComponent),y=function(){function e(){}return e=c([t.NgModule({declarations:[d],imports:[o.CommonModule,u.SharedModule],exports:[d]})],e)}(),g=function(e){function o(t,o){var n=e.call(this,t)||this;return n.store=t,n.fb=o,n}return m(o,e),o.prototype.configForm=function(){return this.getSumConfigForm},o.prototype.onConfigurationSet=function(e){this.getSumConfigForm=this.fb.group({inputKey:[e?e.inputKey:null,[i.Validators.required]],outputKey:[e?e.outputKey:null,[i.Validators.required]]})},o.ctorParameters=function(){return[{type:a.Store},{type:i.FormBuilder}]},o=c([t.Component({selector:"tb-transformation-node-sum-config",template:'<section [formGroup]="getSumConfigForm" fxLayout="column">\n    <mat-form-field class="mat-block">\n        <mat-label translate>tb.rulenode.input-key</mat-label>\n        <input matInput formControlName="inputKey" required>\n    </mat-form-field>\n    <mat-form-field class="mat-block">\n        <mat-label translate>tb.rulenode.output-key</mat-label>\n        <input matInput formControlName="outputKey" required>\n    </mat-form-field>\n</section>\n'}),f("design:paramtypes",[a.Store,i.FormBuilder])],o)}(u.RuleNodeConfigurationComponent),b=function(){function e(){}return e=c([t.NgModule({declarations:[g],imports:[o.CommonModule,u.SharedModule],exports:[g]})],e)}(),h=function(){function e(e){!function(e){e.setTranslation("en_US",{tb:{rulenode:{"msg-key":"Message key","input-key":"Input key","output-key":"Output key"}}},!0)}(e)}return e.ctorParameters=function(){return[{type:r.TranslateService}]},e=c([t.NgModule({imports:[o.CommonModule,u.SharedModule,n.HomeComponentsModule],exports:[p,y,b]}),f("design:paramtypes",[r.TranslateService])],e)}();e.CustomNodesConfigModule=h,e.ɵa=p,e.ɵb=s,e.ɵc=y,e.ɵd=d,e.ɵe=b,e.ɵf=g,Object.defineProperty(e,"__esModule",{value:!0})}));
//# sourceMappingURL=custom-nodes-config.umd.min.js.map