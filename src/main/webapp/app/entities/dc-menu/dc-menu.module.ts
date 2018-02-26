import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataCenterSharedModule } from '../../shared';
import {
    DcMenuService,
    DcMenuPopupService,
    DcMenuComponent,
    DcMenuDetailComponent,
    DcMenuDialogComponent,
    DcMenuPopupComponent,
    DcMenuDeletePopupComponent,
    DcMenuDeleteDialogComponent,
    dcMenuRoute,
    dcMenuPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dcMenuRoute,
    ...dcMenuPopupRoute,
];

@NgModule({
    imports: [
        DataCenterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DcMenuComponent,
        DcMenuDetailComponent,
        DcMenuDialogComponent,
        DcMenuDeleteDialogComponent,
        DcMenuPopupComponent,
        DcMenuDeletePopupComponent,
    ],
    entryComponents: [
        DcMenuComponent,
        DcMenuDialogComponent,
        DcMenuPopupComponent,
        DcMenuDeleteDialogComponent,
        DcMenuDeletePopupComponent,
    ],
    providers: [
        DcMenuService,
        DcMenuPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataCenterDcMenuModule {}
