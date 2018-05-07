import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CommissionGroupComponent } from './commission-group.component';
import { CommissionGroupDetailComponent } from './commission-group-detail.component';
import { CommissionGroupPopupComponent } from './commission-group-dialog.component';
import { CommissionGroupDeletePopupComponent } from './commission-group-delete-dialog.component';

export const commissionGroupRoute: Routes = [
    {
        path: 'commission-group',
        component: CommissionGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommissionGroups'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'commission-group/:id',
        component: CommissionGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommissionGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commissionGroupPopupRoute: Routes = [
    {
        path: 'commission-group-new',
        component: CommissionGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommissionGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commission-group/:id/edit',
        component: CommissionGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommissionGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commission-group/:id/delete',
        component: CommissionGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommissionGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
