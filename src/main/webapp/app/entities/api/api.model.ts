import { BaseEntity } from './../../shared';

export class Api implements BaseEntity {
    constructor(
        public id?: number,
        public enable?: boolean,
        public name?: string,
        public rechargeUrl?: string,
        public balanceUrl?: string,
        public statusUrl?: string,
        public successCode?: string,
        public failCode?: string,
        public apiCommission?: number,
    ) {
        this.enable = false;
    }
}
