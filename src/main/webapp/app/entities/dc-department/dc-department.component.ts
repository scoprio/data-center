import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DcDepartment } from './dc-department.model';
import { DcDepartmentService } from './dc-department.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-dc-department',
    templateUrl: './dc-department.component.html'
})
export class DcDepartmentComponent implements OnInit, OnDestroy {
dcDepartments: DcDepartment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dcDepartmentService: DcDepartmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.dcDepartmentService.query().subscribe(
            (res: HttpResponse<DcDepartment[]>) => {
                this.dcDepartments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDcDepartments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DcDepartment) {
        return item.id;
    }
    registerChangeInDcDepartments() {
        this.eventSubscriber = this.eventManager.subscribe('dcDepartmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
