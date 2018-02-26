import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DcRegionComponent } from './dc-region.component';
import { DcRegionDetailComponent } from './dc-region-detail.component';
import { DcRegionPopupComponent } from './dc-region-dialog.component';
import { DcRegionDeletePopupComponent } from './dc-region-delete-dialog.component';

@Injectable()
export class DcRegionResolvePagingParams implements Resolve<any> {

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

export const dcRegionRoute: Routes = [
    {
        path: 'dc-region',
        component: DcRegionComponent,
        resolve: {
            'pagingParams': DcRegionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dc-region/:id',
        component: DcRegionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dcRegionPopupRoute: Routes = [
    {
        path: 'dc-region-new',
        component: DcRegionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dc-region/:id/edit',
        component: DcRegionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dc-region/:id/delete',
        component: DcRegionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
