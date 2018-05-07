import { BaseEntity } from './../../shared';

export class Operator implements BaseEntity {
    constructor(
        public id?: number,
        public enable?: boolean,
        public name?: string,
        public normalCode?: string,
        public specialCode?: string,
        public firstApiName?: string,
        public firstApiId?: number,
        public secondApiName?: string,
        public secondApiId?: number,
        public thirdApiName?: string,
        public thirdApiId?: number,
    ) {
        this.enable = false;
    }
}
