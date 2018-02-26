import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataCenterSharedModule } from '../../shared';
import {
    DcDepartmentService,
    DcDepartmentPopupService,
    DcDepartmentComponent,
    DcDepartmentDetailComponent,
    DcDepartmentDialogComponent,
    DcDepartmentPopupComponent,
    DcDepartmentDeletePopupComponent,
    DcDepartmentDeleteDialogComponent,
    dcDepartmentRoute,
    dcDepartmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dcDepartmentRoute,
    ...dcDepartmentPopupRoute,
];

@NgModule({
    imports: [
        DataCenterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DcDepartmentComponent,
        DcDepartmentDetailComponent,
        DcDepartmentDialogComponent,
        DcDepartmentDeleteDialogComponent,
        DcDepartmentPopupComponent,
        DcDepartmentDeletePopupComponent,
    ],
    entryComponents: [
        DcDepartmentComponent,
        DcDepartmentDialogComponent,
        DcDepartmentPopupComponent,
        DcDepartmentDeleteDialogComponent,
        DcDepartmentDeletePopupComponent,
    ],
    providers: [
        DcDepartmentService,
        DcDepartmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataCenterDcDepartmentModule {}
