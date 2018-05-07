import { BaseEntity } from './../../shared';

export class CommissionGroup implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public commission?: number,
        public authority?: string,
        public operatorName?: string,
        public operatorId?: number,
    ) {
    }
}
