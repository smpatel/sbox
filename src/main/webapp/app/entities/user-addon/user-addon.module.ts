import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SboxSharedModule } from '../../shared';
import { SboxAdminModule } from '../../admin/admin.module';
import {
    UserAddonService,
    UserAddonPopupService,
    UserAddonComponent,
    UserAddonDetailComponent,
    UserAddonDialogComponent,
    UserAddonPopupComponent,
    UserAddonDeletePopupComponent,
    UserAddonDeleteDialogComponent,
    userAddonRoute,
    userAddonPopupRoute,
    UserAddonResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...userAddonRoute,
    ...userAddonPopupRoute,
];

@NgModule({
    imports: [
        SboxSharedModule,
        SboxAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserAddonComponent,
        UserAddonDetailComponent,
        UserAddonDialogComponent,
        UserAddonDeleteDialogComponent,
        UserAddonPopupComponent,
        UserAddonDeletePopupComponent,
    ],
    entryComponents: [
        UserAddonComponent,
        UserAddonDialogComponent,
        UserAddonPopupComponent,
        UserAddonDeleteDialogComponent,
        UserAddonDeletePopupComponent,
    ],
    providers: [
        UserAddonService,
        UserAddonPopupService,
        UserAddonResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SboxUserAddonModule {}
