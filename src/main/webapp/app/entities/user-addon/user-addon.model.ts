import { BaseEntity } from './../../shared';

export class UserAddon implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public username?: string,
        public password?: string,
        public email?: string,
        public activated?: boolean,
        public parentUserId?: number,
        public securityPassword?: string,
        public authorizedMobile?: number,
        public secondaryMobile?: number,
        public accOpenFee?: number,
        public dailyRental?: number,
        public minimumBalance?: number,
        public flatCommission?: number,
        public apiUrl?: string,
        public apiResponseUrl?: string,
        public userLogin?: string,
        public userId?: number,
        public commissionGroupName?: string,
        public commissionGroupId?: number,
    ) {
        this.activated = false;
    }
}
