import { BaseEntity } from './../../shared';

export class DcRegion implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public regionName?: string,
        public regionCode?: string,
        public province?: string,
        public city?: string,
        public district?: string,
        public adcode?: string,
        public zipCode?: string,
        public level?: number,
        public parentId?: number,
        public dcAuthorities?: BaseEntity[],
    ) {
    }
}
