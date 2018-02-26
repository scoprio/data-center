import { BaseEntity } from './../../shared';

export class DcDepartment implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public createTime?: number,
        public updateTime?: number,
        public isEnable?: boolean,
        public dcMenus?: BaseEntity[],
    ) {
        this.isEnable = false;
    }
}
