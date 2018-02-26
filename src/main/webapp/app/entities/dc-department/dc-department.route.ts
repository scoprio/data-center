import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DcDepartmentComponent } from './dc-department.component';
import { DcDepartmentDetailComponent } from './dc-department-detail.component';
import { DcDepartmentPopupComponent } from './dc-department-dialog.component';
import { DcDepartmentDeletePopupComponent } from './dc-department-delete-dialog.component';

export const dcDepartmentRoute: Routes = [
    {
        path: 'dc-department',
        component: DcDepartmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcDepartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dc-department/:id',
        component: DcDepartmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcDepartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dcDepartmentPopupRoute: Routes = [
    {
        path: 'dc-department-new',
        component: DcDepartmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcDepartment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dc-department/:id/edit',
        component: DcDepartmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcDepartment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dc-department/:id/delete',
        component: DcDepartmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dataCenterApp.dcDepartment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
