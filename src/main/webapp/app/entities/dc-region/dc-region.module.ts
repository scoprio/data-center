import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataCenterSharedModule } from '../../shared';
import {
    DcRegionService,
    DcRegionPopupService,
    DcRegionComponent,
    DcRegionDetailComponent,
    DcRegionDialogComponent,
    DcRegionPopupComponent,
    DcRegionDeletePopupComponent,
    DcRegionDeleteDialogComponent,
    dcRegionRoute,
    dcRegionPopupRoute,
    DcRegionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dcRegionRoute,
    ...dcRegionPopupRoute,
];

@NgModule({
    imports: [
        DataCenterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DcRegionComponent,
        DcRegionDetailComponent,
        DcRegionDialogComponent,
        DcRegionDeleteDialogComponent,
        DcRegionPopupComponent,
        DcRegionDeletePopupComponent,
    ],
    entryComponents: [
        DcRegionComponent,
        DcRegionDialogComponent,
        DcRegionPopupComponent,
        DcRegionDeleteDialogComponent,
        DcRegionDeletePopupComponent,
    ],
    providers: [
        DcRegionService,
        DcRegionPopupService,
        DcRegionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataCenterDcRegionModule {}
