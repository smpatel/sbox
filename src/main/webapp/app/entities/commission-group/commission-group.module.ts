import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SboxSharedModule } from '../../shared';
import {
    CommissionGroupService,
    CommissionGroupPopupService,
    CommissionGroupComponent,
    CommissionGroupDetailComponent,
    CommissionGroupDialogComponent,
    CommissionGroupPopupComponent,
    CommissionGroupDeletePopupComponent,
    CommissionGroupDeleteDialogComponent,
    commissionGroupRoute,
    commissionGroupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...commissionGroupRoute,
    ...commissionGroupPopupRoute,
];

@NgModule({
    imports: [
        SboxSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommissionGroupComponent,
        CommissionGroupDetailComponent,
        CommissionGroupDialogComponent,
        CommissionGroupDeleteDialogComponent,
        CommissionGroupPopupComponent,
        CommissionGroupDeletePopupComponent,
    ],
    entryComponents: [
        CommissionGroupComponent,
        CommissionGroupDialogComponent,
        CommissionGroupPopupComponent,
        CommissionGroupDeleteDialogComponent,
        CommissionGroupDeletePopupComponent,
    ],
    providers: [
        CommissionGroupService,
        CommissionGroupPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SboxCommissionGroupModule {}
