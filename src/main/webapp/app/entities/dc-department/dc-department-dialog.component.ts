import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DcDepartment } from './dc-department.model';
import { DcDepartmentPopupService } from './dc-department-popup.service';
import { DcDepartmentService } from './dc-department.service';
import { DcMenu, DcMenuService } from '../dc-menu';

@Component({
    selector: 'jhi-dc-department-dialog',
    templateUrl: './dc-department-dialog.component.html'
})
export class DcDepartmentDialogComponent implements OnInit {

    dcDepartment: DcDepartment;
    isSaving: boolean;

    dcmenus: DcMenu[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dcDepartmentService: DcDepartmentService,
        private dcMenuService: DcMenuService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dcMenuService.query()
            .subscribe((res: HttpResponse<DcMenu[]>) => { this.dcmenus = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dcDepartment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dcDepartmentService.update(this.dcDepartment));
        } else {
            this.subscribeToSaveResponse(
                this.dcDepartmentService.create(this.dcDepartment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DcDepartment>>) {
        result.subscribe((res: HttpResponse<DcDepartment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DcDepartment) {
        this.eventManager.broadcast({ name: 'dcDepartmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDcMenuById(index: number, item: DcMenu) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-dc-department-popup',
    template: ''
})
export class DcDepartmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dcDepartmentPopupService: DcDepartmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dcDepartmentPopupService
                    .open(DcDepartmentDialogComponent as Component, params['id']);
            } else {
                this.dcDepartmentPopupService
                    .open(DcDepartmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
