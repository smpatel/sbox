import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SboxApiModule } from './api/api.module';
import { SboxOperatorModule } from './operator/operator.module';
import { SboxCommissionGroupModule } from './commission-group/commission-group.module';
import { SboxUserAddonModule } from './user-addon/user-addon.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SboxApiModule,
        SboxOperatorModule,
        SboxCommissionGroupModule,
        SboxUserAddonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SboxEntityModule {}
