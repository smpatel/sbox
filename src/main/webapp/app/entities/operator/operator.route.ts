import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OperatorComponent } from './operator.component';
import { OperatorDetailComponent } from './operator-detail.component';
import { OperatorPopupComponent } from './operator-dialog.component';
import { OperatorDeletePopupComponent } from './operator-delete-dialog.component';

export const operatorRoute: Routes = [
    {
        path: 'operator',
        component: OperatorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Operators'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'operator/:id',
        component: OperatorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Operators'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operatorPopupRoute: Routes = [
    {
        path: 'operator-new',
        component: OperatorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Operators'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operator/:id/edit',
        component: OperatorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Operators'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operator/:id/delete',
        component: OperatorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Operators'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
