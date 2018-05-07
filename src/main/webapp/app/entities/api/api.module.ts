import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SboxSharedModule } from '../../shared';
import {
    ApiService,
    ApiPopupService,
    ApiComponent,
    ApiDetailComponent,
    ApiDialogComponent,
    ApiPopupComponent,
    ApiDeletePopupComponent,
    ApiDeleteDialogComponent,
    apiRoute,
    apiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...apiRoute,
    ...apiPopupRoute,
];

@NgModule({
    imports: [
        SboxSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ApiComponent,
        ApiDetailComponent,
        ApiDialogComponent,
        ApiDeleteDialogComponent,
        ApiPopupComponent,
        ApiDeletePopupComponent,
    ],
    entryComponents: [
        ApiComponent,
        ApiDialogComponent,
        ApiPopupComponent,
        ApiDeleteDialogComponent,
        ApiDeletePopupComponent,
    ],
    providers: [
        ApiService,
        ApiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SboxApiModule {}
