import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DcDepartment } from './dc-department.model';
import { DcDepartmentService } from './dc-department.service';

@Component({
    selector: 'jhi-dc-department-detail',
    templateUrl: './dc-department-detail.component.html'
})
export class DcDepartmentDetailComponent implements OnInit, OnDestroy {

    dcDepartment: DcDepartment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dcDepartmentService: DcDepartmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDcDepartments();
    }

    load(id) {
        this.dcDepartmentService.find(id)
            .subscribe((dcDepartmentResponse: HttpResponse<DcDepartment>) => {
                this.dcDepartment = dcDepartmentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDcDepartments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dcDepartmentListModification',
            (response) => this.load(this.dcDepartment.id)
        );
    }
}
