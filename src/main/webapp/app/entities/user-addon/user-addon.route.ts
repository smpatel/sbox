import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { UserAddonComponent } from './user-addon.component';
import { UserAddonDetailComponent } from './user-addon-detail.component';
import { UserAddonPopupComponent } from './user-addon-dialog.component';
import { UserAddonDeletePopupComponent } from './user-addon-delete-dialog.component';

@Injectable()
export class UserAddonResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const userAddonRoute: Routes = [
    {
        path: 'user-addon',
        component: UserAddonComponent,
        resolve: {
            'pagingParams': UserAddonResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddons'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-addon/:id',
        component: UserAddonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddons'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userAddonPopupRoute: Routes = [
    {
        path: 'user-addon-new',
        component: UserAddonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddons'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-addon/:id/edit',
        component: UserAddonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddons'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-addon/:id/delete',
        component: UserAddonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddons'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
