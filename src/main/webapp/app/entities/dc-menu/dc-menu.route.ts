import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DcMenuComponent } from './dc-menu.component';
import { DcMenuDetailComponent } from './dc-menu-detail.component';
import { DcMenuPopupComponent } from './dc-menu-dialog.component';
import { DcMenuDeletePopupComponent } from './dc-menu-delete-dialog.component';

export const dcMenuRoute: Routes = [
    {
        path: 'dc-menu',
        component: DcMenuComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dc-menu/:id',
        component: DcMenuDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dcMenuPopupRoute: Routes = [
    {
        path: 'dc-menu-new',
        component: DcMenuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dc-menu/:id/edit',
        component: DcMenuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dc-menu/:id/delete',
        component: DcMenuDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
