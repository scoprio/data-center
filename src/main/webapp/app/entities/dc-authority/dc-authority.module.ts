import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataCenterSharedModule } from '../../shared';
import {
    DcAuthorityService,
    DcAuthorityPopupService,
    DcAuthorityComponent,
    DcAuthorityDetailComponent,
    DcAuthorityDialogComponent,
    DcAuthorityPopupComponent,
    DcAuthorityDeletePopupComponent,
    DcAuthorityDeleteDialogComponent,
    dcAuthorityRoute,
    dcAuthorityPopupRoute,
    DcAuthorityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dcAuthorityRoute,
    ...dcAuthorityPopupRoute,
];

@NgModule({
    imports: [
        DataCenterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DcAuthorityComponent,
        DcAuthorityDetailComponent,
        DcAuthorityDialogComponent,
        DcAuthorityDeleteDialogComponent,
        DcAuthorityPopupComponent,
        DcAuthorityDeletePopupComponent,
    ],
    entryComponents: [
        DcAuthorityComponent,
        DcAuthorityDialogComponent,
        DcAuthorityPopupComponent,
        DcAuthorityDeleteDialogComponent,
        DcAuthorityDeletePopupComponent,
    ],
    providers: [
        DcAuthorityService,
        DcAuthorityPopupService,
        DcAuthorityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataCenterDcAuthorityModule {}
