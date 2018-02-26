import { BaseEntity } from './../../shared';

export class DcAuthority implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: number,
        public endDate?: number,
        public level?: number,
        public departmentId?: BaseEntity,
        public dcRegions?: BaseEntity[],
    ) {
    }
}
