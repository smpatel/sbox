import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ApiComponent } from './api.component';
import { ApiDetailComponent } from './api-detail.component';
import { ApiPopupComponent } from './api-dialog.component';
import { ApiDeletePopupComponent } from './api-delete-dialog.component';

export const apiRoute: Routes = [
    {
        path: 'api',
        component: ApiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apis'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'api/:id',
        component: ApiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apis'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiPopupRoute: Routes = [
    {
        path: 'api-new',
        component: ApiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'api/:id/edit',
        component: ApiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'api/:id/delete',
        component: ApiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
