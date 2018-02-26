import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DataCenterDcDepartmentModule } from './dc-department/dc-department.module';
import { DataCenterDcMenuModule } from './dc-menu/dc-menu.module';
import { DataCenterDcRegionModule } from './dc-region/dc-region.module';
import { DataCenterDcAuthorityModule } from './dc-authority/dc-authority.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        DataCenterDcDepartmentModule,
        DataCenterDcMenuModule,
        DataCenterDcRegionModule,
        DataCenterDcAuthorityModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataCenterEntityModule {}
