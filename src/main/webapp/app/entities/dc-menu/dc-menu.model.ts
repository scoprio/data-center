import { BaseEntity } from './../../shared';

export class DcMenu implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public createTime?: number,
        public updateTime?: number,
        public isEnable?: boolean,
        public parentId?: number,
        public level?: number,
        public url?: string,
        public dcDepartments?: BaseEntity[],
    ) {
        this.isEnable = false;
    }
}
