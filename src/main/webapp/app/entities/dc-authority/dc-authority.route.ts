import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DcAuthorityComponent } from './dc-authority.component';
import { DcAuthorityDetailComponent } from './dc-authority-detail.component';
import { DcAuthorityPopupComponent } from './dc-authority-dialog.component';
import { DcAuthorityDeletePopupComponent } from './dc-authority-delete-dialog.component';

@Injectable()
export class DcAuthorityResolvePagingParams implements Resolve<any> {

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

export const dcAuthorityRoute: Routes = [
    {
        path: 'dc-authority',
        component: DcAuthorityComponent,
        resolve: {
            'pagingParams': DcAuthorityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcAuthority.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dc-authority/:id',
        component: DcAuthorityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcAuthority.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dcAuthorityPopupRoute: Routes = [
    {
        path: 'dc-authority-new',
        component: DcAuthorityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcAuthority.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dc-authority/:id/edit',
        component: DcAuthorityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcAuthority.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dc-authority/:id/delete',
        component: DcAuthorityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcAuthority.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
