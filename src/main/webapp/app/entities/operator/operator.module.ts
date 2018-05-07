import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SboxSharedModule } from '../../shared';
import {
    OperatorService,
    OperatorPopupService,
    OperatorComponent,
    OperatorDetailComponent,
    OperatorDialogComponent,
    OperatorPopupComponent,
    OperatorDeletePopupComponent,
    OperatorDeleteDialogComponent,
    operatorRoute,
    operatorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...operatorRoute,
    ...operatorPopupRoute,
];

@NgModule({
    imports: [
        SboxSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OperatorComponent,
        OperatorDetailComponent,
        OperatorDialogComponent,
        OperatorDeleteDialogComponent,
        OperatorPopupComponent,
        OperatorDeletePopupComponent,
    ],
    entryComponents: [
        OperatorComponent,
        OperatorDialogComponent,
        OperatorPopupComponent,
        OperatorDeleteDialogComponent,
        OperatorDeletePopupComponent,
    ],
    providers: [
        OperatorService,
        OperatorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SboxOperatorModule {}
